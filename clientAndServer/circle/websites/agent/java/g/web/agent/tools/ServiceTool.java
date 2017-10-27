package g.web.agent.tools;

import g.service.bet.IVUserBetLimitMultipleService;
import g.service.bet.IVUserBetLimitService;
import g.service.common.*;
import org.soul.iservice.msg.notice.INoticeService;
import org.soul.iservice.security.privilege.ISysUserService;

import static org.soul.commons.dubbo.DubboTool.getService;

/**
 * 远程服务实例获取工具类
 *
 * @author orange
 * @time 2016-3-15 11:51:46
 */
public class ServiceTool {

    //region your codes 1
    public static IMyAccountService myAccountService() {
        return getService(IMyAccountService.class);
    }

    public static IVUserPlayerGroupService vUserPlayerGroupService() {
        return getService(IVUserPlayerGroupService.class);
    }
    public static IUserPlayerGroupService userPlayerGroupService() {
        return getService(IUserPlayerGroupService.class);
    }
    public static IAuditLogService auditLogService() {
        return getService(IAuditLogService.class);
    }
    /**
     * 返回系统公告视图远程服务实例
     *
     * @return 系统公告视图远程服务实例
     */
    public static IVSysAnnouncementService vSysAnnouncementService() {
        return getService(IVSysAnnouncementService.class);
    }

    /**
     * vSysUser视图
     *
     * @return
     */
    public static IVSysUserService vSysUserService() {
        return getService(IVSysUserService.class);
    }

    /**
     * 返回代理单注限额视图远程服务实例
     *
     * @return 代理单注限额视图远程服务实例
     */
    public static IVUserBetLimitService vUserBetLimitService() {
        return getService(IVUserBetLimitService.class);
    }

    /**
     * 返回代理综合过关单注最高限额远程服务实例
     *
     * @return 代理综合过关单注最高限额远程服务实例
     */
    public static IVUserBetLimitMultipleService vUserBetLimitMultipleService() {
        return getService(IVUserBetLimitMultipleService.class);
    }

    /**
     * 返回玩家分组单注投注限额远程服务实例
     *
     * @return 玩家分组单注投注限额远程服务实例
     */
    public static IVUserGroupBetLimitService vUserGroupBetLimitService() {
        return getService(IVUserGroupBetLimitService.class);
    }

    /**
     * 返回玩家分组综合过关单项限额远程服务实例
     *
     * @return 玩家分组综合过关单项限额远程服务实例
     */
    public static IVUserGroupBetLimitMultipleService vUserGroupBetLimitMultipleService() {
        return getService(IVUserGroupBetLimitMultipleService.class);
    }

    public static INoticeService noticeService() {
        return getService(INoticeService.class);
    }

    /**
     * 返回系统公告远程服务实例
     *
     * @return 系统公告远程服务实例
     */
    public static ISystemAnnouncementService systemAnnouncementService() {
        return getService(ISystemAnnouncementService.class);
    }


    /**
     * 公告是否已读
     *
     * @return
     */
    public static ISysAnnouncementReadService sysAnnouncementReadService() {
        return getService(ISysAnnouncementReadService.class);
    }

    /**
     * 返回系统公告远程服务实例
     *
     * @return 系统公告远程服务实例
     */
    public static ISysAnnouncementService sysAnnouncementService() {
        return getService(ISysAnnouncementService.class);
    }

    public static ISysUserService sysUserService() {
        return getService(ISysUserService.class);
    }
    //endregion your codes 1

}
