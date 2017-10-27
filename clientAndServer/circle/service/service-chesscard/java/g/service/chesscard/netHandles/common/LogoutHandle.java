package g.service.chesscard.netHandles.common;

import g.model.common.SessionKey;
import g.model.player.po.UserPlayer;
import g.service.chesscard.netBeans.common.NbLogout;
import g.service.chesscard.netHandles.IWebSocketHandle;
import g.service.engine.manager.IRoomManager;
import g.service.webSocket.context.WsSession;
import g.service.webSocket.context.WsSessionManager;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Double on 2016/9/21.
 */
public class LogoutHandle implements IWebSocketHandle<NbLogout> {

    private static Log log = LogFactory.getLog(LogoutHandle.class);

    @Autowired
    private IRoomManager roomManager;

    @Autowired
    private WsSessionManager wsSessionManager;

    @Override
    public void handle(WsSession wsSession, NbLogout netbean) {
        UserPlayer sysUser = (UserPlayer) wsSession.getAttr(SessionKey.S_USER);
        if (sysUser != null && sysUser.getId() != null
                && sysUser.getId().equals(netbean.userId)) {
            roomManager.exit(netbean.userId);
            wsSessionManager.removeSession(wsSession.getWsChannel().getId());
            wsSession.setAttr(SessionKey.S_USER, null);
        }
    }

}
