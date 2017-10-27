package g.model.admin.agent.quotamanager.vo;

import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.admin.agent.quotamanager.po.VAgentQuota;
import g.model.admin.agent.quotamanager.so.VAgentQuotaSo;


/**
 * 列表页值对象
 *
 * @author black
 * @time 2016-12-12 15:27:30
 */
//region your codes 1
public class VAgentQuotaListVo extends BaseListVo<VAgentQuota, VAgentQuotaSo, VAgentQuotaListVo.VAgentQuotaQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 2617224101132574428L;
    //endregion your codes 5

    /**
     *  列表查询逻辑
     */
    public static class VAgentQuotaQuery extends AbstractQuery<VAgentQuotaSo> {

        //region your codes 6
        private static final long serialVersionUID = -8157267922797696461L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            Criteria criteria = Criteria.and(

                    Criteria.add(VAgentQuota.PROP_ID, Operator.EQ, this.searchObject.getId()),
                    Criteria.add(VAgentQuota.PROP_USERNAME, Operator.EQ, this.searchObject.getUsername()),
                    Criteria.add(VAgentQuota.PROP_REAL_NAME, Operator.EQ, this.searchObject.getRealName()),
                    Criteria.add(VAgentQuota.PROP_MERCHANT_NO, Operator.EQ, this.searchObject.getMerchantNo())
            );
            return criteria;
            //endregion your codes 2
        }


        //region your codes 3

        //endregion your codes 3
    }

    //region your codes 4

    //endregion your codes 4

}