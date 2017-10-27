package g.model.admin.vo;

import g.model.admin.po.BetLimitUserGroupMultiple;
import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.admin.so.BetLimitUserGroupMultipleSo;


/**
 * 用户组与综合投注限额关系表列表页值对象
 *
 * @author tom
 * @time 2016-4-20 11:43:03
 */
//region your codes 1
public class BetLimitUserGroupMultipleListVo extends BaseListVo<BetLimitUserGroupMultiple, BetLimitUserGroupMultipleSo, BetLimitUserGroupMultipleListVo.BetLimitUserGroupMultipleQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 8017473256665461548L;
    //endregion your codes 5

    /**
     *  用户组与综合投注限额关系表列表查询逻辑
     */
    public static class BetLimitUserGroupMultipleQuery extends AbstractQuery<BetLimitUserGroupMultipleSo> {

        //region your codes 6
        private static final long serialVersionUID = -4252208859466905822L;
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