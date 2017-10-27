package g.service.chesscard.netBeans.common;

import g.model.chesscard.mo.UserBet;
import g.service.webSocket.codeCreator.NetBeanField;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Double on 2016/9/21.
 * 投注通讯协议
 */
public class NbBet {

    @Override
    public String toString() {
        return "\n" + ToStringBuilder.reflectionToString(this);
    }

    /**
     * 玩家ID
     */
    @NetBeanField(required = false)
    public Integer playerId;

    /**
     * 座位号
     */
    @NetBeanField(required = false)
    public Integer seatIndex;

    /**
     * 赛事ID
     */
    @NetBeanField
    public long matchId;

    /**
     * 投注类型
     */
    @NetBeanField(required = false)
    public String type = "";

    /**
     * 投注项
     */
    @NetBeanField(required = false)
    public String item = "";

    /**
     * 投注额
     */
    @NetBeanField
    public int gold;

    public static ArrayList fromUserBets(List<UserBet> userBets) {
        ArrayList list = new ArrayList();
        for (UserBet userBet : userBets) {
            NbBet nbBet = new NbBet();
            nbBet.gold = userBet.getCoin() == null ? 0 : userBet.getCoin().intValue();
            nbBet.type = userBet.getBetType();
            nbBet.item = userBet.getBetItem();
            nbBet.seatIndex = userBet.getSeatNo();
//            nbBet.matchId
//            nbBet.playerId
            list.add(nbBet);
        }
        return list;
    }
}
