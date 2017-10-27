package g.service.chesscard.engine.manager;

import g.service.chesscard.engine.model.*;
import g.model.match.po.Match;
import g.service.chesscard.RedisKeyConst;
import g.service.chesscard.engine.EngineConst;
import g.service.chesscard.enums.TipEnum;
import g.service.chesscard.exception.ChessCardException;
import g.service.chesscard.netBeans.common.NbSelf;
import g.service.engine.manager.*;
import g.service.match.IMatchService;
import g.service.webSocket.context.WsSession;
import g.service.webSocket.context.WsSessionManager;
import org.soul.commons.cache.CacheKey;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.nosql.redis.JedisTemplate;
import redis.clients.jedis.Tuple;

import java.util.*;

/**
 * 庄家管理器
 * Created by Jason on 2016/11/7.
 */
public class DealerManager implements IDealerManager {

    private static final Log log = LogFactory.getLog(DealerManager.class);
    public static final int IS_DEALER = 2;
    public static final int NO_DEALER = 0;
    public static final int IN_DEALER_LIST = 1;

    private static long DEALER_COIN_MOD_VALUE = (long)Math.pow(10,13);

    @Autowired
    private JedisTemplate jedisTemplateGame;

    @Autowired
    private IPlayerManager playerManager;

    @Autowired
    private ISeatManager seatManager;

    @Autowired
    private IMatchService matchService;

    @Autowired
    private IBetManager betManager;

    @Autowired
    private IDeskManager deskManager;

    @Autowired
    private WsSessionManager wsSessionManager;

    @Override
    public List<GameDealer> getDealerList(Integer deskId) {
        String dealerCoinKey = CacheKey.getCacheKey(RedisKeyConst.DESK_DEALERS, RedisKeyConst.D, deskId);
        Set<Tuple> tupleSet = this.jedisTemplateGame.zrevrangeWithScores(dealerCoinKey, 0, -1);
        List<GameDealer> gameDealerList = new ArrayList<>();
        for (Tuple tuple : tupleSet) {
            String element = tuple.getElement();
            Double scoreDouble = tuple.getScore();
            //由于存储的是整万的值,取出来需要处理
            Long score = minusCoinSuffix(scoreDouble.longValue()) * EngineConst.DEALER_MIN_INCREMENT_COIN;

            GameDealer gameDealer = new GameDealer();
            gameDealer.setDeskId(deskId);
            gameDealer.setUserId(Integer.parseInt(element));
            gameDealer.setDealerCoin(score);
            gameDealerList.add(gameDealer);
        }
        return gameDealerList;
    }

    @Override
    public String applyDealer(GameDealer gameDealer) {
        Integer userId = gameDealer.getUserId();
        Integer deskId = gameDealer.getDeskId();
        Long dealerCoin = gameDealer.getDealerCoin();
        log.info("桌号:{0},用户:{1},上庄金币:{2},申请上庄!", deskId, userId, dealerCoin);
        String downDealerFlag = this.getDownDealerFlag(userId);
        if (StringTool.isNotEmpty(downDealerFlag)) {//还未下庄
            throw new ChessCardException(TipEnum.NOT_DO_DEALER);
        }
        Desk desk = deskManager.byId(deskId);
        //TODO: Double 需要判断是否在游戏中(考虑冻结金额)
        Long usableCoin;
        Player player = playerManager.get(userId);
        if (desk != null && !desk.isInMatch(userId)) {
            usableCoin = player.getCoin();
        } else {
            long betCoin = betManager.getUserBetCoin(deskId, userId);
            usableCoin = player.getCoin() - (betCoin * EngineConst.BULL_MAX_BEI - 1);
        }
        if (dealerCoin < 0) {
            log.info("桌号:{0},用户:{1},上庄金币:{2},用户余额:{3},上庄金额为非许可值!", deskId, userId, dealerCoin, usableCoin);
            throw new ChessCardException(TipEnum.DEALER_UP_NOT_PERMIT_COIN);
        }

        if (dealerCoin > usableCoin) {
            log.info("桌号:{0},用户:{1},上庄金币:{2},用户余额:{3},金币余额不足无法上庄!", deskId, userId, dealerCoin, usableCoin);
            // 2017.01.20 UPDATE BY WINKEY  BEGIN
            //throw new ChessCardException(TipEnum.BALANCE_LESS)
            throw new ChessCardException(TipEnum.BALANCE_LESS_UP_DEALER);
            // 2017.01.20 UPDATE BY WINKEY  END
        }

        Integer isDealer = this.isDealer(player);
        if (isDealer == 0) {
            log.info("桌号:{0},用户:{1},上庄金币:{2},用户是普通玩家!", deskId, userId, dealerCoin);
            Room room = desk.getRoom();
            long minDealerCoin = room.getDealerBlance();
            if (dealerCoin < minDealerCoin) {
                log.info("桌号:{0},用户:{1},上庄金币:{2},上庄最低限额:{3},上庄金币不满足条件!", deskId, userId, dealerCoin, minDealerCoin);
                throw new ChessCardException(TipEnum.MIN_LIMIT_DEALER);
            }
            addDealer(gameDealer);
        } else if (isDealer == 1) {
            log.info("桌号:{0},用户:{1},上庄金币:{2},用户已在上庄列表中!", deskId, userId, dealerCoin);
            updateDealerCoin(gameDealer);
        } else if (isDealer == 2) {
            log.info("桌号:{0},用户:{1},上庄金币:{2},用户已是庄家!", deskId, userId, dealerCoin);
            incDealerBalanceCoin(deskId, userId, dealerCoin);
        }

        updateUserByUpDealer(player, dealerCoin);
        return TipEnum.SUCCESS.getCode();
    }

    private void incDealerBalanceCoin(Integer deskId, Integer userId, Long coin) {
        jedisTemplateGame.hincrBy(
                CacheKey.getCacheKey(RedisKeyConst.DESK, RedisKeyConst.D, deskId),
                GameDesk.PROP_DEALER_COIN,
                coin
        );
        Player player = playerManager.get(userId);
        player.incrCoin(-coin);
    }

    @Override
    public Map upDealer(Integer deskId) {
        Map<String, Integer> resultMap = new HashMap<>();
        Desk desk = deskManager.byId(deskId);
        Integer dealerUserId = desk.getDealerUserId();
        Room room = desk.getRoom();
        room.setToContinueDealerCoin(0);//重置提示的续庄金额
        if (dealerUserId != null) {
            Player player = playerManager.get(dealerUserId);
            Long dealerCoin = desk.getDealerBalanceCoin() == null ? 0 : desk.getDealerBalanceCoin();
            long minDealerCoin = room.getDealerBlance();
            if (dealerCoin > minDealerCoin * ((float) room.getDealerBlanceQuit() / 100f)) {
                log.info("桌号:{0},原庄家:{1},金币余额还足够,继续坐庄!", deskId, dealerUserId);
                return null;
            }
            log.info("桌号:{0},原庄家:{1},下庄!", deskId, dealerUserId);
            handleDownDealer(player, desk);
            resultMap.put("down", dealerUserId);
        }

        Integer newDealerId = changeDealer(desk);
        resultMap.put("up", newDealerId);
        return resultMap;
    }
    @Override
    public String downDealer(Player player) {
        return downDealer(player, false);
    }




    /**
     * 玩家下庄
     * @param player
     * @param request 是否由玩家发起的请求
     * @return
     */
    @Override
    public String downDealer(Player player, boolean request) {
        Integer userId = player.getId();
        Desk desk = player.getDesk();
        Integer deskId = desk.getId();
        if(request){
            String downDealerFlag = this.getDownDealerFlag(userId);
            if (StringTool.isNotEmpty(downDealerFlag)) {
                throw new ChessCardException(TipEnum.DOWNING_DEALER_ACTION);
            }
        }
        if (desk == null) {
            throw new ChessCardException(TipEnum.SERVER_ERROR);
        }
        Integer isDealer = isDealer(player);
        if (isDealer == IS_DEALER) {
            if (!desk.isMatchRunning()) {
                handleDownDealer(player, desk);
            } else {
                log.info("用户:{0},已经是庄家,赛事已开始,记录下庄动作,等赛事结束后下庄!", userId);
                this.setDownDealerFlag(userId, deskId);
            }
            return TipEnum.DOWN_DEALER_FROM_ROOM.getCode();
        } else if (isDealer == IN_DEALER_LIST) {
            GameDealer gameDealer = this.getDealer(deskId, userId);
            player.incrCoin(gameDealer.getDealerCoin());
            log.info("桌号:{0},用户:{1},上庄剩余金币:{2},下庄成功,用户金币和可用金币加上上庄剩余金币!", player.getDeskId(), userId, gameDealer.getDealerCoin());
            deleteDealerInList(gameDealer);
            return TipEnum.DOWN_DEALER_FROM_DEALER_LIST.getCode();
        }
        return null;//没有上庄还要下庄,不进行响应
    }

    private void handleDownDealer(Player player, Desk desk) {
        if (player.getId().equals(desk.getDealerId())) {
            Long dealerBalanceCoin = desk.getDealerBalanceCoin();
            log.info("用户:{0},下庄,返还上庄冻结金币:{1}!", player.getId(),dealerBalanceCoin);
            player.incrCoin(dealerBalanceCoin);
            desk.resetDeskDealer();
            removeDownDealerFlag(player.getId());
            changeDealer(desk);
            //换庄,重新分配座位
            Integer roomId = player.getRoomId();
            deskManager.allocate(player, roomId);

            //下庄后，需要下发庄家的金币余额
            NbSelf self = new NbSelf();
            self.balance = player.getCoin();
            self.usableBalance = self.balance;
            WsSession wsSession = wsSessionManager.getUserSession(player.getId());
            wsSession.sendNetBean(self);
        }
    }

    /**
     * 获取上庄数据
     *
     * @param deskId
     * @param userId
     * @return
     */
    private GameDealer getDealer(Integer deskId, Integer userId) {
        Double scoreDouble = this.jedisTemplateGame.zscore(
                CacheKey.getCacheKey(RedisKeyConst.DESK_DEALERS, RedisKeyConst.D, deskId),
                userId.toString());
        Long score = minusCoinSuffix(scoreDouble.longValue()) * EngineConst.DEALER_MIN_INCREMENT_COIN;
        GameDealer gameDealer = new GameDealer();
        gameDealer.setDeskId(deskId);
        gameDealer.setUserId(userId);
        gameDealer.setDealerCoin(score);
        return gameDealer;
    }

    @Override
    public void dealerCoinIsWarning(Desk desk) {
        Integer dealerUserId = desk.getDealerUserId();
        if (dealerUserId == null) {
            return;
        }
        Long dealerCoinBalance = desk.getDealerBalanceCoin();
        Room room = desk.getRoom();
        long minDealerCoin = room.getDealerBlance();
        Player player = playerManager.get(dealerUserId);
        Long userCoinBalance = player.getCoin();
        List<GameDealer> gameDealers = this.getDealerList(desk.getId());
        long secondDealerCoin = 0;
        if (gameDealers.size() > 0) {
            secondDealerCoin = gameDealers.get(0).getDealerCoin();
        }
        if (dealerCoinBalance <= minDealerCoin * ((float) room.getDealerBlanceQuit() / 100f)) {
            //下庄
            long userKeepDealerCoin = dealerCoinBalance + userCoinBalance;
            if (userKeepDealerCoin <= minDealerCoin || userKeepDealerCoin < secondDealerCoin) {
                //setDownDealerFlag(gameDesk.getDealerUserId(),deskId); 金币不足在开局会自动下庄 参考 upDealer方法
                throw new ChessCardException(TipEnum.WARNING_DOWN_DEALER);
            }
            //续庄
            long toContinue = secondDealerCoin > 0 ? secondDealerCoin - dealerCoinBalance + EngineConst.DEALER_MIN_INCREMENT_COIN
                    : minDealerCoin - dealerCoinBalance;
            room.setToContinueDealerCoin(toContinue);
            throw new ChessCardException(TipEnum.WARNING_KEEP_DEALER, String.valueOf(toContinue));
        }
        if (dealerCoinBalance < minDealerCoin * ((float) room.getDealerBlanceTip() / 100f)) {
            //下庄警告
            throw new ChessCardException(TipEnum.WARNING_DEALER);
        }
    }

    @Override
    public String continueDealer(Player player, long coin) {
        Long usableCoin = player.getCoin(); // 可用余额
        Desk desk = player.getDesk();
        Integer userId = player.getId();
        Integer deskId = desk.getId();
        boolean isDealer = player.getId().equals(desk.getDealerUserId());
        if (coin < EngineConst.DEALER_MIN_INCREMENT_COIN || coin%EngineConst.DEALER_MIN_INCREMENT_COIN != 0) {
            long toContinue = desk.getRoom().getToContinueDealerCoin();
            if(isDealer && toContinue != 0 && toContinue == coin){//被要求续庄的庄家
            }else{
                log.info("桌号:{0},用户:{1},上庄金币:{2},用户余额:{3},续庄金额为非许可值!", deskId, userId, coin, usableCoin);
                throw new ChessCardException(TipEnum.DEALER_KEEP_NOT_PERMIT_COIN);
            }
        }

        if (isDealer) {
            String downDealerFlag = this.getDownDealerFlag(userId);
            if (StringTool.isNotEmpty(downDealerFlag)) {//还未下庄
                throw new ChessCardException(TipEnum.DOWNING_DEALER_ACTION);
            }
            //user在庄
            GameDealer firstDealer = getFirstDealer(player.getId());
            Long firstDealerCoin = 0l;
            if (firstDealer != null) {
                firstDealerCoin = firstDealer.getDealerCoin();
            } else {
                Room room = desk.getRoom();
                firstDealerCoin = Long.valueOf(room.getDealerBlance());

            }
            Long dealerCoin = firstDealerCoin - desk.getDealerBalanceCoin();//需要上庄的增量金币
            if (coin > usableCoin || usableCoin < dealerCoin || coin < dealerCoin) {
//            downDealer(userId);
                // 2017.01.20 UPDATE BY WINKEY BEGIN
                // throw new ChessCardException(TipEnum.BALANCE_LESS);
                throw new ChessCardException(TipEnum.BALANCE_LESS_KEEP_DEALER);
                // 2017.01.20 UPDATE BY WINKEY END
            }

            GameDealer gameDealer = new GameDealer();
            gameDealer.setUserId(player.getId());
            gameDealer.setDeskId(desk.getId());
            gameDealer.setDealerCoin(coin);
            updateUserByUpDealer(player,coin);
            updateGameDeskByUpDealer(desk, gameDealer);

        } else {
            //上庄列表续庄
            if (coin > usableCoin) {
                // 2017.01.20 UPDATE BY WINKEY BEGIN
                // throw new ChessCardException(TipEnum.BALANCE_LESS);
                throw new ChessCardException(TipEnum.BALANCE_LESS_KEEP_DEALER);
                // 2017.01.20 UPDATE BY WINKEY END
            }
            GameDealer gameDealer = new GameDealer();
            gameDealer.setUserId(userId);
            gameDealer.setDeskId(deskId);
            gameDealer.setDealerCoin(coin);
            updateDealerCoin(gameDealer);
            updateUserByUpDealer(player, coin);
        }

        return TipEnum.SUCCESS_KEEP_DEALER.getCode();
    }

    @Override
    public Integer isDealer(Player player) {
        Integer userId = player.getId();
        Integer deskId = player.getDeskId();
        if (deskId == null) {
            String downDealerFlag = this.getDownDealerFlag(userId);
            if (StringTool.isNotEmpty(downDealerFlag)) {
                return IS_DEALER;
            } else {
                return NO_DEALER;
            }
        }
        Desk desk = player.getDesk();
        Integer dealerUserId = desk.getDealerUserId();
        if (userId.equals(dealerUserId)) {
            return IS_DEALER;
        }
        Boolean existsDealer = desk.existsDealerList(userId);
        if (existsDealer) {
            return IN_DEALER_LIST;
        }
        return NO_DEALER;
    }

    /**
     * 添加上庄列表
     *
     * @param gameDealer
     * @return
     */
    private void addDealer(GameDealer gameDealer) {
        Integer deskId = gameDealer.getDeskId();
        long coin = gameDealer.getDealerCoin() / EngineConst.DEALER_MIN_INCREMENT_COIN; //获取整除数
        coin = addCoinSuffix(coin);
        this.jedisTemplateGame.zadd(CacheKey.getCacheKey(RedisKeyConst.DESK_DEALERS, RedisKeyConst.D, deskId),
                coin,
                "" + gameDealer.getUserId());
        log.info("桌号:{0},用户:{1},上庄金币:{2},添加上庄列表!", deskId, gameDealer.getUserId(), gameDealer.getDealerCoin());
    }

    /**
     * 以下处理方式,为了解决:同一局中多个人请求上庄时,金额相同时的排序问题
     * warning: 庄家列表的金币设置时如下处理:
     *      进入列表前:   1) 金币 / 10000                          注: 存放X万 如: 400000 -->只存放--> 40
     *                  2) * 10 ^ 13                             注: 后面10 ^ 13位为了时间戳
     *                  3) 再加上 (10 ^ 13 - 1) - (时间戳:long)    注: 为了越早提交的请求 score分数越大
     * @param coin
     * @return
     */
    protected Long addCoinSuffix(Long coin) {
        long mod_max = DEALER_COIN_MOD_VALUE - 1;
        long cur = System.currentTimeMillis();
        return coin * DEALER_COIN_MOD_VALUE + mod_max - cur;
    }

    /**
     * warning: 庄家列表的金币获取时如下处理:
     *      取出列表前:   1) 金币 / 10 ^ 13                        注: 去除后面的时间戳
     *                  2) 金币 * 10000                          注: 提取时还原原来的X万 如: 40 --> 400000
     * @param coin
     * @return
     */
    protected long minusCoinSuffix(long coin){
        return coin / DEALER_COIN_MOD_VALUE;
    }

    /**
     * warning: 庄家续庄时，后缀不能添加时间
     * @param coin
     * @return
     */
    protected Long addCoinSuffixWithoutTime(Long coin) {
        return coin * DEALER_COIN_MOD_VALUE;
    }

    /**
     * 删除上庄列表
     *
     * @param gameDealer
     * @return
     */
    private void deleteDealerInList(GameDealer gameDealer) {
        Integer userId = gameDealer.getUserId();
        Integer deskId = gameDealer.getDeskId();
        this.jedisTemplateGame.zrem(
                CacheKey.getCacheKey(RedisKeyConst.DESK_DEALERS, RedisKeyConst.D, deskId),
                userId.toString());
        log.info("桌号:{0},用户:{1},上庄金币:{2},删除上庄列表!", deskId, gameDealer.getUserId(), gameDealer.getDealerCoin());
    }

    /**
     * 获取上庄列表金币最高
     *
     * @return
     */
    private GameDealer getFirstDealer(Integer deskId) {
        String dealerCoinKey = CacheKey.getCacheKey(RedisKeyConst.DESK_DEALERS, RedisKeyConst.D, deskId);
        Set<Tuple> tupleSet = this.jedisTemplateGame.zrevrangeWithScores(dealerCoinKey, 0, 0);
        GameDealer gameDealer = null;
        if (tupleSet.size() == 0) {
            return gameDealer;
        }
        gameDealer = new GameDealer();
        Tuple first = tupleSet.iterator().next();
        String element = first.getElement();
        Double scoreDouble = first.getScore();
        Long score = minusCoinSuffix(scoreDouble.longValue()) * EngineConst.DEALER_MIN_INCREMENT_COIN;
        gameDealer.setDeskId(deskId);
        gameDealer.setUserId(Integer.parseInt(element));
        gameDealer.setDealerCoin(score);
        log.info("桌号:{0},获取上庄列表中金币最高的,用户:{1},上庄金币:{2}", deskId, gameDealer.getUserId(), gameDealer.getDealerCoin());
        return gameDealer;
    }

    /**
     * 修改上庄金额
     *
     * @param gameDealer
     */
    private void updateDealerCoin(GameDealer gameDealer) {
        Integer deskId = gameDealer.getDeskId();
        long coin = gameDealer.getDealerCoin() / EngineConst.DEALER_MIN_INCREMENT_COIN;
        coin = addCoinSuffixWithoutTime(coin);
        this.jedisTemplateGame.zincrby(
                CacheKey.getCacheKey(RedisKeyConst.DESK_DEALERS, RedisKeyConst.D, deskId),
                coin,
                gameDealer.getUserId().toString());
        log.info("桌号:{0},用户:{1},上庄金币:{2},修改上庄金币!", deskId, gameDealer.getUserId(), gameDealer.getDealerCoin());
    }

    /**
     * 修改用户金额和可用金额---扣除上庄金额
     */
    private void updateUserByUpDealer(Player player, Long coin) {
        Integer userId = player.getId();
        Integer deskId = player.getDeskId();
        player.incrCoin(-coin);
        log.info("桌号:{0},用户:{1},上庄金币:{2},上庄成功,用户金币和可用金币扣除上庄金币!", deskId, userId, coin);
    }



    /**
     * 修改桌子对象中庄家信息--上庄
     *
     * @param desk
     * @param gameDealer
     */
    private void updateGameDeskByUpDealer(Desk desk, GameDealer gameDealer) {
        desk.updateDeskDealer(gameDealer.getUserId(), gameDealer.getDealerCoin());
        log.info("桌号:{0},用户:{1},上庄金币:{2},用户上庄时修改桌子对象中庄家信息!", desk.getId(), gameDealer.getUserId(), gameDealer.getDealerCoin());
    }


    /**
     * 变更庄家
     *
     * @param desk
     */
    private Integer changeDealer(Desk desk) {
        GameDealer gameDealer = getFirstDealer(desk.getId());
        if (gameDealer != null) {
            log.info("桌号:{0},用户:{1},变更庄家!", gameDealer.getDeskId(), gameDealer.getUserId());
            desk.updateDeskDealer(gameDealer.getUserId(), gameDealer.getDealerCoin());
            updateMatchDealer(gameDealer);
            deleteDealerInList(gameDealer);

            GameDeskSeat gameDeskSeat = new GameDeskSeat();
            gameDeskSeat.setGameDeskId(gameDealer.getDeskId());
            gameDeskSeat.setUserId(gameDealer.getUserId());
            this.seatManager.outSeat(gameDeskSeat);
            return gameDealer.getUserId();
        }
        return null;
    }

    /**
     * 修改当前赛事中的庄家
     *
     * @param gameDealer
     */
    private void updateMatchDealer(GameDealer gameDealer) {
        Integer deskId = gameDealer.getDeskId();
        Desk desk = deskManager.byId(deskId);
        Integer dealerUserId = gameDealer.getUserId();
        Long matchId = desk.getMatch().getId();
        Match match = new Match();
        match.setId(matchId);
        match.setDealerUserId(dealerUserId);
        this.matchService.updateMatchDealer(match);
    }


    //TODO: Double Notice: 这些标识转到到PlayerPositionManager begin
    @Override
    public void setDownDealerFlag(Integer userId, Integer deskId) {
        this.jedisTemplateGame.set(
                CacheKey.getCacheKey(RedisKeyConst.DESK_DEALER_DOWN_FLAG, RedisKeyConst.U, userId),
                String.valueOf(deskId));
    }

    @Override
    public String getDownDealerFlag(Integer userId) {
        String flag = this.jedisTemplateGame.get(
                CacheKey.getCacheKey(RedisKeyConst.DESK_DEALER_DOWN_FLAG, RedisKeyConst.U, userId)
        );
        return flag;
    }

    /**
     * 删除下庄标识
     * @param userId
     */
    private void removeDownDealerFlag(Integer userId) {
        this.jedisTemplateGame.del(
                CacheKey.getCacheKey(RedisKeyConst.DESK_DEALER_DOWN_FLAG, RedisKeyConst.U, userId)
        );
    }
    //TODO: Double Notice: 这些标识转到到PlayerPositionManager begin
}
