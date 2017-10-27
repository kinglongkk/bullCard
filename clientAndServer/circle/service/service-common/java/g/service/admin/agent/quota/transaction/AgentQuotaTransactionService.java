package g.service.admin.agent.quota.transaction;

import g.common.tool.DateTimeTool;
import g.data.admin.agent.quota.transaction.AgentQuotaTransactionMapper;
import g.model.admin.agent.quota.transaction.po.AgentQuotaTransaction;
import g.model.admin.agent.quota.transaction.vo.AgentQuotaTransactionListVo;
import g.model.admin.agent.quota.transaction.vo.AgentQuotaTransactionVo;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Order;
import org.soul.service.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * 额度日志服务
 *
 * @author black
 * @time 2016-12-3 10:47:44
 */
//region your codes 1
public class AgentQuotaTransactionService extends BaseService<AgentQuotaTransactionMapper, AgentQuotaTransactionListVo, AgentQuotaTransactionVo, AgentQuotaTransaction, Integer> implements IAgentQuotaTransactionService {
    @Autowired(required = false)
    private AgentQuotaTransactionMapper agentQuotaTransactionMapper;
    @Override
    public List<AgentQuotaTransaction> agentQuotaTransactionList(String startTime, String endTime,Object agentId[]) {
        Criteria criteria = null;
        if(agentId.length > 0){
             criteria = Criteria.and(
                    Criteria.add(AgentQuotaTransaction.PROP_DATE, Operator.GE,  DateTimeTool.getDateByFull(startTime)),
                    Criteria.add(AgentQuotaTransaction.PROP_DATE, Operator.LE,  DateTimeTool.getDateByFull(endTime)),
                    Criteria.add(AgentQuotaTransaction.PROP_AGENT_ID, Operator.IN,  agentId)
            );
        }else{
            criteria = Criteria.and(
                    Criteria.add(AgentQuotaTransaction.PROP_DATE, Operator.GE,  DateTimeTool.getDateByFull(startTime)),
                    Criteria.add(AgentQuotaTransaction.PROP_DATE, Operator.LE,  DateTimeTool.getDateByFull(endTime)),
                    Criteria.add(AgentQuotaTransaction.PROP_AGENT_ID, Operator.EQ,  agentId[0])
            );
        }

        List<AgentQuotaTransaction> list = agentQuotaTransactionMapper.search(criteria);
        return list;
    }

    @Override
    public List<AgentQuotaTransaction> agentAllQuotaTransactionList(String startTime, String endTime) {
        Criteria criteria = Criteria.and(
                Criteria.add(AgentQuotaTransaction.PROP_DATE, Operator.GE,  DateTimeTool.getDateByFull(startTime)),
                Criteria.add(AgentQuotaTransaction.PROP_DATE, Operator.LE,  DateTimeTool.getDateByFull(endTime))
        );
        List<AgentQuotaTransaction> list = agentQuotaTransactionMapper.search(criteria);
        return list;
    }

    @Override
    public List<AgentQuotaTransaction> agentOneQuotaTransactionList(String startTime, String endTime, Integer agentId) {
        Criteria criteria = Criteria.and(
                Criteria.add(AgentQuotaTransaction.PROP_DATE, Operator.GE,  DateTimeTool.getDateByFull(startTime)),
                Criteria.add(AgentQuotaTransaction.PROP_DATE, Operator.LE,  DateTimeTool.getDateByFull(endTime)),
                    Criteria.add(AgentQuotaTransaction.PROP_AGENT_ID, Operator.EQ,  agentId)
        );
        List<AgentQuotaTransaction> list = agentQuotaTransactionMapper.search(criteria);
        return list;
    }
}