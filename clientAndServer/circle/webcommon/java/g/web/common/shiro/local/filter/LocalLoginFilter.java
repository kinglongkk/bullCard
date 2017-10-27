package g.web.common.shiro.local.filter;

import g.model.common.Const;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.soul.commons.data.json.JsonTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.locale.LocaleTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.net.ServletTool;
import org.soul.commons.support._Module;
import org.soul.iservice.passport.exception.AccountPasswordException;
import org.soul.model.passport.vo.PassportVo;
import org.soul.model.security.privilege.vo.SysUserVo;
import org.soul.model.session.SessionKey;
import org.soul.web.session.SessionManagerBase;
import org.soul.web.shiro.local.PassportResult;
import org.soul.web.shiro.local.authc.LocalToken;
import g.common.security.AuthTool;
import g.web.common.shiro.common.delegate.IPassportDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by longer on 12/17/15.
 */
public class LocalLoginFilter extends FormAuthenticationFilter {


    private Log log = LogFactory.getLog(getClass());
    private String captchaParam = "captcha";

    private IPassportDelegate passportDelegate;

    @Autowired
    private AuthTool authTool;

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest , ServletResponse servletResponse , Object mappedValue) {
        Subject subject = getSubject(servletRequest,servletResponse);
        if (!subject.isAuthenticated()) {
            return false;
        }
            //已经登录后，再次提交登录，直接跳转到成功页面
        if (isLoginRequest(servletRequest,servletResponse)){
            if (isLoginSubmission(servletRequest, servletResponse)) {
                //case: already login,but submit login again(open two page for login,then login one by one)
                HttpServletRequest request= (HttpServletRequest)servletRequest;
                if (StringTool.isEmpty(getToken(request))) {
                    try {
                        WebUtils.issueRedirect(servletRequest, servletResponse, getSuccessUrl());
                    } catch (IOException e) {
                        log.error(e);
                    }
                }
                return false;

            }
            //warning: go to login controller then it will redirect to /
        }
        return true;
    }

//    @Override
//    protected boolean isLoginSubmission(ServletRequest servletRequest, ServletResponse servletResponse) {
//        HttpServletRequest request= (HttpServletRequest)servletRequest;
//        String token = request.getParameter("token");
//        boolean isPost = (request instanceof HttpServletRequest) && WebUtils.toHttp(request).getMethod().equalsIgnoreCase(POST_METHOD);
//        boolean isToken = StringTool.isNotEmpty(token);
//        return isPost || isToken;
//
//    }

    @Override
    protected boolean executeLogin(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request= (HttpServletRequest)servletRequest;
        HttpServletResponse response= (HttpServletResponse)servletResponse;
        AuthenticationToken token = createToken(request, response);
        if (StringTool.isBlank(getToken(request))){
            PassportResult passportResult = isValidToken(request,(LocalToken)token);
            String message = "请输入正确的用户名和密码!";
            if (!passportResult.getSuccess()){
                passportResult.setMessage(message);
                doDispatcher(request,response,passportResult);
                return false;
            }
        }

        try {
            Subject subject = getSubject(request, response);
            subject.login(token);
            newSession(subject);
            return onLoginSuccess(token, subject, request, response);
        } catch (AuthenticationException e) {
            return onLoginFailure(token, e, request, response);
        }
    }

    private void newSession(Subject subject) {
        Session session = subject.getSession();
        Map<Object,Object> attributes = new HashMap<>();
        for (Object key : session.getAttributeKeys()) {
            attributes.put(key,session.getAttribute(key));
        }
        session.stop();
        session = subject.getSession(true);
        for (Object key : attributes.keySet()) {
            session.setAttribute(key,attributes.get(key));
        }
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse response) {
        HttpServletRequest request= (HttpServletRequest)servletRequest;
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        Integer entranceId = passportDelegate.getEntranceType(contextPath,uri);

        String token = getToken(request);
        String username = getUsername(request);
        String password = getPassword(request);
        String captcha = getCaptch(request);
        LocalToken localToken = new LocalToken();
        localToken.setToken(token);
        localToken.setUsername(username);
        localToken.setPassword(password == null ? null : authTool.md5SysUserPassword(password, username).toCharArray());
        localToken.setCaptcha(captcha);
        localToken.setRememberMe(isRememberMe(request));
        localToken.setHost(getHost(request));
        localToken._setEntrance(entranceId);
        return localToken;
    }

    private String getToken(HttpServletRequest request){
        return request.getParameter("token");
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request= (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        closeCaptcha();

        String successUrlOld = getSuccessUrl();

        passportDelegate.onLoginSuccessDelegate(request);

        if (ServletTool.isAjaxRequest(request)) {
            String json = JsonTool.toJson(PassportResult.SUCCESS);
            response.getWriter().print(json);
            response.getWriter().flush();
        } else {
             passportDelegate.redirectTo(request, response, successUrlOld) ;
        }
        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException ae, ServletRequest servletRequest, ServletResponse servletResponse) {
        HttpServletRequest request= (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        LocalToken localToken = (LocalToken)token;
        boolean isOpenCaptcha = isOpenCaptcha(request);
        Integer loginErrorTimes = 0;
        String message = "";
        PassportResult passportResult = new PassportResult();
        passportResult.setIsOpenCaptcha(isOpenCaptcha);
        Throwable e = ae.getCause();
        if (e != null && e instanceof LoginException) {
            String code = Const.I18N_AUTHENTICATION_FAILURE_PREFIX + e.getClass().getSimpleName();
            message = LocaleTool.tranMessage(_Module.Passport, code);

            if (e instanceof AccountPasswordException) {
                AccountPasswordException exception = ((AccountPasswordException)e);
                SysUserVo sysUserVo = new SysUserVo();
                if(PassportVo.MASTER.equals(localToken._getEntrance())){//站长账号保存在运营商库
                    sysUserVo._setDataSourceId(SessionManagerBase.getSiteParentId());
                }
                sysUserVo.getSearch().setId(exception.getUserId());
                passportDelegate.incLoginErrorTimes(sysUserVo);
                loginErrorTimes = exception.getLoginErrorTimes() + 1;
                localToken.setId(exception.getUserId());
            }
        }

        passportDelegate.onLoginFailureDelegate(request, response, localToken.getId(), localToken.getUsername(), loginErrorTimes);
        passportResult.setMessage(message);
        passportResult.setUsername(((LocalToken) token).getUsername());
        doDispatcher(request,response,passportResult);
        return false;
    }

    private void doDispatcher(HttpServletRequest request, HttpServletResponse response,PassportResult passportResult) {
        try {
            if (ServletTool.isAjaxRequest(request)) {
                passportResult.setUsername(null); //ajax不回传用户名
                passportResult.setPassword(null); //ajax不回传密码
                String json = JsonTool.toJson(passportResult);
                response.getWriter().print(json);
                response.getWriter().flush();
            } else {
                request.getSession().setAttribute(SessionKey.SK_Passport_Rs, passportResult);
                response.sendRedirect(request.getRequestURI());
            }
        } catch (Exception exp) {
            log.error(exp);
        }
    }

    private PassportResult isValidToken(HttpServletRequest request, LocalToken token){
        PassportResult passportResult = new PassportResult();
        passportResult.setSuccess(true);
        passportResult.setIsOpenCaptcha((Boolean) getAttribute(SessionKey.S_IS_CAPTCHA_CODE));
        passportResult.setUsername(getUsername(request));
        passportResult.setPassword(getPassword(request));
        validRequired(passportResult, getUsernameParam(), token.getUsername());
        validRequired(passportResult,getPasswordParam(),token.getPassword() == null ? null : new String(token.getPassword()));
        if (passportResult.getIsOpenCaptcha()) {
            boolean rs = validRequired(passportResult,getCaptchaParam(),token.getCaptcha());
            if (rs) {
                String captchaSessionKey = passportDelegate.getCaptchaSessionKey();
                String captchaSessionVal = (String) getAttribute(captchaSessionKey);
                validCaptcha(request, passportResult,getCaptchaParam(),getCaptch(request),captchaSessionVal);
            }
        }
        return passportResult;
    }

    private boolean validRequired(PassportResult passportResult,String propName,String propVal) {
        if (StringTool.isBlank(propVal)) {
            String prop = propName;
            String code = Const.I18N_AUTHENTICATION_FAILURE_REQUIRED + prop ;
            String message = LocaleTool.tranMessage(_Module.Passport, code);
            passportResult.setSuccess(false);
            passportResult.getPropMessages().put(prop,message);
            return false;
        }
        return true;
    }

    /**
     * 是否有效的验证码
     * @param request
     * @param captcha
     * @param captchaSessionVal
     * @return
     */
    private boolean validCaptcha(HttpServletRequest request, PassportResult passportResult, String propName, String captcha, String captchaSessionVal){
        if (StringTool.isBlank(captchaSessionVal)){
            return true;
        }
        if (StringTool.equals(captcha.toUpperCase(), captchaSessionVal.toUpperCase())){
            return true;
        }
        String prop = propName;
        String code = Const.I18N_AUTHENTICATION_FAILURE_PREFIX + prop ;
        String message = LocaleTool.tranMessage(_Module.Passport, code);
        passportResult.setSuccess(false);
        passportResult.getPropMessages().put(prop,message);
        return false;
    }

    /**
     * 是否开启验证码
     * @param request
     */
    private boolean isOpenCaptcha(HttpServletRequest request) {
        Integer loginErrorTimesInSession = (Integer) getAttribute(SessionKey.S_LOGIN_ERROR_TIMES);
        if (loginErrorTimesInSession == null) {
            loginErrorTimesInSession = 0;
        }
        loginErrorTimesInSession = loginErrorTimesInSession + 1;
        setAttribute(SessionKey.S_LOGIN_ERROR_TIMES, loginErrorTimesInSession);

        if (loginErrorTimesInSession >= g.model.common.Const.AUTHENTICATION_FAILURE_CAPTCHA_SHOWN_LIMIT){
            setAttribute(SessionKey.S_IS_CAPTCHA_CODE, Boolean.TRUE);
            return true;
        }
        return false;
    }

    private void closeCaptcha(){
        setAttribute(SessionKey.S_IS_CAPTCHA_CODE, null);
        setAttribute(passportDelegate.getCaptchaSessionKey(), null);
    }

    public void setPassportDelegate(IPassportDelegate IPassportDelegate) {
        this.passportDelegate = IPassportDelegate;
    }

    protected String getCaptch(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }

    private Object getAttribute(String sessionKey){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        return session.getAttribute(sessionKey);
    }
    private void setAttribute(String sessionKey,Object value){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute(sessionKey, value);
    }

    public String getCaptchaParam() {
        return captchaParam;
    }

    /**
     * Sets the request parameter name to look for when acquiring the username.  Unless overridden by calling this
     * method, the default is <code>username</code>.
     *
     * @param captchaParam the name of the request param to check for acquiring the username.
     */
    public void setCaptchaParam(String captchaParam) {
        this.captchaParam = captchaParam;
    }


}


