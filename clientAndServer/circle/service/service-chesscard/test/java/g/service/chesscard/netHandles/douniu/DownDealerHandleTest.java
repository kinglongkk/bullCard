package g.service.chesscard.netHandles.douniu;

import g.service.chesscard.AbstractHandleTestCase;
import g.service.chesscard.netBeans.douniu.NbDownDealerIn;
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
public class DownDealerHandleTest extends AbstractHandleTestCase {

    private static Log log = LogFactory.getLog(DownDealerHandleTest.class);

    @Autowired
    @Qualifier("downDealerHandle")
    private IWebSocketHandle<NbDownDealerIn> handle;

    @Test
    public void upDealerTest(){
        WsSession wsSession = login(9);
        NbDownDealerIn in = new NbDownDealerIn();
        handle.handle(wsSession,in);
    }

}
