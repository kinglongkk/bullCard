package g.service.chesscard.netBeans.common;

import g.service.webSocket.codeCreator.NetBean;
import g.service.webSocket.codeCreator.NetBeanField;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by tony on 2016/10/13.
 */
@NetBean(sendBy = NetBean.SENDBY_SERVER)
public class NbSelf {

    @Override
    public String toString() {
        return "\n" + ToStringBuilder.reflectionToString(this);
    }

    /**
     * 余额
     */
    @NetBeanField
    public long balance;

    /**
     * 可用余额
     */
    @NetBeanField
    public long usableBalance;

}
