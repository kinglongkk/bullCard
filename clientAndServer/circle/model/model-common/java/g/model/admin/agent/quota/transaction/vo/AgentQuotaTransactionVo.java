package g.model.admin.agent.quota.transaction.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.admin.agent.quota.transaction.po.AgentQuotaTransaction;
import g.model.admin.agent.quota.transaction.so.AgentQuotaTransactionSo;


/**
 * 额度日志值对象
 *
 * @author black
 * @time 2016-12-3 10:47:44
 */
//region your codes 1
public class AgentQuotaTransactionVo extends BaseObjectVo<AgentQuotaTransaction, AgentQuotaTransactionSo, AgentQuotaTransactionVo.AgentQuotaTransactionQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -7975526076866262411L;
    //endregion your codes 5

    /**
     *  额度日志查询逻辑
     */
    public static class AgentQuotaTransactionQuery extends AbstractQuery<AgentQuotaTransactionSo> {

        //region your codes 6
        private static final long serialVersionUID = 2127292006516407875L;
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