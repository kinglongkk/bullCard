package g.service.admin.agent.message;

import g.model.QuotaStatusEnum;
import g.model.QuotaWarnTypeEnum;
import g.model.admin.agent.message.po.UserAgent;
import g.model.admin.agent.message.vo.UserAgentVo;
import g.model.admin.agent.quota.transaction.vo.AgentQuotaTransactionVo;
import g.model.admin.agent.quotamanager.vo.VAgentQuotaVo;
import g.service.admin.agent.quota.transaction.IAgentQuotaTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by black on 2016/12/20.
 */
public class UserAgentManagerService implements IUserAgentManagerService {

    @Autowired
    private IAgentQuotaTransactionService transactionService;

    @Autowired
    private IUserAgentService agentService;

    @Transactional
    public boolean insertAgentTransaction(VAgentQuotaVo objectVo, AgentQuotaTransactionVo transactionVo) {

        boolean isSuccess = transactionService.insert(transactionVo).isSuccess();
        //查找代理额度
        Map map = new HashMap();
        map.put("agentId", objectVo.getResult().getAgentId());
        UserAgent userAgent = agentService.selectInfoByAgentId(map);
        //更新代理信息
        Double quota = userAgent.getQuota();
        Double amount = objectVo.getResult().getAmount();
        userAgent.setQuota(quota + amount);
        Double warnType = userAgent.getCurrentQuota() / userAgent.getQuota() * 100;
        if (warnType < QuotaWarnTypeEnum.GREEN.getCode()) {
            userAgent.setWarnType(QuotaWarnTypeEnum.GREEN.getValue());
        }else if (QuotaWarnTypeEnum.BLUE.getCode() < warnType && warnType < QuotaWarnTypeEnum.YELLOW.getCode()) {
            userAgent.setWarnType(QuotaWarnTypeEnum.BLUE.getValue());
        }else if (QuotaWarnTypeEnum.YELLOW.getCode() < warnType && warnType < QuotaWarnTypeEnum.RED.getCode()) {
            userAgent.setWarnType(QuotaWarnTypeEnum.YELLOW.getValue());
        }else if (warnType > QuotaWarnTypeEnum.RED.getCode()) {
            userAgent.setWarnType(QuotaWarnTypeEnum.RED.getValue());
        }
        if (userAgent.getQuota() < userAgent.getCurrentQuota()) {
            userAgent.setQuotaStatus(QuotaStatusEnum.ARREARAGE.getCode());
        }else {
            userAgent.setQuotaStatus(QuotaStatusEnum.NORMAL.getCode());
        }
        UserAgentVo agentVo = new UserAgentVo();
        agentVo.setResult(userAgent);
        agentVo.setProperties(UserAgent.PROP_QUOTA, UserAgent.PROP_WARN_TYPE, UserAgent.PROP_QUOTA_STATUS);
        boolean success = agentService.updateOnly(agentVo).isSuccess();
        return isSuccess && success;
    }
}
