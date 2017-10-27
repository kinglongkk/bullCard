package g.service.webSocket;

import g.service.chesscard.netBeans.common.NbTest;
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
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

@ChannelHandler.Sharable
public class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static Log log = LogFactory.getLog(WebSocketServerHandler.class);

    private Msg json;

    @Autowired
    private IWsHandle wsHandle;

    @Autowired
    private WsSessionManager wsSessionManager;

    /**
     * 初始化前必须被设置,客户端连接后会马上发一份json映射数据
     */
    public void setJson(Msg json) {
        this.json = json;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                TextWebSocketFrame msg) throws Exception { //必须tryCatch 否则会导致连接断开
        Channel incoming = ctx.channel();//incoming.remoteAddress()
        WsSession session = null;
        if (!wsSessionManager.isContainSession(incoming.id())) {
            //是否游戏客户端(需要心跳包),AI或其他端为false
            boolean isGameClient = false;
            //是web客户端,后续可以根据这个标志判定是玩家还是AI
            if (WebSocketConst.CLIENT_FLAG_WEB.equals(msg.text())) {
                isGameClient = true;
                WebSocketFrame frame = json.insertTime();
                incoming.writeAndFlush(frame);
                frame.release();
            } else {//WebSocketConst.CLIENT_FLAG_JAVA
            }
            session = wsSessionManager.createSession(incoming, this);
            if(isGameClient){
                session.initMsgTime(); //真实玩家客户端连到服务端才需要初始化这个值
            }
            session.setConnected(true);
//            NbTest nbTest = new NbTest();
//            session.sendNetBean(nbTest);
            return;
        } else {
            session = wsSessionManager.getSession(incoming.id());
        }
        session.resetLastMsgTime();
        //读取消息
        Msg read = new Msg(msg.content());
        int event = (int) read.getLong();
        if (event == -1) {//心跳
            read.release();
            return;
        }
//        log.info("通讯事件:{0},信息类型:{1},", event, NetBeanTool.classes.get(event).getSimpleName());
        Object o = NetBeanTool.readObject(read, NetBeanTool.classes.get(event));
        read.release();
        if (wsHandle.onMessage(session, o)) {
            try{
                NetBeanTool.eventAssign(event, o, session);
            }catch (Exception e){
                log.error(e);
                session.close();
                session.setConnected(false);
                wsHandle.disconnected(session);
            }
        }
    }

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
            //连接握手成功
        } else {
            //其他事件
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
        log.info("handlerRemoved " + ctx);
        Channel incoming = ctx.channel();
        WsSession session = wsSessionManager.removeSession(incoming.id());
        if (session != null) {
            session.setConnected(false);
            wsHandle.disconnected(session);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel incoming = ctx.channel();
        log.error(cause, incoming.remoteAddress().toString());//sessions.get(incoming).toString()
    }


}