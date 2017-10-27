package g.service.chesscard.netBeans.douniu;

import g.service.chesscard.netBeans.common.NbOut;
import g.service.chesscard.netBeans.common.NbSeat;
import g.service.webSocket.codeCreator.NetBean;
import g.service.webSocket.codeCreator.NetBeanField;

import java.util.ArrayList;

/**
 * Created by tony on 2016/9/21.
 * 入座,返回
 */
@NetBean(sendBy = NetBean.SENDBY_SERVER)
public class NbSeatOut extends NbOut {

//    @NetBeanField
//    public NbSeat nbSeat;

    @NetBeanField(collectionClass = NbSeat.class)
    public ArrayList<NbSeat> seats;

}
