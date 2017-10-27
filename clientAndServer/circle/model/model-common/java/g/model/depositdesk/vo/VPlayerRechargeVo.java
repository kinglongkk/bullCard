package g.model.depositdesk.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.depositdesk.po.VPlayerRecharge;
import g.model.depositdesk.so.VPlayerRechargeSo;


/**
 * 值对象
 *
 * @author tom
 * @time 2016-7-14 9:46:35
 */
//region your codes 1
public class VPlayerRechargeVo extends BaseObjectVo<VPlayerRecharge, VPlayerRechargeSo, VPlayerRechargeVo.VPlayerRechargeQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -7465529861057495769L;
    //endregion your codes 5

    /**
     *  查询逻辑
     */
    public static class VPlayerRechargeQuery extends AbstractQuery<VPlayerRechargeSo> {

        //region your codes 6
        private static final long serialVersionUID = -1800794534610348562L;
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