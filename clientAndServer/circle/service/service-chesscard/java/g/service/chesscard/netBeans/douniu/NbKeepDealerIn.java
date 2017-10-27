package g.service.chesscard.netBeans.douniu;

import g.service.webSocket.codeCreator.NetBean;
import g.service.webSocket.codeCreator.NetBeanField;

/**
 * Created by tony on 2016/11/9.
 * 续庄
 */
@NetBean(sendBy = NetBean.SENDBY_CLIENT)
public class NbKeepDealerIn {

    @NetBeanField
    public long coin;

}
