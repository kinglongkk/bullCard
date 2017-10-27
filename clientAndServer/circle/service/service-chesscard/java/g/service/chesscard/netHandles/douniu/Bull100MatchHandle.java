package g.service.chesscard.netHandles.douniu;

import g.service.chesscard.engine.model.Desk;
import g.service.chesscard.engine.model.GameDeskSeat;
import g.service.chesscard.engine.bull100.message.*;
import g.service.chesscard.engine.listener.MatchEvent;
import g.service.chesscard.engine.model.Player;
import g.service.chesscard.engine.model.SettleResult;
import g.service.chesscard.engine.schedule.DeskSchedule;
import g.service.chesscard.netBeans.common.NbMatch;
import g.service.chesscard.netBeans.common.NbSeat;
import g.service.chesscard.netBeans.douniu.NbStartMatchOut;
import g.service.engine.listener.IMatchListener;
import g.service.engine.manager.*;
import g.service.engine.message.IMessageSender;
import g.service.webSocket.context.WsSessionManager;
import org.soul.commons.collections.CollectionTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.spring.utils.SpringTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Created by Double on 2016/9/21.
 * 协议处理类: 新的游戏
 */
public class Bull100MatchHandle implements IMatchListener {

    private static Log log = LogFactory.getLog(Bull100MatchHandle.class);

    @Autowired
    private IPlayerManager playerManager;

    @Autowired
    private IDeskScheduleManager deskScheduleManager;

    @Autowired
    private WsSessionManager wsSessionManager;

    @Autowired
    private IDeskManager deskManager;

    @Autowired
    @Qualifier("syncMessageSender")
    private IMessageSender messageSender;

    @Autowired
    private IDealerManager dealerManager;

    @Autowired
    private IRoomManager roomManager;

    @Autowired
    private DealerOutValueHandle dealerOutValueHandle;

    @Autowired
    @Qualifier("bull100SettleManager")
    private ISettleManager settleManager;

    @Override
    public void onStart(MatchEvent matchEvent) {
        try {
            log.info("产生事件:桌号:{0},赛事{1}:ID:{2}", matchEvent.getDeskId(), "启动", matchEvent.getMatchId());
            kickoutPlayer(matchEvent);
            Desk desk = this.deskManager.byId(matchEvent.getDeskId());
            desk.cleanupDealer();
            desk.reAllocate();

            Bull100UpDealerMessage upDealerMessage = (Bull100UpDealerMessage) SpringTool.getBean("upDealerMessage");
            upDealerMessage.setDeskId(matchEvent.getDeskId());
            messageSender.sendMessage(upDealerMessage);


            DeskSchedule deskSchedule = (DeskSchedule) deskScheduleManager.getSchedule(matchEvent.getDeskId());
            if (deskSchedule != null) {
                NbStartMatchOut newMatchOut = new NbStartMatchOut();
                NbMatch nbMatch = NbMatch.fromMatch(matchEvent.getMatch());

                //TODO: 获取座位信息
                List<GameDeskSeat> deskSeats = desk.getGameDeskSeatList();
                newMatchOut.seats = (ArrayList<NbSeat>) NbSeat.fromUserPlayerSeat(deskSeats);
                newMatchOut.match = nbMatch;
                dealerOutValueHandle.setDeskDealer(desk, newMatchOut.match.dealer);
                wsSessionManager.sendNetBeanGroup(matchEvent.getDeskId(), newMatchOut);
            }
        } catch (Exception e) {
            log.error(e);
        }

    }

    /**
     * 踢出玩家
     *
     * @param matchEvent
     */
    protected void kickoutPlayer(MatchEvent matchEvent) {
        Desk desk = deskManager.byId(matchEvent.getDeskId());
        Set<String> timeoutUsers = desk.getTimeoutUsers();
        if (CollectionTool.isNotEmpty(timeoutUsers)) {
            Bull100TimeoutMessage timeoutMessage = (Bull100TimeoutMessage) SpringTool.getBean("timeoutMessage");
            timeoutMessage.setTimeoutUsers(timeoutUsers);
            messageSender.sendMessage(timeoutMessage);
            for (String userId : timeoutUsers) {
                log.warn("桌子:[0],强制踢出玩家:{1}", matchEvent.getDeskId(), userId);
                roomManager.exit(Integer.valueOf(userId));
            }
        }
    }

    @Override
    public void onStop(MatchEvent matchEvent) {
        log.info("产生事件:桌号:{0},赛事{1}:ID:{2}", matchEvent.getDeskId(), "停止", matchEvent.getMatchId());
    }

    @Override
    public void onSettle(MatchEvent matchEvent) {
        try {
            log.info("产生事件:桌号:{0},赛事{1}:ID:{2}", matchEvent.getDeskId(), "结算", matchEvent.getMatchId());

            Bull100SettleMessage settleMessage = (Bull100SettleMessage) SpringTool.getBean("settleMessage");
            SettleResult settleResult = settleManager.newSettle(matchEvent.getDeskId(), matchEvent.getMatchId(), null);
            if(settleResult.getCardsHolder() != null){
                settleMessage.setSettleResult(settleResult);
                settleMessage.setDeskId(matchEvent.getDeskId());
                settleMessage.setMatchId(matchEvent.getMatchId());
                messageSender.sendMessage(settleMessage);
            }

            Bull100WarningDealerMessage warningDealerMessage = (Bull100WarningDealerMessage) SpringTool.getBean("warningDealerMessage");
            warningDealerMessage.setDeskId(matchEvent.getDeskId());
            messageSender.sendMessage(warningDealerMessage);


            Desk desk = this.deskManager.byId(matchEvent.getDeskId());
            Integer dealerUserId = desk.getDealerUserId();
            if (dealerUserId != null) {
                Player player = playerManager.get(dealerUserId);
                String flag = this.dealerManager.getDownDealerFlag(dealerUserId);
                if (StringTool.isNotEmpty(flag)) {
                    this.dealerManager.downDealer(player );

                }
            }
            desk.cleanupDealer();
            desk.clearMatchData();
            cleanupPlayer(matchEvent.getDeskId());
        } catch (Exception e) {
            log.error(e);
        }

    }



    protected void cleanupPlayer(Integer deskId) {
        Desk desk = deskManager.byId(deskId);
        desk.clearDisconnectUsers();
    }

    @Override
    public void onFinish(MatchEvent matchEvent) {
//        //TODO:Double 清理断掉的玩家
//        deskManager.clearDisconnectUsers(matchEvent.getDeskId());
//

    }

}
