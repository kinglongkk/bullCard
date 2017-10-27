package g.service.chesscard;

import g.service.chesscard.engine.model.Player;
import g.model.common.SessionKey;
import g.model.player.po.UserPlayer;
import g.service.chesscard.enums.TipEnum;
import g.service.chesscard.netBeans.common.NbLogin;
import g.service.chesscard.netBeans.common.NbTip;

import g.service.engine.manager.IPlayerManager;
import g.service.engine.manager.IRoomManager;
import g.service.webSocket.codeCreator.IWsHandle;
import g.service.webSocket.context.WsSession;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 网络用户事件
 *
 * @author MK
 * @date 2016-09-06
 */
public class WsHandle implements IWsHandle {

    private static Log log = LogFactory.getLog(WsHandle.class);

    @Autowired
    private IRoomManager roomManager;

    @Autowired
    private IPlayerManager playerManager;

    @Override
    public void connected(WsSession session) {
        //服务端被客户端连接后会等待接收客户端标志后才加入session,所以服务端的connected暂时没用上
    }

    @Override
    public void disconnected(WsSession session) {
        UserPlayer userPlayer = (UserPlayer) session.remove(SessionKey.S_USER);
        if (userPlayer == null) {
            return;
        }
        Integer userId = userPlayer.getId();
        Player player = playerManager.get(userId);
        log.warn("玩家:{0},会话断开连接,位置信息为:{1}-{2}-{3}", userPlayer.getId(), player.getRoomId(), player.getDeskId(), player.getSeatId());
        String sid = player.getSessionId();
        if (sid == null || session.getId().equals(sid)) {
            roomManager.exit(userPlayer.getId());
        }
    }

    @Override
    public boolean onMessage(WsSession session, Object msg) {
        if (session.getAttr(SessionKey.S_USER) == null && msg.getClass() != NbLogin.class) {
            session.sendNetBean(NbTip.tip(TipEnum.LOGIN_NO));
            return false;
        }
        return true;
    }
}
