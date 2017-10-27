package g.service.chesscard.engine.sender;

import g.service.engine.message.ISendMessageSessionWrapper;
import g.service.webSocket.context.WsSession;
import g.service.webSocket.context.WsSessionManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by tony on 2016/10/20.
 */
public class SimpleSendMessageSessionWrapper implements ISendMessageSessionWrapper {

    @Autowired
    private WsSessionManager wsSessionManager;

    @Override
    public void send2Group(Integer groupId, Object obj) {
        wsSessionManager.sendNetBeanGroup(groupId, obj);
    }

    @Override
    public void send2Single(Integer userId, Object obj) {
        WsSession session = wsSessionManager.getUserSession(userId);
        if (session != null) {
            session.sendNetBean(obj);//有可能在服务器重启后,还继续向该用户发送消息
        }

    }
}
