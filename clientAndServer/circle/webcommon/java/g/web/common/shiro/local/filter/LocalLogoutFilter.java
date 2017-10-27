package g.web.common.shiro.local.filter;

import g.web.common.shiro.common.delegate.IPassportDelegate;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.soul.commons.data.json.JsonTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.net.ServletTool;
import org.soul.model.session.SessionKey;
import org.soul.web.shiro.local.PassportResult;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by longer on 12/17/15.
 */
public class LocalLogoutFilter extends LogoutFilter {

    private static final Log log = LogFactory.getLog(LocalLogoutFilter.class);


    private IPassportDelegate passportDelegate;

    @Override
    protected boolean preHandle(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request= (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        passportDelegate.onLogoutDelegate(request,response);

        Subject subject = getSubject(request, response);
        String redirectUrl = getRedirectUrl(request, response, subject);
        try {
            subject.logout();
        } catch (SessionException ise) {
            log.debug("Encountered session exception during logout.  This can generally safely be ignored.", ise);
        }

        if (ServletTool.isAjaxRequest(request)) {
            String json = JsonTool.toJson(PassportResult.SUCCESS);
            response.getWriter().print(json);
            response.getWriter().flush();
        } else {
            issueRedirect(request, response, redirectUrl);
        }
        return false;
    }

    @Override
    protected String getRedirectUrl(ServletRequest servletRequest, ServletResponse servletResponse, Subject subject) {
        HttpServletRequest request= (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String url = request.getParameter("url");
        if (StringTool.isNotBlank(url)) {
            return url;
        }
        Session session = subject.getSession(false);
        if (session != null) {
            String entrance = (String) session.getAttribute(SessionKey.S_ENTRANCE);
            if (StringTool.isNotBlank(entrance)) {
                return passportDelegate.getLoginUrl(Integer.valueOf(entrance));
            }
        }
        return super.getRedirectUrl();
    }

    public void setPassportDelegate(IPassportDelegate passportDelegate) {
        this.passportDelegate = passportDelegate;
    }
}
