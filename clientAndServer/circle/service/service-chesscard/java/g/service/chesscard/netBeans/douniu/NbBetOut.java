package g.service.chesscard.netBeans.douniu;

import g.service.chesscard.netBeans.common.NbBet;
import g.service.chesscard.netBeans.common.NbOut;
import g.service.chesscard.netBeans.common.NbSelf;
import g.service.webSocket.codeCreator.NetBean;
import g.service.webSocket.codeCreator.NetBeanField;

/**
 * Created by Double on 2016/9/21.
 * 抽注,投注人信息返回
 */
@NetBean(sendBy = NetBean.SENDBY_SERVER)
public class NbBetOut extends NbOut {

    @NetBeanField
    public NbBet bet = new NbBet();

    @NetBeanField
    public NbSelf nbSelf = new NbSelf();

}
