package g.web.common.shiro.common.delegate;

import g.web.common.passport.captcha.CaptchaUrlEnum;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.util.WebUtils;
import org.soul.commons.bean.Pair;
import org.soul.commons.cache.CacheKey;
import org.soul.commons.collections.CollectionTool;
import org.soul.commons.exception.ServiceException;
import org.soul.commons.init.context.ClientInfo;
import org.soul.commons.init.context.Const;
import org.soul.commons.init.context.Operator;
import org.soul.commons.lang.SerializationTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.spring.utils.SpringTool;
import org.soul.iservice.security.privilege.ISysUserService;
import org.soul.model.log.audit.enums.OpMode;
import org.soul.model.passport.vo.PassportVo;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.model.security.privilege.vo.SysUserVo;
import org.soul.model.session.SessionKey;
import org.soul.web.log.audit.AuditLogTool;
import org.soul.web.session.RedisSessionDao;
import org.soul.web.session.SessionManagerBase;
import org.soul.web.shiro.common.delegate.IPassportListener;
import org.soul.web.shiro.common.delegate.PassportEvent;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.util.SafeEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by longer on 12/17/15.
 */
public class PassportDelegate implements IPassportDelegate {

    private Log log = LogFactory.getLog(getClass());

    @Value("${sso.player.passport}")
    private String							ssoMaster;

    @Value("${subsys.code}")
    private String                          subsysCode;

    private String                          ssoNormal = "/passport/login.html";

    private List<IPassportListener> passportListeners;

    private RedisSessionDao redisSessionDao;

    @Override
    public void onLoginSuccessDelegate(HttpServletRequest request){
        SysUser sysUser = SessionManagerBase.getUser();
        ClientInfo clientInfo = AuditLogTool.getClientInfo(request);
        Operator operator = AuditLogTool.getOperator(request, sysUser);

        boolean isKickOut = redisSessionDao.kickOutSession(CacheKey.getCacheKey(redisSessionDao.genPrefix(),sysUser.getSessionKey()), OpMode.AUTO);

        PassportEvent passportEvent = new PassportEvent();
        passportEvent.setSysUser(sysUser);
        passportEvent.setOperator(operator);
        passportEvent.setIsMaster(SessionManagerBase.isCurrentSiteMaster());
        passportEvent.setKickOut(isKickOut);

        updateSysUser4Login(sysUser, clientInfo);
        // warning: can't use request.getSession().getId(),because session id is change after subject.login()
        fireLoginListener(passportEvent);
    }

    @Override
    public void onLoginFailureDelegate(HttpServletRequest request, HttpServletResponse response, Integer id, String username, Integer loginErrorTimes) {
        Operator operator = AuditLogTool.getOperator(request, null);
        operator.setOperatorId(id);
        operator.setOperator(username);
        PassportEvent passportEvent = new PassportEvent();
        passportEvent.setOperator(operator);
        passportEvent.setAuthenticationFailureTimes(loginErrorTimes);
        passportEvent.setAuthenticationFailureFreezeLimit(Const.AUTHENTICATION_FAILURE_FREEZE_LIMIT);

        //未登录成功,不能从session中获取entrance(CommonContext中也获取不到,因为在LocalRealm中被设为null by susu)
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        Integer entrance = getEntranceType(contextPath,uri);

        if (PassportVo.MASTER.equals(entrance)) {
            //使用站点虚拟帐号
            passportEvent.setIsMaster(true);
        }
        fireLoginFailListener(passportEvent);
    }

    public void onLogoutDelegate(HttpServletRequest request, HttpServletResponse response){
        SysUser sysUser = SessionManagerBase.getUser();
        if(sysUser == null)
            return;
        ClientInfo clientInfo = AuditLogTool.getClientInfo(request);
        Operator operator = AuditLogTool.getOperator(request, sysUser);

        PassportEvent passportEvent = new PassportEvent();
        passportEvent.setSysUser(sysUser);
        passportEvent.setOperator(operator);
        passportEvent.setIsMaster(SessionManagerBase.isCurrentSiteMaster());

        updateSysUser4Logout(sysUser, clientInfo);
        fireLogoutSuccesslListener(passportEvent);
    }

    /**
     * 登录时更新系统用户信息
     * @param user
     */
    private void updateSysUser4Login(SysUser user, ClientInfo clientInfo) {
        SysUserVo sysUserVo = new SysUserVo();
        sysUserVo.setResult(user);
        sysUserVo.setProperties(
                SysUser.PROP_LAST_LOGIN_IP,
                SysUser.PROP_LAST_LOGIN_IP_DICT_CODE,
                SysUser.PROP_LAST_LOGIN_TIME,
                SysUser.PROP_LOGIN_IP,
                SysUser.PROP_LOGIN_IP_DICT_CODE,
                SysUser.PROP_LOGIN_TIME,
                SysUser.PROP_LAST_ACTIVE_TIME,
                SysUser.PROP_USE_LINE,
                SysUser.PROP_LOGIN_ERROR_TIMES,
                SysUser.PROP_SESSION_KEY
        );

        user.setLastLoginIp(user.getLoginIp());
        user.setLastLoginIpDictCode(user.getLoginIpDictCode());
        user.setLastLoginTime(user.getLoginTime());
        user.setLoginIp(clientInfo.getIp());
        user.setLoginIpDictCode(SessionManagerBase.getIpDictCode());
        user.setLoginTime(new Date());
        user.setLastActiveTime(new Date());
        user.setLoginErrorTimes(0);
        user.setUseLine(clientInfo.getServerName());
        user.setSessionKey(SessionManagerBase.getId());

        try {
            if (SessionManagerBase.isCurrentSiteMaster()) {
                sysUserVo._setDataSourceId(SessionManagerBase.getSiteParentId());
            }
            getSysUserService().updateOnly(sysUserVo);
            SessionManagerBase.setUser(user);
        } catch (Exception e) {
            log.error(e,"登录成功:但更新用户登录信息时异常!");
        }
    }

    /**
     * 登录时更新系统用户信息
     * @param user
     */
    private void updateSysUser4Logout(SysUser user, ClientInfo clientInfo) {
        SysUserVo sysUserVo = new SysUserVo();
        sysUserVo.setResult(user);
        sysUserVo.setProperties(
                SysUser.PROP_LAST_LOGOUT_TIME
        );

        if(user != null) {
            user.setLastLogoutTime(new Date());
            try {
                if (SessionManagerBase.isCurrentSiteMaster()) {
                    //站长:存储在mainsite
                    sysUserVo._setDataSourceId(SessionManagerBase.getSiteParentId());
                }
                getSysUserService().updateOnly(sysUserVo);
            } catch (Exception e) {
                log.error(e,"登录成功:但更新用户登录信息时异常!");
            }
        }
    }

    @Override
    public void redirectTo(HttpServletRequest request, HttpServletResponse response, String successUrlOld) throws IOException {
        String url = request.getParameter("url");
        if (StringTool.isNotBlank(url)) {
            response.getWriter().print("<script>window.top.location.href='" + url + "';</script>");
            return;
        }
        String successUrl = StringTool.defaultIfBlank(url, successUrlOld);
        WebUtils.redirectToSavedRequest(request, response, successUrl);
    }

    /**
     * 获取登录入口类型
     * @param contextPath
     * @param uri
     * @return
     */
    @Override
    public Integer getEntranceType(String contextPath, String uri) {
        if (StringTool.isBlank(ssoMaster)){
            return PassportVo.NORMAL;
        }
        if (uri.indexOf(contextPath + ssoMaster) != -1) {
            return PassportVo.MASTER;
        }
        return PassportVo.NORMAL;
    }

    @Override
    public String getLoginUrl(Integer entranceType) {
        if (entranceType == null) entranceType = 0;
        if (entranceType == PassportVo.MASTER) {
            return ssoMaster;
        }
        return ssoNormal;
    }

    /**
     * 增加登录失败次数
     * @param vo
     * @return
     */
    @Override
    public int incLoginErrorTimes(SysUserVo vo) {
        return getSysUserService().incLoginErrorTimes(vo);
    }

    @Override
    public String getCaptchaSessionKey() {
        return SessionKey.S_CAPTCHA_PREFIX + CaptchaUrlEnum.CODE_LOGIN.getSuffix();
    }

    private void fireLoginListener(PassportEvent passportEvent) {
        if (CollectionTool.isNotEmpty(passportListeners)) {
            for (IPassportListener loginListener : passportListeners) {
                try {
                    loginListener.onLoginSuccess(passportEvent);
                } catch (RuntimeException e) {
                    log.error(e, "登录监听事件处理异常!{0}", loginListener.getClass());
                }
            }
        }
    }

    private void fireLoginFailListener(PassportEvent passportEvent) {
        if (CollectionTool.isNotEmpty(passportListeners)) {
            for (IPassportListener loginListener : passportListeners) {
                try {
                    loginListener.onLoginFailure(passportEvent);
                } catch (RuntimeException e) {
                    log.error(e, "登录监听事件处理异常!{0}", loginListener.getClass());
                }
            }
        }
    }

    private void fireLogoutSuccesslListener(PassportEvent passportEvent) {
        if (CollectionTool.isNotEmpty(passportListeners)) {
            for (IPassportListener loginListener : passportListeners) {
                try {
                    loginListener.onLogoutSuccess(passportEvent);
                } catch (RuntimeException e) {
                    log.error(e, "退出监听事件处理异常!{0}", loginListener.getClass());
                }
            }
        }
    }


    public void setPassportListeners(List<IPassportListener> passportListeners) {
        this.passportListeners = passportListeners;
    }

    public void setRedisSessionDao(RedisSessionDao redisSessionDao) {
        this.redisSessionDao = redisSessionDao;
    }

    public ISysUserService getSysUserService(){
        return SpringTool.getBean(ISysUserService.class);
    }


}
