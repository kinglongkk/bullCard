package g.model.match.vo;

import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.match.po.MatchResult;
import g.model.match.so.MatchResultSo;
import g.model.player.MatchResultData;

import java.util.List;


/**
 * 比赛结果表列表页值对象
 *
 * @author longer
 * @time May 17, 2016 4:38:24 PM
 */
//region your codes 1
public class MatchResultListVo extends BaseListVo<MatchResult, MatchResultSo, MatchResultListVo.MatchResultQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -7637378368154080352L;


    private List<MatchResultData> data;
    //endregion your codes 5

    /**
     *  比赛结果表列表查询逻辑
     */
    public static class MatchResultQuery extends AbstractQuery<MatchResultSo> {

        //region your codes 6
        private static final long serialVersionUID = 8494251130675486548L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            return null;
            //endregion your codes 2
        }


        //region your codes 3

        public Criteria byMatchId() {
            return Criteria.add(MatchResult.PROP_MATCH_ID, Operator.EQ,this.searchObject.getMatchId());
        }

        public Criteria inMatchIds() {
            return Criteria.add(MatchResult.PROP_MATCH_ID, Operator.IN,this.searchObject.getMatchId());
        }

        //endregion your codes 3
    }

    //region your codes 4

    public List<MatchResultData> getData() {
        return data;
    }

    public void setData(List<MatchResultData> data) {
        this.data = data;
    }


    //endregion your codes 4

}