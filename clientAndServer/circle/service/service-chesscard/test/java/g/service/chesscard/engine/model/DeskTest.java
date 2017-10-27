package g.service.chesscard.engine.model;

import g.service.chesscard.AbstractHandleTestCase;
import g.service.chesscard.RedisKeyConst;
import g.service.engine.manager.IBetManager;
import g.service.engine.manager.IDeskManager;
import g.service.engine.manager.IPlayerManager;
import org.junit.Assert;
import org.junit.Test;
import org.soul.commons.cache.CacheKey;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.nosql.redis.JedisTemplate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by longer on 3/15/17.
 */
public class DeskTest extends AbstractHandleTestCase {

    private static Log log = LogFactory.getLog(DeskTest.class);

    @Autowired
    private IDeskManager deskManager;

    @Autowired
    private IBetManager betManager;

    @Autowired
    private IPlayerManager playerManager;

    @Autowired
    private JedisTemplate jedisTemplateGame;


    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);

    private Integer[] userIds = {1,2,3,4,5,6,7,8,9,10};

    @Override
    public void setup() {
        super.roomId = 1;
        super.setup();
    }

    /**
     * 用例说明:
     *      1) 先把座位坐满 + 1群众
     *      2) 进行投注
     *      3) 退出一人
     *      4) 所有人都有座位
     * @throws Exception
     */
    @Test
    public void reAllocate() throws Exception {
        Integer deskId = roomId;
        Desk desk = deskManager.byId(deskId); //roomId == deskId
        for (Integer userId : userIds) {
            login(userId);
            roomManager.exit(userId);//准备数据-先退出玩家
            desk.getRoom().into(userId);

            //桌子上的玩家的投注额
            this.jedisTemplateGame.zincrby(
                    CacheKey.getCacheKey(RedisKeyConst.DESK_USER, RedisKeyConst.D, deskId),
                    userId * 1000,
                    String.valueOf(userId)
            );
        }
        Player player = null;
        player = playerManager.get(8);
        Assert.assertTrue(player.getSeatId() != null);
        Integer seatIdVoid = player.getSeatId();
        roomManager.exit(8);//退出一人
        desk.reAllocate();
        player = playerManager.get(9);
        Assert.assertTrue(player.getSeatId() == null);

        player = playerManager.get(10);
        Assert.assertEquals(player.getSeatId() , seatIdVoid);
        TimeUnit.SECONDS.sleep(1);
    }

    @Override
    public void after() {
        betManager.clearBetData(roomId);  //roomId == deskId
        super.after();
    }
}