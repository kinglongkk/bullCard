package g.service.chesscard.engine.model;

import g.model.match.po.Match;
import g.service.chesscard.RedisKeyConst;
import g.service.chesscard.engine.EngineConst;
import g.service.chesscard.engine.factory.IModelFactory;
import g.service.chesscard.engine.task.IMatchTask;
import g.service.engine.manager.*;
import g.service.engine.schedule.IDeskSchedule;
import g.service.webSocket.context.IWsSessionManager;
import g.service.webSocket.context.WsSession;
import org.soul.commons.cache.CacheKey;
import org.soul.commons.collections.CollectionTool;
import org.soul.commons.collections.MapTool;
import org.soul.commons.collections.SetTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.nosql.redis.JedisTemplate;
import redis.clients.jedis.ZParams;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by lenovo on 2017/2/9.
 */
public class Desk {

    private static Log log = LogFactory.getLog(Desk.class);

    private Room room;
    private AtomicBoolean lastSecondBetd = new AtomicBoolean();

    /**
     * 内存对象
     */
    private GameDesk mo;

    @Autowired
    private IModelFactory modelFactory;

    @Autowired
    private JedisTemplate jedisTemplateGame;

    @Autowired
    private ISeatManager seatManager;

    @Autowired
    private IRoomManager roomManager;

    @Autowired
    private IPlayerManager playerManager;

    @Autowired
    private IBetManager betManager;

    @Autowired
    private IWsSessionManager wsSessionManager;

    @Autowired
    private IDeskScheduleManager deskScheduleManager;

    @Autowired
    private IDeskManager deskManager;

    @Autowired
    private IDealerManager dealerManager;


    public void incrDealerBalanceCoin(Long incrCoin) {
        this.jedisTemplateGame.hincrBy(
                CacheKey.getCacheKey(RedisKeyConst.DESK, RedisKeyConst.D, getId()),
                GameDesk.PROP_DEALER_BALANCE_COIN,
                incrCoin);
    }

    public void updateDeskDealer(Integer userId, Long coin) {
        Integer deskId = getId();
        this.jedisTemplateGame.hincrBy(
                CacheKey.getCacheKey(RedisKeyConst.DESK, RedisKeyConst.D, deskId),
                GameDesk.PROP_DEALER_BALANCE_COIN,
                coin);
        this.jedisTemplateGame.hset(
                CacheKey.getCacheKey(RedisKeyConst.DESK, RedisKeyConst.D, deskId),
                GameDesk.PROP_DEALER_USER_ID,
                String.valueOf(userId));
    }

    public Integer getDealerId() {
        String rs = this.jedisTemplateGame.hget(
                CacheKey.getCacheKey(RedisKeyConst.DESK, RedisKeyConst.D, getId()),
                GameDesk.PROP_DEALER_USER_ID);
        return rs == null ? null : Integer.valueOf(rs);
    }

    /**
     * 清空桌子庄家数据
     */
    public void resetDeskDealer() {
        Integer deskId = getId();
        this.jedisTemplateGame.hdel(
                CacheKey.getCacheKey(RedisKeyConst.DESK, RedisKeyConst.D, deskId),
                GameDesk.PROP_DEALER_USER_ID,
                GameDesk.PROP_DEALER_BALANCE_COIN
        );
    }

    private void incrPlayerCount() {
        this.jedisTemplateGame.hincrBy(
                CacheKey.getCacheKey(RedisKeyConst.DESK, RedisKeyConst.D, getId()),
                GameDesk.PROP_PLAYER_COUNT,
                1l);
    }

    private void decrPlayerCount() {
        this.jedisTemplateGame.hincrBy(
                CacheKey.getCacheKey(RedisKeyConst.DESK, RedisKeyConst.D, getId()),
                GameDesk.PROP_PLAYER_COUNT,
                -1l);
    }

    /**
     * 清除桌子用户列表数据
     *
     * @param userId
     */
    private Boolean delDeskUser(Integer userId) {
        return jedisTemplateGame.zrem(
                CacheKey.getCacheKey(RedisKeyConst.DESK_USER, RedisKeyConst.D, getId())
                , String.valueOf(userId));
    }

    public void incUserBetCoin(Integer userId, Long coin) {
        this.jedisTemplateGame.zincrby(
                CacheKey.getCacheKey(RedisKeyConst.DESK_USER, RedisKeyConst.D, getId()),
                coin,
                String.valueOf(userId)
        );
    }

    public void initDeskUser() {
        Integer deskId = getId();
        this.jedisTemplateGame.zunionstore(
                CacheKey.getCacheKey(RedisKeyConst.DESK_USER, RedisKeyConst.D, deskId),
                new ZParams().weightsByDouble(0),
                CacheKey.getCacheKey(RedisKeyConst.DESK_USER, RedisKeyConst.D, deskId)
        );
    }

    public Set<String> getDeskUsers() {
        return deskManager.getDeskUsers(getId());
    }

    public List<GameDeskSeat> getGameDeskSeatList() {
        return this.seatManager.getSeatList(getId());
    }

    /**
     * 获取桌子在座的玩家列表，包含庄家
     * @return
     */
    public List<GameDeskSeat> getDeskOnSeatUserList() {
        Integer deskId = getId();
        List<GameDeskSeat> seatList = this.seatManager.getSeatList(deskId);
        for (Iterator<GameDeskSeat> iterator = seatList.iterator();iterator.hasNext();) {
            GameDeskSeat gameDeskSeat = iterator.next();
            if (gameDeskSeat.getUserId() == null) {
                iterator.remove();
            }
        }
        Integer dealerUserId = getDealerUserId();
        if (dealerUserId != null) {
            GameDeskSeat dealer = new GameDeskSeat();
            dealer.setUserId(dealerUserId);
            dealer.setSeatNo(EngineConst.SEAT_NO_BANKER);
            seatList.add(dealer);
        }
        return seatList;
    }

    public void outDesk(Integer userId) {
        Player player = playerManager.get(userId);
        Integer deskId = player.getDeskId();
        log.debug("玩家退出桌子 userId:{0}, deskId:{1}", userId, deskId);

        Integer roomId = modelFactory.getRoomId(deskId);
        if (delDeskUser(userId) && roomId != null) {
            Room room = roomManager.get(roomId);
            room.decrPlayerCount();
            if(player.getIsAi()){
                room.decrAiCount();
            }
        }
        player.outDesk();
        this.decrPlayerCount();

        GameDeskSeat gameDeskSeat = new GameDeskSeat();
        gameDeskSeat.setGameDeskId(deskId);
        gameDeskSeat.setUserId(userId);
        this.seatManager.outSeat(gameDeskSeat);

        jedisTemplateGame.zrem(
                CacheKey.getCacheKey(RedisKeyConst.USERS_TIMEOUT, RedisKeyConst.D, deskId),
                String.valueOf(userId));

        WsSession session = wsSessionManager.getUserSession(userId);
        if (session != null) {
            session.removeChannelInGroup(deskId);
        }

    }

    public List<Integer> clearDeskUser() {
        Integer deskId = getId();
        Integer minBalance = room.getMinLimitPlayerBlance() == null ? 0 : room.getMinLimitPlayerBlance();

        Set<String> deskUserList = getDeskUsers();
        List<Integer> userIds = new ArrayList<>();
        for (String userIdStr : deskUserList) {
            Integer userId = Integer.valueOf(userIdStr);
            // tony 如果玩家在上庄列表，或者在庄不去处理
            //TODO: Double to Stone 需要整理到DealerManger
            if (jedisTemplateGame.zscore(
                    CacheKey.getCacheKey(RedisKeyConst.DESK_DEALERS, RedisKeyConst.D, deskId),
                    String.valueOf(userId)) != null || userId.equals(getMo().getDealerUserId())) {
                continue;
            }
            Player player = playerManager.get(userId);
            Long coin = player.getCoin();
            if (coin < minBalance) {
                userIds.add(userId);
                this.roomManager.exit(userId);
            }
        }
        return userIds;
    }

    public void setUserTimeout(Integer userId) {
        Integer deskId = getId();
        Long matchCounter = jedisTemplateGame.getAsLong(CacheKey.getCacheKey(
                RedisKeyConst.MATCH_COUNTER, RedisKeyConst.D, deskId));
        matchCounter = matchCounter == null ? 0 : matchCounter;
        String key = CacheKey.getCacheKey(RedisKeyConst.USERS_TIMEOUT, RedisKeyConst.D, deskId);
        jedisTemplateGame.zadd(key, matchCounter, String.valueOf(userId));
    }

    public Set<String> getTimeoutUsers() {
        Integer deskId = getId();
        Integer playerCount = getPlayerCount();
        Integer maxPlayerCount = room.getMaxLimitPlayerNumber();

        long matchCounter = jedisTemplateGame.incr(CacheKey.getCacheKey(
                RedisKeyConst.MATCH_COUNTER, RedisKeyConst.D, deskId));

        if (playerCount <= maxPlayerCount * 10 / 100) {
            return null; // 人数在10%以下不踢
        } else if (playerCount <= maxPlayerCount * 50 / 100) {
            matchCounter -= 20+1; // 人数在50%以下20局不投踢人,实际第一局不算
        } else if (playerCount <= maxPlayerCount * 80 / 100) {
            matchCounter -= 6+1;  // 人数在80%以下6局不投踢人
        } else {
            matchCounter -= 2+1; // 人数在80%及以上2局不投踢人
        }

        String key = CacheKey.getCacheKey(RedisKeyConst.USERS_TIMEOUT, RedisKeyConst.D, deskId);
        Set<String> rs = jedisTemplateGame.zrangeByScore(key, 0, matchCounter);
        if (rs.size() > 0) {
            Integer dealerId = getDealerId();
            if (dealerId != null) { // 庄家不踢
                rs.remove(String.valueOf(dealerId));
                if(rs.size()==0){
                    return null;
                }
            }
            Set<String> timeoutUsers = new HashSet<>();
            List<String> removes = new ArrayList<>();
            for (String k : rs) {
                Integer userId = Integer.valueOf(k);
                Player player = playerManager.get(userId);
                Integer userDeskId = player.getDeskId();
                if ((userDeskId == null || userDeskId.equals(deskId))
                        && !existsDealerList(Integer.valueOf(k))) {
                    timeoutUsers.add(k);
                    removes.add(k);
                }
            }
            if (removes.size() > 0) {
                jedisTemplateGame.zrem(key, removes.toArray(new String[removes.size()]));
            }
            return timeoutUsers;
        }
        return null;
    }

    /**
     * 获取用户是否在上庄列表
     *
     * @param userId
     * @return
     */
    public Boolean existsDealerList(Integer userId) {
        String dealerCoinKey = CacheKey.getCacheKey(RedisKeyConst.DESK_DEALERS, RedisKeyConst.D, getId());
        Double score = this.jedisTemplateGame.zscore(dealerCoinKey, userId.toString());
        return score != null;
    }

    public boolean isInMatch(Integer userId) {
        boolean hasBet = betManager.getBetCount(getId(), userId) > 0 ? true : false;
        return hasBet || isDealer(userId);
    }

    public boolean isMatchRunning(){
        return getMatchTask().isRunning();
    }

    public boolean isDealer(Integer userId) {
        return userId.equals(getDealerId());
    }

    public void clearMatchData() {
        betManager.clearBetData(getId());
        initDeskUser();
    }

    public void addDisconnectUser(Integer userId) {
        jedisTemplateGame.sadd(
                CacheKey.getCacheKey(RedisKeyConst.DISCONNECT, RedisKeyConst.D, getId())
                , String.valueOf(userId)
        );
    }

    public void clearDisconnectUsers() {
        Integer deskId = getId();
        Set<String> userIdsStr = jedisTemplateGame.smembers(
                CacheKey.getCacheKey(RedisKeyConst.DISCONNECT, RedisKeyConst.D, deskId)
        );
        jedisTemplateGame.del(
                CacheKey.getCacheKey(RedisKeyConst.DISCONNECT, RedisKeyConst.D, deskId)
        );

        for (String userIdStr : userIdsStr) {
            log.warn("桌子ID:{0},用户ID:{1},断线后,参与的赛事结束后的退出操作.", userIdsStr, deskId);
            roomManager.exit(Integer.valueOf(userIdStr));
        }
    }

    public void setPreCards(String cards) {
        jedisTemplateGame.hset(
                CacheKey.getCacheKey(RedisKeyConst.DESK, RedisKeyConst.D, getId())
                , GameDesk.PROP_PRE_CARDS,
                cards
        );
    }

    public String getPreCards() {
        return jedisTemplateGame.hget(
                CacheKey.getCacheKey(RedisKeyConst.DESK, RedisKeyConst.D, getId())
                , GameDesk.PROP_PRE_CARDS
        );
    }

    public void allocate(Player player) {
        Integer userId = player.getId();
        seatManager.atSeat(this,player );
        this.atSeat(userId,false );
        setUserTimeout(userId);
    }

    private void atSeat(Integer userId, boolean isReAllocate) {
        Integer deskId = getId();
        Player player = playerManager.get(userId);
        if (player.inRoom(room.getId())) {
            //进房成功 & 不是重新分配(warning:已经在房间的玩家，player.inRoom()会失败)
            room.incrPlayerCount();
            if(player.getIsAi()){
                room.incrAiCount();
            }
        }
        if (player.inDesk(deskId)) {
            //进桌成功 & 不是重新分配(warning:已经在房间的玩家，player.inRoom()会失败)
            incrPlayerCount();
        }
        jedisTemplateGame.zadd(
                CacheKey.getCacheKey(RedisKeyConst.DESK_USER, RedisKeyConst.D, deskId),
                0,
                String.valueOf(userId)
        );
    }

    public IMatchTask getMatchTask() {
        IDeskSchedule deskSchedule = deskScheduleManager.getSchedule(getId());
        return deskSchedule == null ? null : deskSchedule.getMatchTask2();
    }

    public Match getMatch() {
        IMatchTask matchTask = getMatchTask();
        return matchTask == null ? null : matchTask.getMatch();
    }

    //---------------Getter and Setter--------------------------

    public void setRoom(Room room) {
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }

    private GameDesk getMo() {
        return mo;
    }

    public void setMo(GameDesk mo) {
        this.mo = mo;
    }

    public Integer getId(){
        return getMo().getId();
    }

    public Integer getDealerUserId(){
        String rs = this.jedisTemplateGame.hget(
                CacheKey.getCacheKey(RedisKeyConst.DESK, RedisKeyConst.D, getId()),
                GameDesk.PROP_DEALER_USER_ID
        );

        return StringTool.isNotBlank(rs) ? Integer.valueOf(rs) : null;
    }


    public Integer getGameRoomId() {
        return getRoom().getId();
    }

    public Long getDealerBalanceCoin() {
        String rs = this.jedisTemplateGame.hget(
                CacheKey.getCacheKey(RedisKeyConst.DESK, RedisKeyConst.D, getId()),
                GameDesk.PROP_DEALER_BALANCE_COIN
                );

        return StringTool.isNotBlank(rs) ? Long.valueOf(rs) : 0;
    }

    public Integer getGameId() {
        return getRoom().getGameId();
    }

    public Integer getGameModelId() {
        return getRoom().getGameModelId();
    }

    public Integer getSeatNumber() {
        return getMo().getSeatNumber();
    }


    /**
     * 获取当前房间玩家人数
     * @return
     */
    public Integer getPlayerCount() {
        String count = this.jedisTemplateGame.hget(
                CacheKey.getCacheKey(RedisKeyConst.DESK, RedisKeyConst.D, getId()),
                GameDesk.PROP_PLAYER_COUNT
        );
        return StringTool.isNotBlank(count) ? Integer.valueOf(count) : 0;
    }

    /**
     * 结算清理异常退出的庄家数据
     */
    public void cleanupDealer() {
        Integer dealerUserId = getDealerUserId();
        if (dealerUserId == null) {
            return;
        }
        Player player = playerManager.get(dealerUserId);
        if (player.isAbnormalOutGame()) {
            dealerManager.downDealer(player);
            GameDeskSeat gameDeskSeat = new GameDeskSeat();
            gameDeskSeat.setUserId(dealerUserId);
            gameDeskSeat.setGameDeskId(getId());
            outDesk(dealerUserId);
            player.setAbnormalOutGame(false);
        }
    }

    /**
     * 重新分配位置，将吃瓜群提上座位
     */
    public void reAllocate() {
        if (seatManager.getSeatVoid(getId()).size() <= 0){
            //座位满员
            return;

        }
        Map<String,String> seats = seatManager.getSeatUser(getId());
        for (Map.Entry<String, String> entry : seats.entrySet()) {
            //去除无用户的座位
            if (StringTool.isBlank(entry.getValue())){
                seats.remove(entry.getKey());
            }
        }
        Set<String> lastMaxBetUserIds = jedisTemplateGame.zrevrange(
                CacheKey.getCacheKey(RedisKeyConst.DESK_USER, RedisKeyConst.D, getId()),
                0,
                getSeatNumber()
        );

        if (MapTool.isEmpty(seats)){
            //座位无人，给吃瓜群众分配座位
            for (String lastMaxBetUserId : lastMaxBetUserIds) {
                doReAllocate(lastMaxBetUserId);
            }
            return;
        }

        Collection<String> seatUserIds = seats.values();
        Collection<String> intersection = CollectionTool.intersection(seatUserIds,lastMaxBetUserIds);//在座玩家s 交集 本桌投注最多玩家s
        Collection<String> subtract = CollectionTool.subtract(lastMaxBetUserIds,intersection);//本桌投注最多玩家s 差集 (上一步交集) = 可以上桌集
        int needAllocateSize = Math.min(getSeatNumber() - seatUserIds.size(), subtract.size());      //需要分配数 = 在座人数 + 可以上桌集 - 桌子座位数

        for (int i = 0; i < needAllocateSize; i++) {
            String lastMaxBetUserId = subtract.iterator().next();
            doReAllocate(lastMaxBetUserId);
        }
    }


    private void doReAllocate(String lastMaxBetUserId) {
        Player player = playerManager.get(Integer.valueOf(lastMaxBetUserId));
        seatManager.atSeat(this,player );
        this.atSeat(player.getId(),true );
    }

    public boolean clearLastSecondBets() {
        return lastSecondBetd.compareAndSet(true, false);
    }

    public void lastSecondBet() {
        lastSecondBetd.set(true);
    }
}
