package g.service.chesscard.netBeans.douniu;

import g.service.chesscard.netBeans.common.NbDealer;
import g.service.chesscard.netBeans.common.NbOut;
import g.service.webSocket.codeCreator.NetBean;
import g.service.webSocket.codeCreator.NetBeanField;

/**
 * Created by tony on 2016/11/9.
 * 续庄通知
 */
@NetBean(sendBy = NetBean.SENDBY_SERVER)
public class NbKeepDealerOut extends NbOut {

    @NetBeanField
    public NbDealer dealer;

}
