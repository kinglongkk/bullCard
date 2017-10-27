package g.service.webSocket.context;

import io.netty.channel.ChannelId;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * WebSocket的session
 *
 * @author MK
 * @date 2016-09-06
 */
public class WsSession {
    private static Log log = LogFactory.getLog(WsSession.class);

    private WsChannel wsChannel;

    private ConcurrentHashMap<String, Object> attrs = new ConcurrentHashMap<>();

    private Object owner;

    private Integer groupId;

    private IWsSessionManager sessionManager;

    /**
     * 最后一次收到消息或者心跳的时间, 非服务端这个时间不会被重置
     */
    private volatile long lastMsgTime = Long.MAX_VALUE;

    /**
     * 消息队列,可用于在连接之前或者重连(暂时)之间发送的消息缓存,在连接后全部发送
     * 使得WsSession new之后就可以马上发送消息
     */
    private ConcurrentLinkedQueue<Object> msgsUnconnected = new ConcurrentLinkedQueue<>();

    /**
     * 握手完成后可发送接收消息才算连接完成
     */
    private AtomicBoolean connected = new AtomicBoolean();

    /**
     * 客户端与服务端的最小时间差, 客户端用
     */
    private long timeDiff = 0;

    public WsSession(WsChannel wsChannel) {
        this.wsChannel = wsChannel;
        this.sessionManager = wsChannel.getSessionManager();
    }

    public void sendNetBean(Object obj) {
        if (isConnected()) {
            wsChannel.sendNetBean(obj);
        } else {
            msgsUnconnected.add(obj);
        }
    }

    /**
     * 获取服务器的时间
     */
    public long getServerTime() {
        return System.currentTimeMillis() - (this.timeDiff == Integer.MAX_VALUE ? 0 : this.timeDiff);
    }

    public void resetServerTime(long serverTime) {
        this.timeDiff = Math.min(this.timeDiff, System.currentTimeMillis() - serverTime);
    }

    public WsChannel getWsChannel() {
        return wsChannel;
    }

    public void setAttr(String key, Object val) {
        if (val == null) {
            attrs.remove(key);
            return;
        }
        attrs.put(key, val);
    }

    public Object getAttr(String key) {
        return attrs.get(key);
    }

    public Object remove(String key) {
        return attrs.remove(key);
    }

    public Object getOwner() {
        return owner;
    }

    public void setOwner(Object owner) {
        this.owner = owner;
    }

    //TODO: 待销毁
    public void close() {
        wsChannel.close();
    }

    /**
     * 加入到组
     */
    public boolean addToGroup(Integer groupId) {
        this.groupId = groupId;
        return sessionManager.addChannelGroup(groupId, wsChannel);
    }

    public Integer getGroupId() {
        return this.groupId;
    }

    public void removeChannelInGroup(Integer groupId) {
        if (groupId == null) {
            return;
        }
        sessionManager.removeChannelInGroup(this.groupId, wsChannel);
    }

    public void setConnected(boolean connected) {
        this.connected.set(connected);
        if (connected) {//连接上之后要把消息缓存全部发送出去
            Object msg = msgsUnconnected.poll();
            while (msg != null) {
                wsChannel.sendNetBean(msg);
                msg = msgsUnconnected.poll();
            }
        }
    }

    /**
     * 服务端客户端连接信息就要初始化一下时间
     */
    public void initMsgTime() {
        this.lastMsgTime = System.currentTimeMillis();
    }

    /**
     * 服务端收到消息(包含心跳包)就要重置一下时间
     */
    public void resetLastMsgTime() {
        if(this.lastMsgTime != Long.MAX_VALUE){//没有初始化过,即非游戏客户端则不重置
            this.lastMsgTime = System.currentTimeMillis();
        }
    }

    /** 客户端每4秒发一次,允许最高延迟3秒没有发心跳 */
    public boolean isTimeout() {
        return this.lastMsgTime != Long.MAX_VALUE && (System.currentTimeMillis() - this.lastMsgTime) > 7000;
    }

    /**
     * 判断是否还有连接
     */
    public boolean isConnected() {
        return connected.get() && wsChannel.isActive();
    }

    /**
     * 需要SessionId(以 channelID)
     *
     * @return
     */
    public String getId() {
        ChannelId id = wsChannel.getId();
        return id != null ? id.toString() : null;
    }
}
