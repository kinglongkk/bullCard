package g.service.chesscard.netBeans.douniu;

import g.service.chesscard.netBeans.common.NbMatch;
import g.service.chesscard.netBeans.common.NbOut;
import g.service.chesscard.netBeans.common.NbSeat;
import g.service.chesscard.netBeans.common.NbSelf;
import g.service.webSocket.codeCreator.NetBean;
import g.service.webSocket.codeCreator.NetBeanField;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Double on 2016/9/21.
 * 进入房里
 */
@NetBean(sendBy = NetBean.SENDBY_SERVER)
public class NbIntoRoomOut extends NbOut {

    @NetBeanField
    public NbMatch match = new NbMatch();

    @NetBeanField
    public NbSelf nbSelf = new NbSelf(); //改成自己的index

    @NetBeanField(required = false, collectionClass = NbSeat.class)
    public ArrayList<NbSeat> seats;

    /**
     * 房间名称
     */
    @NetBeanField(required = false)//TODO MK->TONY 下发消息失败暂时先改成required = false
    public String roomName;
    /**
     * 房间id
     */
    @NetBeanField
    public int roomId;

    /**
     * 一张牌(押宝)
     */
    @NetBeanField(required = false)
    public String poker;

    /**
     * 最低上庄金额
     */
    @NetBeanField
    public long minDealerCoin;

    /**
     * 投注筹码
     */
    @NetBeanField(required = false)
    public int[] betChip;

    /**
     * 最大投注次数
     */
    @NetBeanField
    public int betTimes;

    /**
     * 投注区域
     */
    @NetBeanField(collectionClass = String.class, required = false)//TODO MK->TONY 下发消息失败暂时先改成required = false
    public HashMap<String, String> betAreas;


}
