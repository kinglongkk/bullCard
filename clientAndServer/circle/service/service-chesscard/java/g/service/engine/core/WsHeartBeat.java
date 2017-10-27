package g.service.engine.core;

import g.model.common.SessionKey;
import g.model.player.po.UserPlayer;
import g.service.chesscard.engine.schedule.NamedThreadFactory;
import g.service.webSocket.codeCreator.IWsHandle;
import g.service.webSocket.context.IWsSessionManager;
import g.service.webSocket.context.WsSession;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.model.security.privilege.po.SysUser;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by lenovo on 2017/1/23.
 */
public class WsHeartBeat {

    private static Log log = LogFactory.getLog(WsHeartBeat.class);

    private ScheduledExecutorService checkConnectedExecutor =
            Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("heartBeat"));

    @Autowired
    private IWsSessionManager sessionManager;

    @Autowired
    private IWsHandle wsHandle;

    public void start() {
        checkConnectedExecutor.scheduleWithFixedDelay(
                () -> {
                    try {
                        checkConnection();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, 12, 1, TimeUnit.SECONDS);
    }

    public void checkConnection() {
        Map<ChannelId, WsSession> sessions = sessionManager.getSessions();

        if (sessions.size() > 0) {
            Iterator<ChannelId> chs = sessions.keySet().iterator();
            while (chs.hasNext()) {
                ChannelId channelId = chs.next();
                WsSession session = sessions.get(channelId);
                if (session != null && session.isTimeout()) {
                    try {
                        UserPlayer userPlayer = (UserPlayer) session.getAttr(SessionKey.S_USER);
                        if (userPlayer != null) {
                            log.warn("玩家:{0} TCP超时,关闭会话.",userPlayer.getId());
                        }
                        Channel incoming = session.getWsChannel().getChannel();
                        sessionManager.removeSession(incoming.id());
                        session.setConnected(false);
                        wsHandle.disconnected(session);
                        session.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
