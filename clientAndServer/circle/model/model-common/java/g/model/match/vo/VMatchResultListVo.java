package g.model.match.vo;

import g.model.match.po.Match;
import g.model.match.po.VMatchResult;
import g.model.match.so.VMatchResultSo;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Direction;
import org.soul.commons.query.sort.Sort;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;


/**
 * 列表页值对象
 *
 * @author longer
 * @time May 10, 2016 8:14:28 PM
 */
//region your codes 1
public class VMatchResultListVo extends BaseListVo<VMatchResult, VMatchResultSo, VMatchResultListVo.MatchQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -4027263221867874090L;

    private String newResult;
    //endregion your codes 5

    /**
     *  列表查询逻辑
     */
    public static class MatchQuery extends AbstractQuery<VMatchResultSo> {

        //region your codes 6
        private static final long serialVersionUID = -7101964510857893341L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            Criteria criteria=Criteria.add(Match.PROP_BALL_TYPE,Operator.EQ,this.searchObject.getBallType())
                    .addAnd(Match.PROP_CODE,Operator.LIKE,searchObject.getCode())
                    .addAnd(Match.PROP_SETTLE_STATUS,Operator.EQ,searchObject.getSettleStatus())
                    .addAnd(Match.PROP_BEGIN_TIME,Operator.GE,searchObject.getStartTime())
                    .addAnd(Match.PROP_BEGIN_TIME,Operator.LT,searchObject.getEndTime());
//                    .addAnd(Match.PROP_RESULT_GATHER_TIME,Operator.GT,searchObject.getStartTime())
//                    .addAnd(Match.PROP_RESULT_GATHER_TIME,Operator.LT,searchObject.getEndTime());
            if(StringTool.isNotBlank(this.searchObject.getStatus())){
                switch (this.searchObject.getStatus()){
                    case "1":criteria.addAnd(Match.PROP_IS_SETTLING,Operator.EQ,true);
                        break;
                    case "2":criteria.addAnd(Match.PROP_IS_SETTLING,Operator.EQ,false);
                        break;
                    case "3":criteria.addAnd(Match.PROP_IS_CANCEL,Operator.EQ,true);
                        break;
                }
            }
            return criteria;
            //endregion your codes 2
        }


        //region your codes 3
        @Override
        public Sort getDefaultSort() {
            return Sort.add(Match.PROP_CODE, Direction.DESC);
        }
        //endregion your codes 3
    }

    //region your codes 4

    public String getNewResult() {
        return newResult;
    }

    public void setNewResult(String newResult) {
        this.newResult = newResult;
    }


    //endregion your codes 4

}