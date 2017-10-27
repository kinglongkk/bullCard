package g.model.admin.agent.quotamanager.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.admin.agent.quotamanager.po.VAgentQuota;
import g.model.admin.agent.quotamanager.so.VAgentQuotaSo;


/**
 * 值对象
 *
 * @author black
 * @time 2016-12-12 15:27:30
 */
//region your codes 1
public class VAgentQuotaVo extends BaseObjectVo<VAgentQuota, VAgentQuotaSo, VAgentQuotaVo.VAgentQuotaQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -8419448133933236187L;
    //endregion your codes 5

    /**
     *  查询逻辑
     */
    public static class VAgentQuotaQuery extends AbstractQuery<VAgentQuotaSo> {

        //region your codes 6
        private static final long serialVersionUID = 5702266719349848871L;
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