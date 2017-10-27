package g.model.admin.vo;

import g.model.admin.po.BetLimitUserGroupMultiple;
import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.admin.so.BetLimitUserGroupMultipleSo;


/**
 * 用户组与综合投注限额关系表值对象
 *
 * @author tom
 * @time 2016-4-20 11:43:03
 */
//region your codes 1
public class BetLimitUserGroupMultipleVo extends BaseObjectVo<BetLimitUserGroupMultiple, BetLimitUserGroupMultipleSo, BetLimitUserGroupMultipleVo.BetLimitUserGroupMultipleQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 8867311642307634440L;
    //endregion your codes 5

    /**
     *  用户组与综合投注限额关系表查询逻辑
     */
    public static class BetLimitUserGroupMultipleQuery extends AbstractQuery<BetLimitUserGroupMultipleSo> {

        //region your codes 6
        private static final long serialVersionUID = 6915171547657165321L;
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