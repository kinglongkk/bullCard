package g.service.chesscard.engine.model;

import g.model.player.po.UserPlayer;
import g.service.chesscard.RedisKeyConst;
import g.service.chesscard.engine.factory.IModelFactory;
import g.service.engine.manager.IDeskManager;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.soul.commons.cache.CacheKey;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.nosql.redis.JedisTemplate;

/**
 * Created by Double on 16/10/9.
 */
public class Player extends UserPlayer {
    private static final Log log = LogFactory.getLog(Player.class);

    private Desk desk;

    public Player() {
    }

    @Autowired
    private JedisTemplate jedisTemplateGame;

    @Autowired
    private IModelFactory modelFactory;

    public static final String PROP_ROOM_ID = "roomId";
    public static final String PROP_DESK_ID = "deskId";
    public static final String PROP_SEAT_ID = "seatId";
    public static final String PROP_ABNORMAL_OUT_GAME = "abnormalOutGame";

    @Override

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public void destroy() {
       //常规退出
        this.jedisTemplateGame.del(
                CacheKey.getCacheKey(RedisKeyConst.USERS, RedisKeyConst.U, getId())
        );
    }

    public Long incrCoin(Long coin) {
        return this.jedisTemplateGame.hincrBy(
                CacheKey.getCacheKey(RedisKeyConst.USERS, RedisKeyConst.U, getId()),
                UserPlayer.PROP_COIN, coin);
    }

    @Override
    public Long getCoin() {
        String rs = this.jedisTemplateGame.hget(
                CacheKey.getCacheKey(RedisKeyConst.USERS, RedisKeyConst.U, getId()),
                UserPlayer.PROP_COIN);
        if (rs == null) {
            return 0l;
        }
        return Long.valueOf(rs);
    }


    public String getSessionId() {
        return jedisTemplateGame.hget(
                CacheKey.getCacheKey(RedisKeyConst.USERS, RedisKeyConst.U, getId()),
                UserPlayer.PROP_SESSION_ID
        );
    }

    //--Old playerPositionManager begin--

    public boolean inRoom(Integer roomId) {
        return jedisTemplateGame.hsetnx(
                CacheKey.getCacheKey(RedisKeyConst.USERS_POSITION, RedisKeyConst.U, getId()),
                Player.PROP_ROOM_ID, String.valueOf(roomId));
    }

    public boolean outRoom() {
        return jedisTemplateGame.del(
                CacheKey.getCacheKey(RedisKeyConst.USERS_POSITION, RedisKeyConst.U, getId())
        );
    }

    public Integer getRoomId() {
        String id = this.jedisTemplateGame.hget(
                CacheKey.getCacheKey(RedisKeyConst.USERS_POSITION, RedisKeyConst.U, getId()),
                Player.PROP_ROOM_ID);
        return id == null ? null : Integer.valueOf(id);
    }

    public Integer getDeskId() {
        String id = this.jedisTemplateGame.hget(
                CacheKey.getCacheKey(RedisKeyConst.USERS_POSITION, RedisKeyConst.U, getId()),
                Player.PROP_DESK_ID);
        return id == null ? null : Integer.valueOf(id);
    }

    public boolean inDesk(Integer deskId) {
        return jedisTemplateGame.hsetnx(
                CacheKey.getCacheKey(RedisKeyConst.USERS_POSITION, RedisKeyConst.U, getId()),
                Player.PROP_DESK_ID, String.valueOf(deskId));
    }

    public boolean outDesk() {
        Long rs = jedisTemplateGame.hdel(
                CacheKey.getCacheKey(RedisKeyConst.USERS_POSITION, RedisKeyConst.U, getId()),
                Player.PROP_DESK_ID);
        outSeat();
        return rs > 0;
    }

    public boolean inSeat(Integer seatNo) {
        boolean rs = this.jedisTemplateGame.hsetnx(
                CacheKey.getCacheKey(RedisKeyConst.USERS_POSITION, RedisKeyConst.U, getId()),
                Player.PROP_SEAT_ID, String.valueOf(seatNo));
        if (rs) {
            log.info("玩家:{0},入座:{1},成功!", getId(), seatNo);
        } else {
            log.warn("玩家:{0},入座:{1},失败!", getId(), seatNo);
        }
        return rs;
    }

    public Integer getSeatId() {
        String id = this.jedisTemplateGame.hget(
                CacheKey.getCacheKey(RedisKeyConst.USERS_POSITION, RedisKeyConst.U, getId()),
                Player.PROP_SEAT_ID);
        return id == null ? null : Integer.valueOf(id);
    }

    public boolean outSeat() {
        return jedisTemplateGame.hdel(
                CacheKey.getCacheKey(RedisKeyConst.USERS_POSITION, RedisKeyConst.U, getId()),
                Player.PROP_SEAT_ID) > 0;
    }

    public boolean isInMatch() {
        Integer deskId = getDeskId();
        if (deskId == null) {
            return false;
        }
        IDeskManager deskManager = modelFactory.getDeskManager(deskId);
        Desk desk = deskManager.byId(deskId);
        return desk.isInMatch(getId());
    }

    public void setAbnormalOutGame(Boolean isUnnormal) {
        this.jedisTemplateGame.hset(
                CacheKey.getCacheKey(RedisKeyConst.USERS_POSITION, RedisKeyConst.U, getId()),
                Player.PROP_ABNORMAL_OUT_GAME,
                String.valueOf(isUnnormal));
    }

    public boolean isAbnormalOutGame() {
        String rs = this.jedisTemplateGame.hget(
                CacheKey.getCacheKey(RedisKeyConst.USERS_POSITION, RedisKeyConst.U, getId()),
                Player.PROP_ABNORMAL_OUT_GAME);
        return Boolean.valueOf(rs);
    }
    //--Old playerPositionManager end--

    //--------

    public void setJedisTemplateGame(JedisTemplate jedisTemplateGame) {
        this.jedisTemplateGame = jedisTemplateGame;
    }

    public void setModelFactory(IModelFactory modelFactory) {
        this.modelFactory = modelFactory;
    }

    /**
     * warning:此Desk为内存对象,引擎内部方能调用
     * @return
     */
    public Desk getDesk() {
        return desk;
    }

    public void setDesk(Desk desk) {
        this.desk = desk;
    }
}
