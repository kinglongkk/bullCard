package g.service.chesscard.netHandles.bao;

import g.service.chesscard.engine.model.*;
import g.service.chesscard.engine.bull100.message.Bull100SettleMessage;
import g.service.chesscard.engine.listener.MatchEvent;
import g.service.chesscard.engine.schedule.DeskSchedule;
import g.service.chesscard.netBeans.bao.NbBullBaoStartMatchOut;
import g.service.chesscard.netBeans.bao.NbBullBaoStartSettleOut;
import g.service.chesscard.netBeans.common.NbMatch;
import g.service.chesscard.netBeans.common.NbSeat;
import g.service.chesscard.netHandles.douniu.Bull100MatchHandle;
import g.service.chesscard.netHandles.douniu.DealerOutValueHandle;
import g.service.engine.listener.IMatchListener;
import g.service.engine.manager.IDeskManager;
import g.service.engine.manager.IDeskScheduleManager;
import g.service.engine.manager.ISettleManager;
import g.service.engine.message.IMessageSender;
import g.service.webSocket.context.WsSessionManager;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.spring.utils.SpringTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/1/11.
 */
public class BullBaoMatchHandle extends Bull100MatchHandle implements IMatchListener {

    private static Log log = LogFactory.getLog(BullBaoMatchHandle.class);

    @Autowired
    @Qualifier("syncMessageSender")
    private IMessageSender messageSender;

    @Autowired
    private IDeskManager deskManager;

    @Autowired
    @Qualifier("bullBaoSettleManager")
    private ISettleManager settleManager;

    @Autowired
    private IDeskScheduleManager deskScheduleManager;

    @Autowired
    private WsSessionManager wsSessionManager;

    @Autowired
    private DealerOutValueHandle dealerOutValueHandle;

    @Override
    public void onStart(MatchEvent matchEvent) {
        log.info("产生事件:桌号:{0},赛事{1}:ID:{2}", matchEvent.getDeskId(), "启动", matchEvent.getMatchId());
        kickoutPlayer(matchEvent);

        DeskSchedule deskSchedule = (DeskSchedule) deskScheduleManager.getSchedule(matchEvent.getDeskId());
        if (deskSchedule != null) {
            NbBullBaoStartMatchOut newMatchOut = new NbBullBaoStartMatchOut();
            newMatchOut.poker = BullBaoPoker.getCard();
            Desk desk = deskManager.byId(matchEvent.getDeskId());
            desk.setPreCards(String.valueOf(newMatchOut.poker));
            desk.reAllocate();

            NbMatch nbMatch = NbMatch.fromMatch(matchEvent.getMatch());
            List<GameDeskSeat> deskSeats = desk.getGameDeskSeatList();
            newMatchOut.seats = (ArrayList<NbSeat>) NbSeat.fromUserPlayerSeat(deskSeats);
            newMatchOut.match = nbMatch;
            dealerOutValueHandle.setDeskDealer(desk, newMatchOut.match.dealer);
            wsSessionManager.sendNetBeanGroup(matchEvent.getDeskId(), newMatchOut);
        }
    }

    @Override
    public void onSettle(MatchEvent matchEvent) {
        log.info("产生事件:桌号:{0},赛事{1}:ID:{2}", matchEvent.getDeskId(), "结算", matchEvent.getMatchId());
        try {
            Bull100SettleMessage settleMessage = (Bull100SettleMessage) SpringTool.getBean("settleMessage");
            Desk desk = deskManager.byId(matchEvent.getDeskId());
            String cards = desk.getPreCards();

            SettleResult settleResult = settleManager.newSettle(matchEvent.getDeskId(), matchEvent.getMatchId(), cards);
            if(settleResult.getCardsHolder() != null){
                settleMessage.setSettleResult(settleResult);
                settleMessage.setDeskId(matchEvent.getDeskId());
                settleMessage.setMatchId(matchEvent.getMatchId());
                messageSender.sendMessage(settleMessage);
            }

            //warning: 只能先清理投注数据,才能踢人
            desk.clearMatchData();

            cleanupPlayer(matchEvent.getDeskId());
        } catch (Exception e) {
            log.error(e);
        }
    }

    @Override
    public void onStop(MatchEvent matchEvent) {
        NbBullBaoStartSettleOut out = new NbBullBaoStartSettleOut();
        wsSessionManager.sendNetBeanGroup(matchEvent.getDeskId(), out);
    }
}
