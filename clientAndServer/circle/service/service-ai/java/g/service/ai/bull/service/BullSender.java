package g.service.ai.bull.service;

import g.service.ai.bull.manager.AiUserMng;
import g.service.ai.bull.model.AiClient;
import g.service.chesscard.netBeans.common.NbLogin;
import g.service.chesscard.netBeans.douniu.NbIntoRoomIn;
import g.service.webSocket.context.WsSession;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 消息发送管理类
 * Created by MK on 2016/12/20.
 */
@Service
public class BullSender {

    @Autowired
    private AiUserMng userMng;
    @Autowired
    private AiService aiService;
    private static Log log = LogFactory.getLog(BullSender.class);

    public void sendIntoRoom(WsSession session, Integer roomId) {
        NbIntoRoomIn nbIntoRoomIn = new NbIntoRoomIn();
        nbIntoRoomIn.roomId = roomId;
        long now = System.currentTimeMillis();
        AiClient client = (AiClient) session.getOwner();
        if(client == null){
            return;
        }
        client.setIntoRoomTime(now);
        aiService.schedule.schedule(()->{
            try{
                if(client.getIntoRoomTime() == now){//无响应
                    aiService.disconnected(client);
                }
            }catch (Exception e){
                log.error(e);
            }
        },3000, TimeUnit.MILLISECONDS);
        session.sendNetBean(nbIntoRoomIn);
    }

    public void sendLogin(WsSession session, Integer userId) {
        NbLogin login = new NbLogin();
        login.userId=userId;
        login.token=userMng.getLoginToken(String.valueOf(login.userId));
        session.sendNetBean(login);
    }
}
