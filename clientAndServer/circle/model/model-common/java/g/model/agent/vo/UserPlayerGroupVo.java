package g.model.agent.vo;

import g.model.agent.po.UserPlayerGroup;
import g.model.agent.so.UserPlayerGroupSo;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;


/**
 * 值对象
 *
 * @author orange
 * @time 2016-4-28 9:52:30
 */
//region your codes 1
public class UserPlayerGroupVo extends BaseObjectVo<UserPlayerGroup, UserPlayerGroupSo, UserPlayerGroupVo.UserPlayerGroupQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 5855582969972084764L;
    //endregion your codes 5

    /**
     *  查询逻辑
     */
    public static class UserPlayerGroupQuery extends AbstractQuery<UserPlayerGroupSo> {

        //region your codes 6
        private static final long serialVersionUID = -6074465357007837610L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            return Criteria.add(UserPlayerGroup.PROP_IS_DEFUALT, Operator.EQ,this.searchObject.getIsDefault())
                    .addAnd(UserPlayerGroup.PROP_CREATE_USER, Operator.EQ, this.searchObject.getCreateUser())
                    .addAnd(UserPlayerGroup.PROP_ID, Operator.EQ, this.searchObject.getId());
            //endregion your codes 2
        }

        //region your codes 3

        //endregion your codes 3

    }

    //region your codes 4

    //endregion your codes 4

}