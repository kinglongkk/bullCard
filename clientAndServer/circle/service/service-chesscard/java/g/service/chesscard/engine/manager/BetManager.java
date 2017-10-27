package g.service.chesscard.engine.manager;

import g.common.tool.DoubleTool;
import g.model.bet.BetTypeEnum;
import g.model.bet.IBetItem;
import g.model.chesscard.mo.UserBet;
import g.service.chesscard.engine.model.Desk;
import g.service.chesscard.DouniuMultiple;
import g.service.chesscard.RedisKeyConst;
import g.service.chesscard.engine.EngineConst;
import g.service.chesscard.engine.model.Player;
import g.service.chesscard.engine.model.Room;
import g.service.chesscard.enums.TipEnum;
import g.service.chesscard.exception.ChessCardException;
import g.service.engine.manager.*;
import org.soul.commons.cache.CacheKey;
import org.soul.commons.lang.ArrayTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.nosql.redis.JedisTemplate;

import java.util.*;

/**
 * Created by Jason on 16/10/9.
 */
public class BetManager implements IBetManager {
    private static final Log log = LogFactory.getLog(BetManager.class);

    @Autowired
    private JedisTemplate jedisTemplateGame;

    @Autowired
    private IPlayerManager playerManager;

    @Autowired
    private IDeskManager deskManager;

    @Autowired
    private IBetCoinLimitManager betCoinLimitManager;

    @Override
    public boolean betting(Player player, BetTypeEnum betType, IBetItem betItem, Long coin) {
        Integer userId = player.getId();
        Integer deskId = player.getDeskId();
        if (deskId == null) {
            log.info("玩家userId{0}投注找不到桌子deskId", player);
            throw new ChessCardException(TipEnum.MATCH_NO_EXIST);
        }
        Desk desk = deskManager.byId(deskId);
        desk.setUserTimeout(userId); // 玩家有投注行为都不踢

        isCanBetting(userId, betType, coin, deskId);
        incBetItemRecord(userId, betType, betItem, coin, deskId, player.getIsAi());
        incBetItemRecordTemp(userId, betType, betItem, coin, deskId);
        addBetCount(deskId, userId);
        addUserBetCoin(userId, coin, deskId);
        player.incrCoin(-coin);
        desk.incUserBetCoin(userId, coin);
        log.debug("玩家:{0},在桌子号:{1},完成投注,类型:{2},投注项:{3} ,金币:{4}"
                ,userId,deskId,betType,betItem.toString(),coin);
        if(desk.getMatch().getEndTime().getTime()-System.currentTimeMillis()<1000){//最后一秒
            desk.lastSecondBet();
            return true;
        }
        return false;
    }

    protected void incBetItemRecordTemp(Integer userId, BetTypeEnum betType, IBetItem betItem, Long coin, Integer deskId) {
        //临时存储
        this.jedisTemplateGame.hincrBy(
                CacheKey.getCacheKey(RedisKeyConst.BET_TEMP, RedisKeyConst.D, deskId),
                CacheKey.getCacheKey(userId, betType.getCode(), betItem.toString()),
                coin);
    }

    protected void incBetItemRecord(Integer userId, BetTypeEnum betType, IBetItem betItem, Long coin, Integer deskId, boolean isAi) {
        //更新赛事下投注情况
        this.jedisTemplateGame.hincrBy(
                CacheKey.getCacheKey(RedisKeyConst.BET, RedisKeyConst.D, deskId)
                , CacheKey.getCacheKey(userId, betType.getCode(), betItem.toString(), isAi?1:0)
                , coin);
    }

    private void isCanBetting(Integer userId, BetTypeEnum betType, Long coin, Integer deskId) {
        //获取用户金币
        Player player = playerManager.get(userId);
        Long userCoin = player.getCoin();
        if (userCoin < coin) {
            throw new ChessCardException(TipEnum.BALANCE_LESS);
        }

        long userBetCoin = getUserBetCoin(deskId, userId);
        Long usableCoin = (userCoin - userBetCoin * (DouniuMultiple.getDouniuMaxMultiple(betType.getCode()) - 1))
                / DouniuMultiple.getDouniuMaxMultiple(betType.getCode());
        if (usableCoin < coin) {
            throw new ChessCardException(TipEnum.BET_MAX_LIMIT);
        }

        Desk desk = player.getDesk();
        if (userId.equals(desk.getDealerUserId())) {
            throw new ChessCardException(TipEnum.IS_DEALER_NOT_BETTING);
        }

        //结算阶段不能投注
        if (!desk.getMatchTask().isRunning()) {
            throw new ChessCardException(TipEnum.BET_NOT_PERMIT_BET);
        }

        int bettingCount = getBetCount(deskId, userId);
        Room room = desk.getRoom();
        if (bettingCount >= room.getBetTimes()) {
            throw new ChessCardException(TipEnum.BET_MAX_COUNT);
        }

        if (ArrayTool.indexOf(room.getBetChip(), coin.intValue()) < 0) {
            throw new ChessCardException(TipEnum.BET_NOT_PERMIT_COIN); // 投注金额不在许可值内
        }

        betCoinLimitManager.canBetting(desk, coin, betType.getCode());

    }

    @Override
    public long getUserBetCoin(Integer deskId, Integer userId) {
        String coin = this.jedisTemplateGame.hget(
                CacheKey.getCacheKey(RedisKeyConst.BET_COIN, RedisKeyConst.D, deskId),
                String.valueOf(userId));
        return coin == null ? 0 : Long.valueOf(coin);
    }

    private void addUserBetCoin(Integer userId, Long coin, Integer deskId) {
        this.jedisTemplateGame.hincrBy(
                CacheKey.getCacheKey(RedisKeyConst.BET_COIN, RedisKeyConst.D, deskId),
                String.valueOf(userId),
                coin);
    }

    private void clearUserBetCoin(Integer deskId) {
        this.jedisTemplateGame.del(
                CacheKey.getCacheKey(RedisKeyConst.BET_COIN, RedisKeyConst.D, deskId)
        );
    }

    //增加投注次数
    private void addBetCount(Integer deskId, Integer userId) {
        jedisTemplateGame.zincrby(
                CacheKey.getCacheKey(RedisKeyConst.DESK_BET_COUNT, RedisKeyConst.D, deskId),
                1,
                String.valueOf(userId));
    }

    //清理投注次数
    private void clearBetCount(Integer deskId) {
        jedisTemplateGame.del(
                CacheKey.getCacheKey(RedisKeyConst.DESK_BET_COUNT, RedisKeyConst.D, deskId)
        );
    }

    //初始化投注次数


    @Override
    public void clearBetData(Integer deskId) {
        this.clearBetCount(deskId);
        this.clearUserBetCoin(deskId);
        this.clearBetting(deskId);
        this.clearBettingTemp(deskId);
        betCoinLimitManager.clear(deskId);
    }

    @Override
    public List<UserBet> getUserBetList(Integer deskId, Long matchId) {
        String bettingKey = CacheKey.getCacheKey(RedisKeyConst.BET, RedisKeyConst.D, deskId);
        Map<String, String> map = this.jedisTemplateGame.hgetAll(bettingKey);

        List<UserBet> userBetList = genUserBetList(map);
        return userBetList;
    }

    private List<UserBet> getUserBetListTemp(Integer deskId) {
        String bettingKey = CacheKey.getCacheKey(RedisKeyConst.BET_TEMP, RedisKeyConst.D, deskId);
        Map<String, String> map = this.jedisTemplateGame.hgetAll(bettingKey);
        this.clearBettingTemp(deskId);
        List<UserBet> userBetList = genUserBetList(map);
        return userBetList;
    }

    private List<UserBet> genUserBetList(Map<String, String> map) {
        List<UserBet> list = new ArrayList<>(map.size());
        UserBet userBet = null;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Double coin = Double.parseDouble(value);
            String[] array = StringTool.split(key, CacheKey.CACHE_KEY_SEPERATOR);
            Integer userId = Integer.parseInt(array[0]);
            String betType = array[1];
            String betItem = array[2];

            userBet = new UserBet();
            userBet.setUserId(userId);
            userBet.setBetType(betType);
            userBet.setBetItem(betItem);
            userBet.setCoin(coin);
            if(array.length>3){//temp的key没有这个isAi属性
                userBet.setAi("1".equals(array[3]));
            }
            list.add(userBet);
        }
        return list;
    }

    @Override
    public List<UserBet> getUserBetStatistics(Integer deskId) {
        List<UserBet> betList = new ArrayList<>();
        List<UserBet> userBetList = this.getUserBetListTemp(deskId);
        Desk desk = deskManager.byId(deskId);
        Set<String> userList = desk.getDeskUsers();
        List<UserBet> seatBetList = new ArrayList<>();
        for (String userId : userList) {
            Integer tempUserId = Integer.valueOf(userId);
            if (tempUserId.equals(desk.getDealerUserId())) {
                continue;
            }
            Player player = playerManager.get(tempUserId);
            Integer seatNo = player.getSeatId();
            if (seatNo != null && StringTool.isNotEmpty(String.valueOf(seatNo)) && seatNo != 0) {
                for (UserBet userBet : userBetList) {
                    if (tempUserId.equals(userBet.getUserId())) {
                        userBet.setSeatNo(seatNo);
                        betList.add(userBet);
                        seatBetList.add(userBet);
                    }
                }
            }
        }
        userBetList.removeAll(seatBetList);

        Map<String, List<UserBet>> map = new HashMap<>();
        for (UserBet userBet : userBetList) {
            String betItem = userBet.getBetItem();
            if (map.containsKey(betItem)) {
                map.get(betItem).add(userBet);
            } else {
                List<UserBet> list = new ArrayList<>();
                list.add(userBet);
                map.put(betItem, list);
            }
        }
        for (Map.Entry<String, List<UserBet>> entry : map.entrySet()) {
            String betItem = entry.getKey();
            List<UserBet> list = entry.getValue();
            Double coin = 0d;
            String betType = "";
            for (UserBet userBet : list) {
                betType = userBet.getBetType();
                coin = DoubleTool.add(coin, userBet.getCoin());
            }
            UserBet userBet = new UserBet();
            userBet.setSeatNo(EngineConst.SEAT_NO_PLAYER);
            userBet.setBetType(betType);
            userBet.setBetItem(betItem);
            userBet.setCoin(coin);
            betList.add(userBet);
        }
        return betList;
    }

    private void clearBetting(Integer deskId) {
        String bettingKey = CacheKey.getCacheKey(RedisKeyConst.BET, RedisKeyConst.D, deskId);
        this.jedisTemplateGame.del(bettingKey);
    }

    private void clearBettingTemp(Integer deskId) {
        String bettingKey = CacheKey.getCacheKey(RedisKeyConst.BET_TEMP, RedisKeyConst.D, deskId);
        this.jedisTemplateGame.del(bettingKey);
    }

    @Override
    public int getBetCount(Integer deskId, Integer userId) {
        Double score = this.jedisTemplateGame.zscore(
                CacheKey.getCacheKey(RedisKeyConst.DESK_BET_COUNT, RedisKeyConst.D, deskId),
                String.valueOf(userId)
        );
        return score == null ? 0 : score.intValue();
    }
}
