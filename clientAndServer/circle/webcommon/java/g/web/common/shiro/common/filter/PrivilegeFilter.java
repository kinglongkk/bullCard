package g.web.common.shiro.common.filter;

import g.model.common.PrivilegeStatusEnum;
import g.web.common.cache.ParamTool;
import org.soul.commons.lang.BooleanTool;
import org.soul.commons.lang.DateTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.net.ServletTool;
import org.soul.model.error.ErrorCodeEnum;
import org.soul.model.session.SessionKey;
import org.soul.web.filter.FilterTool;
import org.soul.web.session.SessionManagerBase;
import g.model.SiteParamEnum;
import g.web.common.filter.PrivilegeThread;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Created by tony on 15-8-11.
 */
public class PrivilegeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (FilterTool.isErrorRequest(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        Object needCheck = request.getAttribute(SessionKey.R_URL_PRIVILEGE);
        if (needCheck != null && BooleanTool.toBoolean(needCheck.toString())) {
            if (ServletTool.isAjaxRequest(request)) {
                if (isChecked()) {
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
                response.setStatus(Integer.valueOf(ErrorCodeEnum.SC_PRIVILEGE.getCode()));
                return;
            } else {
                doCheckPrivilege(servletRequest, (HttpServletResponse) servletResponse);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void doCheckPrivilege(ServletRequest servletRequest, HttpServletResponse servletResponse) {
        AsyncContext sc = servletRequest.startAsync(servletRequest, servletResponse);
        Thread t = new Thread(new PrivilegeThread(sc));
        t.start();
        while (true) {
            try {
                Thread.sleep(1000);
                if (isChecked()) break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isChecked() {
        Map privilegeUsers;
        privilegeUsers = SessionManagerBase.getPrivilegeStatus();
        if (privilegeUsers != null) {
            String userId = SessionManagerBase.getUserId() + "";
            Map data = (Map) privilegeUsers.get(userId);
            final String state = (String) data.get("state");
            if (state.equals(PrivilegeStatusEnum.STATUS_OK.getCode())) {
                //上次验证成功，判断是否在不需要输入密码的时间内。
                //TODO cj get from settings.暂时设为5分钟
                final Date lastTime = (Date) data.get("time");
                long l = DateTool.minutesBetween(new Date(), lastTime);
                String paramValue = ParamTool.getSysParam(SiteParamEnum.SETTING_PRIVILAGE_PASS_TIME).getParamValue();
                Integer privilegeTime=0;
                if(StringTool.isNotBlank(paramValue)){
                    privilegeTime=Integer.parseInt(paramValue);
                }
                if (l <= privilegeTime) {
                    //还在允许的时间范围内
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void destroy() {

    }
}
