package g.model.admin.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.admin.po.BetLimitUserGroup;
import g.model.admin.so.BetLimitUserGroupSo;


/**
 * 用户组与综合投注限额关系表值对象
 *
 * @author tom
 * @time 2016-4-20 11:41:52
 */
//region your codes 1
public class BetLimitUserGroupVo extends BaseObjectVo<BetLimitUserGroup, BetLimitUserGroupSo, BetLimitUserGroupVo.BetLimitUserGroupQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 9007932998839654290L;
    //endregion your codes 5

    /**
     *  用户组与综合投注限额关系表查询逻辑
     */
    public static class BetLimitUserGroupQuery extends AbstractQuery<BetLimitUserGroupSo> {

        //region your codes 6
        private static final long serialVersionUID = -6650076482743917993L;
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

    //endregion your codes 4

}