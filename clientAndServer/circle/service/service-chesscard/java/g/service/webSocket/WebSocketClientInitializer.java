package g.service.webSocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.net.URI;

public class WebSocketClientInitializer extends ChannelInitializer<Channel> {

    private URI webSocketURI;
    private WebSocketClientHandler webSocketClientHandler;

    public WebSocketClientInitializer(URI webSocketURI, WebSocketClientHandler webSocketClientHandler) {
        this.webSocketURI = webSocketURI;
        this.webSocketClientHandler = webSocketClientHandler;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpClientCodec());
        pipeline.addLast(new HttpObjectAggregator(64 * 1024));
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new WebSocketClientProtocolHandler(
                WebSocketClientHandshakerFactory.newHandshaker(
                        webSocketURI, WebSocketVersion.V13, null, false, new DefaultHttpHeaders())));
        pipeline.addLast(webSocketClientHandler);
    }
}