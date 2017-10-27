package g.model.admin.vo;

import g.model.admin.so.VUserBetLimitSo;
import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.admin.po.VUserBetLimit;


/**
 * 代理单注限额视图值对象
 *
 * @author tom
 * @time 2016-4-21 17:14:24
 */
//region your codes 1
public class VUserBetLimitVo extends BaseObjectVo<VUserBetLimit, VUserBetLimitSo, VUserBetLimitVo.VUserBetLimitQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -852092606808573324L;
    //endregion your codes 5

    /**
     *  代理单注限额视图查询逻辑
     */
    public static class VUserBetLimitQuery extends AbstractQuery<VUserBetLimitSo> {

        //region your codes 6
        private static final long serialVersionUID = 3259219271327788864L;
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