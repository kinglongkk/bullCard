package g.data.match;

import g.model.match.po.MatchResult;
import g.model.match.po.MatchResultInfo;
import g.model.match.po.MatchBetCount;
import org.soul.data.rdb.mybatis.IBaseMapper;
import g.model.match.so.MatchResultSo;

import java.util.List;


/**
 * 比赛结果表数据访问对象
 *
 * @author longer
 * @time May 17, 2016 4:38:24 PM
 */
//region your codes 1
public interface MatchResultMapper extends IBaseMapper<MatchResult, Long> {
//endregion your codes 1

    //region your codes 2
    List<MatchBetCount> countFtMatchBet(MatchResultSo so);

    MatchResultInfo queryFtMatchResult(MatchResultSo so);

    List<MatchResult> matchResultGroupMatchId(MatchResultSo so);

    /**
     * 保存结果,如果未有结果
     * @return
     */
    int saveResultWhenNoResulted(MatchResult matchResult);

    List<MatchResult> getMatchResultListByMatchId(Long matchId);

    //endregion your codes 2

}