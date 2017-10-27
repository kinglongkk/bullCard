package g.service.bet.settle;

import g.data.bet.IBetDao;
import g.data.bet.BetMapper;
import g.data.bet.VBetSettleMapper;
import g.data.match.MatchMapper;
import g.data.match.MatchResultMapper;
import g.model.bet.BetResultEnum;
import g.model.bet.BetSettleStatus;
import g.model.bet.po.IBetSettle;
import g.model.bet.po.VBetSettle;
import g.model.bet.vo.BetVo;
import g.model.bet.vo.VBetSettleListVo;
import g.model.gameroom.JackpotAdd;
import g.model.match.po.Match;
import g.model.match.po.MatchResult;
import g.model.match.vo.MatchResultListVo;
import g.model.match.vo.MatchVo;
import g.service.bet.ISettleService;
import g.service.bet.settle.strategy.AbstractSettleStrategy;
import g.service.bet.settle.strategy.SettleStrategyContext;
import org.soul.commons.collections.CollectionTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/10/17.
 * 结算抽象类
 */
public abstract class AbstractSettleService implements ISettleService {
    private static final Log log = LogFactory.getLog(AbstractSettleService.class);

    @Autowired(required = false)
    private SettleStrategyContext strategyContext;

    protected IBetDao betDao;

    @Autowired
    private MatchMapper matchMapper;

    @Autowired
    private MatchResultMapper matchResultMapper;

    @Autowired
    private VBetSettleMapper vBetSettleMapper;

    @Override
    public void settle(Long matchId) {
        settleByMatchId(matchId,false);
    }

    @Override
    public int[] batchSettle(List<BetVo> betVos, boolean isReSettle, JackpotAdd jackpotAdd) {
        return betDao.batchSettle(betVos,isReSettle, jackpotAdd);
    }

    protected void settleByMatchId(Long matchId,boolean isReSettle) {
        return;
    }


    protected void changeSettleStatus(Match match) {
        log.warn("比赛结算:赛事ID:{0},还未有投注记录,无需结算!",match.getId());
        //TODO: 注意,如果赛事进行中可以进行部份结算,则此方法有问题
        match.setSettleStatus(BetSettleStatus.SETTLED.getCode());
        MatchVo matchVo = new MatchVo();
        matchVo.setResult(match);
        matchVo.getSearch().setSettleStatus(BetSettleStatus.UN_SETTLE.getCode());
        matchMapper.toSettled(matchVo);
    }

    /**
     * 是否有賽事結果
     * @param matchId
     * @return
     */
    protected List<MatchResult> hasMatchResult(Long matchId) {
        MatchResultListVo vo = new MatchResultListVo();
        vo.getSearch().setMatchId(matchId);
        List<MatchResult> matchResults = matchResultMapper.search(vo.getQuery().byMatchId());
        if (CollectionTool.isEmpty(matchResults)){
            log.warn("比赛结算:赛事ID:{0},还未有比赛结果,无法结算!",matchId);
            return null;
        }
        return matchResults;
    }

    @Override
    public void cancel(Long matchId) {
        cancelByMatchId(matchId);
    }

    protected void cancelByMatchId(Long matchId) {
        return;
    }


    @Override
    public void reSettle(Long matchId) {
        settleByMatchId(matchId,true);
    }

    /**
     * 分批结算
     * @param matchResults
     * @param betSettles
     */
    @Override
    public int[] settleByBatch(List<MatchResult> matchResults,  List<? extends IBetSettle> betSettles, JackpotAdd jackpotAdd) {
        return settleByBatch(matchResults,betSettles,false, jackpotAdd);
    }

    /**
     * 分批结算,或重结
     * @param matchResults
     * @param betSettles
     * @param isReSettle
     * @return
     */
    protected int[] settleByBatch(List<MatchResult> matchResults, List<? extends IBetSettle> betSettles, boolean isReSettle, JackpotAdd jackpotAdd) {
        List<BetVo> bets = new ArrayList<>(betSettles.size());
        long waterSum = 0;
        long singleSum = 0;//流水
        for (IBetSettle betSettle : betSettles) {
            AbstractSettleStrategy strategy = strategyContext.decide(betSettle);
            BetResultEnum betResultEnum = strategy.judge(betSettle,matchResults);
            betSettle.setResult(betResultEnum.getCode());
            BetVo betVo = strategy.settle(betSettle, betResultEnum, isReSettle);
            bets.add(betVo);
            if(betSettle.isSave()){
                waterSum= waterSum+betSettle.getWaterAmount();
                singleSum = singleSum + ((long)(double) betSettle.getSingleAmount());
            }
        }
        singleSum = singleSum*jackpotAdd.getSingleFee()/100;
        jackpotAdd.setJackpotAdd(jackpotAdd.getJackpotAdd()-singleSum);
        jackpotAdd.setJackpotOverflowAdd(jackpotAdd.getJackpotOverflowAdd()+waterSum+singleSum);//抽水计入溢出池
        if (CollectionTool.isEmpty(bets)){
            return new int[0];
        }


        preSaveSettleByBatch(bets,betSettles);
        return betDao.batchSettle(bets,isReSettle, jackpotAdd);
    }

    protected void preSaveSettleByBatch(List<BetVo> bets,List<? extends IBetSettle> betSettles){}


    @Override
    public int[] reSettleByBatch(List<MatchResult> matchResults, List<? extends IBetSettle> betSettles, JackpotAdd jackpotAdd) {
        return settleByBatch(matchResults,betSettles,true, jackpotAdd);
    }

    @Override
    public int[] betCancel(List<Long> betIds) {
        if (CollectionTool.isEmpty(betIds)){
            return new int[0];
        }

        VBetSettleListVo listVo = new VBetSettleListVo();
        listVo.getSearch().setIds(betIds);
        List<VBetSettle> bets = vBetSettleMapper.search(listVo.getQuery().byIdsAndUnCancel());
        return betDao.betCancel(bets);
    }

    @Override
    public List<VBetSettle> groupBySettleState(Long matchId){
        return vBetSettleMapper.groupBySettleState(matchId);
    }

    public void setBetDao(IBetDao betDao) {
        this.betDao = betDao;
    }
}
