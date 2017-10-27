package g.service.chesscard.engine.sender;

import g.service.chesscard.engine.model.SettleResult;
import g.service.engine.message.IMessage;
import g.service.engine.message.MessageEntity;

import java.util.List;

/**
 * Created by tony on 2016/10/21
 * 消息基类.
 */
public abstract class AbstractMessage implements IMessage {

    /**
     * 批量消息
     */
    private List<MessageEntity> listMsg;

    /**
     * 单条消息
     */
    private MessageEntity objMsg;

    protected SettleResult settleResult;

    /**
     * 设置批量消息
     *
     * @param listMessage
     */
    protected void setListMessage(List<MessageEntity> listMessage) {
        this.listMsg = listMessage;
    }

    /**
     * 设置单条消息
     *
     * @param objMessage
     */
    protected void setObjMessage(MessageEntity objMessage) {
        this.objMsg = objMessage;
    }

    /**
     * 获取批量消息
     */
    @Override
    public List<MessageEntity> listMessage() {
        return listMsg;
    }

    /**
     * 获取单条消息
     */
    @Override
    public MessageEntity objMessage() {
        return objMsg;
    }


    public void setSettleResult(SettleResult settleResult) {
        this.settleResult = settleResult;
    }
}
