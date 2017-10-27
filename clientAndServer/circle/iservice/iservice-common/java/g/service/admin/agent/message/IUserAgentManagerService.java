package g.service.admin.agent.message;

import g.model.admin.agent.quota.transaction.vo.AgentQuotaTransactionVo;
import g.model.admin.agent.quotamanager.vo.VAgentQuotaVo;

/**
 * 代理额度日志管理
 * Created by black on 2016/12/20.
 */
public interface IUserAgentManagerService {

    /**
     * 新增代理额度日志
     * @param objectVo
     * @param transactionVo
     * @return
     */
    boolean insertAgentTransaction(VAgentQuotaVo objectVo, AgentQuotaTransactionVo transactionVo);
}
