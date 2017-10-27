package g.service.chesscard.engine.manager;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import g.model.TransactionStatusEnum;
import g.model.TransactionTypeEnum;
import g.model.player.po.PlayerTransaction;
import g.model.player.po.UserPlayer;
import g.service.chesscard.RedisKeyConst;
import g.service.chesscard.engine.factory.IModelFactory;
import g.service.chesscard.engine.model.Desk;
import g.service.chesscard.engine.model.Player;
import g.service.chesscard.engine.schedule.NamedThreadFactory;
import g.service.chesscard.exception.ChessCardException;
import g.service.engine.manager.IDeskManager;
import g.service.engine.manager.IPlayerManager;
import org.soul.commons.cache.CacheKey;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.nosql.redis.JedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 订阅者
 * 玩家交易信息
 * Created by black on 2017/3/29.
 */
public class PlayerTransactionManager extends JedisPubSub {

    private static Log log = LogFactory.getLog(PlayerTransactionManager.class);

    private final static String CHANNEL = "playerTransaction";

    @Autowired
    private JedisTemplate jedisTemplateGame;

    @Autowired
    private PlayerTransactionResultManager resultManager;

    @Autowired
    private IPlayerManager playerManager;
    @Autowired
    private IModelFactory modelFactory;

    private ScheduledExecutorService checkConnectedExecutor =
            Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("playerTransaction"));

    public void start() {

        checkConnectedExecutor.submit(
            () -> {
                try {
                    subscribe();
                } catch (Exception e) {
                    log.error(e);
                }
            }
        );
    }

    /**
     * 监听到订阅频道接受到消息时的回调
     * @param channel 频道
     * @param message 消息
     */
    @Override
    public void onMessage(String channel, String message) {
        PlayerTransaction playerTransaction = null;
        try {
            //取出我们想要的内容
            playerTransaction = JSON.parse(message, PlayerTransaction.class);
            log.info(String.format("Message. Channel: %s, Msg: %s", channel, message));
            Player player = playerManager.get(playerTransaction.getPlayerId());
            if(playerTransaction.getTransactionType().equals(TransactionTypeEnum.WITHDRAWALS.getCode())) {//取款
                if(player == null){
                    playerTransaction.setStatus(TransactionStatusEnum.PENDING.getCode());
                    return;
                }
                if (player.getWalletBalance() + playerTransaction.getTransactionMoney() < 0) {
                    playerTransaction.setFailureReason("余额不足以提现.");
                    playerTransaction.setStatus(TransactionStatusEnum.FAILURE.getCode());
                    return;
                } else if (playerTransaction.getTransactionMoney() > 0) {
                    playerTransaction.setFailureReason("非法提现金额.");
                    playerTransaction.setStatus(TransactionStatusEnum.FAILURE.getCode());
                    return;
                } else {
                    Integer deskId = player.getDeskId();
                    if(deskId != null){
                        try {
                            IDeskManager deskManager = modelFactory.getDeskManager(deskId);
                            Desk desk = deskManager.byId(deskId);
                            if (desk.isInMatch(player.getId())) {
                                playerTransaction.setFailureReason("正在游戏中,不可提现.");
                                playerTransaction.setStatus(TransactionStatusEnum.FAILURE.getCode());
                                return;
                            }
                        }catch (ChessCardException e){
                        }
                    }
                }
                jedisTemplateGame.hincrBy(CacheKey.getCacheKey(RedisKeyConst.USERS, RedisKeyConst.U, playerTransaction.getPlayerId()),
                        UserPlayer.PROP_COIN, (long) (double)playerTransaction.getTransactionMoney());
                playerTransaction.setStatus(TransactionStatusEnum.PENDING.getCode());
            }else if(playerTransaction.getTransactionType().equals(TransactionTypeEnum.DEPOSIT.getCode())) {//存款
                if(player == null)return;
                jedisTemplateGame.hincrBy(CacheKey.getCacheKey(RedisKeyConst.USERS, RedisKeyConst.U, playerTransaction.getPlayerId()),
                        UserPlayer.PROP_COIN, (long) (double)playerTransaction.getTransactionMoney());
                playerTransaction = null;
                return;
            }else{//其余操作不做处理
                playerTransaction = null;
                return;
            }
        } catch (ParseException e) {
            log.error(e);
            if(playerTransaction != null){
                playerTransaction.setStatus(TransactionStatusEnum.FAILURE.getCode());
            }
        }finally {
            if(playerTransaction != null){
                resultManager.setResultToApi(playerTransaction);
            }
        }
    }

    /**
     * 订阅频道时的回调
     * @param channel
     * @param subscribedChannels
     */
    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        log.info("消息订阅:玩家预转账信息订阅成功.");
    }

    /**
     * 订阅频道消息
     */
    private void subscribe() {

        jedisTemplateGame.execute(new JedisTemplate.JedisAction<Object>() {
            @Override
            public Object action(Jedis jedis) {
                jedis.subscribe(PlayerTransactionManager.this, CHANNEL);
                return null;
            }
        });
    }
}
