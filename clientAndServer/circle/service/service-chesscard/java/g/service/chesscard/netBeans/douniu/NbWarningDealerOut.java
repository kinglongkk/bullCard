package g.service.chesscard.netBeans.douniu;

import g.service.chesscard.netBeans.common.NbOut;
import g.service.webSocket.codeCreator.NetBean;
import g.service.webSocket.codeCreator.NetBeanField;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by tony on 2016/11/15.
 * 庄家警告消息
 */
@NetBean(sendBy = NetBean.SENDBY_SERVER)
public class NbWarningDealerOut extends NbOut {

    @NetBeanField
    public long coin;

}
