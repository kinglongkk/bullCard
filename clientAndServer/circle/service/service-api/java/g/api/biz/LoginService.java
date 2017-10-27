package g.api.biz;

import g.api.base.BaseService;
import g.api.service.IGameLoginService;
import g.common.security.AuthTool;
import g.model.admin.agent.message.po.UserAgent;
import g.model.api.enums.ResultStatusEnums;
import g.model.api.param.LoginParams;
import g.model.api.result.LoginResults;
import g.model.api.result.Results;
import g.model.player.PlayerBean;
import g.model.sys.vo.SysSiteListVo;
import g.service.sys.ISysSiteService;
import org.soul.commons.lang.SerializationTool;
import org.soul.commons.lang.string.RandomStringTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.security.CryptoTool;
import org.soul.iservice.passport.IPassportService;
import org.soul.model.passport.vo.PassportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springside.modules.nosql.redis.JedisTemplate;

import javax.security.auth.login.LoginException;
import java.util.Date;

public class LoginService extends BaseService implements IGameLoginService {

    @Autowired
    private AuthTool authTool;

    @Autowired
    private IPassportService passportService;

    @Autowired
    private ISysSiteService sysSiteService;

    @Autowired
    @Qualifier("jedisTemplateApi")
    private JedisTemplate jedisTemplateApi;

    @Override
    public Results login(LoginParams loginParam) throws LoginException {

        LoginResults result = new LoginResults();
        UserAgent userAgent = getUserAgent(loginParam.getMerchantNo());
        if (userAgent == null) {
            throwBusinessException(ResultStatusEnums.AGENT_NOT_EXISTS);
        }
        //获取玩家完整用户名
        String username = getPlayerAccount(userAgent.getId(), loginParam.getUserAccount());

        this.log.info("{0}请求登录", loginParam.getUserAccount());
        PassportVo passportVo = new PassportVo();
        passportVo.getSearch().setUsername(getPlayerAccount(userAgent.getId(), loginParam.getUserAccount()));
        passportVo.getSearch().setPassword(authTool.md5SysUserPassword(loginParam.getUserPwd(), username));
        passportVo.getSearch().setSiteId(siteId);
        passportVo.getSearch().setOwnerId(userAgent.getAgentId());

        PassportVo passport = this.passportService.login(passportVo);
        Date date = new Date();
        String timestamp = CryptoTool.encryptDES3(date.getTime() + "", authTool.getSalt4ApiParam());
        PlayerBean playerBean = new PlayerBean();
        playerBean.setTimestamp(date.getTime());
        playerBean.setPassportVo(passport);
        String token = RandomStringTool.uuid();
        this.jedisTemplateApi.set(token.getBytes(), SerializationTool.serialize(playerBean),10);
        SysSiteListVo listVo = new SysSiteListVo();
        listVo = sysSiteService.search(listVo);
        if (listVo.getResult() != null && !listVo.getResult().isEmpty()) {
            String url = listVo.getResult().get(0).getWebSite();
            //设置登录所需信息
            if (StringTool.isNotEmpty(url)) {
                result.setWebSite(url);
                result.setTimestamp(timestamp);
                result.setToken(token);
            }
        }
        return result;
    }

}
