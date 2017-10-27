package g.service.chesscard.netBeans.douniu;

import g.service.chesscard.netBeans.common.NbOut;
import g.service.webSocket.codeCreator.NetBean;
import g.service.webSocket.codeCreator.NetBeanField;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by tony on 2016/12/2.
 * 玩家余额不足
 */
@NetBean(sendBy = NetBean.SENDBY_SERVER)
public class NbInsufficientBalanceOut extends NbOut {

    @Override
    public String toString() {
        return "\n" + ToStringBuilder.reflectionToString(this);
    }

    // 0 是 结算
    @NetBeanField
    public int type;

}
