package g.service.chesscard.netBeans.common;

import g.service.chesscard.enums.TipEnum;
import g.service.webSocket.codeCreator.NetBeanField;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by lenovo on 2016/10/11.
 */
public class NbOut {

    @Override
    public String toString() {
        return "\n" + ToStringBuilder.reflectionToString(this);
    }

    @NetBeanField
    public NbTip tip = NbTip.tip(TipEnum.SUCCESS);

}
