package g.service.webSocket;

import g.service.chesscard.netBeans.common.NbLogin;
import g.service.webSocket.codeCreator.IWsHandle;
import g.service.webSocket.codeCreator.NetBeanTool;
import g.service.webSocket.context.WsChannel;
import g.service.webSocket.context.WsSession;
import g.service.webSocket.context.WsSessionManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;

@ChannelHandler.Sharable
public class WebSocketClientHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static Log log = LogFactory.getLog(WebSocketClientHandler.class);

    private IWsHandle wsHandle;

    private TextWebSocketFrame msgFlag = new TextWebSocketFrame(WebSocketConst.CLIENT_FLAG_JAVA);


    public WebSocketClientHandler(IWsHandle wsHandle) {
        this.wsHandle = wsHandle;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                TextWebSocketFrame msg) throws Exception { //必须tryCatch 否则会导致连接断开
        WsSession session = WebSocketClient.wsSessionManager.getSession(ctx.channel().id());
        //读取消息
        Msg read = new Msg(msg.content());
        session.resetServerTime(read.getLong());//作为客户端,要先读取一个时间戳
        try {
            int event = (int) read.getLong();
            log.info("通讯事件:{0},信息类型:{1},", event, NetBeanTool.classes.get(event).getSimpleName());
            Object o = NetBeanTool.readObject(read, NetBeanTool.classes.get(event));
            if (wsHandle.onMessage(session, o)) {
                NetBeanTool.eventAssign(event, o, session);
            }
        } catch (Exception e) {
            log.error(e, "处理信息错误");
        }finally {
            read.release();
        }
    }

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt == WebSocketClientProtocolHandler.ClientHandshakeStateEvent.HANDSHAKE_COMPLETE) {
            //连接握手成功,发送数据表示是netty客户端
            ctx.channel().writeAndFlush(msgFlag.copy());
            WsSession session = WebSocketClient.wsSessionManager.getSession(ctx.channel().id());
            if (session == null) {
                session = WebSocketClient.wsSessionManager.createSession(ctx.channel(), null);
            }
            session.setConnected(true);
            wsHandle.connected(session);
        } else {
            //其他事件
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("handlerRemoved " + ctx);
        Channel incoming = ctx.channel();
        WsSession session = WebSocketClient.wsSessionManager.removeSession(incoming.id());
        if (session != null) {
            session.setConnected(false);
            wsHandle.disconnected(session);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel incoming = ctx.channel();
        log.error(cause, incoming.remoteAddress().toString());
    }


}