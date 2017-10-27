package g.service.chesscard.engine.sender;

import g.service.engine.message.IMessage;

import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by tony on 2016/12/13.
 */
public class SyncMessageSender extends AbstractMessageSender<IMessage> {

    @Override
    public ScheduledExecutorService getMessageThreadPool() {
        return null;
    }

    @Override
    protected void submitMessageTask(AbstractMessageSender.MessageTask task) {
        task.run();
    }

}
