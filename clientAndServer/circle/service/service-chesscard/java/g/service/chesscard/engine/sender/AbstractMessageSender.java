package g.service.chesscard.engine.sender;

import g.service.engine.message.*;
import org.apache.commons.collections.CollectionUtils;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;

import  java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by tony on 2016/10/19.
 */
public abstract class AbstractMessageSender<MSG extends g.service.engine.message.IMessage> implements IMessageSender<MSG> {

    protected int mDelay = 0;
    private ISendMessageSessionWrapper mSendMessageSessionWrapper;

    @Override
    public void sendMessage(MSG message) {
        if (!onInterceptSendMessage(message)) {
            submitMessageTask(getMessageTask(message));
        }
    }

    protected boolean onInterceptSendMessage(MSG message) {
        return false;
    }

    protected void onCompleteSendMessage1(MSG message) {
    }

    @Override
    public void setDelay(int delay) {
        this.mDelay = delay;
    }

    @Override
    public void setSendMessageSessionWrapper(ISendMessageSessionWrapper messageServer) {
        this.mSendMessageSessionWrapper = messageServer;
    }

    private final IMessageSenderListener<MSG> iMessageSenderListener = new IMessageSenderListener<MSG>() {

        public void onCompleteSendMessage(MSG message) {
            onCompleteSendMessage1(message);
        }

    };

    private MessageTask getMessageTask(MSG message) {
        MessageTask messageTask = new MessageTask(iMessageSenderListener, mSendMessageSessionWrapper);
        messageTask.setMessage(message);
        return messageTask;
    }

    protected void submitMessageTask(MessageTask task) {
        getMessageThreadPool().schedule(task, mDelay, TimeUnit.MILLISECONDS);
    }

    protected static class MessageTask implements Runnable {

        private static Log log = LogFactory.getLog(MessageTask.class);

        private final ISendMessageSessionWrapper sendMessageSessionWrapper;

        private final IMessageSenderListener messageSenderListener;

        private g.service.engine.message.IMessage message;

        public MessageTask(IMessageSenderListener iMessageSenderListener, ISendMessageSessionWrapper iSendMessageSessionWrapper) {
            this.sendMessageSessionWrapper = iSendMessageSessionWrapper;
            this.messageSenderListener = iMessageSenderListener;
        }

        public void setMessage(g.service.engine.message.IMessage message) {
            this.message = message;
        }

        @Override
        public void run() {
            try {
                final IMessage message = this.message;
                if (message != null) {
                    message.handleMessage();
                    this.send(message.objMessage());
                    final List<MessageEntity> messageEntityList = message.listMessage();
                    if (CollectionUtils.isNotEmpty(messageEntityList)) {
                        for (final MessageEntity messageEntity : messageEntityList) {
                            this.send(messageEntity);
                        }
                    }
                    messageSenderListener.onCompleteSendMessage(message);
                }
            } catch (Exception e) {
                log.error(e);
            }
        }

        private void send(MessageEntity messageEntity) {
            if (messageEntity != null) {
                if (messageEntity.isGroup()) {
                    sendMessageSessionWrapper.send2Group(messageEntity.getId(), messageEntity.getMsg());
                } else {
                    sendMessageSessionWrapper.send2Single(messageEntity.getId(), messageEntity.getMsg());
                }
            }
        }


    }


}
