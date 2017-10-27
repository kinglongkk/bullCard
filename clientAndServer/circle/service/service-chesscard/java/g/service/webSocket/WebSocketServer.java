package g.service.webSocket;

import g.service.webSocket.codeCreator.CodeCreator;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class WebSocketServer {

    private static Log log = LogFactory.getLog(WebSocketServer.class);
    private String ip;
    private int port;
    private String netBeanPackage;
    private String netHandelPackage;

    @Autowired
    private WebSocketServerHandler webSocketServerHandler;

    @Autowired
    private WebSocketServerInitializer webSocketServerInitializer;

    public WebSocketServer() {
    }

    public void init() {
        Msg json = new CodeCreator(netBeanPackage, netHandelPackage, true).getJson();//客户端连接后会马上发一份json映射数据
        webSocketServerHandler.setJson(json);
    }

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors()*2); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors()*20);
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(webSocketServerInitializer)  //(4)
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
            ChannelFuture f = b.bind(ip, port).sync(); // (7)
            log.info("WebSocketServer启动[成功],port:{0}", port);
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error(e, "WebSocketServer启动[异常]!");
            System.exit(-1);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            log.info("WebSocketServer关闭");
        }
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setNetBeanPackage(String netBeanPackage) {
        this.netBeanPackage = netBeanPackage;
    }

    public void setNetHandelPackage(String netHandelPackage) {
        this.netHandelPackage = netHandelPackage;
    }

    public void setWebSocketServerHandler(WebSocketServerHandler webSocketServerHandler) {
        this.webSocketServerHandler = webSocketServerHandler;
    }
}