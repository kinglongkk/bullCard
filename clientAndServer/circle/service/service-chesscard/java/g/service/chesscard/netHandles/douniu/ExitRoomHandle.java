package g.service.chesscard.netHandles.douniu;

import g.model.common.SessionKey;
import g.service.chesscard.engine.model.Desk;
import g.model.player.po.UserPlayer;
import g.service.chesscard.engine.model.Player;
import g.service.chesscard.enums.TipEnum;
import g.service.chesscard.netBeans.common.NbTip;
import g.service.chesscard.netBeans.douniu.NbExitRoomIn;
import g.service.chesscard.netBeans.douniu.NbExitRoomOut;
import g.service.chesscard.netHandles.IWebSocketHandle;
import g.service.engine.manager.IDeskManager;
import g.service.engine.manager.IPlayerManager;

import g.service.engine.manager.IRoomManager;
import g.service.webSocket.context.WsSession;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Double on 2016/9/21.
 * 协议处理类: 退出房间
 */
public class ExitRoomHandle implements IWebSocketHandle<NbExitRoomIn> {

    private static Log log = LogFactory.getLog(ExitRoomHandle.class);

    @Autowired
    private IRoomManager roomManager;

    @Autowired
    private IPlayerManager playerManager;


    @Autowired
    private IDeskManager deskManager;

    @Override
    public void handle(WsSession session, NbExitRoomIn exitRoomIn) {
        UserPlayer sysUser = (UserPlayer) session.getAttr(SessionKey.S_USER);
        try {
            NbExitRoomOut exitRoomOut = new NbExitRoomOut();
            Integer userId = sysUser.getId();
            Player player = playerManager.get(userId);
            Integer deskId = player.getDeskId();
            if(deskId == null){
                return;
            }
            Desk desk = deskManager.byId(deskId);
            boolean isDealer = desk.isDealer(userId);
            boolean isInMatch = desk != null && desk.isInMatch(sysUser.getId());
            if (exitRoomIn.isBreak || (!isDealer && !isInMatch)) {
                this.roomManager.exit(sysUser.getId());
                session.removeChannelInGroup(deskId);
                exitRoomOut.tip = NbTip.tip(TipEnum.SUCCESS);
                exitRoomOut.exitRoomIn = exitRoomIn;
            } else {
                if (isDealer) {
                    exitRoomOut.tip = NbTip.tip(TipEnum.EXIT_ROOM_DEALER);
                } else {
                    exitRoomOut.tip = NbTip.tip(TipEnum.EXIT_ROOM_IN_MATCH);
                }
            }
            exitRoomOut.exitRoomIn = exitRoomIn;
            session.sendNetBean(exitRoomOut);
        } catch (Exception e) {
            log.error(e, "退出房间失败!");
        }

    }
}
