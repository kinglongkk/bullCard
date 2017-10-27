package g.service.ai.bull.netHandles;

import g.service.ai.bull.service.AiService;
import g.service.ai.bull.model.AiClient;
import g.service.webSocket.codeCreator.IWsHandle;
import g.service.webSocket.context.WsSession;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by KXZ on 2016/12/20.
 */
@Service
public class AiWsHandle implements IWsHandle {

    private static Log log = LogFactory.getLog(AiWsHandle.class);

    @Autowired
    private AiService aiService;

    private AtomicInteger clientCount = new AtomicInteger();

    @Override
    public void connected(WsSession session) {
        log.warn("已连接数量:"+clientCount.incrementAndGet());
    }

    @Override
    public void disconnected(WsSession session) {
        AiClient aiClient = (AiClient)session.getOwner();
        if(aiClient != null){
//            log.error("掉线:"+aiClient.getUserId());
            aiService.disconnected(aiClient);
        }else{
//            log.error("掉线!没有用户");
        }
        log.warn("掉线,已连接数量:"+clientCount.decrementAndGet());
    }

    @Override
    public boolean onMessage(WsSession wsSession, Object msg) {
//        if(((AiClient)wsSession.getOwner()).isHead())log.error("收到消息"+msg.getClass());
//                wsSession.close();
        return true;
    }

    public void setAiService(AiService aiService) {
        this.aiService = aiService;
    }
}
