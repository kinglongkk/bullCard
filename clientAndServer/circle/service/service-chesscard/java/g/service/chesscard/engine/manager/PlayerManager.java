package g.service.chesscard.engine.manager;

import g.model.bet.po.IBetSettle;
import g.model.player.po.UserPlayer;
import g.service.chesscard.RedisKeyConst;
import g.service.chesscard.engine.factory.IModelFactory;
import g.service.chesscard.engine.model.Player;
import g.service.engine.manager.IDeskManager;
import g.service.engine.manager.IPlayerManager;
import org.apache.commons.beanutils.BeanUtils;
import org.soul.commons.bean.BeanTool;
import org.soul.commons.cache.CacheKey;
import org.soul.commons.collections.CollectionTool;
import org.soul.commons.collections.ListTool;
import org.soul.commons.collections.MapTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.nosql.redis.JedisTemplate;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Jason on 16/10/9.
 */
public class PlayerManager implements IPlayerManager {
    private static final Log log = LogFactory.getLog(PlayerManager.class);

    @Autowired
    private IDeskManager deskManager;

    @Autowired
    private JedisTemplate jedisTemplateGame;

    @Autowired
    private IModelFactory modelFactory;

    @Override
    public Player get(Integer id) {
        if(id == null || id == -1){//系统庄为-1
            return null;
        }
        UserPlayer userPlayer = getById(id);
        if (userPlayer == null) {
            log.error("玩家ID:{0},指定的玩家不存在.", id);
            return null;
        }
        Player player = new Player();
        BeanTool.copyProperties(userPlayer, player);
        player.setJedisTemplateGame(jedisTemplateGame);
        player.setModelFactory(modelFactory);

        Integer deskId = player.getDeskId();
        if (deskId != null) {
            player.setDesk(deskManager.byId(deskId));
        }
        return player;
    }

    @Override
    public void init(UserPlayer userPlayer) {
        Integer userId = userPlayer.getId();
        jedisTemplateGame.hmset(
                CacheKey.getCacheKey(RedisKeyConst.USERS, RedisKeyConst.U, userId),
                BeanTool.extractNoNull(userPlayer));
    }

    @Override
    public UserPlayer getById(Integer userId) {
        Map<String, String> map = jedisTemplateGame.hgetAll(
                CacheKey.getCacheKey(RedisKeyConst.USERS, RedisKeyConst.U, userId)
        );
        if (MapTool.isEmpty(map)) {
            return null;
        }
        UserPlayer userPlayer = new UserPlayer();
        try {
            BeanUtils.populate(userPlayer, map);
            userPlayer.setWalletBalance(Double.valueOf(userPlayer.getCoin()));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return userPlayer;
    }

    @Override
    public List<UserPlayer> getByIds(List<Integer> userIds) {
        if (CollectionTool.isEmpty(userIds)) {
            return ListTool.newArrayList();
        }
        //TODO:Double 考虑使用批量命令
        List<UserPlayer> userPlayerList = new ArrayList<>(userIds.size());
        for (Integer userId : userIds) {
            UserPlayer userPlayer = getById(userId);
            if (userPlayer != null) {
                userPlayerList.add(userPlayer);
            }
        }
        return userPlayerList;
    }


    @Override
    public CoinPiple batchUpdateCoin(final List<? extends IBetSettle> betSettles) {
        CoinPiple coinPiple = new CoinPiple();
        coinPiple.setBetSettles(betSettles);
        jedisTemplateGame.execute(coinPiple);
        return coinPiple;

    }


}
