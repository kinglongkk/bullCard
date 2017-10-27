package g.service.chesscard.netHandles;

import g.service.webSocket.context.WsSession;

/**
 * Created by Double on 2016/9/21.
 * 统一接口
 */
public interface IWebSocketHandle<T> {

    void handle(WsSession wsSession, T netBean);
}
