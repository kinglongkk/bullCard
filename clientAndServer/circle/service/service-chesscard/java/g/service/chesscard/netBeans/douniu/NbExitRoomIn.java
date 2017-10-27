package g.service.chesscard.netBeans.douniu;

import g.service.webSocket.codeCreator.NetBean;
import g.service.webSocket.codeCreator.NetBeanField;

/**
 * Created by Double on 2016/9/21.
 * 退出房里
 */
@NetBean(sendBy = NetBean.SENDBY_CLIENT)
public class NbExitRoomIn {

    @NetBeanField
    public int roomId;

    @NetBeanField
    public boolean isBreak;

}
