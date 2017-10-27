package g.model.admin.agent.quota.transaction.vo;

import g.model.admin.agent.quota.transaction.po.AgentQuotaTransaction;
import g.model.admin.agent.quota.transaction.so.AgentQuotaTransactionSo;
import org.soul.commons.lang.DateTool;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;


/**
 * 额度日志列表页值对象
 *
 * @author black
 * @time 2016-12-3 10:47:44
 */
public class AgentQuotaTransactionListVo extends BaseListVo<AgentQuotaTransaction, AgentQuotaTransactionSo, AgentQuotaTransactionListVo.AgentQuotaTransactionQuery> {

    private static final long serialVersionUID = 8540662183917999712L;

    /**
     *  额度日志列表查询逻辑
     */
    public static class AgentQuotaTransactionQuery extends AbstractQuery<AgentQuotaTransactionSo> {

        private static final long serialVersionUID = -6912510371166169657L;

        @Override
        public Criteria getCriteria() {

            if (this.searchObject.getStartTime() != null && this.searchObject.getEndTime() != null
                    && this.searchObject.getStartTime().equals(this.searchObject.getEndTime())) {
                this.searchObject.setEndTime(DateTool.addDays(this.searchObject.getStartTime(), 1));
            }

            Criteria criteria = Criteria.and(

                    Criteria.add(AgentQuotaTransaction.PROP_ID, Operator.EQ, this.searchObject.getId()),
                    Criteria.add(AgentQuotaTransaction.PROP_AGENT_ID, Operator.EQ, this.searchObject.getAgentId()),
                    Criteria.add(AgentQuotaTransaction.PROP_TYPE, Operator.EQ, this.searchObject.getType()),
                    Criteria.add(AgentQuotaTransaction.PROP_DATE, Operator.GE, this.searchObject.getStartTime()),
                    Criteria.add(AgentQuotaTransaction.PROP_DATE, Operator.LE, this.searchObject.getEndTime())
            );
            return criteria;
        }

    }

}