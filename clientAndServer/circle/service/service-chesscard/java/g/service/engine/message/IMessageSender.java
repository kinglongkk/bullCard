package g.service.engine.message;

import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by tony on 2016/10/19.
 * 消息下发接口类
 * MSG @see IMessage
 */
public interface IMessageSender<MSG> {

    /**
     * 设置延迟时间单位毫秒
     *
     * @param delay
     */
    void setDelay(int delay);

    /**
     * 设置消息下发组件包装类
     *
     * @param messageServer
     */
    void setSendMessageSessionWrapper(ISendMessageSessionWrapper messageServer);

    /**
     * 发送消息
     *
     * @param message
     */
    void sendMessage(MSG message);

    /**
     * 获取发送消息线程池
     */
    ScheduledExecutorService getMessageThreadPool();


}
