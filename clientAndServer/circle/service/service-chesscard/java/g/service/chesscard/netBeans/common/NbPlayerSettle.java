package g.service.chesscard.netBeans.common;

import g.model.chesscard.mo.UserBetResult;
import g.service.webSocket.codeCreator.NetBeanField;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Double on 2016/10/17.
 * 通讯协议:结算玩家
 */
public class NbPlayerSettle {

    @Override
    public String toString() {
        return "\n" + ToStringBuilder.reflectionToString(this);
    }

    /**
     * 玩家ID
     */
    @NetBeanField
    public int playerId;

    @NetBeanField
    public String nickname = "";

    @NetBeanField
    public String icon = "";

    /**
     * 输赢额
     */
    @NetBeanField
    public long coin;

    /**
     * 座位号
     */
    @NetBeanField
    public int seatIndex;

    /**
     * 余额
     */
    @NetBeanField
    public long balance;


    @NetBeanField
    public long dealerBalance;


    public static List<NbPlayerSettle> fromUserBet(List<UserBetResult> list) {
        List<NbPlayerSettle> resultList = new ArrayList<>();
        for (UserBetResult userBet : list) {
            NbPlayerSettle playerSettle = new NbPlayerSettle();
            //@TODO MK　TO Double\Tony userBet.getSeatNo()不能为空,这里暂时判断为空的话是其他人的座位
            playerSettle.seatIndex = userBet.getSeatNo() == null ? -2 : userBet.getSeatNo();
            playerSettle.coin = userBet.getIncCoin();
            playerSettle.playerId = userBet.getUserId() == null ? -1 : userBet.getUserId();
            resultList.add(playerSettle);
        }
        return resultList;
    }


}
