package g.service.chesscard.engine.manager;

import g.model.room.po.VRoom;
import g.model.room.vo.VRoomVo;
import g.service.chesscard.AbstractHandleTestCase;
import g.service.chesscard.engine.model.Player;
import g.service.chesscard.engine.model.Room;
import g.service.engine.manager.IPlayerManager;
import g.service.engine.manager.IRoomManager;
import g.service.room.IVRoomService;
import g.service.webSocket.context.WsSession;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.soul.commons.spring.utils.SpringTool;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by longer on 2016/12/15.
 */
public class RoomManagerTest extends AbstractHandleTestCase {

    @Autowired
    private IRoomManager roomManager;

    @Autowired
    private IVRoomService roomService;

    @Autowired
    private IPlayerManager playerManager;


    private VRoom room;

    @Before
    public void setup(){
        super.setup();

        //初始化房间(百人大战的房间)
        VRoomVo vo = new VRoomVo();
        vo.getSearch().setId(1);
        vo = roomService.get(vo);
        room = vo.getResult();
        roomManager.init(room);
    }

    @After
    public void tearDown(){
        super.after();
    }

    /**
     * 用例: 一个玩家请求多次进入房间，只能有一次分配座位的动作
     */
    @Test
    public void test_into(){
        Integer userId = 9;
        login(userId);

        Room room = roomManager.get(1);

        Integer seatNo = null;
        Integer seatNoFirst = null;
        for (int i = 0; i < 10; i++) {
            room.into(userId);
            Player player = playerManager.get(userId);
            seatNo = player.getSeatId();
            Assert.assertNotNull(seatNo);
            if (seatNoFirst != null) {
                //后面尝试进入房间的请求，只能得到第一次分配的座位
                Assert.assertEquals(seatNo,seatNoFirst);
            } else {
                seatNoFirst = seatNo;
            }
        }
    }

    /**
     * 用例: 多人进入同一个房间
     * 预期: 按座位分配玩家,后来者没有座位
     */
    @Test
    public void into_with_multiple_player(){
        Room room = roomManager.get(7);

        int i = 0;
        int[] userIds = new int[]{1,2,3,4,5,6,7,8,9};
        for (int userId : userIds) {
            login(userId);
            room.into(userId);
            Player player = playerManager.get(userId);
            Integer seatNo = player.getSeatId();
            if (i++ >= 8) { //百人大战一个桌子人数
                assertNull(seatNo);
            }
        }
    }

    @Test
    public void test_exit_null(){
        Integer userId = 9;
        WsSession wsSession = login(userId);
        roomManager.exit(9); // ,wsSession.getId()
    }

    @Test
    public void test_exit_1(){
        Integer roomId = 1;
        Room room = roomManager.get(roomId);

        Integer userId = 9;
        WsSession wsSession = login(userId);
        room.into(userId);
        roomManager.exit(9); // ,wsSession.getId()
    }
}