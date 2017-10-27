package g.service.engine.message;

/**
 * Created by tony on 2016/10/20.
 * 发送消息组件包装类
 */
public interface ISendMessageSessionWrapper {

    /**
     * 发送组通道消息
     *
     * @param id
     * @param obj
     */
    void send2Group(Integer id, Object obj);

    /**
     * 发送单通道消息
     *
     * @param id
     * @param obj
     */
    void send2Single(Integer id, Object obj);

}
