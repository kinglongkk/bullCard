package g.web.common.cache;

import org.soul.commons.bean.Pair;
import org.soul.commons.init.context.CommonContext;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.param.IParamEnum;
import org.soul.model.sys.po.SysParam;
import g.model.SiteParamEnum;

import java.util.Collection;

/**
 * Created by longer on 12/24/15.
 */
public class ParamTool extends ParamToolBase {
    private static final Log log = LogFactory.getLog(ParamTool.class);


    private  static  final Integer BOSS_SITE_ID=0;
    /**
     * 是否开启:访问管理中心IP限制开关
     * @return
     */
    public static boolean isOpenVisitManagementCenterStatus(){

        Pair<String,String> pair=null;

        Integer siteId=CommonContext.get().getSiteId();
        if(siteId>0){
            pair=get(SiteParamEnum.SETTING_VISIT_MANAGEMENT_CENTER_STATUS);
        }
        return toBoolean(pair, false);
    }
    public static String getVisitManagementCenterStatusCacheKey(){

        Pair<String,String> pair;

        Integer siteId=CommonContext.get().getSiteId();
        if(siteId>0){
            return cacheKey(SiteParamEnum.SETTING_VISIT_MANAGEMENT_CENTER_STATUS);
        }
        return null;
    }
    public static IParamEnum getVisitManagementCenterStatusParamEnum(){

        Pair<String,String> pair;

        Integer siteId=CommonContext.get().getSiteId();
        if(siteId>0){
            return SiteParamEnum.SETTING_VISIT_MANAGEMENT_CENTER_STATUS;
        }
        return null;
    }
    /**
     * 是否开启:访问站点IP限制开关
     * @return
     */
    public static boolean isOpenVisitSiteStatus(){
        Pair<String,String> pair = get(SiteParamEnum.SETTING_VISIT_SITE_STATUS);
        return toBoolean(pair, false);
    }

    /**
     * 获取验证码样式
     * @return*/
    public static String getCaptchaStyle(){
        Pair<String,String> pair = get(SiteParamEnum.SETTING_CAPTCHA_STYLE);
        return blankThenDefault(pair);
    }

    /**
     * 获取验证码排除字符
     * @return
     */
    public static String getCaptchaExclusion(){
        Pair<String,String> pair = get(SiteParamEnum.SETTING_CAPTCHA_EXCLUSIONS);
        return blankThenDefault(pair);
    }

    /**
     * 获取指定站点参数
     * @param paramEnum
     * @param siteId
     * @return
     */
    public static SysParam getSysParam(SiteParamEnum paramEnum,Integer siteId){

        SysParam sysParam = raw(siteId, paramEnum);
        if(sysParam == null){
            log.debug("获取Site SysParam缓存为空{0},siteId{1}",paramEnum,siteId);
        }
        return sysParam;
    }
    /**
     * 获取当前站点参数
     * @param paramEnum
     * @return
     */
    public static SysParam getSysParam(SiteParamEnum paramEnum){

        SysParam sysParam = raw(CommonContext.get().getSiteId(), paramEnum);
        if(sysParam == null){
            log.debug("获取Site SysParam缓存为空{0},siteId{1}",paramEnum,CommonContext.get().getSiteId());
        }
        return sysParam;
    }
    /**
     * 获取当前站点参数
     * @param paramEnum
     * @return
     */
    public static Collection<SysParam> getSysParams(SiteParamEnum paramEnum){
        Collection<SysParam> sysParams = rawByType(CommonContext.get().getSiteId(), paramEnum);
        if (sysParams == null || sysParams.isEmpty()){
            log.debug("Site 获取SysParam缓存为空{0},siteId{1}",paramEnum,CommonContext.get().getSiteId());
        }
        return sysParams;
    }
    /**
     * 获取指定站点参数
     * @param paramEnum
     * @param siteId
     * @return
     */
    public static Collection<SysParam> getSysParams(SiteParamEnum paramEnum,Integer siteId){
        Collection<SysParam> sysParams = rawByType(siteId, paramEnum);
        if(sysParams == null || sysParams.isEmpty()){
            log.debug("Site 获取SysParam缓存为空{0},siteId{1}",paramEnum,siteId);
        }
        return sysParams;
    }
    /**
     * 刷新当前站点参数
     * @param paramEnum
     */
    public static void refresh(SiteParamEnum paramEnum){
        Integer siteId = CommonContext.get().getSiteId();
        refresh(siteId, paramEnum);
    }
    /**
     * 刷新指定站点参数
     * @param paramEnum
     */
    public static void refresh(SiteParamEnum paramEnum,Integer siteId){
        refresh(siteId, paramEnum);
    }
}
