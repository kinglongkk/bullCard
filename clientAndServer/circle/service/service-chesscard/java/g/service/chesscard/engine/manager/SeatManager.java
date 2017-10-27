package g.service.chesscard.engine.manager;

import g.service.chesscard.engine.model.Desk;
import g.service.chesscard.engine.model.GameDeskSeat;
import g.service.chesscard.RedisKeyConst;
import g.service.chesscard.engine.model.Player;
import g.service.chesscard.enums.TipEnum;
import g.service.chesscard.exception.ChessCardException;
import g.service.engine.manager.IDeskManager;

import g.service.engine.manager.IPlayerManager;
import g.service.engine.manager.ISeatManager;
import org.soul.commons.cache.CacheKey;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.nosql.redis.JedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 座位管理器
 * Created by Jason on 2016/11/7.
 */
public class SeatManager implements ISeatManager {

    private static Log log = LogFactory.getLog(SeatManager.class);

    @Autowired
    private JedisTemplate jedisTemplateGame;

    @Autowired
    private IPlayerManager playerManager;


    @Override
    public void init(Desk desk) {
        Integer deskSeat = desk.getSeatNumber();
        String[] seatNos = new String[deskSeat];
        for (int i = 0; i < deskSeat; i++) {
            seatNos[i] = String.valueOf(i + 1);
        }

        String deskSeatAvailableKey = CacheKey.getCacheKey(RedisKeyConst.SEAT_VOID, RedisKeyConst.D, desk.getId());
        if (!jedisTemplateGame.exists(deskSeatAvailableKey)) {
            jedisTemplateGame.sadd(deskSeatAvailableKey, seatNos);
        }
    }

    @Override
    public void destroy() {

    }

    //TODO:Double 检查座位的释放
    @Override
    public List<GameDeskSeat> getSeatList(Integer deskId) {
        Map<String, String> seatMap = getSeatUser(deskId);
        List<GameDeskSeat> seatList = new ArrayList<>();
        for (Map.Entry<String, String> entry : seatMap.entrySet()) {
            GameDeskSeat gameDeskSeat = new GameDeskSeat();
            gameDeskSeat.setGameDeskId(deskId);
            gameDeskSeat.setSeatNo(Integer.valueOf(entry.getKey()));
            gameDeskSeat.setUserId(Integer.valueOf(entry.getValue()));
            seatList.add(gameDeskSeat);
        }
        return seatList;
    }

    @Override
    public Map<String, String> getSeatUser(Integer deskId) {
        return this.jedisTemplateGame.hgetAll(
                CacheKey.getCacheKey(RedisKeyConst.SEAT_USER, RedisKeyConst.D, deskId)

        );
    }

    @Override
    public Set<String> getSeatVoid(Integer deskId){
        return jedisTemplateGame.smembers(
                CacheKey.getCacheKey(RedisKeyConst.SEAT_VOID, RedisKeyConst.D, deskId)
        );
    }

    @Override
    public Integer atSeat(Desk desk, Player player) {
        Integer userId = player.getId();
        if (desk.isDealer(userId) ){
            return null;
        }
        Integer deskId = desk.getId();
        String seatNo = jedisTemplateGame.spop(CacheKey.getCacheKey(RedisKeyConst.SEAT_VOID, RedisKeyConst.D, deskId));
        if (seatNo == null) {
            log.info("玩家:{0},桌子:{1},申请不到座位", userId, deskId, seatNo);
            return null;
        }
        boolean success = player.inSeat(Integer.valueOf(seatNo));
        if (success) {
            jedisTemplateGame.zincrby(
                    CacheKey.getCacheKey(RedisKeyConst.ROOM_DESK, RedisKeyConst.R, desk.getGameRoomId())
                    , -1, String.valueOf(deskId)
            );
        } else {
            //玩家已在座
            jedisTemplateGame.sadd(
                    CacheKey.getCacheKey(RedisKeyConst.SEAT_VOID, RedisKeyConst.D, deskId)
                    , seatNo
            );
            log.warn("玩家:{0},分配不到座位bug:桌子:{1},座位:{2}", userId, deskId, seatNo);
//            throw new ChessCardException(TipEnum.PLAYER_ALREADY_IN_SEAT);//不应抛错
        }

        this.jedisTemplateGame.hsetnx(
                CacheKey.getCacheKey(RedisKeyConst.SEAT_USER, RedisKeyConst.D, deskId),
                CacheKey.getCacheKey(seatNo),
                String.valueOf(userId));
        log.info("玩家:{0},落座于桌子:{1},座位:{2}", userId, deskId, seatNo);
        return Integer.valueOf(seatNo);

    }

    @Override
    public void outSeat(GameDeskSeat gameDeskSeat) {
        Integer userId = gameDeskSeat.getUserId();
        Player player = playerManager.get(userId);
        Integer deskId = gameDeskSeat.getGameDeskId();
        Integer roomId = player.getRoomId();
        if (deskId == null) return;
        player.outSeat();

        if (jedisTemplateGame.zscore(
                CacheKey.getCacheKey(RedisKeyConst.ROOM_DESK, RedisKeyConst.R, roomId),
                String.valueOf(deskId)) != null) {
            jedisTemplateGame.zincrby(
                    CacheKey.getCacheKey(RedisKeyConst.ROOM_DESK, RedisKeyConst.R, roomId)
                    , 1, String.valueOf(deskId)
            );
        }

        String seatHashKey = CacheKey.getCacheKey(RedisKeyConst.SEAT_USER, RedisKeyConst.D, deskId);
        //临时方案：查询所有座位上的人
        Map<String, String> seatUserIds = jedisTemplateGame.hgetAll(seatHashKey);
        for (Map.Entry<String, String> entry : seatUserIds.entrySet()) {
            if (entry.getValue().equals(String.valueOf(gameDeskSeat.getUserId()))) {
                String seatMapKey = CacheKey.getCacheKey(entry.getKey());
                this.jedisTemplateGame.hdel(seatHashKey, seatMapKey);
                jedisTemplateGame.sadd(
                        CacheKey.getCacheKey(RedisKeyConst.SEAT_VOID, RedisKeyConst.D, deskId)
                        , entry.getKey()
                );
                log.info("玩家:{0},离开于桌子:{1},座位:{2}", gameDeskSeat.getUserId(), gameDeskSeat.getGameDeskId(), entry.getKey());
            }
        }
    }
}
