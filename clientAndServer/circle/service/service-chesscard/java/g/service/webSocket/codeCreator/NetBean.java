package g.service.webSocket.codeCreator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * 网络传输实体对象
 *
 * @author MK.
 * @date 2016-8-28
 */
@Target(ElementType.TYPE)
public @interface NetBean {
    /**
     * sendBy类型: NetBean的子对象不需要event,不需要被单独解析作为一个事件
     */
    public static final int SENDBY_NONE = 0;
    /**
     * sendBy类型: 服务端发出的消息,客户端js一定要有解析handle处理.
     */
    public static final int SENDBY_SERVER = 1;
    /**
     * sendBy类型: 客户端发出的消息,服务端js一定要有解析handle处理.
     */
    public static final int SENDBY_CLIENT = 2;
    /**
     * sendBy类型: 服务端客户端都有可能发出的消息,服务端客户端js都要有解析handle处理.
     */
    public static final int SENDBY_ALL = 3;

    /**
     * 事件号
     */
    public int event() default -1;

    /**
     * 发送消息者,默认服务端客户端都可以发送
     */
    public int sendBy() default SENDBY_NONE;
}