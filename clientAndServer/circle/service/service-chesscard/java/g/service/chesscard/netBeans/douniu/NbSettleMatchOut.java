package g.service.chesscard.netBeans.douniu;

import g.service.chesscard.netBeans.common.*;
import g.service.webSocket.codeCreator.NetBean;
import g.service.webSocket.codeCreator.NetBeanField;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Double on 2016/9/21.
 * 通讯协议:结算
 */
@NetBean(sendBy = NetBean.SENDBY_SERVER)
public class NbSettleMatchOut extends NbOut {

    @Override
    public String toString() {
        return "\n" + ToStringBuilder.reflectionToString(this);
    }

    @NetBeanField(collectionClass = NbPlayerSettle.class, required = false)
    public List<NbPlayerSettle> players;

    @NetBeanField(collectionClass = NbPokerSettle.class)
    public ArrayList<NbPokerSettle> pokers = new ArrayList<>(5);

}
