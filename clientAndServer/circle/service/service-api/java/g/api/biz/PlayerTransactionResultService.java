package g.api.biz;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import g.model.player.po.PlayerTransaction;
import g.service.common.IPlayerTransactionService;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springside.modules.nosql.redis.JedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * 订阅者
 * 玩家交易服务端返回结果
 * Created by black on 2017/3/29.
 */
public class PlayerTransactionResultService extends JedisPubSub {

    private static Log log = LogFactory.getLog(PlayerTransactionResultService.class);

    private final static String CHANNEL = "playerTransactionResult";

    @Autowired
    @Qualifier("jedisTemplateApi")
    private JedisTemplate jedisTemplateApi;

    @Autowired
    private IPlayerTransactionService playerTransactionService;

    public void start() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                subscribe();
            }
        }).start();
    }

    /**
     * 监听到订阅频道接受到消息时的回调
     * @param channel 频道
     * @param message 消息
     */
    @Override
    public void onMessage(String channel, String message) {

        try {
            //取出我们想要的内容
            PlayerTransaction playerTransaction = JSON.parse(message, PlayerTransaction.class);
            //玩家取款生成订单
            if (playerTransaction.getTransactionMoney() < 0) {
                playerTransactionService.updatePlayerTransactionStatus(playerTransaction);
            }
            log.info(String.format("Message. Channel: %s, Msg: %s", channel, message));
        } catch (ParseException e) {
            log.error(e);
        }
    }

    /**
     * 订阅频道时的回调
     * @param channel
     * @param subscribedChannels
     */
    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        log.info("消息订阅:玩家预转账结果信息订阅成功.");
    }

    /**
     * 订阅频道消息
     */
    private void subscribe() {

        jedisTemplateApi.execute(new JedisTemplate.JedisAction<Object>() {
            @Override
            public Object action(Jedis jedis) {
                jedis.subscribe(PlayerTransactionResultService.this, CHANNEL);
                return null;
            }
        });
    }
}
