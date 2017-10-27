package g.model.cache;

import g.model.admin.po.VUserGroupBetLimit;
import g.model.admin.po.VUserGroupBetLimitMultiple;
import g.model.admin.vo.VUserGroupBetLimitMultipleVo;
import g.model.admin.vo.VUserGroupBetLimitVo;
import g.model.payaccount.po.Bank;
import g.model.site.SiteCurrency;
import g.model.site.SiteLanguage;
import org.soul.commons.cache.CacheTool;
import org.soul.commons.collections.MapTool;
import org.soul.commons.spring.utils.SpringTool;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by tony on 15-11-16.
 */
public class CacheBase {

    private static Map<String, SiteCurrency> Site_Currency;
    private static Map<String, SiteLanguage> Site_Language;
    private static CacheTool cacheTool;
    private static CacheTool getProxy() {
        if (cacheTool == null) {
            cacheTool = (CacheTool) SpringTool.getBean("cacheTool");
        }
        return cacheTool;
    }
    static {
        initSiteCurrency();
        initSiteLanguage();
    }

    /**
     * 初始化系统支持的货币
     */
    private static void initSiteCurrency() {
        Site_Currency = new HashMap<>(2);
        SiteCurrency bean = new SiteCurrency();
        bean.setId(0);
        bean.setSiteId(0);
        bean.setCode("CNY");
        Site_Currency.put(bean.getCode(),bean);
    }

    /**
     * 初始化系统支持的语言
     */
    private static void initSiteLanguage() {
        Site_Language = new HashMap<>(2);
        SiteLanguage bean = new SiteLanguage();
        bean.setId(0);
        bean.setSiteId(0);
        bean.setLanguage("zh_CN");
        Site_Language.put(bean.getLanguage(),bean);
    }
    /**
     * 运营商库银行缓存
     *
     * @return
     */
    public static Map<String, Bank> getBank() {
        return getProxy().get(CacheKey.CACHE_KEY_BANK);
    }
    /**
     * 获取系统支持的货币
     * @return
     */
    public static Map<String, SiteCurrency> getSiteCurrency() {
        return Site_Currency;
    }

    public static Map<String, SiteLanguage> getSiteLanguage() {
        return Site_Language;
    }

    public static Map<Integer,List<VUserGroupBetLimit>> getAgentUserGroupBetLimit(String agentId) {
        Map<String, VUserGroupBetLimitVo> userGroupBetLimit = getUserGroupBetLimit();
        if (MapTool.isNotEmpty(userGroupBetLimit)) {
            VUserGroupBetLimitVo vUserGroupBetLimitVo = userGroupBetLimit.get(agentId);
            if (vUserGroupBetLimitVo !=null)
                return vUserGroupBetLimitVo.getUserGroupBetLimitMap();
            else
                return MapTool.newLinkedHashMap();
        } else {
            return MapTool.newLinkedHashMap();
        }
    }

    public static Map<Integer, List<VUserGroupBetLimitMultiple>> getAgentUserGroupBetLimitMultiple(String agentId) {
        Map<String, VUserGroupBetLimitMultipleVo> userGroupBetLimitMultiple = getUserGroupBetLimitMultiple();
        if (MapTool.isNotEmpty(userGroupBetLimitMultiple)) {
            VUserGroupBetLimitMultipleVo vUserGroupBetLimitMultipleVo = userGroupBetLimitMultiple.get(agentId);
            if (vUserGroupBetLimitMultipleVo !=null)
                return vUserGroupBetLimitMultipleVo.getGroupIdListMap();
            else
                return MapTool.newLinkedHashMap();
        } else {
            return MapTool.newLinkedHashMap();
        }
    }

    /**
     * 获得所有代理的对应玩家分组的单注单项投注限额集合
     * @return
     */
    public static Map<String, VUserGroupBetLimitVo> getUserGroupBetLimit() {
        return getProxy().get(CacheKey.getCacheKey(CacheKey.CK_PLAYER_GROUP_LIMIT));
    }

    /**
     * 刷新所有代理的对应玩家分组的单注单项投注限额集合
     * @return
     */
    public static void refreshUserGroupBetLimit() {
        getProxy().refresh(CacheKey.getCacheKey(CacheKey.CK_PLAYER_GROUP_LIMIT));
    }

    /**
     * 获得所有代理的对应玩家分组的综合过关单注最高限额集合
     * @return
     */
    public static Map<String, VUserGroupBetLimitMultipleVo> getUserGroupBetLimitMultiple() {
        return getProxy().get(CacheKey.getCacheKey(CacheKey.CK_PLAYER_GROUP_MUL_LIMIT));
    }

    /**
     * 刷新所有代理的对应玩家分组的综合过关单注最高限额集合
     * @return
     */
    public static void refreshUserGroupBetLimitMultiple() {
        getProxy().refresh(CacheKey.getCacheKey(CacheKey.CK_PLAYER_GROUP_MUL_LIMIT));
    }
}

