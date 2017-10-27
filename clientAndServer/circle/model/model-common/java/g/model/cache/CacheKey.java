package g.model.cache;

/**
 * Created by tony on 15-5-27.
 */
public class CacheKey extends org.soul.commons.cache.CacheKey{

    public static final String TOKEN = "TOKEN";

    /**
     * 银行相关国际化
     */
    public static final String CACHE_KEY_BANK = "BANK";

    //数据采集过滤后的数据
    public static final String CK_GATHER_RESULT = "CK_RS";

    //采集:请求KEY集合
    public static final String CK_REQUEST_KEYS = "CK_REQ_KEYS";

    // 玩家分组限额
    public static final String CK_PLAYER_GROUP_LIMIT = "CK_PLAYER_GROUP_LIMIT";
    public static final String CK_PLAYER_GROUP_MUL_LIMIT = "CK_PLAYER_GROUP_MUL_LIMIT";

}
