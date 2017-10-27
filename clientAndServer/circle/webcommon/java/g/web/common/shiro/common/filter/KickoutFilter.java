package g.web.common.shiro.common.filter;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.soul.commons.data.json.JsonTool;
import org.soul.commons.init.context.CommonContext;
import org.soul.commons.locale.LocaleTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.net.ServletTool;
import org.soul.commons.support._Module;
import org.soul.model.error.ErrorCodeEnum;
import org.soul.model.error.ErrorMessage;
import org.soul.model.log.audit.enums.OpMode;
import org.soul.model.session.SessionKey;
import org.soul.web.session.KickOutMessage;
import org.soul.web.session.SessionManagerBase;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by longer on 5/29/15.
 */
public class KickoutFilter extends AccessControlFilter {

    private static final Log log = LogFactory.getLog(KickoutFilter.class);

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Subject subject = getSubject(request, response);
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            return true;
        }
        Session session = subject.getSession();

        KickOutMessage kickOutMessage = SessionManagerBase.getKickOut();
        if(kickOutMessage == null){
            return true;
        }

        try {
            subject.logout();
        } catch (Exception e) {
            log.error(e);
        }

        try {
            log.warn("强制踢出:用户[{0}]命中[待踢出]标识.位置{1},", session.getAttribute(SessionKey.S_USER_ID), CommonContext.get());
        } catch (UnknownSessionException e) {
        }
        ErrorMessage errorMessage = kickOutMsg(kickOutMessage);
        response.setStatus(Integer.valueOf(ErrorCodeEnum.SC_KICK_OUT.getCode()));

        if (ServletTool.isAjaxRequest(request)) {
            String json = JsonTool.toJson(errorMessage);
            response.getWriter().print(json);
            response.getWriter().flush();
            return false;
        }
        String kickOutUrl = ErrorCodeEnum.SC_KICK_OUT.getUrl();
        response.sendRedirect(request.getContextPath() + kickOutUrl+".html");



        return false;
    }


    /**
     * 踢出消息
     * @param kickOutMessage
     * @return
     */
    private ErrorMessage kickOutMsg(KickOutMessage kickOutMessage) {
        if (kickOutMessage == null) {
            return new ErrorMessage();
        }
        ErrorMessage errorMessage = new ErrorMessage();
        String title = "";
        String message = "";
        if (OpMode.AUTO.equals(kickOutMessage.getOpMode())){
            title = LocaleTool.tranMessage(_Module.Passport, "kick.out.auto.title");
            message = LocaleTool.tranMessage(_Module.Passport, "kick.out.auto.content",kickOutMessage.getOperateIp());
        } else {
            //手工踢出，但未给踢出原因的情况
            title =LocaleTool.tranMessage(_Module.Passport, "kick.out.manual.title");
            message = LocaleTool.tranMessage(_Module.Passport, "kick.out.manual.content");
        }
        errorMessage.setCode(ErrorCodeEnum.SC_KICK_OUT.getCode());
        errorMessage.setTitle(title);
        errorMessage.setMessage(message);
        return errorMessage;
    }

}