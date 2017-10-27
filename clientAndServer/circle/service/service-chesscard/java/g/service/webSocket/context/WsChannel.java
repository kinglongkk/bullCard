package g.service.webSocket.context;

import g.service.webSocket.Msg;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;

/**
 * Created by Double on 2016/9/29.
 */
public class WsChannel {

    private static Log log = LogFactory.getLog(WsChannel.class);

    private Channel channel;

    private IWsSessionManager sessionManager;

    public WsChannel() {

    }

    public void sendNetBean(Object obj) {
        log.debug("下发信息,类型:{0},值:{1}", obj.getClass().getSimpleName(), obj);
        WebSocketFrame m = sessionManager.getMsg(obj).getFrame();
        channel.writeAndFlush(m);
        m.release();
    }

    public void writeAndFlush(Object msg) {
        this.channel.writeAndFlush(msg);
    }

    public void close() {
        this.channel.close();
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public IWsSessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(WsSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public ChannelId getId() {
        return getChannel().id();
    }


    public boolean isActive() {
        if (channel == null) {
            //mock
            return true;
        }
        return channel.isActive();
    }
}
