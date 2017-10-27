package g.model.admin.so;


import g.model.admin.po.VUserGroupBetLimit;
import org.soul.commons.cache.CacheKey;

/**
 * 玩家分组单注投注限额查询对象
 *
 * @author tom
 * @time 2016-4-21 17:20:59
 */
//region your codes 1
public class VUserGroupBetLimitSo extends VUserGroupBetLimit {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -8366440586209852436L;
	//endregion your codes 3

	//region your codes 2
	public static final String PROP_CACHE_KEY = "cacheKey";

	public String getCacheKey() {
		return CacheKey.getCacheKey(new String[]{ String.valueOf(this.getSysUserGroupId()), this.getBetType()});
	}
	//endregion your codes 2
}