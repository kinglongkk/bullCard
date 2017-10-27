package g.web.agent.controller;


import g.model.DictEnum;
import g.model.UserTypeEnum;
import g.model.admin.vo.AccountVo;
import g.model.common.notice.enums.ManualNoticeEvent;
import g.model.player.po.VSysUser;
import g.model.player.vo.VSysUserVo;
import g.web.agent.controller.form.AccountOptionForm;
import g.web.agent.session.SessionManager;
import g.web.agent.tools.ServiceTool;
import org.soul.commons.bean.Pair;
import org.soul.commons.cache.CacheKey;
import org.soul.commons.dict.DictTool;
import org.soul.commons.enums.EnumTool;
import org.soul.commons.locale.LocaleDateTool;
import org.soul.commons.locale.LocaleTool;
import org.soul.iservice.security.privilege.ISysUserService;
import org.soul.model.log.audit.enums.OpMode;
import org.soul.model.msg.notice.enums.INoticeEventType;
import org.soul.model.msg.notice.vo.NoticeLocaleTmpl;
import org.soul.model.msg.notice.vo.NoticeVo;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.model.security.privilege.vo.SysUserListVo;
import org.soul.model.security.privilege.vo.SysUserVo;
import org.soul.model.sys.po.SysDict;
import org.soul.web.controller.BaseCrudController;
import org.soul.web.security.privilege.form.SysUserForm;
import org.soul.web.security.privilege.form.SysUserSearchForm;
import org.soul.web.session.RedisSessionDao;
import org.soul.web.session.SessionManagerBase;
import org.soul.web.validation.form.js.JsRuleCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller
//region your codes 1
/**
 * Created by jeff on 15-12-18.
 * 账号相关操作（停用，冻结，找回密码）
 */
@RequestMapping("/share/account")
public class AccountController extends BaseCrudController<ISysUserService, SysUserListVo, SysUserVo, SysUserSearchForm, SysUserForm, SysUser, Integer> {
//endregion your codes 1

    @Autowired
    RedisSessionDao redisSessionDao;

    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/player/account/";
        //endregion your codes 2
    }

    //region your codes 3

    /**
     * 账号停用
     *
     * @param accountVo
     * @return
     */
    @RequestMapping(value = "/disabledAccount", method = RequestMethod.GET)
    public String disabledAccount(Model model, AccountVo accountVo) {
        String type = accountVo.getType();
        /*停用模板 账号信息 在线状态*/
        /*模板*/
        accountVo = ServiceTool.vSysUserService().accountOption(accountVo);
        accountVo.setType(type);
        model.addAttribute("command", accountVo);
        return getViewBasePath() + "DisabledAccount";

    }

    /*
     * 基于账号类型获取账号停用消息通知事件类型
     * @param accountVo
     * @return
     */
    private INoticeEventType getDisabledNoticeEventType(String accountType) {
        INoticeEventType eventType = null;
        if (AccountVo.TYPE_AGENT.equals(accountType)) {
            eventType = ManualNoticeEvent.ACENTER_ACCOUNT_STOP;
        }else {
            eventType = ManualNoticeEvent.PLAYER_ACCOUNT_STOP;
        }
        return eventType;
    }

    /*
     * 基于账号类型获取账号冻结消息通知事件类型
     * @param accountVo
     * @return
     */
    private INoticeEventType getFreezeNoticeEventType(String accountType) {
        INoticeEventType eventType = null;
        if (AccountVo.TYPE_AGENT.equals(accountType)) {
            eventType = ManualNoticeEvent.ACENTER_ACCOUNT_FREEZON;
        }else {
            eventType = ManualNoticeEvent.PLAYER_ACCOUNT_FREEZON;
        }
        return eventType;
    }

    /**
     * 账号停用
     *
     * @param model
     * @param accountVo
     * @return
     */
    @RequestMapping(value = "setAccountDisabled", method = RequestMethod.POST)
    @ResponseBody
    public Map setAccountDisabled(Model model, AccountVo accountVo) {
        createRemarkTitle(accountVo, "disabledAccount.text");
        ServiceTool.vSysUserService().setAccountDisabled(accountVo);
        /*踢出当前登录的用户*/
        loginKickout(accountVo.getResult().getId());
        // code by cogo
        INoticeEventType eventType = getDisabledNoticeEventType(accountVo.getType());
        // code by cogo end
        String messageType = "accountDisabled";
//        sendMessageByGroupCode(messageType, accountVo, accountVo.getGroupCode(), accountVo.getResult().getId(), eventType);
        return getVoMessage(accountVo);
    }

    /**
     * 账号冻结
     *
     * @param model
     * @param accountVo
     * @return
     */
    @RequestMapping("/freezeAccount")
    public String freezeAccount(Model model, AccountVo accountVo) {
        /*冻结模板 账号信息 在线状态*/
        accountVo = ServiceTool.vSysUserService().accountOption(accountVo);
        Map<String, SysDict> freezeTime = DictTool.get(DictEnum.COMMON_FREEZE_TIME);
        accountVo.setFreezeTime(freezeTime);
        model.addAttribute("command", accountVo);
        return getViewBasePath() + "FreezeAccount";
    }

    /**
     * 账号冻结弹窗
     *
     * @param model
     * @param accountVo
     * @return
     */
    @RequestMapping("/setFreezeAccount")
    @ResponseBody
    public Map setFreezeAccount(Model model, AccountVo accountVo) {

        INoticeEventType eventType = getFreezeNoticeEventType(accountVo.getType());
        /*'备注类型 sys_dict Model：common,Dict_type：remark_type';*/
        createRemarkTitle(accountVo, "freezeAccount.text");
        accountVo._setUserId(SessionManager.getUserId());
        accountVo = ServiceTool.vSysUserService().setAccountFreeze(accountVo);
        /*踢出当前登录的用户*/
        loginKickout(accountVo.getResult().getId());
        /*发送信息*/
//        String messageType = "accountFreeze";//玩家账号冻结
//        sendMessageByGroupCode(messageType, accountVo, accountVo.getGroupCode(), accountVo.getResult().getId(), eventType);
        return getVoMessage(accountVo);
    }

    /***
     * 如果登录就踢出
     *
     * @param userId
     */
    private void loginKickout(Integer userId) {
        //调用单点退出登录
        SysUser sysUser = SessionManagerBase.getUser();
        redisSessionDao.kickOutSession(CacheKey.getCacheKey(redisSessionDao.genPrefix(),sysUser.getSessionKey()), OpMode.MANUAL);
    }

    /***
     * 取消账户冻结前查看
     */
    @RequestMapping("/toCancelAccountFreeze")
    public String toCancelAccountFreeze(VSysUserVo vo, Model model, String sign) {
        vo = ServiceTool.vSysUserService().get(vo);
        model.addAttribute("command", vo);
        if ("player".equals(sign)) {
            model.addAttribute("urls", "/player/playerView.html");
        }
        if ("agent".equals(sign)) {
            model.addAttribute("urls", "/userAgent/agent/detail.html");
        }
        return getViewBasePath() + "CancelFreezeAccount";
    }

    /***
     * 取消账户冻结
     *
     * @param accountVo
     * @return
     */
    @RequestMapping("/cancelAccountFreeze")
    @ResponseBody
    public Map cancelAccountFreeze(AccountVo accountVo) {
        accountVo.setResult(new SysUser());
        accountVo.getResult().setId(accountVo.getSearch().getId());
        accountVo.getResult().setFreezeEndTime(new Date());
        accountVo.setProperties(SysUser.PROP_FREEZE_END_TIME);
        ServiceTool.sysUserService().updateOnly(accountVo);
        return getVoMessage(accountVo);
    }

    /*
     *  系统生成备注标题
     * @param accountVo
     * @param viewsKey
     * @return
     */
    private AccountVo createRemarkTitle(AccountVo accountVo, String viewsKey) {
        //获取被操作实体用户信息
        SysUserVo sysUserVo = new SysUserVo();
        sysUserVo.getSearch().setId(accountVo.getResult().getId());
        sysUserVo = this.getService().get(sysUserVo);
        //设置result
        accountVo.setResult(sysUserVo.getResult());

        //获取国际化后的角色显示信息
        String role = LocaleTool.tranView("account", "" + EnumTool.enumOf(UserTypeEnum.class, accountVo.getResult().getUserType().toString()));
        //获取备注标题
        String title = SessionManager.getUser().getUsername() + LocaleTool.tranView("account", viewsKey, new Object[]{role, accountVo.getResult().getUsername()});
        accountVo.setRemarkTitle(title);
        accountVo.setOperator(SessionManager.getUserName());
        return accountVo;
    }

    @RequestMapping("/freezeBalance")
    public String freezeBalance(Model model, AccountVo accountVo) {

        /*账号冻结模板*/
        accountVo = ServiceTool.vSysUserService().accountOption(accountVo);
        NoticeVo noticeVo = new NoticeVo();
        noticeVo.setEventType(ManualNoticeEvent.BALANCE_FREEZON);
        List<NoticeLocaleTmpl> noticeLocaleTmpls = ServiceTool.noticeService().fetchLocaleTmpls(noticeVo);
        accountVo.setNoticeLocaleTmpls(noticeLocaleTmpls);

        /*冻结时间字典*/
        Map<String, SysDict> freezeTime = DictTool.get(DictEnum.COMMON_FREEZE_TIME);
        accountVo.setFreezeTime(freezeTime);
        accountVo.setValidateRule(JsRuleCreator.create(AccountOptionForm.class, "result"));
        model.addAttribute("command", accountVo);
        return getViewBasePath() + "FreezeBalance";
    }

    /**
     * 根据模板code发送信息
     *
     * @param groupCode
     * @param userId
     */
    private void sendMessageByGroupCode(String messageType, AccountVo accountVo, String groupCode, Integer userId, INoticeEventType eventType) {
        //获取接收用户的默认时区
        SysUserVo sysUserVo = new SysUserVo();
        sysUserVo.setResult(new SysUser());
        sysUserVo.getSearch().setId(accountVo.getResult().getId());
        sysUserVo = ServiceTool.sysUserService().get(sysUserVo);
        //发送站内信模板内容
        NoticeVo noticeVo = new NoticeVo();
        noticeVo.setTmplGroupCode(groupCode);
        noticeVo.setEventType(eventType);
        noticeVo.addUserIds(userId);
        //账号冻结
        if ("accountFreeze".equals(messageType)) {
            String time = LocaleDateTool.formatDate(accountVo.getResult().getFreezeEndTime(), "yyyy-MM-dd HH:mm:ss", sysUserVo.getResult().getDefaultTimezone());
            noticeVo.addParams(new Pair("unfreezetime", time));
        }
        //账号停用
        if ("accountDisabled".equals(messageType)) {
            String time = LocaleDateTool.formatDate(accountVo.getResult().getFreezeTime(), "yyyy-MM-dd HH:mm:ss", sysUserVo.getResult().getDefaultTimezone());
            noticeVo.addParams(new Pair("operatetime", time));
        }
        ServiceTool.noticeService().publish(noticeVo);
    }
    //endregion your codes 3

}