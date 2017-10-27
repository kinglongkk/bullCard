package g.web.common.filter.intercepter;

import g.model.common.Const;
import g.model.common.SessionKey;
import g.model.sys.vo.SysSiteVo;
import g.service.sys.ISysSiteService;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.soul.commons.init.context.CommonContext;
import org.soul.commons.init.context.ContextParam;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.model.common.BaseObjectVo;
import org.soul.web.locale.SessionLocaleResolver;
import org.soul.web.shiro.common.servlet.intercepter.ISubjectInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by longer on 1/24/16.
 */
public class SubjectIntercepter implements ISubjectInterceptor {

    private SessionLocaleResolver localeResolver;

    private static final Log LOG = LogFactory.getLog(SubjectIntercepter.class);

    private Object lock = new Object();


    private SysSiteVo sysSiteVo;

    @Autowired
    private ISysSiteService sysSiteService;

    public BaseObjectVo beforeCreate(HttpServletRequest request) {
        if (sysSiteVo == null){
            synchronized (lock) {
                SysSiteVo sysSiteVo = new SysSiteVo();
                sysSiteVo.getSearch().setId(Const.Default_Site_Id);
                sysSiteVo = sysSiteService.get(sysSiteVo);
                this.sysSiteVo = sysSiteVo;
                return sysSiteVo;
            }
        }
        return sysSiteVo;
    }

    @Override
    public void afterCreate(HttpServletRequest request, HttpServletResponse response, Subject subject) {
        ThreadContext.bind(subject);
        ContextParam contextParam = new ContextParam();
        CommonContext.set(contextParam);
        Session session = subject.getSession(false);
        LocaleContext localeContext = localeResolver.resolveLocaleContext(request);
        localeResolver.setLocaleContext(request, response, localeContext);

        contextParam.setSiteId(Integer.valueOf(1));
        contextParam.setLocale(Const.Default_Locale);
        contextParam.setSiteLocale(Const.Default_Locale);
        contextParam.setSiteTimeZone(Const.TimeZone_0);

        if (sysSiteVo == null) {
            beforeCreate(request);
        }
        if (session != null) {
            session = subject.getSession(true);
            session.setAttribute(SessionKey.S_SYS_SITE_PO,sysSiteVo.getResult());
        }
    }


    public void setLocaleResolver(SessionLocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }
}
