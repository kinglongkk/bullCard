package g.service.chesscard.engine.manager;

import g.service.chesscard.engine.model.Player;
import g.service.chesscard.AbstractHandleTestCase;
import g.service.chesscard.RedisKeyConst;
import g.service.chesscard.engine.model.Desk;
import g.service.engine.manager.IDeskManager;

import g.service.engine.manager.IPlayerManager;
import org.junit.After;
import org.junit.Test;
import org.soul.commons.cache.CacheKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.nosql.redis.JedisTemplate;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by lenovo on 2017/1/17.
 */
public class DeskManagerTest extends AbstractHandleTestCase {

    @Autowired
    private IDeskManager deskManager;

    @Autowired
    private JedisTemplate jedisTemplateGame;

    @Autowired
    private IPlayerManager playerManager;



    private int deskId = 100, userId = 200;

    @Test
    public void setUserTimeout() throws Exception {

        Long matchCounter = jedisTemplateGame.getAsLong(CacheKey.getCacheKey(
                RedisKeyConst.MATCH_COUNTER, RedisKeyConst.D, deskId));
        matchCounter = (matchCounter == null ? 0 : matchCounter) + 3;

        // 调用测试方法
        Desk desk = deskManager.byId(deskId);
        desk.setUserTimeout(userId);

        String key = CacheKey.getCacheKey(RedisKeyConst.USERS_TIMEOUT, RedisKeyConst.D, deskId);
        Set<String> rs = jedisTemplateGame.zrangeByScore(key, matchCounter, matchCounter);

        assertTrue(rs.contains(String.valueOf(userId)));

    }

    @Test
    public void getTimeoutUsers() throws Exception {

        Player player = playerManager.get(userId);
        player.inDesk(deskId); // 分配坐位

        Desk desk = deskManager.byId(deskId);
        desk.setUserTimeout(userId);

        for (int i = 0; i < 2; i++) { // 两轮内不踢
            Set<String> users = desk.getTimeoutUsers();
            assertNull(users);
        }

        // 第 3 轮 踢人
        Set<String> users = desk.getTimeoutUsers();
        assertNotNull(users);
        assertTrue(users.contains(String.valueOf(userId)));

    }

    @After
    public void tearDown(){
        String key = CacheKey.getCacheKey(RedisKeyConst.USERS_TIMEOUT, RedisKeyConst.D, deskId);
        jedisTemplateGame.zrem(key, String.valueOf(userId));

        jedisTemplateGame.hdel(
                CacheKey.getCacheKey(RedisKeyConst.USERS_POSITION,RedisKeyConst.U,
                        String.valueOf(userId)), Player.PROP_DESK_ID);
    }

}