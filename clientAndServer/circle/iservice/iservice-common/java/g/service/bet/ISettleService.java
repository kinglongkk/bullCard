package g.service.bet;

import g.model.bet.po.IBetSettle;
import g.model.bet.po.VBetSettle;
import g.model.bet.vo.BetVo;
import g.model.gameroom.JackpotAdd;
import g.model.match.po.MatchResult;

import java.util.List;

/**
 * Created by longer on 5/19/16.
 * 结算服务接口
 */
public interface ISettleService {

    /**
     * 结算:通过比赛ID
     * @param matchId
     */
    void settle(Long matchId);

    /**
     * 结算: 保存结算注单
     * @param betVos
     * @param isReSettle
     * @return
     */
    int[] batchSettle(List<BetVo> betVos, boolean isReSettle, JackpotAdd jackpotAdd);

    /**
     * 取消:通过比赛ID
     * @param matchId
     */
    void cancel(Long matchId);

    /**
     * 重新结算:通过比赛ID
     * @param matchId
     */
    void reSettle(Long matchId);

    /**
     * 结算:批量
     * @param matchResults
     * @param betSettles
     */
    int[] settleByBatch(List<MatchResult> matchResults, List<? extends IBetSettle> betSettles, JackpotAdd jackpotAdd);

    /**
     * 重新结算:批量
     * @param matchResults
     * @param betSettles
     * @return
     */
    int[] reSettleByBatch(List<MatchResult> matchResults, List<? extends IBetSettle> betSettles, JackpotAdd jackpotAdd);


    /**
     * 注单列表
     * @param betIds
     * @return
     */
    int[] betCancel(List<Long> betIds);

    /**
     * 统计:统计某场赛事,结算状态
     * @param matchId
     * @return
     */
    List<VBetSettle> groupBySettleState(Long matchId);
}
