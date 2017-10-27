package g.service.admin.agent.quota.transaction;

import org.soul.iservice.support.IBaseService;
import g.model.admin.agent.quota.transaction.po.AgentQuotaTransaction;
import g.model.admin.agent.quota.transaction.vo.AgentQuotaTransactionListVo;
import g.model.admin.agent.quota.transaction.vo.AgentQuotaTransactionVo;

import java.util.List;


/**
 * 额度日志服务接口
 *
 * @author black
 * @time 2016-12-3 10:47:43
 */
//region your codes 1
public interface IAgentQuotaTransactionService extends IBaseService<AgentQuotaTransactionListVo, AgentQuotaTransactionVo, AgentQuotaTransaction, Integer> {

    /**
     * 获取多个代理商额度
     * @param startTime
     * @param endTime
     * @return
     */
    public List<AgentQuotaTransaction> agentQuotaTransactionList(String startTime,String endTime,Object agentId[]);

    /**
     * 所有代理商汇总额度
     * @param startTime
     * @param endTime
     * @return
     */
    public List<AgentQuotaTransaction> agentAllQuotaTransactionList(String startTime,String endTime);

    /**
     * 获取指定代理商额度
     * @param startTime
     * @param endTime
     * @param agentId
     * @return
     */
    public List<AgentQuotaTransaction> agentOneQuotaTransactionList(String startTime,String endTime,Integer agentId);

}