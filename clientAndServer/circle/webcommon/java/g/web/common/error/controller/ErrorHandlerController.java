package g.web.common.error.controller;

import g.web.common.shiro.common.delegate.IPassportDelegate;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.soul.commons.data.json.JsonTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.net.ServletTool;
import org.soul.model.error.ErrorCodeEnum;
import org.soul.web.session.KickOutMessage;
import org.soul.web.session.SessionManagerBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by longer on 1/5/16.
 */
@Controller
@RequestMapping("/errors")
public class ErrorHandlerController {

    private static final Log log = LogFactory.getLog(ErrorHandlerController.class);

    @Autowired(required = false)
    private IPassportDelegate passportDelegate;


    private String getLoginUrl(HttpServletRequest request){
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        Integer entranceId = passportDelegate.getEntranceType(contextPath,uri);
        if (entranceId == null) {
            entranceId = 0;
        }
        String loginUrl = passportDelegate.getLoginUrl(entranceId);
        return loginUrl;
    }

    @RequestMapping("/403")
    public String no_auth(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String url = ErrorCodeEnum.SC_FORBIDDEN.getUrl();
        return url;
    }

    @RequestMapping("/404")
    public String no_exist(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String url = ErrorCodeEnum.SC_NOT_FOUND.getUrl();
        return url;
    }

    @RequestMapping("/602")
    public String serverBusy(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String url = ErrorCodeEnum.SC_SERVICE_BUSY.getUrl();
        return url;
    }

    @RequestMapping("/603")
    public String noExist(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String url = ErrorCodeEnum.SC_DOMAIN_NO_EXIST.getUrl();
        return url;
    }

    @RequestMapping("/604")
    public String tempTimeOut(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String url = ErrorCodeEnum.SC_DOMAIN_TEMP_TIMEOUT.getUrl();
        return url;
    }

    @RequestMapping("/605")
    public String ipConfine(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String url = ErrorCodeEnum.SC_IP_CONFINE.getUrl();
        return url;
    }

    /**
     * 踢出
     */
    @RequestMapping("/606")
    public String kickOut(HttpServletRequest request,HttpServletResponse response) throws IOException {

        String loginUrl = getLoginUrl(request);
        KickOutMessage kickOutMessage = null;
        Subject subject = ThreadContext.getSubject();
        if (subject.isAuthenticated()){
            try {
                kickOutMessage = SessionManagerBase.getKickOut();
                subject.logout();
            } catch (Exception e) {
                log.error(e);
            }
        } else {
            return "redirect:" + loginUrl;
        }
        if (kickOutMessage == null) {
            return "redirect:" + loginUrl;
        }

        if (ServletTool.isAjaxRequest(request)){
            response.getWriter().write(JsonTool.toJson(kickOutMessage));
            response.getWriter().flush();
            return "";
        } else {
            request.setAttribute("errorMessage",kickOutMessage);
            request.setAttribute("loginUrl",request.getContextPath() + loginUrl);
        }
        return ErrorCodeEnum.SC_KICK_OUT.getUrl();
    }

    /**
     * 站点维护
     */
    @RequestMapping("/607")
    public String maintain(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String url = ErrorCodeEnum.SC_SITE_MAINTAIN.getUrl();
        return url;
    }

}
