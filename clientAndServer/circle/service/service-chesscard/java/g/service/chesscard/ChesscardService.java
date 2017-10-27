package g.service.chesscard;

import org.soul.commons.cache.CacheKey;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.nosql.redis.JedisTemplate;

/**
 * Created by Jason on 16/8/26.
 */
public class ChesscardService implements IChesscardService {
    private static final Log log = LogFactory.getLog(ChesscardService.class);

    @Autowired
    private JedisTemplate jedisTemplateGame;

    @Override
    public boolean verifyToken(String token) {
        String key = CacheKey.getCacheKey(g.model.cache.CacheKey.TOKEN, token);
        String userId = this.jedisTemplateGame.get(key);

        if (StringTool.isNotBlank(userId)) {
            return true;
        }
        return false;
    }


}
