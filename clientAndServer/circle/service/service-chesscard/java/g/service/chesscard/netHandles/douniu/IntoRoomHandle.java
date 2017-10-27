package g.service.chesscard.netHandles.douniu;

import g.model.bet.BetTypeEnum;
import g.model.common.SessionKey;
import g.model.match.po.Match;
import g.model.player.po.UserPlayer;
import g.service.chesscard.DouniuMultiple;
import g.service.chesscard.engine.factory.IModelFactory;
import g.service.chesscard.engine.listener.SeatEvent;
import g.service.chesscard.engine.model.Desk;
import g.service.chesscard.engine.model.GameDeskSeat;
import g.service.chesscard.engine.model.Player;
import g.service.chesscard.engine.model.Room;
import g.service.chesscard.enums.ParamEnum;
import g.service.chesscard.enums.TipEnum;
import g.service.chesscard.exception.ChessCardException;
import g.service.chesscard.netBeans.common.NbMatch;
import g.service.chesscard.netBeans.common.NbSeat;
import g.service.chesscard.netBeans.common.NbSelf;
import g.service.chesscard.netBeans.common.NbTip;
import g.service.chesscard.netBeans.douniu.NbIntoRoomIn;
import g.service.chesscard.netBeans.douniu.NbIntoRoomOut;
import g.service.chesscard.netBeans.douniu.NbRoomActionIn;
import g.service.chesscard.netHandles.IWebSocketHandle;
import g.service.engine.cache.ParamManager;
import g.service.engine.listener.ISeatListener;
import g.service.engine.manager.IDeskManager;
import g.service.engine.manager.IPlayerManager;
import g.service.engine.manager.IRoomManager;
import g.service.webSocket.context.WsSession;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Double on 2016/9/21.
 * 协议处理类: 进入房间
 */
public class IntoRoomHandle implements IWebSocketHandle<NbIntoRoomIn> {

    private static Log log = LogFactory.getLog(IntoRoomHandle.class);

    @Autowired
    private IModelFactory modelFactory;

    @Autowired
    private ISeatListener seatListener;

    @Autowired
    private DealerOutValueHandle dealerOutValueHandle;

    @Autowired
    private ParamManager paramManager;
    @Autowired
    private IPlayerManager playerManager;
    @Autowired
    private IDeskManager deskManager;

    public void handle(WsSession session, NbRoomActionIn roomActionIn) {
        UserPlayer sysUser = (UserPlayer) session.getAttr(SessionKey.S_USER);
        Player player = playerManager.get(sysUser.getId());
        Integer deskId = session.getGroupId();
        if(deskId == null){//已经不在房间里
            return;
        }
        Desk desk = deskManager.byId(deskId);
        desk.setUserTimeout(sysUser.getId()); // 玩家有投注行为都不踢
    }

    @Override
    public void handle(WsSession session, NbIntoRoomIn intoRoomIn) {
        UserPlayer sysUser = (UserPlayer) session.getAttr(SessionKey.S_USER);
        NbIntoRoomOut intoRoomOut = new NbIntoRoomOut();
        try {
            intoRoomOut.roomId = intoRoomIn.roomId;
            IRoomManager roomManager = modelFactory.getRoomManager(intoRoomIn.roomId);
            Room room = roomManager.get(intoRoomIn.roomId);
            Player player = room.into(sysUser.getId());
            Desk desk = player.getDesk();
            session.addToGroup(desk.getId());

            Match match = desk.getMatch();
            List<GameDeskSeat> gameDeskSeats = desk.getDeskOnSeatUserList();
            intoRoomOut.seats = (ArrayList<NbSeat>) NbSeat.fromUserPlayer(gameDeskSeats);
            intoRoomOut.seats.add(NbSeat.fromUserPlayer(sysUser));
            intoRoomOut.match = NbMatch.fromMatch(match);
            intoRoomOut.roomName = room.getName();
            intoRoomOut.betChip = room.getBetChip();
            intoRoomOut.betTimes = room.getBetTimes();
            intoRoomOut.minDealerCoin = room.getDealerBlance();
            intoRoomOut.betAreas = (HashMap<String, String>) paramManager.get(ParamEnum.BULL_BAO_BEI);
            if (match.getIsRunning()) { // （押宝）投注阶段补发一张牌
                intoRoomOut.poker = desk.getPreCards();
            }
            dealerOutValueHandle.setDeskDealer(desk, intoRoomOut.match.dealer);

            NbSelf nbSelf = new NbSelf();
            nbSelf.balance = player.getCoin();
            nbSelf.usableBalance = player.getCoin() / DouniuMultiple.getDouniuMaxMultiple(BetTypeEnum.BULL_100.getCode());
            intoRoomOut.nbSelf = nbSelf;
            seatListener.onSeat(new SeatEvent(sysUser.getId(), desk.getId()));
        } catch (ChessCardException e) {
            intoRoomOut.tip = NbTip.tip(e.getTipEnum());
            log.warn(e.getMessage());
        } catch (Exception e) {
            log.error(e);
            intoRoomOut.tip = NbTip.tip(TipEnum.SUCCESS.SERVER_ERROR);
        }
        session.sendNetBean(intoRoomOut);
    }

}
