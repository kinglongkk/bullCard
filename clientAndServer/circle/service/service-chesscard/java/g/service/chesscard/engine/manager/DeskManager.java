package g.service.chesscard.engine.manager;

import g.service.chesscard.RedisKeyConst;
import g.service.chesscard.engine.factory.IModelFactory;
import g.service.chesscard.engine.listener.DeskEvent;
import g.service.chesscard.engine.model.Desk;
import g.service.chesscard.engine.model.GameDesk;
import g.service.chesscard.engine.model.Player;
import g.service.chesscard.engine.model.Room;
import g.service.engine.listener.IDeskListener;
import g.service.engine.manager.IBetCoinLimitManager;
import g.service.engine.manager.IDeskManager;
import g.service.engine.manager.IPlayerManager;
import g.service.engine.manager.ISeatManager;
import org.soul.commons.bean.BeanTool;
import org.soul.commons.cache.CacheKey;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.spring.utils.SpringTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.nosql.redis.JedisTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.gentlyweb.utils.GeneralComparator.XMLConstants.id;

/**
 * Created by Double on 2016/9/29.
 * 桌子管理器
 */
public class DeskManager implements IDeskManager {

    private static Log log = LogFactory.getLog(DeskManager.class);

    @Autowired
    private IModelFactory modelFactory;

    private IDeskListener deskListener;

    @Autowired
    private JedisTemplate jedisTemplateGame;

    @Autowired
    private ISeatManager seatManager;

    @Autowired
    private IPlayerManager playerManager;

    @Autowired
    private IBetCoinLimitManager betCoinLimitManager;

    private ConcurrentHashMap<Integer,Desk> concurrentHashMap = new ConcurrentHashMap();

    @Override
    public void init(Room room) {
        Integer gameId = room.getGameId();
        Integer modelId = room.getGameModelId();
        Integer roomId = room.getId();

        //生成桌子对象
        Integer deskId = roomId; //一个房间一张桌子
        modelFactory.addDeskManager(roomId, deskId, this);

        GameDesk gameDesk = new GameDesk();
        gameDesk.setId(deskId);
        gameDesk.setGameId(gameId);
        gameDesk.setGameModelId(modelId);
        gameDesk.setGameRoomId(roomId);
        gameDesk.setSeatNumber(getSeatSize());
        gameDesk.setPlayerCount(0);

        Desk desk = (Desk) SpringTool.getBean("desk");
        desk.setRoom(room);
        desk.setMo(gameDesk);
        this.flush(gameDesk);
        concurrentHashMap.put(deskId,desk);

        this.seatManager.init(desk);
        this.initSortedDesk(desk);
        this.betCoinLimitManager.init(desk.getId());
        deskListener.onNew(new DeskEvent(desk.getId()));
    }

    /**
     * 以空闲的座位为分值
     *  @param desk
     *
     */
    private void initSortedDesk(Desk desk) {
        jedisTemplateGame.zadd(
                CacheKey.getCacheKey(RedisKeyConst.ROOM_DESK, RedisKeyConst.R, desk.getId())
                , getSeatSize()
                , String.valueOf(desk.getId())
        );
    }

    private void flush(GameDesk gameDesk) {
        jedisTemplateGame.hmset(
                CacheKey.getCacheKey(RedisKeyConst.DESK, RedisKeyConst.D, gameDesk.getId()),
                BeanTool.extractNoNull(gameDesk));
    }

    @Override
    public void destroy(Integer deskId) {

    }

    @Override
    public Desk byId(Integer deskId) {
        return concurrentHashMap.get(deskId);
    }

    @Override
    public Desk allocate(Player player, int roomId) {
        Set<String> deskIds = jedisTemplateGame.zrange(
                CacheKey.getCacheKey(RedisKeyConst.ROOM_DESK, RedisKeyConst.R, roomId)
                , 0, 1
        );
        //warning: 投注玩法一间房只有一张桌子
        Integer deskId = Integer.valueOf(deskIds.iterator().next());
        Desk desk = byId(deskId);
        desk.allocate(player);
        return desk;
    }

    // getter and setter
    public void setDeskListener(IDeskListener deskListener) {
        this.deskListener = deskListener;
    }

    public Integer getSeatSize() {
        return 8;
    }

    @Override
    public Desk byUserId(Integer userId) {
        Player player = playerManager.get(userId);
        Integer deskId = player.getDeskId();
        return deskId == null ? null : byId(deskId);
    }

    @Override
    public Set<String> getDeskUsers(Integer deskId) {
        return this.jedisTemplateGame.zrange(
                CacheKey.getCacheKey(RedisKeyConst.DESK_USER, RedisKeyConst.D, deskId)
                , 0,
                -1
        );
    }

    @Override
    public Integer getDealerId(Integer deskId) {
        String rs = this.jedisTemplateGame.hget(
                CacheKey.getCacheKey(RedisKeyConst.DESK, RedisKeyConst.D, deskId),
                GameDesk.PROP_DEALER_USER_ID);
        return rs == null ? null : Integer.valueOf(rs);
    }
}
