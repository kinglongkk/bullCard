package g.service.webSocket.codeCreator;


import java.lang.reflect.Method;

/**
 * msg消息组件处理方法信息,仅限于NetBeanTool使用,业务无需关注
 *
 * @author MK.
 * @date 2016-9-6
 */
public class HandleInfo {
    public Class handle;
    Method method;

    HandleInfo(Class handle, Method method) {
        this.handle = handle;
        this.method = method;
    }
}