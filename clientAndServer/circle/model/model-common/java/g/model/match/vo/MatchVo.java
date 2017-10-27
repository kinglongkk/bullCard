package g.model.match.vo;

import g.model.match.po.Match;
import g.model.match.po.MatchResultInfo;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.Criterion;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.match.po.MatchBetCount;
import g.model.match.so.MatchSo;

import java.util.List;


/**
 * 值对象
 *
 * @author longer
 * @time May 10, 2016 8:14:28 PM
 */
//region your codes 1
public class MatchVo extends BaseObjectVo<Match, MatchSo, MatchVo.MatchQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 7279699893342295679L;
    //endregion your codes 5

    /**
     *  查询逻辑
     */
    public static class MatchQuery extends AbstractQuery<MatchSo> {

        //region your codes 6
        private static final long serialVersionUID = 1370537755102276727L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            return Criteria.add(Match.PROP_CODE,Operator.EQ,searchObject.getCode());
            //endregion your codes 2
        }



        //region your codes 3

        public Criterion[] byCode(){
            return new Criterion[]{
                    new Criterion(Match.PROP_CODE,Operator.EQ,searchObject.getCode())
            };
        }

        public  Criteria byCode2() {
            return Criteria.add(Match.PROP_CODE, Operator.EQ,this.searchObject.getCode());
        }

        public Criteria endTimeIsNull() {
            return Criteria.add(Match.PROP_END_TIME, Operator.IS_NULL, null);
        }
        //endregion your codes 3

    }

    //region your codes 4

    /**
     * 原来的状态
     */
    private String statusOld;

    public String getStatusOld() {
        return statusOld;
    }

    public void setStatusOld(String statusOld) {
        this.statusOld = statusOld;
    }

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