package g.service.chesscard.netHandles.douniu;

import g.service.engine.listener.ISeatListener;
import g.service.chesscard.engine.listener.SeatEvent;
import g.service.chesscard.engine.bull100.message.Bull100SeatMessage;
import g.service.chesscard.engine.sender.SyncMessageSender;
import org.soul.commons.spring.utils.SpringTool;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by tony on 2016/10/14.
 */
public class SeatHandle implements ISeatListener {

    @Autowired
    private SyncMessageSender messageSender;

    @Override
    public void onSeat(SeatEvent seatEvent) {
        Bull100SeatMessage seatMessage = (Bull100SeatMessage) SpringTool.getBean("seatMessage");
        seatMessage.setDeskId(seatEvent.getDeskId());
        messageSender.sendMessage(seatMessage);
    }

}
