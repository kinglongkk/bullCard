package g.service.engine.message;

/**
 * Created by tony on 2016/10/20.
 * 消息发送监听
 */
public interface IMessageSenderListener<MSG extends IMessage> {

    /**
     * 消息发送结束调用
     *
     * @param message
     */
    void onCompleteSendMessage(MSG message);

}
