package g.service.chesscard.engine.bull100.message;

import g.service.chesscard.engine.model.Desk;
import g.service.chesscard.engine.model.GameDeskSeat;
import g.service.chesscard.engine.sender.AbstractMessage;
import g.service.chesscard.netBeans.common.NbSeat;
import g.service.chesscard.netBeans.douniu.NbSeatOut;
import g.service.engine.manager.IDeskManager;
import g.service.engine.message.MessageEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tony on 2016/10/21.
 */
public class Bull100SeatMessage extends AbstractMessage {

    private int deskId;

    public int getDeskId() {
        return deskId;
    }

    public void setDeskId(int deskId) {
        this.deskId = deskId;
    }

    @Autowired
    private IDeskManager deskManager;

    @Override
    public void handleMessage() {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setGroup(true);
        messageEntity.setId(getDeskId());
        NbSeatOut seatBatchOut = new NbSeatOut();
        Desk desk = deskManager.byId(getDeskId());
        List<GameDeskSeat> gameDeskSeatList = desk.getDeskOnSeatUserList();
        seatBatchOut.seats = (ArrayList<NbSeat>) NbSeat.fromUserPlayer(gameDeskSeatList);
        messageEntity.setMsg(seatBatchOut);
        setObjMessage(messageEntity);
    }


}
