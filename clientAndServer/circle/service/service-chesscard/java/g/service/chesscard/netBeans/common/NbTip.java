package g.service.chesscard.netBeans.common;

import g.service.chesscard.enums.TipEnum;
import g.service.webSocket.codeCreator.NetBeanField;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.text.MessageFormat;

/**
 * Created by Double on 2016/9/21.
 */
public class NbTip {

    @Override
    public String toString() {
        return "\n" + ToStringBuilder.reflectionToString(this);
    }

    @NetBeanField
    public String code;

    @NetBeanField
    public String tip;

    public static NbTip tip(TipEnum tipEnum) {
        NbTip tip = new NbTip();
        tip.code = tipEnum.getCode();
        tip.tip = tipEnum.getTrans();
        return tip;
    }

    public static NbTip tipFormat(TipEnum tipEnum, String format) {
        NbTip tip = new NbTip();
        tip.code = tipEnum.getCode();
        tip.tip = MessageFormat.format(tipEnum.getTrans(), format);
        return tip;
    }

}
