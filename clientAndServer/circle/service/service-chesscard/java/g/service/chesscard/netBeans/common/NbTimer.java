package g.service.chesscard.netBeans.common;

import g.service.webSocket.codeCreator.NetBeanField;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by tony on 2016/12/13.
 */
public class NbTimer {

    @Override
    public String toString() {
        return "\n" + ToStringBuilder.reflectionToString(this);
    }

    @NetBeanField
    public String type;

    @NetBeanField
    public int time;


}
