package g.service.webSocket.codeCreator;

import g.service.webSocket.context.WsSession;

/**
 * websocket 网络连接断开处理
 *
 * @author MK.
 * @date 2016-8-28
 */
public interface IWsHandle {
    /**
     * websocket 握手成功,真正连接上.之后才可以发送消息
     */
    void connected(WsSession session);

    /**
     * 客户端主动断开连接触发事件
     */
    void disconnected(WsSession session);

    /**
     * 消息过滤，未登陆时除了登陆对象都返回false
     *
     * @return 返回true时才会把消息对象分配给netHandle处理
     */
    boolean onMessage(WsSession wsSession, Object msg);
}
