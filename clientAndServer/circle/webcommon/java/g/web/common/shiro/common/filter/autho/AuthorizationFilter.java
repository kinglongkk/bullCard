package g.web.common.shiro.common.filter.autho;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.soul.commons.bean.Pair;
import org.soul.commons.collections.MapTool;
import org.soul.commons.init.context.CommonContext;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.spring.utils.SpringTool;
import org.soul.iservice.security.privilege.ISysResourceService;
import org.soul.iservice.security.privilege.ISysUserService;
import org.soul.model.security.privilege.po.SysResource;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.model.security.privilege.vo.SysResourceVo;
import org.soul.model.session.SessionKey;
import org.soul.web.session.SessionManagerBase;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kevice on 2015/3/30 0030.
 */
public class AuthorizationFilter extends org.apache.shiro.web.filter.authz.AuthorizationFilter {

    private static final Log log = LogFactory.getLog(AuthorizationFilter.class);

    private Map<String, Pair<String, Boolean>> permissionMapping;
    private Map<String, Pair<String, Boolean>> patternPermissionMapping;

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Subject subject = getSubject(request,response);
        if (!subject.isAuthenticated()){
            return false;
        }

        Session session = subject.getSession();
        Map<String, Pair<String, Boolean>> userPermissionMapping = (Map<String, Pair<String, Boolean>>) session.getAttribute(SessionKey.S_USER_PERMISSIONS);
        SysUser sysUser = (SysUser) session.getAttribute(SessionKey.S_USER);
//        UserTypeEnum userTypeEnum = UserTypeEnum.enumOf(sysUser.getUserType());
        if (permissionMapping == null) {
            getPerimssionMapping(sysUser);
        }
        if (userPermissionMapping == null){
            userPermissionMapping = permissionMapping;
        }
        if (patternPermissionMapping == null) {
            getPatternPermissionMapping(userPermissionMapping);
        }
        return isPermitted(userPermissionMapping,request, subject);
    }

    private boolean isPermitted(Map<String, Pair<String, Boolean>> userPermissionMapping ,HttpServletRequest request, Subject subject) {
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String requestPath = requestURI.replaceFirst(contextPath+"/", "");
        Pair<String, Boolean> pair = userPermissionMapping.get(requestPath);
        String permission = null;
        if (pair == null) {
            pair = isPatternMatch(requestPath);
        }
        if (pair != null) {
            permission = pair.getKey();
            request.setAttribute(SessionKey.R_URL_PRIVILEGE, pair.getValue());
        }

        //用户权限不包含当前请求url时，需要判断该url是否需要权限才可访问
        if (StringTool.isBlank(permission)) {
            pair = permissionMapping.get(requestPath);
            if (pair != null)
                return false;
            log.warn("上下文:{0},用户:{1},权限:permission为空![允许访问],请求地址:{2}", CommonContext.get(), SessionManagerBase.getUserId(), requestURI);
            return true;
        }
        boolean rs = subject.isPermitted(permission);
        if (!rs) {
            log.warn("上下文:{0},用户:{1},权限:({2})[禁止访问],请求地址:{3}", CommonContext.get(), SessionManagerBase.getUserId(),permission,requestURI);
        }
        return rs;
    }

    /**
     * 是否符合正则表达式匹配
     * @param requestPath
     * @return
     */
    private Pair<String, Boolean> isPatternMatch(String requestPath) {
        if (MapTool.isNotEmpty(patternPermissionMapping)) {
            for (Map.Entry<String, Pair<String, Boolean>> entry : patternPermissionMapping.entrySet()) {
                if (requestPath.matches(entry.getKey())) {
                    Pair<String, Boolean> pair2 = entry.getValue();
                    return pair2;
                }
            }
        }
        return null;
    }

    /**
     * 获取URL所对应的所有权限列表
     *
     * @param user
     */
    private void getPerimssionMapping(SysUser user) {
        permissionMapping = new HashMap();
        SysResourceVo vo = new SysResourceVo();
        //warning: 如果页面存在不同的subsysCode用户,访问一个web应用时,逻辑有误
        vo.getSearch().setSubsysCode(user.getSubsysCode());
        List<SysResource> sysResources = getSysResourceService().bySubSysCode(vo);
        for (SysResource sysResource : sysResources) {
            if (StringTool.isNotBlank(sysResource.getUrl())) {
                permissionMapping.put(sysResource.getUrl(), new Pair(sysResource.getPermission(), sysResource.getPrivilege()));
            }
        }

    }

    /**
     * 获取用户权限中需要替换的特殊字符
     * @param userPermissionMapping
     */
    private void getPatternPermissionMapping(Map<String, Pair<String, Boolean>> userPermissionMapping){
        patternPermissionMapping = new HashMap();
        if(userPermissionMapping == null){
            return;
        }
        for (Map.Entry<String, Pair<String, Boolean>> entry : userPermissionMapping.entrySet()) {
            String url = entry.getKey();
            if (StringTool.isNotBlank(url) && url.contains("{")) {
                patternPermissionMapping.put(url.replaceAll("\\{\\w+\\}", "\\\\w+"), entry.getValue());
            }
        }
    }


    public ISysUserService getSysUserService() {
        return SpringTool.getBean(ISysUserService.class);
    }

    public ISysResourceService getSysResourceService() {
        return SpringTool.getBean(ISysResourceService.class);
    }

}
