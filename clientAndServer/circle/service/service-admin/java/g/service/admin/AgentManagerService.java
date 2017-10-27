package g.service.admin;

import g.data.admin.agent.message.UserAgentMapper;
import g.model.QuotaStatusEnum;
import g.model.QuotaWarnTypeEnum;
import g.model.admin.agent.message.po.UserAgent;
import org.soul.commons.lang.string.RandomStringTool;
import org.soul.iservice.security.privilege.ISysUserService;
import org.soul.model.security.privilege.vo.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by black on 2016/12/20.
 */
public class AgentManagerService implements IAgentManagerService {

    @Autowired
    private UserAgentMapper userAgentMapper;

    @Autowired
    private ISysUserService sysUserService;

    @Transactional
    public boolean insertAgent(SysUserVo sysUserVo) {

        sysUserVo = sysUserService.insert(sysUserVo);
        //代理商务信息
        UserAgent agent = new UserAgent();
        agent.setAgentId(sysUserVo.getResult().getId());
        agent.setMerchantNo(RandomStringTool.randomAlphabetic(16));
        agent.setMerchantKey(RandomStringTool.randomAlphanumeric(16));
        agent.setQuota(0d);
        agent.setCurrentQuota(0d);
        agent.setWarnType(QuotaWarnTypeEnum.GREEN.getValue());
        agent.setQuotaStatus(QuotaStatusEnum.NORMAL.getCode());
        boolean isSuccess = userAgentMapper.insert(agent);

        return sysUserVo.isSuccess() && isSuccess;
    }
}
