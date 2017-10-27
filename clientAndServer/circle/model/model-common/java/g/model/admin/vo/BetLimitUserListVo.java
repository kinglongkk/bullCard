package g.model.admin.vo;

import g.model.admin.po.BetLimitUser;
import g.model.admin.so.BetLimitUserSo;
import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;


/**
 * 用户与投注限额关系表列表页值对象
 *
 * @author tom
 * @time 2016-4-20 11:39:00
 */
//region your codes 1
public class BetLimitUserListVo extends BaseListVo<BetLimitUser, BetLimitUserSo, BetLimitUserListVo.BetLimitUserQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 9084508516824357133L;
    //endregion your codes 5

    /**
     *  用户与投注限额关系表列表查询逻辑
     */
    public static class BetLimitUserQuery extends AbstractQuery<BetLimitUserSo> {

        //region your codes 6
        private static final long serialVersionUID = 5792690897177950875L;
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