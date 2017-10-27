package g.service.common.support;

import com.alibaba.dubbo.common.utils.ReflectUtils;
import org.aspectj.lang.JoinPoint;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;

import java.lang.reflect.Method;

/**
 * Created by longer on 8/20/15.
 */
public class JoinPointSupport {

    private static Log log = LogFactory.getLog(JoinPointSupport.class);

    public static Method getMethod(JoinPoint jp) throws NoSuchMethodException {
        String methodName = jp.getSignature().getName();
        Class[] argsType = getArgsTypes(jp);
        String className = jp.getTarget().getClass().getSimpleName();
        Class[] interfaces = jp.getTarget().getClass().getInterfaces();
        Method method = null;
        for (Class anInterface : interfaces) {
            if (anInterface.getSimpleName().indexOf(className) != -1) {
                try {
                    method = ReflectUtils.findMethodByMethodSignature(anInterface, methodName, getArgsTypes(argsType));
                } catch (Exception e) {
                    log.error(e, "第一层接口找不到该方法:{0}", methodName);
                }
                break;
            }
        }
        return method;
    }

    /**
     * 获取方法参数类型
     * @param jp
     * @return
     */
    private static Class[] getArgsTypes(JoinPoint jp) {
        Class[] argsType = new Class[jp.getArgs().length];
        for (int i = 0; i < jp.getArgs().length; i++) {
            Object obj = jp.getArgs()[i];
            argsType[i] = obj.getClass();
        }
        return argsType;
    }

    private static String[] getArgsTypes(Class[] argCls) {
        String[] parameterTypes = new String[argCls.length];
        int i = 0;
        for (Class cls : argCls) {
            parameterTypes[i++] = cls.getName();
        }
        return parameterTypes;
    }
}
