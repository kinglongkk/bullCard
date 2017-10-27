package g.web.player.reids;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springside.modules.nosql.redis.JedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by longer on 6/14/16.
 */
public class RequestKeySubcribe implements InitializingBean,DisposableBean {

    private Set<String> subscribedKeys = new HashSet<>();

    private PubSub pubSub = new PubSub();

    @Autowired
    @Qualifier("jedisTemplateGather")
    private JedisTemplate jedisTemplateGather;      //采集缓存

    private Jedis jedis;

    public void subcribe(String key){
        if (subscribedKeys.contains(key)) {
            return;
        }
        jedis.subscribe(pubSub);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        jedis = jedisTemplateGather.getJedisPool().getResource();
    }

    @Override
    public void destroy() throws Exception {
        if (jedisTemplateGather != null && jedis != null){
            jedisTemplateGather.getJedisPool().returnResource(jedis);
        }
    }

    class PubSub extends JedisPubSub {

    }
}
