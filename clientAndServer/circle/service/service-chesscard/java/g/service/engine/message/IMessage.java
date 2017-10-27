package g.service.engine.message;

import java.util.List;

/**
 * Created by tony on 2016/10/19.
 * 消息接口类
 */
public interface IMessage {

    /**
     * 单条消息
     */
    MessageEntity objMessage();

    /**
     * 多条消息
     */
    List<MessageEntity> listMessage();

    /**
     * 处理消息
     */
    void handleMessage();

}
