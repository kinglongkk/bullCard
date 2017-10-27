package g.service.chesscard;

import g.model.common.SessionKey;
import g.model.player.po.UserPlayer;
import g.model.room.po.VRoom;
import g.model.room.vo.VRoomVo;
import g.service.chesscard.netBeans.common.NbLogout;
import g.service.chesscard.netHandles.common.LoginHandle;
import g.service.chesscard.netHandles.common.LogoutHandle;
import g.service.engine.manager.IRoomManager;
import g.service.room.IVRoomService;
import g.service.webSocket.context.IWsSessionManager;
import g.service.webSocket.context.WsChannel;
import g.service.webSocket.context.WsSession;
import g.service.engine.model.BaseTestCase;
import io.netty.channel.ChannelId;
import org.junit.After;
import org.junit.Before;
import org.soul.commons.lang.string.RandomStringTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Set;

/**
 * Created by Double on 2016/9/28.
 */
public abstract class AbstractHandleTestCase extends BaseTestCase {

    @Autowired
    @Qualifier("loginHandle")
    private LoginHandle loginHandle;

    @Autowired
    @Qualifier("logoutHandle")
    protected LogoutHandle logoutHandle;

    @Autowired
    private IWsSessionManager wsSessionManager;

    @Autowired
    protected IRoomManager roomManager;

    @Autowired
    private IVRoomService roomService;

    protected VRoom vRoom;

    protected Integer roomId = 1;   //default is room1

    @Before
    public void setup(){
        //默认加载房间号1(百人大战)
        VRoomVo vo = new VRoomVo();
        vo.getSearch().setId(roomId);
        vo = roomService.get(vo);
        vRoom = vo.getResult();
        roomManager.init(vRoom);
    }

    protected WsSession login(Integer playerId) {
        String channelId = RandomStringTool.randomAscii(12);
        MyChannelId myChannelId = new MyChannelId(channelId);
        WsChannel wsChannel = mock(WsChannel.class);
        when(wsChannel.getSessionManager()).thenReturn(wsSessionManager);
        when(wsChannel.getId()).thenReturn(myChannelId);
        when(wsChannel.isActive()).thenReturn(true);
        WsSession wsSession = new WsSession(wsChannel);
        wsSession.setConnected(true);
        loginHandle.saveUserToSession(wsSession,playerId);
        wsSessionManager.putSession(myChannelId, wsSession);
        return wsSession;
    }

    @After
    public void after(){
        Set<ChannelId> channelIds = wsSessionManager.getSessions().keySet();
        logout(channelIds);
    }

    protected void logout(Set<ChannelId> channelIds) {
        for (ChannelId channelId : channelIds) {
            WsSession v = wsSessionManager.getSession(channelId);
            UserPlayer userPlayer = (UserPlayer) v.getAttr(SessionKey.S_USER);
            if (userPlayer != null) {
                NbLogout nbLogout = new NbLogout();
                nbLogout.userId = userPlayer.getId();
                logoutHandle.handle(v,nbLogout);
            }
        }
    }

    class MyChannelId implements ChannelId{

        private String id;

        public MyChannelId(String id){
            this.id = id;
        }

        @Override
        public String asShortText() {
            return id;
        }

        @Override
        public String asLongText() {
            return id;
        }

        @Override
        public int compareTo(ChannelId o) {
            return asShortText().compareTo(o.asShortText());
        }

        @Override
        public String toString() {
            return id;
        }
    }

}
