package g.model.admin.vo;

import g.model.admin.po.VUserGroupBetLimitMultiple;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.admin.so.VUserGroupBetLimitMultipleSo;


/**
 * 玩家分组综合过关单项限额列表页值对象
 *
 * @author tom
 * @time 2016-4-21 17:23:47
 */
//region your codes 1
public class VUserGroupBetLimitMultipleListVo extends BaseListVo<VUserGroupBetLimitMultiple, VUserGroupBetLimitMultipleSo, VUserGroupBetLimitMultipleListVo.VUserGroupBetLimitMultipleQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -5198799552494436296L;
    //endregion your codes 5

    /**
     *  玩家分组综合过关单项限额列表查询逻辑
     */
    public static class VUserGroupBetLimitMultipleQuery extends AbstractQuery<VUserGroupBetLimitMultipleSo> {

        //region your codes 6
        private static final long serialVersionUID = -1611198219117804314L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            return Criteria.add(VUserGroupBetLimitMultiple.PROP_SYS_USER_GROUP_ID, Operator.EQ, this.searchObject.getSysUserGroupId());
            //endregion your codes 2
        }


        //region your codes 3

        //endregion your codes 3
    }

    //region your codes 4

    //endregion your codes 4

}