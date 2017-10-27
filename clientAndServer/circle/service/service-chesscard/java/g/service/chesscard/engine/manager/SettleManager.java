package g.service.chesscard.engine.manager;

import com.alibaba.fastjson.JSONArray;
import g.common.tool.DoubleTool;
import g.data.support.GeneratIdTool;
import g.model.bet.*;
import g.model.bet.po.Bet;
import g.model.bet.po.BetDetail;
import g.model.bet.po.IBetSettle;
import g.model.chesscard.mo.UserBet;
import g.model.chesscard.mo.UserBetResult;
import g.model.enums.ChessCardEnum;
import g.model.enums.GameModelCodeEnum;
import g.model.enums.GameTypeEnum;
import g.model.gameroom.JackpotAdd;
import g.model.room.po.RoomPool;
import g.service.chesscard.engine.model.*;
import g.model.match.po.Match;
import g.model.match.po.MatchResult;
import g.service.bet.BetService;
import g.service.bet.settle.AbstractSettleService;
import g.service.chesscard.engine.EngineConst;
import g.service.chesscard.engine.factory.IModelFactory;
import g.service.chesscard.engine.listener.MatchEvent;
import g.service.engine.evaluator.IEvaluator;
import g.service.engine.manager.*;
import g.service.match.IMatchResultService;
import g.service.match.IMatchService;
import org.soul.commons.collections.MapTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Created by Jason on 16/10/10.
 */
public abstract class SettleManager implements ISettleManager {
    private static final Log log = LogFactory.getLog(SettleManager.class);

    @Autowired
    private IMatchService matchService;

    @Autowired
    private IBetManager betManager;

    @Autowired
    private ISeatManager seatManager;

    @Autowired
    private IDeskManager deskManager;

    @Autowired
    private BetService betService;

    @Autowired
    private IModelFactory modelFactory;

    @Autowired
    @Qualifier("memorySettleService")
    private AbstractSettleService memorySettleService;

    @Autowired
    private BroadcastMessageManager broadcastMessageManager;

    public SettleResult newSettle(Integer deskId, Long matchId, String preCards) {
        SettleResult settleResult = new SettleResult();

        log.info("比赛结算:桌号:{0},赛事ID:{1},开始结算!", deskId, matchId);
        Match match = this.matchService.get(matchId);
        if (match == null) {
            log.error("比赛结算:桌号:{0},赛事ID:{1},没有此比赛,无法结算!", deskId, matchId);
            settleResult.setSettleResultEnum(SettleResultEnum.NOT_MATCH);
            return settleResult;
        }

        Desk desk = this.deskManager.byId(deskId);
        List<UserBet> userBetList = this.betManager.getUserBetList(deskId, matchId);
        Room room = desk.getRoom();
        String modelCode = room.getModelCode();
        GameModelCodeEnum gameModel = GameModelCodeEnum.valueOf(modelCode);

        IEvaluator bull1Evaluator = modelFactory.getEvaluator(gameModel);

        boolean isSystemDealer = desk.getDealerUserId() == null;
        RoomPool pool = room.getRoomPool();
        CardsHolder cardsHolder = bull1Evaluator.evaluate(userBetList, deskId, isSystemDealer, preCards, pool.getMaxLose());
        settleResult.setCardsHolder(cardsHolder);

        //更新赛事结果
        List<MatchResult> matchResults = this.updateMatchResult(match, cardsHolder);

        if (userBetList.size() == 0) {
            log.info("比赛结算:桌号:{0},赛事ID:{1},没有注单,无需结算!", deskId, matchId);
            settleResult.setSettleResultEnum(SettleResultEnum.NOT_BET);
            return settleResult;
        }

        List<IBetSettle> betSettleList = generateBetSettleList(desk, match, userBetList, isSystemDealer);
        //保存资金池变化
        JackpotAdd jackpotAdd = new JackpotAdd(room.getId(), 0, 0);
        jackpotAdd.setSingleFee((int)(long)pool.getMaxJackpotAmatch());//MaxJackpotAmatch,作为流水总额抽水百分比，0为0%。10为10%
        if(cardsHolder.getProfit() !=0 ){
            long diff = cardsHolder.getProfit();
            //超出池总限额
            long overflow = cardsHolder.getProfit() + pool.getJackpot() - pool.getMaxJackpotLimit();
            if(overflow < 0) overflow = 0;
            else diff -= overflow;
            jackpotAdd.set(diff, overflow);
            room.incrJackpot(diff);
        }
        //TODO:Double to Jason 注单结算需要与赛事的状态同一事务
        int[] rs = memorySettleService.settleByBatch(matchResults, betSettleList, jackpotAdd);

        List<UserBetResult> userBetResultList = genUserBetResultList(betSettleList, desk);

        //产生的信息处理，生成需要发送公告的消息
        settleResult.setSettleResultEnum(SettleResultEnum.SUCCESS);
        settleResult.setUserBetResultList(userBetResultList);
//        broadcastMessageManager.createMsgFromGameSettle(settleResult,room.getId());
        log.info("比赛结算:桌号:{0},赛事ID:{1},结算结束!", deskId, matchId);
        return settleResult;
    }

    /**
     * 生成用户投注结果列表
     *
     * @param betSettleList
     * @param desk
     * @return
     */
    private List<UserBetResult> genUserBetResultList(List<IBetSettle> betSettleList, Desk desk) {
        List<UserBetResult> userBetResultList = new ArrayList<>();

        List<Integer> userIds = new ArrayList<>();
        for (IBetSettle betSettle : betSettleList) {
            Integer userId = betSettle.getSysUserId();
            userIds.add(userId);
        }
        Map<String, String> seatUsers = seatManager.getSeatUser(desk.getId());
        Map<String, String> userSeats = MapTool.invertMap(seatUsers);
        Double incBankerCoin = 0.0;
        Map<Integer, List<IBetSettle>> betSettleMap = genBetSettleListByGroup(betSettleList);
        for (Map.Entry<Integer, List<IBetSettle>> entry : betSettleMap.entrySet()) {
            Integer userId = entry.getKey();
            List<IBetSettle> betList = entry.getValue();
            String seatNoStr = userSeats.get(String.valueOf(userId));
            Integer seatNo = StringTool.isNotBlank(seatNoStr) ? Integer.valueOf(seatNoStr) : 0;
            Double coin = 0d;
            Double incCoin = 0d;
            UserBetResult userBetResult = new UserBetResult();
            userBetResult.setUserId(userId);
            userBetResult.setSeatNo(seatNo);
            for (IBetSettle betSettle : betList) {
                Bet bet = (Bet) betSettle;
                coin = DoubleTool.add(coin, bet.getIncCoin());
                incCoin = DoubleTool.add(incCoin, bet.getProfitAmount());
                incBankerCoin = DoubleTool.add(incBankerCoin, bet.getIncBankerCoin());
            }
            userBetResult.setCoin(coin.longValue());
            userBetResult.setIncCoin(incCoin.longValue());
            userBetResultList.add(userBetResult);
        }
        UserBetResult bankerResult = new UserBetResult();
        bankerResult.setSeatNo(EngineConst.SEAT_NO_BANKER);
        bankerResult.setCoin(incBankerCoin.longValue());
        bankerResult.setIncCoin(incBankerCoin.longValue());
        Integer dealerId = desk.getDealerUserId();
        if (dealerId != null) {
            bankerResult.setUserId(dealerId);
        } else {
            bankerResult.setUserId(EngineConst.SEAT_NO_BANKER);
        }
        userBetResultList.add(bankerResult);

        return userBetResultList;
    }

    /**
     * TODO: Double to Jason
     * 1) 赛事的结算状态应该在看保存完所有注单后才能设置
     * 2) 赛事结果的保存需要使用批量
     * 更新赛事结果
     *
     * @param match
     * @param cardsHolder
     */
    private List<MatchResult> updateMatchResult(Match match, CardsHolder cardsHolder) {
        //更新赛事庄家结果
        BullCards bankCards = cardsHolder.getCards(CardsType.BANKER);
        int[] bankResult = bankCards.getCards();
        Integer bankPoint = bankCards.getCardsPoint();
        match.setResult(JSONArray.toJSONString(bankResult));
        match.setSettleStatus(BetSettleStatus.SETTLED.getCode());
        this.matchService.updateMatchResult(match);
        return getMatchResult(match, cardsHolder, bankResult, bankPoint);
    }

    /**
     * 投注列表按用户ID分组
     *
     * @param betSettleList
     * @return
     */
    private Map<Integer, List<IBetSettle>> genBetSettleListByGroup(List<IBetSettle> betSettleList) {
        Map<Integer, List<IBetSettle>> map = new HashMap<>();
        for (IBetSettle betSettle : betSettleList) {
            Integer userId = betSettle.getSysUserId();
            if (map.containsKey(userId)) {
                map.get(userId).add(betSettle);
            } else {
                List<IBetSettle> list = new ArrayList<>();
                list.add(betSettle);
                map.put(userId, list);
            }
        }
        return map;
    }

    /**
     * 组装投注列表
     *
     * @param desk
     * @param match
     * @param isSystemDealer
     * @return
     */
    private List<IBetSettle> generateBetSettleList(Desk desk, Match match, List<UserBet> userBetList, boolean isSystemDealer) {
        List<IBetSettle> betSettleList = new ArrayList<>();

        if (userBetList.size() == 0) {
            return betSettleList;
        }
        Long[] betIds = this.betService.getBetIds(userBetList.size());
        int i = 0;
        Date now = new Date();
        for (UserBet userBet : userBetList) {
            Integer userId = userBet.getUserId();
            Double coin = userBet.getCoin();
            String betType = userBet.getBetType();
            String betItem = userBet.getBetItem();

            String betNo = GeneratIdTool.genBetNo(userId);
            Bet bet = new Bet();
            bet.setId(betIds[i++]);
            bet.setSysUserId(userId);
            bet.setBallType(ChessCardEnum.DOU_NIU.getCode());
            bet.setRtype(GameTypeEnum.CHESS_CARD.getCode());
            bet.setBetNo(betNo);
            bet.setSingleAmount(coin);
            bet.setStatus(BetStatus.CONFIRMD.getCode());
            bet.setSettleStatus(BetSettleStatus.SETTLED.getCode());
            bet.setIsDeleted(false);
            bet.setBetTime(now);
            bet.setConfirmTime(now);
            bet.setSettleTime(now);
            //bet.setSave(!(isSystemDealer&&userBet.isAi()));//是AI并且系统庄时不保存
            bet.setSave(!(userBet.isAi()));//暂时设置系统庄也不保存AI投注记录


            BetDetail betDetail = new BetDetail();
            betDetail.setBetId(bet.getId());
            betDetail.setMatchId(match.getId());
            betDetail.setBetType(betType);
            betDetail.setIorField(betItem);

            List<BetDetail> betDetailList = new ArrayList<>();
            betDetailList.add(betDetail);
            bet.setBetDetailList(betDetailList);
            bet.setDealerUserId(desk.getDealerUserId());
            betSettleList.add(bet);
        }
        return betSettleList;
    }

    public abstract List<MatchResult> getMatchResult(Match match, CardsHolder cardsHolder, int[] bankResult, Integer bankPoint);

}