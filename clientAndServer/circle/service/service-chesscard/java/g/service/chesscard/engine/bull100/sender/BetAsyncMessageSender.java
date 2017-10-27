package g.service.chesscard.engine.bull100.sender;

import g.service.chesscard.engine.bull100.message.Bull100BetMessage;
import g.service.chesscard.engine.model.Desk;
import g.service.chesscard.engine.schedule.NamedThreadFactory;
import g.service.chesscard.engine.sender.AbstractMessageSender;
import g.service.engine.manager.IDeskManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by tony on 2016/10/19.
 */
public class BetAsyncMessageSender extends AbstractMessageSender<Bull100BetMessage> {

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(
            10, new NamedThreadFactory(NamedThreadFactory.THREAD_BET_MSG));
    private final ConcurrentHashMap<Integer, Object> deskDeliver = new ConcurrentHashMap<>();

    @Autowired
    private IDeskManager deskManager;

    @Override
    protected boolean onInterceptSendMessage(Bull100BetMessage message) {
        final Integer deskId = message == null ? null : message.getDeskId();
        if (deskId == null) {
            return false;
        }
        if (this.deskDeliver.containsKey(deskId) == false) {
            this.deskDeliver.putIfAbsent(message.getDeskId(), Boolean.TRUE);
            Desk desk = deskManager.byId(deskId);
            long beginTime = desk.getMatch().getBeginTime().getTime();
            mDelay = (int) (1000-(System.currentTimeMillis()-beginTime)%1000);
            return false;
        }
        return true;
    }

    @Override
    protected void onCompleteSendMessage1(Bull100BetMessage message) {
        final Integer deskId = message == null ? null : message.getDeskId();
        if (deskId != null) {
            this.deskDeliver.remove(message.getDeskId());
        }
    }

    @Override
    public ScheduledExecutorService getMessageThreadPool() {
        return executorService;
    }
}
