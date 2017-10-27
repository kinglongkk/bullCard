package g.service.chesscard.engine.model;

import g.model.admin.gameroomconfig.po.GameRoomConfigBull100;
import g.model.room.po.RoomPool;
import g.model.room.po.VRoom;
import g.service.chesscard.RedisKeyConst;
import g.service.chesscard.engine.factory.IModelFactory;
import g.service.chesscard.enums.TipEnum;
import g.service.chesscard.exception.ChessCardException;
import g.service.engine.manager.IDealerManager;
import g.service.engine.manager.IDeskManager;
import g.service.engine.manager.IPlayerManager;
import g.service.engine.manager.IRoom;
import org.soul.commons.cache.CacheKey;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.nosql.redis.JedisTemplate;

import java.io.Serializable;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Double on 2016/9/29.
 * 投注类的房间管理
 */
public class Room implements IRoom {

    private static Log log = LogFactory.getLog(Room.class);

    private VRoom mo;

    @Autowired
    private JedisTemplate jedisTemplateGame;

    @Autowired
    private IPlayerManager playerManager;

    @Autowired
    private IDeskManager deskManager;

    @Autowired
    private IDealerManager dealerManager;

    @Autowired
    IModelFactory modelFactory;
    private Integer minLimitPlayerBlance;
    /** 续庄金额,上庄玩家被提醒续庄时,必须=该金额才能续庄 */
    private volatile long toContinueDealerCoin;

    public Integer getId(){
        return getMo().getId();
    }

    public String getName(){
        return getMo().getName();
    }

    @Override
    public String getModelCode() {
        return jedisTemplateGame.hget(
                CacheKey.getCacheKey(RedisKeyConst.ROOM, RedisKeyConst.R, getId()),
                VRoom.PROP_GAME_MODEL_CODE
        );
    }

    /**
     * 进入房间
     *
     * @return
     */
    @Override
    public Player into(Integer userId) {
        //获取玩家对象
        Player player = playerManager.get(userId);
        Integer deskId = player.getDeskId();
        if (deskId != null) {
            IDeskManager deskManager = modelFactory.getDeskManager(deskId);
            Desk desk = deskManager.byId(deskId);
            if(desk.isInMatch(player.getId())){ // 赛事没结束
                if(player.getId().equals(desk.getDealerId())){ // 下庄处理
                    dealerManager.setDownDealerFlag(player.getId(), deskId);
                }
                throw new ChessCardException(TipEnum.PLAYER_IN_MATCH);
            }
        }

        Long coin = player.getCoin();
        if (coin <= 0) {
            throw new ChessCardException(TipEnum.BALANCE_LESS);
        }

        //获取房间对象
        Integer minLimitPlayerBlance = getMo().getMinLimitPlayerBlance();
        Integer maxLimitPlayerNumber = getMaxLimitPlayerNumber();
        Integer currentPlayerCount = getCurrentPlayerCount();
        if (coin < minLimitPlayerBlance) {
            throw new ChessCardException(TipEnum.BALANCE_LESS);
        }
        if (coin > getMo().getMaxLimitPlayerBlance()) {
            throw new ChessCardException(TipEnum.BALANCE_MORE);
        }
        if (maxLimitPlayerNumber - (currentPlayerCount == null ? 0 : currentPlayerCount) <= 0) {
            throw new ChessCardException(TipEnum.ROOM_FULL);
        }

        Integer seatId = player.getSeatId();
        if (seatId == null) {
            //warning: 无座位的人,才允许分配新的座位(即:清理桌子)
            Desk desk = deskManager.byUserId(userId);
            if (desk != null) {
                desk.outDesk(userId);
            }
        }
        Desk desk = this.deskManager.allocate(player, getId());
        player.setDesk(desk);
        return player;
    }

    @Override
    public void incrPlayerCount() {
        this.jedisTemplateGame.hincrBy(
                CacheKey.getCacheKey(RedisKeyConst.ROOM, RedisKeyConst.R, getId()),
                VRoom.PROP_CURRENT_PLAYER_COUNT,
                1l);
    }

    @Override
    public void incrAiCount() {
        String key = CacheKey.getCacheKey(RedisKeyConst.ROOM, RedisKeyConst.R, getId());
        this.jedisTemplateGame.hincrBy(key, VRoom.PROP_CURRENT_AI_COUNT, 1l);
    }

    @Override
    public void decrPlayerCount() {
        String key = CacheKey.getCacheKey(RedisKeyConst.ROOM, RedisKeyConst.R, getId());
        Long rs = this.jedisTemplateGame.hincrBy(key, VRoom.PROP_CURRENT_PLAYER_COUNT, -1l);
        //warning: 预防小于0的情况
        if (rs < 0) {
            Long newRs = this.jedisTemplateGame.hincrBy(key, VRoom.PROP_CURRENT_PLAYER_COUNT, 1l);
            log.error("房间号:{0},出现人数小于0的情况，当前值:{1},重新设置为:{2}", getId(), rs, newRs);
        }
    }

    @Override
    public void decrAiCount() {
        String key = CacheKey.getCacheKey(RedisKeyConst.ROOM, RedisKeyConst.R, getId());
        Long rs = this.jedisTemplateGame.hincrBy(key, VRoom.PROP_CURRENT_AI_COUNT, -1l);
        //warning: 预防小于0的情况
        if (rs < 0) {
            Long newRs = this.jedisTemplateGame.hincrBy(key, VRoom.PROP_CURRENT_AI_COUNT, 1l);
            log.error("房间号:{0},出现Ai人数小于0的情况，当前值:{1},重新设置为:{2}", getId(), rs, newRs);
        }
    }

    @Override
    public int[] getBetChip() {
        String key = CacheKey.getCacheKey(RedisKeyConst.ROOM, RedisKeyConst.R, getId());
        String betChip = jedisTemplateGame.hget(key, GameRoomConfigBull100.PROP_BET_CHIP);
        if (betChip != null) {
            StringTokenizer st = new StringTokenizer(betChip, ",");
            int[] betChips = new int[st.countTokens()];
            for (int i = 0; st.hasMoreElements(); i++) {
                betChips[i] = Integer.valueOf(st.nextToken());
            }
            return betChips;
        }
        return new int[0];
    }

    @Override
    public int getBetTimes() {
        return getRoomValueInt(getId(), GameRoomConfigBull100.PROP_BET_TIMES);
    }

    @Override
    public int getDealerBlance() {
        return getRoomValueInt(getId(), GameRoomConfigBull100.PROP_DEALER_BLANCE);
    }

    @Override
    public int getDealerBlanceTip() {
        return getRoomValueInt(getId(), GameRoomConfigBull100.PROP_DEALER_BLANCE_TIP);
    }

    @Override
    public int getDealerBlanceQuit() {
        return getRoomValueInt(getId(), GameRoomConfigBull100.PROP_DEALER_BLANCE_QUIT);
    }

    @Override
    public int getRoomValueInt(Serializable roomId, String fieldName) {
        String key = CacheKey.getCacheKey(RedisKeyConst.ROOM, RedisKeyConst.R, getId());
        String v = jedisTemplateGame.hget(key, fieldName);
        return v == null ? 0 : Integer.valueOf(v);
    }

    @Override
    public void incrJackpot(long diffCoin) {
        String key = CacheKey.getCacheKey(RedisKeyConst.ROOM, RedisKeyConst.R, getId());
        Long rs = this.jedisTemplateGame.hincrBy(key, VRoom.PROP_JACKPOT, diffCoin);
    }

    public RoomPool getRoomPool() {
        String key = CacheKey.getCacheKey(RedisKeyConst.ROOM, RedisKeyConst.R, getId());
        List<String> result = jedisTemplateGame.hmget(key, RoomPool.PROP_JACKPOT, RoomPool.PROP_MAX_JACKPOT_LIMIT,
                RoomPool.PROP_MAX_LIMIT_GAME_LOSE, RoomPool.PROP_MIN_JACKPOT_LIMIT, RoomPool.PROP_JACKPOT_OVERFLOW,
                RoomPool.PROP_MAX_JACKPOT_A_MATCH);
        if (result != null && !result.isEmpty()) {
            RoomPool roomPool = new RoomPool();
            roomPool.setJackpot(Long.parseLong(result.get(0)));
            roomPool.setMaxJackpotLimit(Long.parseLong(result.get(1)));
            roomPool.setMaxLimitGameLose(Long.parseLong(result.get(2)));
            roomPool.setMinJackpotLimit(Long.parseLong(result.get(3)));
            roomPool.setJackpotOverflow(Long.parseLong(result.get(4)));
            roomPool.setMaxJackpotAmatch(Long.parseLong(result.get(5)));
            return roomPool;
        } else {
            return null;
        }
    }

    //---------------Getter & Setter-------------

    private VRoom getMo() {
        return mo;
    }

    public void setMo(VRoom mo) {
        this.mo = mo;
    }

    public Integer getGameId() {
        return getMo().getGameId();
    }

    public Integer getGameModelId() {
        return getMo().getGameModelId();
    }


    public Integer getMinLimitPlayerBlance() {
        return getMo().getMinLimitPlayerBlance();
    }

    public Integer getMaxLimitPlayerNumber() {
        String count = this.jedisTemplateGame.hget(
                CacheKey.getCacheKey(RedisKeyConst.ROOM, RedisKeyConst.R, getId()),
                VRoom.PROP_MAX_LIMIT_PLAYER_NUMBER
        );
        return StringTool.isNotBlank(count) ? Integer.valueOf(count) : 0;
    }

    /**
     * 获取当前房间玩家人数
     * @return
     */
    public Integer getCurrentPlayerCount() {
        String count = this.jedisTemplateGame.hget(
                CacheKey.getCacheKey(RedisKeyConst.ROOM, RedisKeyConst.R, getId()),
                VRoom.PROP_CURRENT_PLAYER_COUNT
        );
        return StringTool.isNotBlank(count) ? Integer.valueOf(count) : 0;
    }

    public long getToContinueDealerCoin() {
        return toContinueDealerCoin;
    }

    public void setToContinueDealerCoin(long toContinueDealerCoin) {
        this.toContinueDealerCoin = toContinueDealerCoin;
    }
}
