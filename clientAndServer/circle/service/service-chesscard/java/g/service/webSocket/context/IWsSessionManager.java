package g.service.webSocket.context;

import g.service.webSocket.Msg;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;

import java.util.Map;

/**
 * Created by tony on 2016/10/20.
 * 会话管理器
 */

public interface IWsSessionManager {

    boolean isContainSession(ChannelId channelId);

    void putUserSession(Integer userId, WsSession session);

    WsSession getUserSession(Integer userId);

    WsSession removeUserSession(Integer userId);

    void putSession(ChannelId id, WsSession session);

    WsSession getSession(ChannelId channelId);

    WsSession removeSession(ChannelId channelId);

    boolean addChannelGroup(Integer groupId, WsChannel wsChannel);

    void removeChannelInGroup(Integer groupId, WsChannel wsChannel);

    ChannelGroup getChannelGroup(Integer groupId);

    void sendNetBeanGroup(Integer groupId, Object obj);

    Map<ChannelId, WsSession> getSessions();

    Msg getMsg(Object obj);
}
