package g.service.chesscard.engine.bull100.message;

import g.model.bet.BetResultEnum;
import g.model.bet.BetTypeEnum;
import g.model.bet.SettleResultEnum;
import g.service.chesscard.engine.model.*;
import g.model.player.po.UserPlayer;
import g.service.chesscard.DouniuMultiple;
import g.service.chesscard.engine.EngineConst;
import g.service.chesscard.engine.sender.AbstractMessage;
import g.service.chesscard.enums.TipEnum;
import g.service.chesscard.netBeans.common.NbPlayerSettle;
import g.service.chesscard.netBeans.common.NbPoker;
import g.service.chesscard.netBeans.common.NbPokerSettle;
import g.service.chesscard.netBeans.common.NbTip;
import g.service.chesscard.netBeans.douniu.NbSettleMatchOut;
import g.service.chesscard.netBeans.douniu.NbSettlePlayerOut;
import g.service.engine.manager.IDeskManager;
import g.service.engine.manager.IPlayerManager;
import g.service.engine.message.MessageEntity;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by tony on 2016/10/21.
 * 结算消息下发
 */
public class Bull100SettleMessage extends AbstractMessage {

    private static final Log log = LogFactory.getLog(Bull100SettleMessage.class);

    private int deskId;
    private long matchId;

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public int getDeskId() {
        return deskId;
    }

    public void setDeskId(int deskId) {
        this.deskId = deskId;
    }

    @Autowired
    private IPlayerManager playerManager;

    @Autowired
    private IDeskManager deskManager;

    @Override
    public void handleMessage() {
        long logTime = System.currentTimeMillis();
        Desk desk = deskManager.byId(getDeskId());
        List<MessageEntity> messageEntityList = new ArrayList<>();
        MessageEntity messageEnity = new MessageEntity();
        messageEnity.setId(desk.getId());
        messageEnity.setGroup(true);
        NbSettleMatchOut settleMatchOut = new NbSettleMatchOut();
        ArrayList<NbPokerSettle> outPokers = new ArrayList<>();
        CardsHolder settlePoker = settleResult.getCardsHolder();
        for (CardsType cardsType : CardsType.values()) {
            NbPokerSettle nbPokerSettle = getNbPokerSettle(cardsType, settlePoker);
            if (nbPokerSettle != null) {
                outPokers.add(nbPokerSettle);
            }
        }
        settleMatchOut.pokers = outPokers;

        List<NbPlayerSettle> rankPlayer = new ArrayList<>();
        List<NbPlayerSettle> groupPlayer = new ArrayList<>();
        if (StringTool.equals(settleResult.getSettleResultEnum().getCode(), SettleResultEnum.SUCCESS.getCode())) {
            settleMatchOut.tip = NbTip.tip(TipEnum.SUCCESS);
            List<NbPlayerSettle> allPlayer = NbPlayerSettle.fromUserBet(settleResult.getUserBetResultList());
            for (NbPlayerSettle playerSettle : allPlayer) {
                int seatIndex = playerSettle.seatIndex;
                if (seatIndex != EngineConst.SEAT_NO_PLAYER) {
                    groupPlayer.add(playerSettle);
                }

                Integer userId = playerSettle.playerId;
                Player player = playerManager.get(userId);
                if (seatIndex == EngineConst.SEAT_NO_BANKER) {
                    if (desk.getDealerUserId() != null) {
                        playerSettle.balance = desk.getDealerBalanceCoin();
                        playerSettle.dealerBalance = player.getCoin();
                        continue;
                    } else {
                        playerSettle.balance = 88888888L;
                    }
                } else {
                    playerSettle.balance = player.getCoin();
                }
                if (playerSettle.playerId != -1) {

                    UserPlayer userPlayer = playerManager.getById(playerSettle.playerId);
                    playerSettle.icon = userPlayer.getAvatarUrl();
                    playerSettle.nickname = userPlayer.getNickname();

                    MessageEntity singlePlayerMessageEntity = new MessageEntity();
                    NbSettlePlayerOut settlePlayerOut = new NbSettlePlayerOut();
                    settlePlayerOut.player = playerSettle;
                    settlePlayerOut.players = rankPlayer;
                    settlePlayerOut.usableBalance = player.getCoin() / DouniuMultiple.getDouniuMaxMultiple(BetTypeEnum.BULL_100.getCode());
                    singlePlayerMessageEntity.setMsg(settlePlayerOut);
                    singlePlayerMessageEntity.setId(playerSettle.playerId);
                    singlePlayerMessageEntity.setGroup(false);
                    messageEntityList.add(singlePlayerMessageEntity);
                    rankPlayer.add(playerSettle);
                }
            }
            getRankingPlayers(rankPlayer);
            settleMatchOut.players = groupPlayer;
        } else if (StringTool.equals(settleResult.getSettleResultEnum().getCode(), SettleResultEnum.NOT_MATCH.getCode())) {
            settleMatchOut.tip = NbTip.tip(TipEnum.NOT_MATCH);
        } else if (StringTool.equals(settleResult.getSettleResultEnum().getCode(), SettleResultEnum.NOT_BET.getCode())) {
            settleMatchOut.tip = NbTip.tip(TipEnum.NOT_BET);
        }

        messageEnity.setMsg(settleMatchOut);
        setObjMessage(messageEnity);
        setListMessage(messageEntityList);
        if ((logTime = System.currentTimeMillis() - logTime) > 500) {
            log.error("结算时间过长,用时: " + logTime);
        }
    }
    
    private static final Comparator<NbPlayerSettle> NB_PLAYER_SETTLE_COMPARATOR = new Comparator<NbPlayerSettle>() {
        @Override
        public int compare(NbPlayerSettle o1, NbPlayerSettle o2) {
            int result = 0;
            final long o1Coin = o1.coin;
            final long o2Coin = o2.coin;
            if (o1Coin > o2Coin) {
                result = 1;
            } else if (o1Coin < o2Coin) {
                result = -1;
            }
            return result;
        }
    };

    private void getRankingPlayers(List<NbPlayerSettle> players) {
        Collections.sort(players, NB_PLAYER_SETTLE_COMPARATOR);
        if (players.size() > 4) {
            for (int i = 4; i < players.size(); i++) {
                players.remove(i);
            }
        }
    }


    private NbPokerSettle getNbPokerSettle(CardsType cardsType, CardsHolder cardsHolder) {
        BullCards cards = cardsHolder.getCards(cardsType);
        if (cards == null) {
            return null;
        }
        NbPokerSettle pokerSettle = new NbPokerSettle();
        pokerSettle.group = cardsType.getCode();
        pokerSettle.code = NbPoker.getCardsCode(cards.getCards());
        pokerSettle.type = cardsHolder.getCards(cardsType).getCardsPoint() + "";
        pokerSettle.odds = DouniuMultiple.getDouniuMultiple(Integer.valueOf(pokerSettle.type));
        pokerSettle.result = cardsHolder.getCards(cardsType).isBigger() ? BetResultEnum.WIN.getCode() : BetResultEnum.LOSE.getCode();
        pokerSettle.cardsPoint = cards.getCardsPoint();
        return pokerSettle;
    }

}
