package g.model.admin.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.admin.po.BetLimitUserMultiple;
import g.model.admin.so.BetLimitUserMultipleSo;


/**
 * 用户与综合投注限额关系表值对象
 *
 * @author tom
 * @time 2016-4-20 11:46:13
 */
//region your codes 1
public class BetLimitUserMultipleVo extends BaseObjectVo<BetLimitUserMultiple, BetLimitUserMultipleSo, BetLimitUserMultipleVo.BetLimitUserMultipleQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 7412967174806702305L;
    //endregion your codes 5

    /**
     *  用户与综合投注限额关系表查询逻辑
     */
    public static class BetLimitUserMultipleQuery extends AbstractQuery<BetLimitUserMultipleSo> {

        //region your codes 6
        private static final long serialVersionUID = -2798684017798870180L;
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