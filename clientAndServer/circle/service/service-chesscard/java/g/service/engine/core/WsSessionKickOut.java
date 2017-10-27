package g.service.engine.core;

import g.model.common.RedisPubSubKey;
import g.service.chesscard.engine.schedule.NamedThreadFactory;
import g.service.chesscard.enums.TipEnum;
import g.service.chesscard.netBeans.common.NbKickOut;
import g.service.chesscard.netBeans.common.NbTip;
import g.service.webSocket.codeCreator.IWsHandle;
import g.service.webSocket.context.IWsSessionManager;
import g.service.webSocket.context.WsChannel;
import g.service.webSocket.context.WsSession;
import io.netty.channel.Channel;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.nosql.redis.JedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Double on 3/8/17.
 * 会话踢出
 */
public class WsSessionKickOut implements IWsSessionKickOut {

    private static Log log = LogFactory.getLog(WsSessionKickOut.class);

    @Autowired
    private IWsHandle wsHandle;

    @Autowired
    private JedisTemplate jedisTemplateGame;

    @Autowired
    private IWsSessionManager wsSessionManager;

    private ScheduledExecutorService checkConnectedExecutor =
            Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("kickOut"));

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
     * 订阅频道消息
     */
    private void subscribe() {

        //订阅消息
        jedisTemplateGame.execute(new JedisTemplate.JedisAction<Object>() {
            @Override
            public Object action(Jedis jedis) {
                log.info("jedis client:",jedis);
                jedis.subscribe( new KickOutSubscriber(), RedisPubSubKey.USER_KICK_OUT);
                return null;
            }
        });
    }

    class KickOutSubscriber extends JedisPubSub {

        @Override
        public void onMessage(String channel, String message) {
            if (StringTool.isNotBlank(message)) {
                Integer userId = Integer.valueOf(message);
                doKickOut(userId);
            }
        }

        /**
         * 做踢出操作
         * @param userId
         */
        private void doKickOut(Integer userId) {
            log.warn("玩家ID:{0},在异地登录,需要被跳出.",userId);
            WsSession wsSession = wsSessionManager.getUserSession(userId);
            if (wsSession != null) {
                NbKickOut kickOut = new NbKickOut();
                kickOut.tip = NbTip.tip(TipEnum.KICK_OUT);
                wsSession.sendNetBean(kickOut);

                WsChannel wsChannel = wsSession.getWsChannel();
                wsSessionManager.removeSession(wsChannel.getId());
                wsSession.setConnected(false);
                wsHandle.disconnected(wsSession);
                wsSession.close();
            }
        }

        @Override
        public void onSubscribe(String channel, int subscribedChannels) {
            log.info("订阅服务:玩家踢出事件");
        }
    }
}
