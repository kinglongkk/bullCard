package g.service.chesscard.netBeans.douniu;

import g.service.webSocket.codeCreator.NetBean;
import g.service.webSocket.codeCreator.NetBeanField;

/**
 * Created by Double on 2016/9/21.
 * 进入房里
 */
@NetBean(sendBy = NetBean.SENDBY_CLIENT)
public class NbIntoRoomIn {

    /**
     * 房间id
     */
    @NetBeanField
    public int roomId;

}
