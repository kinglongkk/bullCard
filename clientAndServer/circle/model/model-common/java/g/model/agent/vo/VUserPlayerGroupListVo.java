package g.model.agent.vo;

import g.model.agent.po.VUserPlayerGroup;
import g.model.agent.so.VUserPlayerGroupSo;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Order;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;


/**
 * 列表页值对象
 *
 * @author orange
 * @time 2016-4-27 15:56:13
 */
//region your codes 1
public class VUserPlayerGroupListVo extends BaseListVo<VUserPlayerGroup, VUserPlayerGroupSo, VUserPlayerGroupListVo.VUserPlayerGroupQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -1078802946197631801L;
    //endregion your codes 5

    /**
     *  列表查询逻辑
     */
    public static class VUserPlayerGroupQuery extends AbstractQuery<VUserPlayerGroupSo> {

        //region your codes 6
        private static final long serialVersionUID = -5928698907715098108L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            return Criteria.add(VUserPlayerGroup.PROP_CREATE_USER, Operator.EQ,this.searchObject.getCreateUser());
            //endregion your codes 2
        }


        //region your codes 3

        @Override
        public Order[] getOrders() {
            return new Order[]{Order.desc(VUserPlayerGroup.PROP_CREATE_TIME)};
        }

        //endregion your codes 3
    }

    //region your codes 4

    //endregion your codes 4

}