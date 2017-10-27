package g.web.player.listener;

import g.model.common.RedisPubSubKey;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.web.shiro.common.delegate.IPassportListener;
import org.soul.web.shiro.common.delegate.PassportEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.nosql.redis.JedisTemplate;
import redis.clients.jedis.Jedis;

/**
 * Created by Double on 3/8/17.
 * 玩家中心: 登录成功事件处理()
 *
 */
public class PlayerPassportListener implements IPassportListener {

    private static Log log = LogFactory.getLog(PlayerPassportListener.class);

    @Autowired
    private JedisTemplate jedisTemplateGame;

    @Override
    public void onLoginSuccess(final PassportEvent passportEvent) {
        if (passportEvent.isKickOut()) {
            jedisTemplateGame.execute(new JedisTemplate.JedisAction<Object>() {
                @Override
                public Object action(Jedis jedis) {
                    Integer userId = passportEvent.getSysUser().getId();
                    jedis.publish(RedisPubSubKey.USER_KICK_OUT, String.valueOf(userId));
                    log.info("发布消息:类型:[{0}],值:{1}","踢出消息",userId );
                    return null;
                }
            });
        }
    }

    @Override
    public void onLoginFailure(PassportEvent passportEvent) {

    }

    @Override
    public void onLogoutSuccess(PassportEvent passportEvent) {

    }
}
