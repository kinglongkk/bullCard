package g.api.base;

import g.api.exception.BusinessException;
import g.common.tool.NumberTool;
import g.model.SubSysCodeEnum;
import g.model.admin.agent.message.po.UserAgent;
import g.model.admin.agent.message.vo.UserAgentVo;
import g.model.api.enums.ResultStatusEnums;
import g.model.api.param.Params;
import g.service.admin.agent.message.IUserAgentService;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.query.Criterion;
import org.soul.commons.query.enums.Operator;
import org.soul.iservice.security.privilege.ISysUserService;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.model.security.privilege.vo.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: double
 * @date: 16-4-7
 */
public abstract class BaseService<P extends Params> {

    @Autowired
    protected ISysUserService sysUserService;

    @Autowired
    private IUserAgentService agentService;

    public final static Integer siteId = 1;

    protected final Log log = LogFactory.getLog(getClass());


    public boolean validatePreConditions(P params){
        return false;
    }

    protected UserAgent getUserAgent(String merchantNo){
        if (merchantNo != null && !StringTool.isBlank(merchantNo)) {
            UserAgentVo agentVo = new UserAgentVo();
            agentVo.getQuery().setCriterions(new Criterion[]{
                new Criterion(UserAgent.PROP_MERCHANT_NO, Operator.EQ, merchantNo)
            });
            return this.agentService.search(agentVo).getResult();
        }else {
            return null;
        }
    }

    protected SysUser getSysAgent(Integer agentId){
        if (agentId != null){
            SysUserVo agentVo = new SysUserVo();
            agentVo.getQuery().setCriterions(new Criterion[]{
                new Criterion(SysUser.PROP_ID, Operator.EQ, agentId)
            });
            return this.sysUserService.search(agentVo).getResult();
        } else {
            return null;
        }
    }

    protected SysUser getPlayer(String playerAccount, Integer agentId){

        if (playerAccount != null && agentId != null) {
            SysUserVo playerVo = new SysUserVo();
            playerVo.getQuery().setCriterions(new Criterion[]{
                    new Criterion(SysUser.PROP_USERNAME, Operator.IEQ, playerAccount),
                    new Criterion(SysUser.PROP_OWNER_ID, Operator.EQ, agentId),
                    new Criterion(SysUser.PROP_SUBSYS_CODE, Operator.EQ, SubSysCodeEnum.PLAYER.getCode())
            });
            return this.sysUserService.search(playerVo).getResult();
        }else {
            return null;
        }
    }

    protected void throwBusinessException(ResultStatusEnums statusEnum){
        throw new BusinessException(statusEnum.getCode(),statusEnum.getMsg(),"");
    }

    /**
     * 获取代理商前缀
     * @param agentId
     * @return String
     */
    protected String getAgentPrefix(Integer agentId) {

        short userAgentId = new Integer(agentId).shortValue();
        return NumberTool.format(userAgentId);
    }

    /**
     * 获取玩家完整的用户名
     * @param agentId
     * @param username
     * @return String
     */
    protected String getPlayerAccount(Integer agentId, String username) {

        short userAgentId = new Integer(agentId).shortValue();
        String agentPrefix =  NumberTool.format(userAgentId);
        return agentPrefix + username;
    }
}
