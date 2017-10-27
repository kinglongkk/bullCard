package g.service.chesscard.engine.bull100.message;

import g.service.chesscard.engine.model.Desk;
import g.service.chesscard.engine.manager.DealerManager;
import g.service.chesscard.engine.sender.AbstractMessage;
import g.service.chesscard.exception.ChessCardException;
import g.service.chesscard.netBeans.common.NbTip;
import g.service.chesscard.netBeans.douniu.NbWarningDealerOut;
import g.service.engine.manager.IDeskManager;
import g.service.engine.message.MessageEntity;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by tony on 2016/12/21.
 * 庄家下庄警告
 */
public class Bull100WarningDealerMessage extends AbstractMessage {


    private int deskId;

    public int getDeskId() {
        return deskId;
    }

    public void setDeskId(int deskId) {
        this.deskId = deskId;
    }

    @Autowired
    private DealerManager dealerManager;

    @Autowired
    private IDeskManager deskManager;


    @Override
    public void handleMessage() {
        Desk desk = this.deskManager.byId(getDeskId());
        if (desk.getDealerUserId() != null) {
            try {
                dealerManager.dealerCoinIsWarning(desk);
            } catch (ChessCardException e) {
                MessageEntity messageEntity = new MessageEntity();
                messageEntity.setGroup(false);
                messageEntity.setId(desk.getDealerUserId());
                NbWarningDealerOut warningDealerOut = new NbWarningDealerOut();
                warningDealerOut.coin = Long.valueOf(e.getTransFmt() == null ? "0" : e.getTransFmt());
                warningDealerOut.tip = NbTip.tipFormat(e.getTipEnum(), e.getTransFmt());
                messageEntity.setMsg(warningDealerOut);
                setObjMessage(messageEntity);
            }
        }
    }

}
