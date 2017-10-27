package g.web.common.shiro.local.filter;

import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class LocalRegistFilter extends PathMatchingFilter{

    private String registUrl;

    public String getRegistUrl() {
        return registUrl;
    }

    public void setRegistUrl(String registUrl) {
        this.registUrl = registUrl;
    }

    @Override
    protected boolean pathsMatch(String path, ServletRequest request) {
        String requestURI = this.getPathWithinApplication(request);
        return this.pathsMatch("/share*",requestURI);
    }

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        WebUtils.issueRedirect(request, response, getRegistUrl());
        return false;
    }
}
