package g.api.biz;

import g.api.base.BaseService;
import g.api.service.IGameKickoutService;
import g.model.admin.agent.message.po.UserAgent;
import g.model.api.enums.ResultStatusEnums;
import g.model.api.param.KickOutParams;
import g.model.api.param.Params;
import g.model.api.result.KickOutResults;
import g.model.api.result.Results;
import org.soul.commons.cache.CacheKey;
import org.soul.commons.exception.ServiceException;
import org.soul.commons.lang.string.StringTool;
import org.soul.model.log.audit.enums.OpMode;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.web.session.RedisSessionDao;
import org.springframework.beans.factory.annotation.Autowired;

public class KickoutService extends BaseService implements IGameKickoutService {

    @Autowired
    private RedisSessionDao redisSessionDao;

    @Override
    public Results kickout(KickOutParams params) {

        KickOutResults result = new KickOutResults();
        if (!validatePreConditions(params)) {
           throwBusinessException(ResultStatusEnums.VALIDATE_PARAMS_FAILED);
        }
        this.log.info("将{0}踢出", params.getUserAccount());
        UserAgent userAgent = getUserAgent(params.getMerchantNo());
        if (userAgent == null) {
            throwBusinessException(ResultStatusEnums.AGENT_NOT_EXISTS);
        }
        SysUser player = getPlayer(getPlayerAccount(userAgent.getId(), params.getUserAccount()), userAgent.getAgentId());
        if (player != null) {
            try {
                redisSessionDao.kickOutSession(CacheKey.getCacheKey(redisSessionDao.genPrefix(), player.getSessionKey()), OpMode.AUTO);
            } catch (ServiceException e) {
                throwBusinessException(ResultStatusEnums.USER_KICK_OUT_FAILED);
                this.log.error(e, "{0}踢出异常", params.getUserAccount());
            }
        } else {
            throwBusinessException(ResultStatusEnums.USER_NOT_EXIST);
        }
        return result;
    }

    @Override
    public boolean validatePreConditions(Params params) {

        KickOutParams kickOutParam = (KickOutParams) params;
        if (StringTool.isNotEmpty(kickOutParam.getUserAccount()) && StringTool.isNotEmpty(kickOutParam.getUserPwd())) {
            return true;
        } else {
            return false;
        }
    }

}
