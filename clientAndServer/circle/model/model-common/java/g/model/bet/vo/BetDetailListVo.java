package g.model.bet.vo;

import g.model.bet.po.BetDetail;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.bet.so.BetDetailSo;


/**
 * 投注详情列表页值对象
 *
 * @author tom
 * @time 2016-5-10 15:13:30
 */
//region your codes 1
public class BetDetailListVo extends BaseListVo<BetDetail, BetDetailSo, BetDetailListVo.BetDetailQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -8315200241465735947L;
    //endregion your codes 5

    /**
     *  投注详情列表查询逻辑
     */
    public static class BetDetailQuery extends AbstractQuery<BetDetailSo> {

        //region your codes 6
        private static final long serialVersionUID = 1850639016600898793L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            return Criteria.add(BetDetail.PROP_BET_ID, Operator.EQ, this.searchObject.getBetId());
            //endregion your codes 2
        }


        //region your codes 3

        //endregion your codes 3
    }

    //region your codes 4

    //endregion your codes 4

}