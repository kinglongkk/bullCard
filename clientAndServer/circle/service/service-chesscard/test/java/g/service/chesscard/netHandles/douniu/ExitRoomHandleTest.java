package g.service.chesscard.netHandles.douniu;

import g.service.chesscard.AbstractHandleTestCase;
import g.service.chesscard.netBeans.douniu.NbExitRoomIn;
import g.service.chesscard.netHandles.IWebSocketHandle;
import g.service.webSocket.context.WsSession;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by Double on 2016/9/28.
 */
public class ExitRoomHandleTest extends AbstractHandleTestCase {

    @Autowired
    @Qualifier("exitRoomHandle")
    private IWebSocketHandle<NbExitRoomIn> handle;

    @Test
    public void handle() throws Exception {
        WsSession wsSession = login(9);
        NbExitRoomIn in = new NbExitRoomIn();
        in.roomId = 1;
        handle.handle(wsSession,in);
    }

}