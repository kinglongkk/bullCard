package g.service.bet.settle;

import g.data.bet.IBetDao;
import g.data.bet.BetMapper;
import g.data.bet.VBetSettleMapper;
import g.data.match.MatchMapper;
import g.data.match.MatchResultMapper;
import g.model.bet.BetSettleStatus;
import g.model.bet.po.VBetSettle;
import g.model.bet.vo.VBetSettleListVo;
import g.model.match.po.Match;
import g.model.match.po.MatchResult;
import g.model.match.vo.MatchVo;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.Paging;
import org.soul.commons.query.sort.Order;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by longer on 5/18/16.
 * 结算服务(基于数据库注单)
 */
public class DatabaseSettleService  extends AbstractSettleService{

    private static final Log log = LogFactory.getLog(DatabaseSettleService.class);

    /**
     * 批次大小
     */
    private static final int BATCH_SIZE = 5000;


    @Autowired
    private BetMapper betMapper;

    @Autowired
    private MatchMapper matchMapper;

    @Autowired
    private MatchResultMapper matchResultMapper;

    @Autowired
    private VBetSettleMapper vBetSettleMapper;

    protected void settleByMatchId(Long matchId,boolean isReSettle) {
        Match match = matchMapper.get(matchId);
        List<MatchResult> matchResults = hasMatchResult(matchId);
        if (matchResults == null) return;

        VBetSettleListVo vBetSettleListVo = getCriteriaCondition(match,isReSettle ? BetSettleStatus.SETTLED : BetSettleStatus.UN_SETTLE);
        Criteria beSettle = vBetSettleListVo.getQuery().byBallTypeAndMatchId();
        long count = vBetSettleMapper.count(beSettle);

        if (count <= 0) {
            changeSettleStatus(match);
            return;
        }

        Paging paging = vBetSettleListVo.getPaging();
        paging.setTotalCount(count);
        paging.setPageSize(BATCH_SIZE);
        paging.cal();

        int batchNumber = 1;
        log.info("结算开始,比赛ID:{0},总记录数:{1},总批次:{2}",matchId,count,paging.getLastPageNumber());
        while (batchNumber <= paging.getLastPageNumber()) {
            List<VBetSettle> betSettles = vBetSettleMapper.pagingSearch(beSettle,batchNumber, BATCH_SIZE,
                    Order.asc(VBetSettle.PROP_BET_DETAIL_ID));
            int[] rs = settleByBatch(matchResults, betSettles,isReSettle, null);
            log.info("结算完成,比赛ID:{0},批次:{1},更新记录数:{2}",matchId,batchNumber,rs.length);
            batchNumber++;
        }
        log.info("结算结束,比赛ID:{0},总记录数:{1},总批次:{2}",matchId,count,paging.getLastPageNumber());

        count = vBetSettleMapper.count(beSettle);
        if (count <= 0) {
            changeSettleStatus(match);
        }
    }

    /**
     * 獲取条件
     * @param match
     * @param betSettleStatus
     * @return
     */
    private VBetSettleListVo getCriteriaCondition(Match match, BetSettleStatus betSettleStatus) {
        VBetSettleListVo vBetSettleListVo = new VBetSettleListVo();
        vBetSettleListVo.getSearch().setMatchId(match.getId());
        vBetSettleListVo.getSearch().setBallType(match.getBallType());
        vBetSettleListVo.getSearch().setSettleStatus(betSettleStatus.getCode());
        return vBetSettleListVo;
    }

    protected void cancelByMatchId(Long matchId) {
        Match match = matchMapper.get(matchId);
        List<MatchResult> matchResults = hasMatchResult(matchId);
        if (matchResults == null) return;


        VBetSettleListVo vBetSettleListVo = new VBetSettleListVo();
        vBetSettleListVo.getSearch().setMatchId(matchId);
        Criteria beSettle = vBetSettleListVo.getQuery().byMatchIdAndUnCancel();
        long count = vBetSettleMapper.count(beSettle);

        if (count <= 0) {
            changeIsCancel(match);
            return;
        }

        Paging paging = vBetSettleListVo.getPaging();
        paging.setTotalCount(count);
        paging.setPageSize(BATCH_SIZE);
        paging.cal();

        int batchNumber = 1;
        log.info("取消开始,比赛ID:{0},总记录数:{1},总批次:{2}",matchId,count,paging.getLastPageNumber());
        while (batchNumber <= paging.getLastPageNumber()) {
            List<VBetSettle> betSettles = vBetSettleMapper.pagingSearch(beSettle,batchNumber, BATCH_SIZE,
                    Order.asc(VBetSettle.PROP_BET_DETAIL_ID));
            int[] rs = betDao.betCancel(betSettles);
            log.info("取消完成,比赛ID:{0},批次:{1},更新记录数:{2}",matchId,batchNumber,rs.length);
            batchNumber++;
        }
        log.info("取消结束,比赛ID:{0},总记录数:{1},总批次:{2}",matchId,count,paging.getLastPageNumber());

        count = vBetSettleMapper.count(beSettle);
        if (count <= 0) {
            changeIsCancel(match);
        }
    }

    private void changeIsCancel(Match match) {
        log.warn("比赛结算:赛事ID:{0},还未有投注记录,无需结算!",match.getId());
        match.setIsCancel(true);
        MatchVo matchVo = new MatchVo();
        matchVo.setResult(match);
        matchVo.getSearch().setIsCancel(false);
        matchMapper.toCanceled(matchVo);
    }

}
