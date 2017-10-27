package g.service.chesscard.netHandles.douniu;

import g.model.common.SessionKey;
import g.service.chesscard.engine.model.Desk;
import g.model.player.po.UserPlayer;
import g.service.chesscard.engine.model.Player;
import g.service.chesscard.enums.TipEnum;
import g.service.chesscard.exception.ChessCardException;
import g.service.chesscard.netBeans.common.NbTip;
import g.service.chesscard.netBeans.douniu.NbDownDealerIn;
import g.service.chesscard.netBeans.douniu.NbDownDealerOut;
import g.service.chesscard.netHandles.IWebSocketHandle;
import g.service.engine.manager.*;
import g.service.webSocket.context.WsSession;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by tony on 2016/11/9.
 */
public class DownDealerHandle implements IWebSocketHandle<NbDownDealerIn> {

    private static Log log = LogFactory.getLog(UpDealerHandle.class);

    @Autowired
    private IPlayerManager playerManager;

    @Autowired
    private IDealerManager dealerManager;

    @Autowired
    private DealerOutValueHandle dealerOutValueHandle;

    @Override
    public void handle(WsSession session, NbDownDealerIn netBean) {

        NbDownDealerOut out = new NbDownDealerOut();
        UserPlayer sysUser = (UserPlayer) session.getAttr(SessionKey.S_USER);
        try {
            int userId = sysUser.getId();
            Player player = playerManager.get(userId);
            Desk desk = player.getDesk();
            desk.setUserTimeout(userId); // 玩家有触发行为都不踢
            dealerOutValueHandle.setDeskDealer(desk, out.deskDealer);

            String result = this.dealerManager.downDealer(player, true);
            if(result == null){//没在上庄列表或者不在庄不响应请求
                return;
            }
            dealerOutValueHandle.setPlayerDealer(userId, out.dealer);
            dealerOutValueHandle.setPlayerDealers(desk, out.players);
            out.tip = NbTip.tip(TipEnum.enumOf(result));
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
