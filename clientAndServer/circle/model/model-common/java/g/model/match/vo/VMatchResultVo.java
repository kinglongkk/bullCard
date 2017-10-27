package g.model.match.vo;

import g.model.match.po.MatchBetCount;
import g.model.match.po.MatchResultInfo;
import g.model.match.po.VMatchResult;
import g.model.match.so.VMatchResultSo;
import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;

import java.util.List;


/**
 * 值对象
 *
 * @author longer
 * @time May 10, 2016 8:14:28 PM
 */
//region your codes 1
public class VMatchResultVo extends BaseObjectVo<VMatchResult, VMatchResultSo, VMatchResultVo.MatchQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 7279699893342295679L;
    //endregion your codes 5

    /**
     *  查询逻辑
     */
    public static class MatchQuery extends AbstractQuery<VMatchResultSo> {

        //region your codes 6
        private static final long serialVersionUID = 1370537755102276727L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            return null;
            //endregion your codes 2
        }

        //region your codes 3

        //endregion your codes 3

    }

    //region your codes 4
    private List<MatchBetCount> matchBetCounts;

    private MatchResultInfo matchResultInfo;

    public List<MatchBetCount> getMatchBetCounts() {
        return matchBetCounts;
    }

    public void setMatchBetCounts(List<MatchBetCount> matchBetCounts) {
        this.matchBetCounts = matchBetCounts;
    }

    public MatchResultInfo getMatchResultInfo() {
        return matchResultInfo;
    }

    public void setMatchResultInfo(MatchResultInfo matchResultInfo) {
        this.matchResultInfo = matchResultInfo;
    }
    //endregion your codes 4

}