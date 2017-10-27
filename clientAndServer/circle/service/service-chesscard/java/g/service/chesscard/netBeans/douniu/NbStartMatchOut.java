package g.service.chesscard.netBeans.douniu;

import g.service.chesscard.netBeans.common.*;
import g.service.webSocket.codeCreator.NetBean;
import g.service.webSocket.codeCreator.NetBeanField;

import java.util.ArrayList;

/**
 * Created by Double on 2016/9/21.
 * 新开局
 */
@NetBean(sendBy = NetBean.SENDBY_SERVER)
public class NbStartMatchOut extends NbOut {

    @NetBeanField
    public NbMatch match = new NbMatch();

    @NetBeanField(collectionClass = NbSeat.class)
    public ArrayList<NbSeat> seats;

}
