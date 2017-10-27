package g.service.webSocket.context;

import g.model.common.SessionKey;
import g.model.player.po.UserPlayer;
import g.service.webSocket.Msg;
import g.service.webSocket.codeCreator.NetBeanTool;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Double on 2016/9/29.
 */
public class WsSessionManager implements IWsSessionManager {

//    private Collection<WsSessionManagerListener> listeners;

    /**
     * 是否是服务端, 如果是服务端的话发送消息都会在前面加上一个时间戳
     */
    private boolean server = true;

    public WsSessionManager(boolean server) {
        this.server = server;
    }

    public WsSessionManager() {
    }

    private static Log log = LogFactory.getLog(WsSessionManager.class);

    /**
     * 个人会话
     */
    private static ConcurrentHashMap<Integer, WsSession> userSession = new ConcurrentHashMap<>();

    /**
     * 会话组
     */
    private static ConcurrentHashMap<ChannelId, WsSession> sessions = new ConcurrentHashMap<>();

    /**
     * 通道组的组
     */
    private static ConcurrentHashMap<Integer, ChannelGroup> channelGroups = new ConcurrentHashMap<>();

    /**
     * 是否包括会话
     *
     * @param channelId
     * @return
     */
    @Override
    public boolean isContainSession(ChannelId channelId) {
        return sessions.containsKey(channelId);
    }

    /**
     * 添加个人会话
     *
     * @param userId
     * @param session
     */
    @Override
    public void putUserSession(Integer userId, WsSession session) {
        userSession.put(userId, session);
    }

    /**
     * 获取个人会话
     *
     * @param userId
     */
    @Override
    public WsSession getUserSession(Integer userId) {
        return userSession.get(userId);
    }

    /**
     * 删除会话
     *
     * @param userId
     * @return
     */
    @Override
    public WsSession removeUserSession(Integer userId) {
        return userSession.remove(userId);
    }

    /**
     * 添加会话
     *
     * @param id
     * @param session
     */
    @Override
    public void putSession(ChannelId id, WsSession session) {
        sessions.put(id, session);
    }

    /**
     * 获取会话
     *
     * @param channelId
     * @return
     */
    @Override
    public WsSession getSession(ChannelId channelId) {
        return sessions.get(channelId);
    }

    /**
     * 删除会话
     *
     * @param channelId
     * @return
     */
    @Override
    public WsSession removeSession(ChannelId channelId) {
        WsSession wsSession = sessions.remove(channelId);
        UserPlayer sysUser = (UserPlayer) wsSession.getAttr(SessionKey.S_USER);
        if (sysUser != null) {
            removeUserSession(sysUser.getId());
        }
        return wsSession;
    }

    /**
     * 加入通道组
     *
     * @param groupId
     * @param wsChannel
     */
    @Override
    public boolean addChannelGroup(Integer groupId, WsChannel wsChannel) {
        ChannelGroup channelGroup = channelGroups.get(groupId);
        if (channelGroup == null) {
            channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
            channelGroups.put(groupId, channelGroup);
        }
        if (wsChannel.getChannel() != null) {
            channelGroup.add(wsChannel.getChannel());
            return true;
        }
        return false;
    }

    /**
     * 退出通道组
     *
     * @param groupId
     * @param wsChannel
     */
    @Override
    public void removeChannelInGroup(Integer groupId, WsChannel wsChannel) {
        if (groupId == null) {
            return;
        }
        ChannelGroup channelGroup = channelGroups.get(groupId);
        if (channelGroup != null) {
            if (wsChannel.getChannel() != null) {
                channelGroup.remove(wsChannel.getChannel());
            }
        }
    }

    /**
     * 获取通道组
     *
     * @param groupId
     * @return
     */
    @Override
    public ChannelGroup getChannelGroup(Integer groupId) {
        return channelGroups.get(groupId);
    }

    @Override
    public void sendNetBeanGroup(Integer groupId, Object obj) {
        try {
            log.debug("下发信息,消息组:{0},类型:{1},值:{2}", groupId, obj.getClass().getSimpleName(), obj);
            ChannelGroup group = getChannelGroup(groupId);
            if (group == null || group.size() <= 0) {
//                log.error("组不存在.");
            } else {
                WebSocketFrame m = getMsg(obj).getFrame();
                group.writeAndFlush(m);
                m.release();
            }
        } catch (Exception e) {
            log.error(e, "下发消息组出错:");
        }
    }

    @Deprecated
    @Override
    public Map<ChannelId, WsSession> getSessions() {
        return sessions;
    }

    public boolean isServer() {
        return server;
    }

    public void setServer(boolean server) {
        this.server = server;
    }

    @Override
    public Msg getMsg(Object obj) {
        Msg m = new Msg();
        if (server) m.putLong(System.currentTimeMillis());
        NetBeanTool.writeObject(m, obj, true);
        return m;
    }

    /**
     * 创建完session并且马上加入
     */
    public WsSession createSession(Channel channel, Object owner) {
        WsChannel wsChannel = new WsChannel();
        wsChannel.setChannel(channel);
        wsChannel.setSessionManager(this);
        WsSession session = new WsSession(wsChannel);
        if (sessions.putIfAbsent(channel.id(), session) != null) {
            session = sessions.get(channel.id());
        }
        if (owner != null) {
            session.setOwner(owner);
        }
        return session;
    }
}
