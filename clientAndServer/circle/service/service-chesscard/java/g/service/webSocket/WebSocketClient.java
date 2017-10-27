package g.service.webSocket;

import g.service.webSocket.codeCreator.CodeCreator;
import g.service.webSocket.codeCreator.IWsHandle;
import g.service.webSocket.context.WsSession;
import g.service.webSocket.context.WsSessionManager;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.ConnectException;
import java.net.URI;

public class WebSocketClient {

    private static Log log = LogFactory.getLog(WebSocketClient.class);
    private WebSocketClientHandler webSocketHandler;
    private Channel channel;
    private WebSocketClientInitializer wsClientInitializer;
    EventLoopGroup workerGroup = new NioEventLoopGroup(4);
    static WsSessionManager wsSessionManager = new WsSessionManager(false);
    /**
     * 服务端uri
     */
    private URI webSocketURI;

    public WebSocketClient(URI webSocketURI, IWsHandle wsHandle) {
        this.webSocketURI = webSocketURI;
        webSocketHandler = new WebSocketClientHandler(wsHandle);
    }

    /**
     * 连接到服务器,拿到session后可以立即发送消息(实际会在握手成功后才发送出去)
     *
     * @return
     */
    public WsSession conectToServer() {
        try {
            Bootstrap b = new Bootstrap(); // (2)
            wsClientInitializer = new WebSocketClientInitializer(webSocketURI, webSocketHandler);
            b.group(workerGroup)
                    .channel(NioSocketChannel.class) // (3)
                    .handler(wsClientInitializer);
            channel = b.connect(webSocketURI.getHost(), webSocketURI.getPort()).sync().channel();
//            channel.closeFuture().sync();不需要等待连接关闭,不占用线程
            WsSession session = wsSessionManager.createSession(channel, null);
            return session;
        } catch (Exception e) {
            log.error("WebSocketClient连接[异常]!"+e.getMessage());
            return null;
        }
    }

}