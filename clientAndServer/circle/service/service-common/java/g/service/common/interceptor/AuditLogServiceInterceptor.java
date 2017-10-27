package g.service.common.interceptor;

import g.model.common.Audit;
import org.aspectj.lang.JoinPoint;
import org.soul.model.log.audit.annotation.IAudit;
import org.soul.service.interceptor.AbstractAuditLogServiceInterceptor;
import g.model.common.AuditAnnationSupport;
import g.service.common.support.JoinPointSupport;

import java.lang.reflect.Method;

/**
 * Created by longer on 7/15/15.
 */
public class AuditLogServiceInterceptor extends AbstractAuditLogServiceInterceptor {

    @Override
    protected IAudit getAuditAnnotation(JoinPoint jp) throws NoSuchMethodException {
        Method method = JoinPointSupport.getMethod(jp);
        if (method == null) {
            return null;
        }
        Audit annotation = method.getAnnotation(Audit.class);
        return AuditAnnationSupport.annotationToInterface(annotation);
    }

}