package g.model.admin.vo;

import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Order;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.admin.po.VUserBetLimitMultiple;
import g.model.admin.so.VUserBetLimitMultipleSo;


/**
 * 代理综合过关单注最高限额列表页值对象
 *
 * @author tom
 * @time 2016-4-21 17:17:49
 */
//region your codes 1
public class VUserBetLimitMultipleListVo extends BaseListVo<VUserBetLimitMultiple, VUserBetLimitMultipleSo, VUserBetLimitMultipleListVo.VUserBetLimitMultipleQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 4811052682241513327L;
    //endregion your codes 5

    /**
     *  代理综合过关单注最高限额列表查询逻辑
     */
    public static class VUserBetLimitMultipleQuery extends AbstractQuery<VUserBetLimitMultipleSo> {

        //region your codes 6
        private static final long serialVersionUID = 5063514622272103663L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            return Criteria.add(VUserBetLimitMultiple.PROP_SYS_USER_ID, Operator.EQ, this.searchObject.getSysUserId());
            //endregion your codes 2
        }


        //region your codes 3

        @Override
        public Order[] getOrders() {
            return new Order[]{Order.asc(VUserBetLimitMultiple.PROP_ORDER_NUM)};
        }

        //endregion your codes 3
    }

    //region your codes 4

    //endregion your codes 4

}