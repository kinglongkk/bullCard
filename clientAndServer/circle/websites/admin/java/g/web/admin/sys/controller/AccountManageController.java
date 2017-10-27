package g.web.admin.sys.controller;

import g.model.DictEnum;
import g.model.Module;
import g.model.RemarkEnum;
import g.model.UserTypeEnum;
import g.model.account.po.VAccountManage;
import g.model.account.vo.ResetPwdVo;
import g.model.account.vo.VAccountManageListVo;
import g.model.account.vo.VAccountManageVo;
import g.model.admin.po.Remark;
import g.model.common.notice.enums.ManualNoticeEvent;
import g.service.account.IVAccountManageService;
import g.web.admin.session.SessionManager;
import g.web.admin.sys.form.ResetPwdForm;
import g.web.admin.sys.form.VAccountManageForm;
import g.web.admin.sys.form.VAccountManageSearchForm;
import g.web.admin.tools.ServiceTool;
import org.soul.commons.bean.Pair;
import org.soul.commons.cache.CacheKey;
import org.soul.commons.dict.DictTool;
import org.soul.commons.lang.string.I18nTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.locale.LocaleTool;
import org.soul.commons.support._Module;
import org.soul.model.log.audit.enums.OpMode;
import org.soul.model.msg.notice.enums.NoticePublishMethod;
import org.soul.model.msg.notice.enums.NoticeReceiverGroupType;
import org.soul.model.msg.notice.vo.NoticeVo;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.model.security.privilege.po.SysUserStatus;
import org.soul.model.security.privilege.vo.SysUserVo;
import org.soul.web.controller.BaseCrudController;
import org.soul.web.session.RedisSessionDao;
import org.soul.web.session.SessionManagerBase;
import org.soul.web.validation.form.annotation.FormModel;
import org.soul.web.validation.form.js.JsRuleCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 账号管理控制器
 *
 * @author tom
 * @time 2016-04-15
 */
@Controller
@RequestMapping("/accountManage")
public class AccountManageController extends BaseCrudController<IVAccountManageService, VAccountManageListVo, VAccountManageVo, VAccountManageSearchForm, VAccountManageForm, VAccountManage, Integer> {

    @Autowired
    private RedisSessionDao redisSessionDao;

    @Override
    protected String getViewBasePath() {
        return "/player/account/";
    }

    //region your codes 3
    @RequestMapping("/freezeAccount")
    public String freeze(VAccountManageVo vo,Model model) {
        model.addAttribute("command", getService().get(vo));
        model.addAttribute("freezeTime", DictTool.get(DictEnum.COMMON_FREEZE_TIME));
        model.addAttribute("validate", JsRuleCreator.create(VAccountManageForm.class));
        return getViewBasePath() + "FreezeAccount";
    }

    @RequestMapping("/previewFreezeAccount")
    public String previewFreezeAccount(VAccountManageVo vo,@FormModel("result") @Valid VAccountManageForm form, BindingResult result,Model model) {
        if (!result.hasErrors()) {
            model.addAttribute("sub",vo);
            model.addAttribute("command", getService().get(vo));
            return getViewBasePath() + "PreviewFreeze";
        }
        return null;
    }

    @RequestMapping("/saveFreezeAccount")
    @ResponseBody
    public Map saveFreezeAccount(VAccountManageVo vo,@FormModel("result") @Valid VAccountManageForm form, BindingResult result,Model model) {
        if (!result.hasErrors()) {
            vo = getService().get(vo);
            Date now = SessionManager.getDate().getNow();
            Remark remark = new Remark();
            remark.setRemarkContent(vo.getSysUser().getFreezeContent());
            remark.setRemarkTime(now);
            remark.setModel(RemarkEnum.USER.getModel());
            remark.setRemarkType(RemarkEnum.USER_USER_FREEZE.getType());
            remark.setOperatorId(SessionManager.getUserId());
            remark.setOperator(SessionManager.getUserName());
            remark.setEntityUserId(vo.getResult().getId());
            remark.setEntityId(vo.getResult().getId());
            Map<String, Map<String, String>> dictMaps = I18nTool.getDictsMap(SessionManagerBase.getLocale().toString()).get(Module.COMMON.getCode());
            String remarkTitle = dictMaps.get(DictEnum.COMMON_REMARK_TITLE.getType()).get(RemarkEnum.USER_USER_FREEZE.getType());
            remarkTitle = remarkTitle.replace("{operator}", SessionManager.getUserName())
                    .replace("{userType}", dictMaps.get(DictEnum.COMMON_USER_TYPE.getType()).get(vo.getResult().getUserType()))
                    .replace("{user}", vo.getResult().getUsername());
            remark.setRemarkTitle(remarkTitle);
            vo.setRemark(remark);
            vo.getSysUser().setId(vo.getResult().getId());
            vo.getSysUser().setUpdateTime(now);
            vo.getSysUser().setUpdateUser(SessionManager.getUserId());
            vo.getSysUser().setStatus(SysUserStatus.LOCKED.getCode());
            vo.getSysUser().setFreezeUser(SessionManager.getUserId());
            vo = getService().saveFreezeAccount(vo);
            Map<String, Object> map = new HashMap<>(2);
            if (vo.isSuccess()) {
                loginKickout(vo.getResult().getId());
                // sendMessage(vo);
                map.put("msg", LocaleTool.tranMessage(_Module.COMMON, "operation.success"));
                map.put("state", vo.isSuccess());
            } else {
                map.put("msg", LocaleTool.tranMessage(_Module.COMMON, "operation.failed"));
                map.put("state", vo.isSuccess());
            }
            return map;
        }
        return null;
    }

    /**
     * 跳转取消账号冻结页面
     *
     * @param vo
     * @param model
     * @return
     */
    @RequestMapping("/cancelAccountFreeze")
    public String cancelFreeze(VAccountManageVo vo, Model model) {
        vo = getService().get(vo);
        model.addAttribute("command", vo);
        model.addAttribute("validate", JsRuleCreator.create(VAccountManageForm.class));
        return getViewBasePath() + "CancelFreeze";
    }

    /**
     * 取消冻结
     *
     * @param vo
     * @return
     */
    @RequestMapping("/saveCancelFreeze")
    @ResponseBody
    public Map saveCancelFreeze(VAccountManageVo vo, Model model) {
        vo.getSysUser().setUpdateTime(new Date());
        vo.getSysUser().setUpdateUser(SessionManager.getUserId());
        vo = getService().cancelFreezeAccount(vo);
        return getVoMessage(vo);
    }

    /**
     * 账户停用
     * @param vo
     * @param model
     * @return
     */
    @RequestMapping("/disabledAccount")
    public String accountStop(VAccountManageVo vo,Model model) {
        model.addAttribute("command",vo);
        return getViewBasePath() + "DisabledAccount";
    }

    /**
     * 账号停用确认页面
     * @param vMasterManageVo
     * @param model
     * @return
     */
    @RequestMapping("/confirmDisabled")
    public String confirmDisabled(VAccountManageVo vMasterManageVo,Model model) {
        model.addAttribute("command", getService().get(vMasterManageVo));
        String userType = vMasterManageVo.getResult().getUserType();
        if (UserTypeEnum.AGENT.getCode().equals(userType) || UserTypeEnum.AGENT_SUB.getCode().equals(userType)) {
            // 玩家数 TODO 统计玩家数，展示
            return getViewBasePath() + "ConfirmDisabled";
        } else {
            return "";
        }
    }

    /**
     * 保存账号停用
     * @param vo
     * @param model
     * @return
     */
    @RequestMapping("/saveAccountDisabled")
    @ResponseBody
    public Map saveAccountDisabled(VAccountManageVo vo,Model model) {
        Date now = SessionManager.getDate().getNow();
        vo.getSysUser().setUpdateUser(SessionManager.getUserId());
        vo.getSysUser().setUpdateTime(now);
        vo.getSysUser().setDisabledTime(now);
        vo.getSysUser().setDisabledUser(SessionManager.getUserId());
        vo.getSysUser().setStatus(SysUserStatus.DISABLED.getCode());
        vo = getService().saveAccountStop(vo);
        Map<String, Object> map = new HashMap<>(2);
        if (vo.isSuccess()) {
            // 踢出当前登录的用户
            loginKickout(vo.getSysUser().getId());
            map.put("msg", LocaleTool.tranMessage(_Module.COMMON, "operation.success"));
            map.put("state", vo.isSuccess());
        } else {
            map.put("msg", LocaleTool.tranMessage(_Module.COMMON, "operation.failed"));
            map.put("state", vo.isSuccess());
        }
        return map;
    }

    /**
     * 手动重置登录密码弹窗
     *
     * @param vo
     * @param model
     * @return
     */
    @RequestMapping("/resetPwdByHand")
    public String loginPwdByHand(SysUserVo vo,String resetType,Model model) {
        vo = ServiceTool.sysUserService().get(vo);
        vo.setValidateRule(JsRuleCreator.create(ResetPwdForm.class));
        model.addAttribute("command", vo);
//        model.addAttribute("isLogin", true);
        model.addAttribute("resetType", resetType);
        return getViewBasePath() + "ChangePassword";
    }

    /**
     * 手动重置密码
     */
    @RequestMapping("/doRestUserPwdByHand")
    @ResponseBody
    public Map doRestUserPwdByHand(ResetPwdVo resetPwdVo, BindingResult result ) {
        if (!result.hasErrors()) {
            // 重置密码
            Boolean isOk = getService().resetSysUserPwd(resetPwdVo);
            resetPwdVo.setSuccess(isOk);

            if (isOk) {
                loginKickout(resetPwdVo.getUserId());
                resetPwdVo.setOkMsg(LocaleTool.tranMessage("resetPwd", "resetPwd.success"));
            } else {
                resetPwdVo.setErrMsg(LocaleTool.tranMessage("resetPwd", "resetPwd.failed"));
            }
        } else {
            resetPwdVo.setSuccess(false);
            resetPwdVo.setErrMsg(LocaleTool.tranMessage("resetPwd", "resetPwd.failed"));
        }

        return getVoMessage(resetPwdVo);
    }

    /***
     * 如果登录就踢出
     * @param userId
     */
    private void loginKickout(Integer userId){
        SysUser sysUser = SessionManagerBase.getUser();
        redisSessionDao.kickOutSession(CacheKey.getCacheKey(redisSessionDao.genPrefix(),sysUser.getSessionKey()), OpMode.MANUAL);
    }

    /**
     * 发送信息
     * @param vo
     */
    private void sendMessage(VAccountManageVo vo){
        NoticeVo noticeVo = new NoticeVo();
        //构造发送方式
        NoticePublishMethod noticePublishMethod = NoticePublishMethod.SITE_MSG;
        //具体用户
        noticeVo.addReceiverGroup(NoticeReceiverGroupType.USER);
        //发送方式
        noticeVo.setEventType(ManualNoticeEvent.GROUP_SEND);
        // 站长信息
        SysUserVo sysUserVo = new SysUserVo();
        sysUserVo.getSearch().setId(vo.getResult().getId());
        sysUserVo = ServiceTool.sysUserService().get(sysUserVo);
        sysUserVo = ServiceTool.sysUserService().search(sysUserVo);
        String local = StringTool.isNotEmpty(sysUserVo.getResult().getDefaultLocale())?sysUserVo.getResult().getDefaultLocale():SessionManager.getUser().getDefaultLocale();
        Pair<String, String> messagePair = new Pair<>();
        messagePair.setKey(LocaleTool.tranView(Module.COMMON,"account.frozen"));
        messagePair.setValue(vo.getSysUser().getFreezeContent());
        Map<String, Pair<String, String>> localTmpMap = new HashMap<>();
        localTmpMap.put(local, messagePair);
        noticeVo.setLocaleTmplMap(localTmpMap);
        noticeVo.setPublishMethod(noticePublishMethod);
        noticeVo.setSendUserId(NoticeVo.SEND_USER_ID);
        noticeVo.addUserIds(vo.getResult().getId());
        ServiceTool.noticeService().publish(noticeVo);
    }
    //endregion your codes 3

}