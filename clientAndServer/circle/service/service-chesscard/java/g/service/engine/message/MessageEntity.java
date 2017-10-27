package g.service.engine.message;

/**
 * Created by tony on 2016/10/21.
 * 消息实体类
 */
public class MessageEntity {

    /**
     * 消息通道id
     */
    private int id;

    /**
     * 消息内容
     */
    private Object msg;

    /**
     * 是否为组消息
     */
    private boolean isGroup;

    /**
     * 获取通道id
     */
    public int getId() {
        return id;
    }

    /**
     * 设置通道id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 获取消息
     */
    public Object getMsg() {
        return msg;
    }

    /**
     * 设置消息
     */
    public void setMsg(Object msg) {
        this.msg = msg;
    }

    /**
     * 是否为组消息
     */
    public boolean isGroup() {
        return isGroup;
    }

    /**
     * 设置是否为组消息
     */
    public void setGroup(boolean group) {
        isGroup = group;
    }

    public MessageEntity(int id, Object msg, boolean isGroup) {
        this.id = id;
        this.msg = msg;
        this.isGroup = isGroup;
    }

    public MessageEntity() {
    }
}
