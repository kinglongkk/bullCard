package g.model.admin.vo;

import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Order;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.admin.po.VUserBetLimit;
import g.model.admin.so.VUserBetLimitSo;


/**
 * 代理单注限额视图列表页值对象
 *
 * @author tom
 * @time 2016-4-21 17:14:24
 */
//region your codes 1
public class VUserBetLimitListVo extends BaseListVo<VUserBetLimit, VUserBetLimitSo, VUserBetLimitListVo.VUserBetLimitQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 3323182290132331437L;
    //endregion your codes 5

    /**
     *  代理单注限额视图列表查询逻辑
     */
    public static class VUserBetLimitQuery extends AbstractQuery<VUserBetLimitSo> {

        //region your codes 6
        private static final long serialVersionUID = -8102870522540444558L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            return Criteria.add(VUserBetLimit.PROP_SYS_USER_ID, Operator.EQ,this.searchObject.getSysUserId());
            //endregion your codes 2
        }


        //region your codes 3
        @Override
        public Order[] getOrders() {
            return new Order[]{Order.asc(VUserBetLimit.PROP_ORDER_NUM)};
        }
        //endregion your codes 3
    }

    //region your codes 4

    //endregion your codes 4

}