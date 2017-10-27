package g.service.chesscard.netBeans.common;

import g.model.chesscard.enums.Bull100BetItemPrefixEnum;
import g.service.webSocket.codeCreator.NetBean;
import g.service.webSocket.codeCreator.NetBeanField;

/**
 * Created by tony on 2016/10/17.
 * 通讯类:结算牌
 */
public class NbPokerSettle extends NbPoker {

    /**
     * 牌组
     *
     * @see Bull100BetItemPrefixEnum
     */
    @NetBeanField
    public String group;

    @NetBeanField
    public int odds;

    @NetBeanField
    public int cardsPoint;


}
