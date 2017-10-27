package g.service.chesscard.netHandles.douniu;

import g.service.chesscard.AbstractHandleTestCase;
import g.service.chesscard.netBeans.douniu.NbIntoRoomIn;
import g.service.chesscard.netBeans.douniu.NbUpDealerIn;
import g.service.chesscard.netHandles.IWebSocketHandle;
import g.service.webSocket.context.WsSession;
import org.junit.Test;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by tony on 2016/11/15.
 * 上庄测试
 */
public class UpDealerHandleTest extends AbstractHandleTestCase {

    private static Log log = LogFactory.getLog(UpDealerHandleTest.class);

    @Autowired
    @Qualifier("intoRoomHandle")
    private IWebSocketHandle<NbIntoRoomIn> intoRoomHandle;

    @Autowired
    @Qualifier("upDealerHandle")
    private IWebSocketHandle<NbUpDealerIn> upDealerHandle;


    @Test
    public void upDealerTest(){
        WsSession wsSession = login(9);
        NbIntoRoomIn intoRoomIn = new NbIntoRoomIn();
        intoRoomIn.roomId = 1;
        intoRoomHandle.handle(wsSession,intoRoomIn);

        NbUpDealerIn dealerIn = new NbUpDealerIn();
        dealerIn.coin = 6000000;
        upDealerHandle.handle(wsSession,dealerIn);
    }

}
