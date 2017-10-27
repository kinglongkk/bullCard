package g.service.chesscard.netBeans.common;

import g.model.chesscard.enums.BullBaoItemPrefixEnum;
import g.model.poker.PokerEnum;
import g.service.webSocket.codeCreator.NetBeanField;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by Double on 2016/9/21.
 * 通讯协议:扑克牌
 */
public class NbPoker {

    @Override
    public String toString() {
        return "\n" + ToStringBuilder.reflectionToString(this);
    }

    /**
     * 一手牌,X张,以逗号隔开
     *
     * @see PokerEnum
     */
    @NetBeanField
    public String code;

    /**
     * 牌型:牛牛,牛1....
     *
     * @see BullBaoItemPrefixEnum
     */
    @NetBeanField
    public String type;

    /**
     * 输赢结果
     *
     * @see g.model.bet.BetResultEnum
     */
    @NetBeanField(required = false)
    public String result;


    public static String getCardsCode(int[] cards) {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < cards.length; i++) {
            if (i != 0) code.append(",");
            code.append(cards[i]);
        }
        return code.toString();
    }


}
