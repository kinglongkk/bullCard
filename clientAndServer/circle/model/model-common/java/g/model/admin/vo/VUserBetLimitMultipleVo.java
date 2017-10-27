package g.model.admin.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.admin.po.VUserBetLimitMultiple;
import g.model.admin.so.VUserBetLimitMultipleSo;


/**
 * 代理综合过关单注最高限额值对象
 *
 * @author tom
 * @time 2016-4-21 17:17:49
 */
//region your codes 1
public class VUserBetLimitMultipleVo extends BaseObjectVo<VUserBetLimitMultiple, VUserBetLimitMultipleSo, VUserBetLimitMultipleVo.VUserBetLimitMultipleQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -5702057158186886485L;
    //endregion your codes 5

    /**
     *  代理综合过关单注最高限额查询逻辑
     */
    public static class VUserBetLimitMultipleQuery extends AbstractQuery<VUserBetLimitMultipleSo> {

        //region your codes 6
        private static final long serialVersionUID = 731000701915900702L;
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