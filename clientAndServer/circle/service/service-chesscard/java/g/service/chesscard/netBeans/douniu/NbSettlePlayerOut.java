package g.service.chesscard.netBeans.douniu;

import g.service.chesscard.netBeans.common.NbOut;
import g.service.chesscard.netBeans.common.NbPlayerSettle;
import g.service.webSocket.codeCreator.NetBean;
import g.service.webSocket.codeCreator.NetBeanField;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tony on 2016/9/21.
 * 通讯协议:结算玩家
 */
@NetBean(sendBy = NetBean.SENDBY_SERVER)
public class NbSettlePlayerOut extends NbOut {

    @Override
    public String toString() {
        return "\n" + ToStringBuilder.reflectionToString(this);
    }

    /**
     * 可用余额
     */
    @NetBeanField
    public long usableBalance;

    @NetBeanField
    public NbPlayerSettle player;

    @NetBeanField(collectionClass = NbPlayerSettle.class)
    public List<NbPlayerSettle> players = new ArrayList<>();

}
