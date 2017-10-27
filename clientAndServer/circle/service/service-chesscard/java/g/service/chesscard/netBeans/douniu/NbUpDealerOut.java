package g.service.chesscard.netBeans.douniu;

import g.service.chesscard.netBeans.common.NbDealer;
import g.service.chesscard.netBeans.common.NbOut;
import g.service.webSocket.codeCreator.NetBean;
import g.service.webSocket.codeCreator.NetBeanField;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tony on 2016/11/9.
 * 上庄下发信息
 */
@NetBean(sendBy = NetBean.SENDBY_SERVER)
public class NbUpDealerOut extends NbOut {

    @NetBeanField
    public NbDealer dealer = new NbDealer();

    @NetBeanField
    public NbDealer deskDealer = new NbDealer();

    @NetBeanField(collectionClass = NbDealer.class)
    public List<NbDealer> players = new ArrayList<>();


}
