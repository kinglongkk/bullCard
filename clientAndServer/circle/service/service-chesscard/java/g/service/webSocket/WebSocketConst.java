package g.service.webSocket;

/**
 * websocket通讯相关的常量
 */
public class WebSocketConst {
    /**
     * web客户端连到服务器后会发送一个标志位数据"0"表示,服务端会发送一个通讯数据定义
     */
    public final static String CLIENT_FLAG_WEB = "0";
    /**
     * java客户端连到服务器后会发送一个标志位数据"1"表示,共享使用同一份java版的通讯数据定义,不需要发送
     */
    public final static String CLIENT_FLAG_JAVA = "1";
}
