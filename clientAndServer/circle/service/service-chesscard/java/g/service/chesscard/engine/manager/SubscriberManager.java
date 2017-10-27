package g.service.chesscard.engine.manager;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import g.model.common.RedisPubSubKey;
import g.service.chesscard.engine.schedule.NamedThreadFactory;
import g.service.chesscard.netBeans.common.NbGameAnnouncement;
import g.service.webSocket.context.IWsSessionManager;
import g.service.webSocket.context.WsSession;
import io.netty.channel.ChannelId;
import org.soul.commons.cache.CacheKey;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.nosql.redis.JedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 订阅者
 * 游戏公告
 * Created by black on 2017/2/22.
 */
public class SubscriberManager extends JedisPubSub {

    private static Log log = LogFactory.getLog(SubscriberManager.class);

    @Autowired
    private JedisTemplate jedisTemplateGame;

    @Autowired
    private IWsSessionManager wsSessionManager;

    private ScheduledExecutorService checkConnectedExecutor =
            Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("announcement"));

    public void start(Set<Integer> gameIdSet) {

        checkConnectedExecutor.submit(
            () -> {
                try {
                    subscribe(gameIdSet);
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

        try {
            //取出我们想要的内容
            NbGameAnnouncement announcement = JSON.parse(message, NbGameAnnouncement.class);
            //获取所有会话
            Map<ChannelId, WsSession> wsSessionMap = wsSessionManager.getSessions();
            for (Map.Entry<ChannelId, WsSession> entry : wsSessionMap.entrySet()) {
                WsSession session = entry.getValue();
                session.sendNetBean(announcement);
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
        log.info("消息订阅:游戏公告订阅成功.");
    }

    /**
     * 订阅频道消息
     * @param gameIdSet 游戏id集合
     */
    private void subscribe(Set<Integer> gameIdSet) {

        String channel = null;
        List<Integer> list = new ArrayList<>(gameIdSet);
        for (int i = 0, length = list.size(); i < length; i ++) {
            if (i == 0) {
                channel = CacheKey.getCacheKey(RedisPubSubKey.CHANNEL, list.get(i));
            } else {
                channel = CacheKey.getCacheKey(channel + " " + RedisPubSubKey.CHANNEL, list.get(i));
            }
        }
        final String subChannel = channel;
        //订阅消息
        jedisTemplateGame.execute(new JedisTemplate.JedisAction<Object>() {
            @Override
            public Object action(Jedis jedis) {
                jedis.subscribe(SubscriberManager.this, subChannel);
                return null;
            }
        });
    }

}
