package g.service.chesscard.netHandles.douniu;

import g.model.bet.BetItem;
import g.model.bet.BetTypeEnum;
import g.model.bet.IBetItem;
import g.model.common.SessionKey;
import g.model.player.po.UserPlayer;
import g.service.chesscard.DouniuMultiple;
import g.service.chesscard.engine.listener.BetEvent;
import g.service.chesscard.engine.model.Desk;
import g.service.chesscard.engine.model.Player;
import g.service.chesscard.enums.SessionEnum;
import g.service.engine.listener.IBetListener;
import g.service.chesscard.enums.TipEnum;
import g.service.chesscard.exception.ChessCardException;
import g.service.chesscard.netBeans.common.NbBet;
import g.service.chesscard.netBeans.common.NbSelf;
import g.service.chesscard.netBeans.common.NbTip;
import g.service.chesscard.netBeans.douniu.NbBetOut;
import g.service.chesscard.netHandles.IWebSocketHandle;
import g.service.engine.manager.IBetManager;
import g.service.engine.manager.IPlayerManager;
import g.service.webSocket.context.WsSession;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Double on 2016/9/21.
 * 协议处理类: 投注
 */
public class BetHandle implements IWebSocketHandle<NbBet> {

    private static Log log = LogFactory.getLog(BetHandle.class);

    @Autowired
    private IBetManager betManager;

    @Autowired
    private IBetListener betListener;

    @Autowired
    private IPlayerManager playerManager;

    /** 测试预警用, 总共处理投注时间 */
    //AtomicLong totalBetUseime = new AtomicLong();
    /** 测试预警用, 总共处理投注个数 */
    //AtomicLong totalBetCount = new AtomicLong();
    @Override
    public void handle(WsSession session, NbBet bet) {
        //long now = System.currentTimeMillis();
        NbBetOut betOut = new NbBetOut();
        try {
            UserPlayer sysUser = (UserPlayer) session.getAttr(SessionKey.S_USER);
            Player player = playerManager.get(sysUser.getId());
            Integer deskId = session.getGroupId();
            if(deskId == null){//已经不在房间里
                return;
            }
            BetTypeEnum betType = BetTypeEnum.enumOf(bet.type);
            IBetItem betItem = BetItem.fromString(betType, bet.item);
            if (betItem == null) {
                throw new ChessCardException(TipEnum.BET_NOT_PERMIT_BET);
            }
            BetEvent betEvent = genBetEvent(sysUser.getId(), deskId, bet);
            betListener.beforeBet(betEvent);
            boolean isLastSec = betManager.betting(player, betType, betItem, Long.valueOf(bet.gold));
            betOut.bet = bet;
            NbSelf nbSelf = new NbSelf();
            nbSelf.balance = player.getCoin();
            nbSelf.usableBalance = calcUsableBalance(deskId,player,betType);
            betOut.nbSelf = nbSelf;
            if(!isLastSec){
                betListener.onBet(betEvent);
            }
        } catch (ChessCardException e) {
            betOut.tip = NbTip.tip(e.getTipEnum());
        } catch (Exception e) {
            log.error(e);
        }
        session.sendNetBean(betOut);
//        long time = totalBetUseime.addAndGet(System.currentTimeMillis() - now);
//        long count = totalBetCount.incrementAndGet();
//        if(count%500 == 0){
//            log.error("平均投注处理消耗:"+time/count);
//        }
    }

    private long calcUsableBalance(Integer deskId, Player player, BetTypeEnum betType) {
        Long coin = player.getCoin();
        Long betCoin = betManager.getUserBetCoin(deskId, player.getId());
        int multiple = DouniuMultiple.getDouniuMaxMultiple(betType.getCode());
        Long usableCoin = (coin - betCoin * (multiple - 1)) / multiple;

        if (usableCoin < 0) {
            usableCoin = 0l;
        }
        return usableCoin;

    }

    //input to bet event
    private BetEvent genBetEvent(Integer playerId, Integer deskId, NbBet bet) {
        BetEvent betEvent = new BetEvent();
        betEvent.setId(playerId);
        betEvent.setDeskId(deskId);
        betEvent.setMatchId(bet.matchId);
        betEvent.setType(bet.type);
        betEvent.setItem(bet.item);
        betEvent.setGold(bet.gold);
        return betEvent;
    }
}
