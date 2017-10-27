package g.service.chesscard.netBeans.douniu;

import g.service.webSocket.codeCreator.NetBean;
import g.service.webSocket.codeCreator.NetBeanField;

/**
 * Created by Double on 2016/9/21.
 * 在房间里除了下注,上庄操作,其他操作都要由客户端发送该消息
 */
@NetBean(sendBy = NetBean.SENDBY_CLIENT)
public class NbRoomActionIn {
}
