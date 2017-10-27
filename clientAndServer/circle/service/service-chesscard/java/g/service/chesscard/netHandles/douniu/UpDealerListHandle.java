package g.service.chesscard.netHandles.douniu;

import g.model.common.SessionKey;
import g.service.chesscard.engine.model.Desk;
import g.model.player.po.UserPlayer;
import g.service.chesscard.engine.model.Player;
import g.service.chesscard.enums.TipEnum;
import g.service.chesscard.exception.ChessCardException;
import g.service.chesscard.netBeans.common.NbTip;
import g.service.chesscard.netBeans.douniu.NbUpDealerListIn;
import g.service.chesscard.netBeans.douniu.NbUpDealerOut;
import g.service.chesscard.netHandles.IWebSocketHandle;
import g.service.engine.manager.IDeskManager;

import g.service.engine.manager.IPlayerManager;
import g.service.webSocket.context.WsSession;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by tony on 2016/11/14.
 * 上庄列表
 */
public class UpDealerListHandle implements IWebSocketHandle<NbUpDealerListIn> {

    private static Log log = LogFactory.getLog(UpDealerListHandle.class);

    @Autowired
    private IPlayerManager playerManager;

    @Autowired
    private IDeskManager deskManager;

    @Autowired
    private DealerOutValueHandle dealerOutValueHandle;

    @Override
    public void handle(WsSession session, NbUpDealerListIn netBean) {

        NbUpDealerOut out = new NbUpDealerOut();
        UserPlayer sysUser = (UserPlayer) session.getAttr(SessionKey.S_USER);
        try {

            int userId = sysUser.getId();
            Player player = playerManager.get(userId);
            Desk desk = player.getDesk();
            desk.setUserTimeout(userId); // 玩家有触发行为都不踢
            dealerOutValueHandle.setDeskDealer(desk, out.deskDealer);
            dealerOutValueHandle.setPlayerDealer(userId, out.dealer);
            dealerOutValueHandle.setPlayerDealers(desk, out.players);
        } catch (ChessCardException e) {
            out.tip = NbTip.tip(e.getTipEnum());
            log.error(e.getMessage());
        } catch (Exception e) {
            out.tip = NbTip.tip(TipEnum.SUCCESS.SERVER_ERROR);
            log.error(e);
        }
        session.sendNetBean(out);
    }
}
