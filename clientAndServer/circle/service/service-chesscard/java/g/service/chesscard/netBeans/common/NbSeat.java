package g.service.chesscard.netBeans.common;

import g.model.bet.BetTypeEnum;
import g.service.chesscard.engine.model.GameDeskSeat;
import g.model.player.po.UserPlayer;
import g.service.chesscard.DouniuMultiple;
import g.service.chesscard.engine.EngineConst;
import g.service.engine.manager.IPlayerManager;
import g.service.webSocket.codeCreator.NetBeanField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.soul.commons.spring.utils.SpringTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Double on 2016/9/21.
 */
public class NbSeat {

    public static String[] ICONS = {
            "comm_1_icon_head_01.jpg",
            "comm_1_icon_head_02.jpg",
            "comm_1_icon_head_03.jpg",
            "comm_1_icon_head_04.jpg",
            "comm_1_icon_head_05.jpg",
            "comm_1_icon_head_06.jpg",
            "comm_1_icon_head_07.jpg",
            "comm_1_icon_head_08.jpg",
            "comm_1_icon_head_09.jpg",
            "comm_1_icon_head_unknown1.jpg",
    };

    @Override
    public String toString() {
        return "\n" + ToStringBuilder.reflectionToString(this);
    }

    /**
     * 座位号
     */
    @NetBeanField
    public int seatIndex;

    /**
     * 玩家信息
     */
    @NetBeanField
    public NbPlayer player;


    public static NbSeat fromUserPlayer(UserPlayer userPlayer) {
        if (userPlayer == null) {
            return null;
        }
        NbSeat seat = new NbSeat();
        NbPlayer player = new NbPlayer();
        seat.seatIndex = EngineConst.SEAT_NO_MYSELF;   //默认自己
        seat.player = player;
        player.playerId = userPlayer.getId();
        player.coin = userPlayer.getCoin();
        player.usableBalance = userPlayer.getCoin() / DouniuMultiple.getDouniuMaxMultiple(BetTypeEnum.BULL_100.getCode());
        player.nickname = userPlayer.getNickname();
        player.icon = userPlayer.getAvatarUrl();
//        player.icon = ICONS[RandomUtils.nextInt(0,ICONS.length)];
        return seat;
    }

    public static List<NbSeat> fromUserPlayer(List<GameDeskSeat> gameDeskUsers) {
        //TODO: Double 暂时处理,为了合并GameDeskSeat与GameDeskSeat
        IPlayerManager playerManager = (IPlayerManager) SpringTool.getBean("playerManager");
        List<NbSeat> seats = new ArrayList<>();
        for (GameDeskSeat gameDeskUser : gameDeskUsers) {
            UserPlayer userPlayer = playerManager.getById(gameDeskUser.getUserId());
            NbSeat seat = fromUserPlayer(userPlayer);
            if (seat != null) {
                seat.seatIndex = gameDeskUser.getSeatNo();
                seats.add(seat);
            }
        }
        return seats;
    }

    public static List<NbSeat> fromUserPlayerSeat(List<GameDeskSeat> gameDeskSeats) {
        //TODO: Double 暂时处理,为了合并GameDeskSeat与GameDeskSeat
        IPlayerManager playerManager = (IPlayerManager) SpringTool.getBean("playerManager");
        List<NbSeat> seats = new ArrayList<>();
        for (GameDeskSeat gameDeskSeat : gameDeskSeats) {
            UserPlayer userPlayer = playerManager.getById(gameDeskSeat.getUserId());
            NbSeat seat = fromUserPlayer(userPlayer);
            if (seat != null) {
                seat.seatIndex = gameDeskSeat.getSeatNo();
                seats.add(seat);
            }
        }
        return seats;
    }

}
