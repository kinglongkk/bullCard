package g.service.chesscard.netBeans.common;

import g.service.webSocket.codeCreator.NetBeanField;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Double on 2016/9/21.
 * 通讯协议:玩家
 */
public class NbPlayer {

    @Override
    public String toString() {
        return "\n" + ToStringBuilder.reflectionToString(this);
    }

    /**
     * 玩家ID
     */
    @NetBeanField
    public int playerId;

    /**
     * 头像地址
     */
    @NetBeanField(required = false)
    public String icon;

    /**
     * 金币余额
     */
    @NetBeanField
    public long coin;

    /**
     * 可投注余额
     */
    @NetBeanField
    public long usableBalance;

    /**
     * 昵称
     */
    @NetBeanField(required = false)
    public String nickname;


}
