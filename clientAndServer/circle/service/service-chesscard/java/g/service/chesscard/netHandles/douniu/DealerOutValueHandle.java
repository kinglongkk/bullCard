package g.service.chesscard.netHandles.douniu;

import g.model.bet.BetTypeEnum;
import g.service.chesscard.engine.model.Desk;
import g.service.chesscard.engine.model.GameDealer;
import g.model.player.po.UserPlayer;
import g.service.chesscard.DouniuMultiple;
import g.service.chesscard.engine.model.Player;
import g.service.chesscard.netBeans.common.NbDealer;
import g.service.engine.manager.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by tony on 2017/1/5.
 * 庄消息处理超类
 */
public class DealerOutValueHandle {

    @Autowired
    private IPlayerManager playerManager;

    @Autowired
    private IDealerManager dealerManager;


    /**
     * 设置本桌庄家
     *
     * @param desk
     */
    public void setDeskDealer(Desk desk, NbDealer deskDealer) {
        Integer dealerUserId = desk.getDealerUserId();
        if (dealerUserId != null) {
            deskDealer.usableBalance = desk.getDealerBalanceCoin();
            deskDealer.playerId = dealerUserId;
            UserPlayer userPlayer = playerManager.getById(dealerUserId);
            if (userPlayer != null) {
                deskDealer.coin = userPlayer.getCoin();
                deskDealer.icon = userPlayer.getAvatarUrl();
                deskDealer.nickname = userPlayer.getNickname();
            }
        } else {
            deskDealer.coin = 88888888L;
            deskDealer.usableBalance = 88888888L;
            deskDealer.icon = "images/head/comm_1_icon_head_08.jpg";
            deskDealer.nickname = "系统庄";
        }
    }

    /**
     * 设置当前玩家上庄数据
     *
     * @param userId
     */
    public void setPlayerDealer(int userId, NbDealer playerDealer) {
        Player player = playerManager.get(userId);
        long coin = player.getCoin();
        long userBalance = coin / DouniuMultiple.getDouniuMaxMultiple(BetTypeEnum.BULL_100.getCode());

        playerDealer.maxDealerCoin = coin;
        playerDealer.usableBalance = userBalance;
        playerDealer.coin = coin;
        playerDealer.playerId = userId;
    }

    /**
     * 设置上庄列表
     *
     * @param desk
     */
    public void setPlayerDealers(Desk desk, List<NbDealer> dealers) {
        List<GameDealer> gameDealers = dealerManager.getDealerList(desk.getId());
        List<NbDealer> nbDealers = NbDealer.fromGameDealers(gameDealers);
        for (NbDealer dealer : nbDealers) {
            UserPlayer userPlayer = this.playerManager.getById(dealer.playerId);
            dealer.icon = userPlayer.getAvatarUrl();
            dealer.nickname = userPlayer.getNickname();
            dealers.add(dealer);
        }
    }


}
