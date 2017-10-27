package g.service.chesscard.engine.manager;

import g.model.player.po.PlayerTransaction;
import org.soul.commons.data.json.JsonTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.spring.utils.SpringTool;
import org.springside.modules.nosql.redis.JedisTemplate;
import redis.clients.jedis.Jedis;

/**
 * 玩家交易服务端返回结果
 * Created by black on 2017/3/29.
 */
public class PlayerTransactionResultManager {

    private Log log = LogFactory.getLog(PlayerTransactionResultManager.class);

    private final static String CHANNEL = "playerTransactionResult";

    public void setResultToApi(PlayerTransaction playerTransaction) {

        JedisTemplate jedisTemplateApi = (JedisTemplate)SpringTool.getBean("jedisTemplateApi");
        jedisTemplateApi.execute(new JedisTemplate.JedisAction<Object>() {
            @Override
            public Object action(Jedis jedis) {
                jedis.publish(CHANNEL, JsonTool.toJson(playerTransaction));
                log.info("玩家预转账返回结果" + JsonTool.toJson(playerTransaction));
                return null;
            }
        });
    }
}
