package g.service.chesscard.netHandles.douniu;

import g.model.common.SessionKey;
import g.service.chesscard.engine.model.Desk;
import g.service.chesscard.engine.model.GameDealer;
import g.model.player.po.UserPlayer;
import g.service.chesscard.engine.model.Player;
import g.service.chesscard.enums.TipEnum;
import g.service.chesscard.exception.ChessCardException;
import g.service.chesscard.netBeans.common.NbTip;
import g.service.chesscard.netBeans.douniu.NbUpDealerIn;
import g.service.chesscard.netBeans.douniu.NbUpDealerOut;
import g.service.chesscard.netHandles.IWebSocketHandle;
import g.service.engine.manager.*;
import g.service.webSocket.context.WsSession;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by tony on 2016/11/9.
 * 上庄
 */
public class UpDealerHandle implements IWebSocketHandle<NbUpDealerIn> {

    private static Log log = LogFactory.getLog(UpDealerHandle.class);

    @Autowired
    private IDealerManager dealerManager;

    @Autowired
    private IPlayerManager playerManager;


    @Autowired
    private DealerOutValueHandle dealerOutValueHandle;

    @Override
    public void handle(WsSession session, NbUpDealerIn netBean) {

        NbUpDealerOut out = new NbUpDealerOut();
        UserPlayer sysUser = (UserPlayer) session.getAttr(SessionKey.S_USER);
        try {
            int userId = sysUser.getId();
            Player player = playerManager.get(userId);
            Desk desk = player.getDesk();
            desk.setUserTimeout(userId); // 玩家有触发行为都不踢
            dealerOutValueHandle.setDeskDealer(desk, out.deskDealer);

            GameDealer gameDealer = new GameDealer();
            gameDealer.setDealerCoin(netBean.coin);
            gameDealer.setDeskId(desk.getId());
            gameDealer.setUserId(userId);

            String result = this.dealerManager.applyDealer(gameDealer);
            if (StringTool.equals(TipEnum.SUCCESS.getCode(), result)) {
                dealerOutValueHandle.setPlayerDealer(userId, out.dealer);
                dealerOutValueHandle.setPlayerDealers(desk, out.players);
            } else {
                out.tip = NbTip.tip(TipEnum.enumOf(result));
            }
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
