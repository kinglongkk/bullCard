package g.service.chesscard.netBeans.douniu;

import g.service.chesscard.netBeans.common.NbBet;
import g.service.chesscard.netBeans.common.NbOut;
import g.service.webSocket.codeCreator.NetBean;
import g.service.webSocket.codeCreator.NetBeanField;

import java.util.ArrayList;

/**
 * Created by Double on 2016/9/21.
 * 抽注,返回
 */
@NetBean(sendBy = NetBean.SENDBY_SERVER)
public class NbBetBatchOut extends NbOut {

    @NetBeanField(collectionClass = NbBet.class)
    public ArrayList<NbBet> bets = new ArrayList<>();


}
