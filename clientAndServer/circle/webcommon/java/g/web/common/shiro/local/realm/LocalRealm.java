package g.web.common.shiro.local.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.soul.commons.bean.Pair;
import org.soul.commons.cache.CacheKey;
import org.soul.commons.init.context.CommonContext;
import org.soul.commons.lang.ArrayTool;
import org.soul.commons.lang.SerializationTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.iservice.passport.IPassportService;
import org.soul.iservice.passport.exception.AccountDisabledException;
import org.soul.iservice.passport.exception.AccountInActiveException;
import org.soul.iservice.passport.exception.AccountPasswordException;
import org.soul.iservice.security.privilege.ISysUserService;
import org.soul.model.passport.vo.PassportVo;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.model.session.SessionKey;
import org.soul.web.session.SessionManagerBase;
import org.soul.web.shiro.local.authc.LocalToken;
import org.soul.web.support.BaseWebConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springside.modules.nosql.redis.JedisTemplate;
import g.model.player.PlayerBean;

import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.FailedLoginException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by longer on 12/17/15.
 */
public class LocalRealm extends AuthorizingRealm {

    private static final Log log = LogFactory.getLog(LocalRealm.class);

    @Autowired(required = false)
    @Qualifier("jedisTemplateApi")
    private JedisTemplate jedisTemplateApi;

    private BaseWebConf baseWebConf;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private IPassportService passportService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            return null;
        }

        Session session = subject.getSession(false);
        SysUser sysUser = (SysUser) session.getAttribute(SessionKey.S_USER);
        String entrance = (String) session.getAttribute(SessionKey.S_ENTRANCE);

        //菜单权限
        Map<String, Pair<String,Boolean>> urlPermission = fetchUrlPermission(sysUser);
        session.setAttribute(SessionKey.S_USER_PERMISSIONS, urlPermission);

        //功能权限
        Set<String> permissions = findPermissions(sysUser, entrance);

        SimpleAuthorizationInfo simpleAuthorizationInfo =  new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permissions == null ? new HashSet<String>(0) : permissions);
        return simpleAuthorizationInfo;
    }

    private Set<String> findPermissions(SysUser sysUser, String entrance) {
        PassportVo passportVo = new PassportVo();
        passportVo.getSearch().setUsername(sysUser.getUsername());
        passportVo.getSearch().setUserType(sysUser.getUserType());
        passportVo.getSearch().setSiteId(sysUser.getSiteId());
        passportVo.getSearch().setSubsysCode(baseWebConf.getSubsysCode());
        return getPassportService().findPermissions(passportVo);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        LocalToken localToken = (LocalToken) token;
        Integer entrance = localToken._getEntrance();
        PassportVo passportVo = null;
        if (StringTool.isNotBlank(localToken.getToken())){
            passportVo = byToken(localToken);
        } else {
            passportVo = byDb(localToken);
        }

        SysUser sysUser = passportVo.getResult();
        Subject subject = ThreadContext.getSubject();
        Session session = subject.getSession(false);
        if(session == null) {
            session = subject.getSession(true);
        }
        //warning:此处一定要调用SessionManagerBase.setUser,方法内有特定逻辑
        SessionManagerBase.setUser(sysUser);
        session.setAttribute(SessionKey.S_USER_ID, sysUser.getId());
        session.setAttribute(SessionKey.S_ENTRANCE, String.valueOf(entrance));

        Map<String, Pair<String,Boolean>> urlPermission = fetchUrlPermission(sysUser);
        session.setAttribute(SessionKey.S_USER_PERMISSIONS, urlPermission);

        String principal = CacheKey.getCacheKey(
                String.valueOf(sysUser.getSiteId()),
                String.valueOf(sysUser.getId()));
        return new SimpleAuthenticationInfo(principal,passportVo.getResult().getPassword(),getName());
    }

    private PassportVo byToken(LocalToken localToken) {
        byte[] data = jedisTemplateApi.get(localToken.getToken().getBytes());
        if (ArrayTool.isEmpty(data)){
            throw new AuthenticationException(new CredentialExpiredException("token登录,token过期"));
        }
        PlayerBean playerBean = (PlayerBean) SerializationTool.deserialize(data);
        if (playerBean == null) {
            throw new AuthenticationException(new CredentialExpiredException("token登录,token过期"));
        }
        localToken.setUsername(playerBean.getPassportVo().getResult().getUsername());
        localToken.setPassword(playerBean.getPassportVo().getResult().getPassword().toCharArray());
        return playerBean.getPassportVo();
    }

    private PassportVo byDb(LocalToken localToken) {
        String username = localToken.getUsername();
        String password = new String(localToken.getPassword());
        PassportVo passportVo = PassportVo.loginParamVo(username, password,localToken._getEntrance());
        passportVo.getSearch().setSubsysCode(baseWebConf.getSubsysCode());
        log.debug("进入用户名密码验证");
        try {
            try {
                CommonContext.get().setEntrance(localToken._getEntrance());
                passportVo = getPassportService().login(passportVo);
            } finally {
                CommonContext.get().setEntrance(null);
            }
            if (passportVo.getResult()== null) {
                throw new FailedLoginException("Password does not match value on record.");
            }
        } catch (AccountNotFoundException e){
            log.error(e, e.getMessage());
            throw new AuthenticationException(e);
        } catch (AccountPasswordException e){
            log.error(e, e.getMessage());
            throw new AuthenticationException(e);
        } catch (AccountInActiveException e){
            log.error(e, e.getMessage());
            throw new AuthenticationException(e);
        } catch (AccountLockedException e){
            log.error(e, e.getMessage());
            throw new AuthenticationException(e);
        } catch (AccountDisabledException e){
            log.error(e, e.getMessage());
            throw new AuthenticationException(e);
        } catch (Exception e) {
            log.error(e, "其它异常");
            throw new AuthenticationException(e);
        } finally {
        }
        return passportVo;
    }

    /**
     * 菜单权限
     * @param sysUser
     */
    private Map<String, Pair<String, Boolean>> fetchUrlPermission(SysUser sysUser) {
        PassportVo vo = new PassportVo();
        vo.getSearch().setSubsysCode(sysUser.getSubsysCode());
        vo.getSearch().setSiteId(sysUser.getSiteId());
        vo.getSearch().setUsername(sysUser.getUsername());
        vo.getSearch().setUserType(sysUser.getUserType());
        return getPassportService().findPermissionMapping(vo);
    }

    @Override
    protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
        return principals.getPrimaryPrincipal().toString();
    }

    public ISysUserService getSysUserService(){
        return sysUserService;
    }

    public IPassportService getPassportService() {
        return passportService;
    }

    public void setBaseWebConf(BaseWebConf baseWebConf) {
        this.baseWebConf = baseWebConf;
    }
}
