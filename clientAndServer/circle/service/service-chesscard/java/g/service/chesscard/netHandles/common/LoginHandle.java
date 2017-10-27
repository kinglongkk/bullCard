package g.service.chesscard.netHandles.common;

import g.model.common.SessionKey;
import g.model.player.po.UserPlayer;
import g.service.chesscard.IChesscardService;
import g.service.chesscard.engine.model.Player;
import g.service.chesscard.enums.SessionEnum;
import g.service.chesscard.enums.TipEnum;
import g.service.chesscard.netBeans.common.NbLogin;
import g.service.chesscard.netBeans.common.NbTip;
import g.service.chesscard.netHandles.IWebSocketHandle;
import g.service.common.IUserPlayerService;
import g.service.engine.manager.IPlayerManager;
import g.service.engine.manager.IRoomManager;
import g.service.webSocket.context.WsSession;
import g.service.webSocket.context.WsSessionManager;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Double on 2016/9/21.
 */
public class LoginHandle implements IWebSocketHandle<NbLogin> {

    private static Log log = LogFactory.getLog(LoginHandle.class);

    @Autowired
    private IChesscardService chesscardService;

    @Autowired
    private IUserPlayerService userPlayerService;

    @Autowired
    private WsSessionManager wsSessionManager;

    @Autowired
    private IPlayerManager playerManager;


    @Autowired
    private IRoomManager roomManager;

    @Override
    public void handle(WsSession wsSession, NbLogin login) {
        if (wsSession.getAttr(SessionKey.S_USER) != null) {
            log.warn("已登录.");
//            return;
        }
        boolean rs = chesscardService.verifyToken(login.token);
        if (!rs) {
            wsSession.sendNetBean(NbTip.tip(TipEnum.LOGIN_FAIL));

        }
        saveUserToSession(wsSession, login.userId);
        Integer userId = login.userId;
        Player player = playerManager.get(userId);
        wsSession.setAttr(SessionEnum.IS_AI.getKey(), player.getIsAi());
        wsSession.sendNetBean(NbTip.tip(TipEnum.SUCCESS));
        if (player.isAbnormalOutGame()) {
            // 不正常退出
            roomManager.exit(login.userId);
        }
    }

    /**
     * save user to session
     *
     * @param wsSession
     * @param userId
     * @return
     */
    public UserPlayer saveUserToSession(WsSession wsSession, Integer userId) {
        UserPlayer userPlayer = userPlayerService.selectUserPlayerInfoById(userId);
        userPlayer.setSessionId(wsSession.getId());
        wsSession.setAttr(SessionKey.S_USER, userPlayer);
        this.playerManager.init(userPlayer);
        wsSessionManager.putUserSession(userId, wsSession);
        return userPlayer;
    }
}
