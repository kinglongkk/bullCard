package g.service.chesscard.netBeans.bao;

import g.service.chesscard.netBeans.douniu.NbStartMatchOut;
import g.service.webSocket.codeCreator.NetBean;
import g.service.webSocket.codeCreator.NetBeanField;

/**
 * Created by tony on 2016/9/21.
 * 押宝新开局
 */
@NetBean(sendBy = NetBean.SENDBY_SERVER)
public class NbBullBaoStartMatchOut extends NbStartMatchOut {

    @NetBeanField
    public int poker;

}
