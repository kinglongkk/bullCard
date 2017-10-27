package g.service.chesscard.netHandles.douniu;

import g.service.chesscard.engine.listener.BetEvent;
import g.service.chesscard.engine.bull100.message.Bull100BetMessage;
import g.service.chesscard.engine.sender.SyncMessageSender;
import g.service.engine.listener.IBetListener;
import g.service.engine.message.IMessageSender;
import org.soul.commons.spring.utils.SpringTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by Double on 2016/10/8.
 */
public class BetBatchHandle implements IBetListener {

    @Autowired
    @Qualifier("betAsyncMessageSender")
    private IMessageSender<Bull100BetMessage> messageSender;

    @Override
    public void beforeBet(BetEvent betEvent) {
    }

    @Override
    public void onBet(BetEvent betEvent) {
        if (betEvent != null && this.messageSender != null) {
            final Bull100BetMessage message = (Bull100BetMessage) SpringTool.getBean("betMessage");
            if (message != null) {
                message.setDeskId(betEvent.getDeskId());
                messageSender.sendMessage(message);
            }
        }
    }


}
