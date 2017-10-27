package g.web.common.filter;

import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.iservice.common.IIpOrAreaResolver;
import org.soul.model.ip.IpBean;

/**
 * Created by longer on 12/6/15.
 */
public class IpOrAreaResolver implements IIpOrAreaResolver {

    private String subsysCode;

    private Log log = LogFactory.getLog(IpOrAreaResolver.class);

    @Override
    public boolean isAllowIp(String ipStr,Integer siteId,String domain) {
//        long ip = IpTool.ipv4StringToLong(ipStr);
//        Map<String, SiteConfineIp> ipMap = CacheBase.getSiteConfineIp();
//        for (Map.Entry<String, SiteConfineIp> entry : ipMap.entrySet()) {
//            SiteConfineIp siteConfineIp = entry.getValue();
//            if (isSiteFront() && SiteConfineIpTypeEnum.SITE_ALLOW.getCode().equals(siteConfineIp.getType())
//                    || isSiteManagement() && SiteConfineIpTypeEnum.CENTER_ALLOW.getCode().equals(siteConfineIp.getType())) {
//                boolean isAllow = siteConfineIp.isAllowAccess(ip, SiteConfineIpTypeEnum.SITE_ALLOW);
//                if (isAllow) {
//                    log.warn("站点ID:{0},域名为:{1},允许IP访问:{2},管理中心!",siteId,domain,ipStr);
//                    return true;
//                }
//            }
//        }
//        return false;
        return true;
    }

    @Override
    public boolean isDenyIp(IpBean ipBean,Integer siteId,String domain) {
//        if (ipBean == IpDb.NULL) {
//            return false;
//        }
//        if (!isSiteFront()) {
//            return false;
//        }
//        long ip = ipBean.getIp();
//        Map<String, SiteConfineIp> ipMap = CacheBase.getSiteConfineIp();
//        for (Map.Entry<String, SiteConfineIp> entry : ipMap.entrySet()) {
//            SiteConfineIp siteConfineIp = entry.getValue();
//            if (SiteConfineIpTypeEnum.SITE_DENY.getCode().equals(siteConfineIp.getType())) {
//                boolean isAllow = siteConfineIp.isAllowAccess(ip, SiteConfineIpTypeEnum.SITE_DENY);
//                if (!isAllow) {
//                    return true;
//                }
//            }
//        }
//
//        Map<String,SiteConfineArea> areaMap = CacheBase.getSiteConfineArea();
//        for (Map.Entry<String, SiteConfineArea> confineAreaEntry : areaMap.entrySet()) {
//            SiteConfineArea siteConfineArea = confineAreaEntry.getValue();
//            if (siteConfineArea.isInRange(ipBean)) {
//                return true;
//            }
//        }

        return false;
    }

    @Override
    public IpBean getIpFormDb(String ipStr) {
//        boolean allowAccessSwitch;
//        if (isSiteFront()){
//            //站点||玩家中心 使用站点开关
//            allowAccessSwitch = ParamTool.isOpenVisitSiteStatus();
//        } else {
//            //其它 使用管理开关
//            allowAccessSwitch = ParamTool.isOpenVisitManagementCenterStatus();
//        }
//        IIpDbService ipDbService = DubboTool.getService(IIpDbService.class);
//        IpDbVo vo = new IpDbVo();
//        vo.getSearch().setIpStr(ipStr);
//        IpDb ipDb = null;
//        try {
//            ipDb = ipDbService.getIp(vo);
//        } catch (Exception e){
//            log.debug("IP库:服务调用异常!");
//        }
//        if (ipDb == null || !ipDb.isInDb() ) {
//            log.warn("IP库:查找不到IP:[{0}],请手工添加!",ipStr);
//            ipDb = IpDb.NULL;
//        }else{
//            ipDb.setIp(IpTool.ipv4StringToLong(ipStr));
//        }
//        ipDb.setAllowAccessSwitch(allowAccessSwitch);
//        return ipDb;
        return null;
    }

    @Override
    public boolean isExceptionIp(String ipStr, Integer siteId) {
        return false;
    }
}
