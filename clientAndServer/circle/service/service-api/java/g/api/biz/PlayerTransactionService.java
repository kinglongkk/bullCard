package g.api.biz;

import g.api.base.BaseService;
import g.api.service.IGamePlayerTransactionService;
import g.model.player.po.PlayerTransaction;
import org.soul.commons.data.json.JsonTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.spring.utils.SpringTool;
import org.springside.modules.nosql.redis.JedisTemplate;
import redis.clients.jedis.Jedis;

/**
 * 玩家预转账操作
 * Created by black on 2017/3/28.
 */
public class PlayerTransactionService extends BaseService implements IGamePlayerTransactionService {

    private Log log = LogFactory.getLog(PlayerTransactionService.class);

    private final static String CHANNEL = "playerTransaction";

    public void sendTransactionMessage(final PlayerTransaction playerTransaction) {

        JedisTemplate template = (JedisTemplate) SpringTool.getBean("jedisTemplateGame");
        template.execute(new JedisTemplate.JedisAction<Object>() {
            @Override
            public Object action(Jedis jedis) {
                jedis.publish(CHANNEL, JsonTool.toJson(playerTransaction));
                log.info("玩家预转账" + JsonTool.toJson(playerTransaction));
                return null;
            }
        });
    }
}
