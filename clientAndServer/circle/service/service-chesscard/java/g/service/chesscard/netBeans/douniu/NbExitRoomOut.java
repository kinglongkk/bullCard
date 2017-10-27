package g.service.chesscard.netBeans.douniu;

import g.service.chesscard.netBeans.common.NbOut;
import g.service.webSocket.codeCreator.NetBean;
import g.service.webSocket.codeCreator.NetBeanField;

/**
 * Created by Double on 2016/9/21.
 * 退出房间
 */
@NetBean(sendBy = NetBean.SENDBY_SERVER)
public class NbExitRoomOut extends NbOut {

    /**
     * 房间id
     */
    @NetBeanField
    public NbExitRoomIn exitRoomIn;

}
