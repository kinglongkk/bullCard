package g.web.admin.tools;

import g.service.admin.IBetLimitMultipleService;
import g.service.admin.IBetLimitService;
import g.service.admin.agent.message.IUserAgentService;
import g.service.admin.gamebull100room.*;
import g.service.admin.gameroomconfig.IGameRoomConfigBull100Service;
import g.service.announcement.IGameAnnouncementService;
import g.service.announcement.VGameAnnounncementService;
import g.service.game.IGameService;
import g.service.player.IUserBankcardService;
import g.service.bet.*;
import g.service.common.*;
import g.service.match.IMatchService;
import g.service.match.IVMatchResultService;
import g.service.payaccount.BankService;
import g.service.payaccount.IBankService;
import g.service.payaccount.IVPayAccountService;
import g.service.payaccount.VPayAccountService;
import g.service.sys.ISysSiteService;
import org.soul.iservice.msg.notice.INoticeContactWayService;
import org.soul.iservice.msg.notice.INoticeService;
import org.soul.iservice.pay.IOnlinePayService;
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

    /**
     * 返回玩家远程服务实例
     *
     * @return 玩家远程服务实例
     */
    /**
     * 返回玩家银行卡远程服务实例
     *
     * @return
     */
    public static IUserBankcardService userBankcardService() {
        return getService(IUserBankcardService.class);
    }

    /**
     * 我的账号
     *
     * @return
     */
    public static IMyAccountService myAccountService() {
        return getService(IMyAccountService.class);
    }

    public static ISysUserService sysUserService() {
        return getService(ISysUserService.class);
    }

    public static IGameService gameService() { return  getService(IGameService.class); }

    public static ISysSiteService sysSiteService() { return getService(ISysSiteService.class); }

    public static INoticeService noticeService() {
        return getService(INoticeService.class);
    }

    /**
     * 返回偏好设置远程服务实例
     *
     * @return 偏好设置远程服务实例
     */
    public static IPreferenceService preferenceService() {
        return getService(IPreferenceService.class);
    }

    /**
     * 代理信息
     * @return
     */
    public static IUserAgentService userAgentService() {return getService(IUserAgentService.class); }

    /**
     * vSysUser视图
     *
     * @return
     */
    public static IVSysUserService vSysUserService() {
        return getService(IVSysUserService.class);
    }

    public static IAuditLogService auditLogService() {
        return getService(IAuditLogService.class);
    }

    /**
     * 返回联系方式表远程服务实例
     *
     * @return 联系方式表远程服务实例
     */
    public static INoticeContactWayService noticeContactWayService() {
        return getService(INoticeContactWayService.class);
    }

    /**
     * 返回系统公告远程服务实例
     *
     * @return 系统公告远程服务实例
     */
    public static ISysAnnouncementService sysAnnouncementService() {
        return getService(ISysAnnouncementService.class);
    }

    /**
     * 返回游戏公告远程服务实例
     *
     * @return 游戏公告远程服务实例
     */
    public static IGameAnnouncementService gameAnnouncementService() {
        return getService(IGameAnnouncementService.class);
    }

    /**
     * 返回游戏公告远程服务实例
     *
     * @return 游戏房间配置远程服务实例
     */
    public static IGameRoomConfigBull100Service gameRoomConfigBull100Service() {
        return getService(IGameRoomConfigBull100Service.class);
    }

    /**
     * 联系方式视图查询
     *
     * @return 联系方式视图查询
     */
    public static IVSysUserContactService vSysUserContactService() {
        return getService(IVSysUserContactService.class);
    }

    /**
     * 返回远程服务实例
     *
     * @return 远程服务实例
     */
    public static ISysAnnouncementI18nService sysAnnouncementI18nService() {
        return getService(ISysAnnouncementI18nService.class);
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
     * 返回游戏公告视图远程服务实例
     *
     * @return 游戏公告视图远程服务实例
     */
    public static IVGameAnnouncementService vGameAnnouncementService() {
        return getService(IVGameAnnouncementService.class);
    }

    public static IVRoomBull100Service vRoomBull100Service(){
        return getService(IVRoomBull100Service.class);
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
     * 返回投注限额远程服务实例
     *
     * @return 投注限额远程服务实例
     */
    public static IBetLimitService betLimitService() {
        return getService(IBetLimitService.class);
    }

    /**
     * 返回综合投注限额远程服务实例
     *
     * @return 综合投注限额远程服务实例
     */
    public static IBetLimitMultipleService betLimitMultipleService() {
        return getService(IBetLimitMultipleService.class);
    }

    /**
     * 返回用户与投注限额关系表远程服务实例
     *
     * @return 用户与投注限额关系表远程服务实例
     */
    public static IBetLimitUserService betLimitUserService() {
        return getService(IBetLimitUserService.class);
    }

    /**
     * 返回用户组与综合投注限额关系表远程服务实例
     *
     * @return 用户组与综合投注限额关系表远程服务实例
     */
    public static IBetLimitUserGroupService betLimitUserGroupService() {
        return getService(IBetLimitUserGroupService.class);
    }

    /**
     * 返回用户组与综合投注限额关系表远程服务实例
     *
     * @return 用户组与综合投注限额关系表远程服务实例
     */
    public static IBetLimitUserGroupMultipleService betLimitUserGroupMultipleService() {
        return getService(IBetLimitUserGroupMultipleService.class);
    }

    /**
     * 返回用户与综合投注限额关系表远程服务实例
     *
     * @return 用户与综合投注限额关系表远程服务实例
     */
    public static IBetLimitUserMultipleService betLimitUserMultipleService() {
        return getService(IBetLimitUserMultipleService.class);
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

    public static IMatchService matchService() {
        return getService(IMatchService.class);
    }

    public static ISettleService settleService(){
        return getService(ISettleService.class);
    }


    public static IVMatchResultService vMatchResultService(){
        return getService(IVMatchResultService.class);
    }

    public static IBankService bankService() {
        return getService(BankService.class);
    }

    public static IVPayAccountService vPayAccountService() {
        return getService(VPayAccountService.class);
    }

    /**
     * 接口参数服务接口
     *
     * @return
     */
    public static IOnlinePayService onlinePayService() {
        return getService(IOnlinePayService.class);
    }
    //endregion your codes 1

}
