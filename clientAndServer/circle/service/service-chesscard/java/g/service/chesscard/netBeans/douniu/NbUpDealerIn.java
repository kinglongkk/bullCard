package g.service.chesscard.netBeans.douniu;

import g.service.webSocket.codeCreator.NetBean;
import g.service.webSocket.codeCreator.NetBeanField;

/**
 * Created by tony on 2016/11/9.
 * 上庄
 */

@NetBean(sendBy = NetBean.SENDBY_CLIENT)
public class NbUpDealerIn {

    /**
     * 上庄金额
     */
    @NetBeanField
    public long coin;

}
