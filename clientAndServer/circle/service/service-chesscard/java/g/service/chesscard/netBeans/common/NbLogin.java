package g.service.chesscard.netBeans.common;

import g.service.webSocket.codeCreator.NetBean;
import g.service.webSocket.codeCreator.NetBeanField;

/**
 * Created by lenovo on 2016/9/21.
 * 投注通讯协议
 */
@NetBean(sendBy = NetBean.SENDBY_ALL)
public class NbLogin {

    @NetBeanField
    public String token;

    @NetBeanField
    public Integer userId;

}
