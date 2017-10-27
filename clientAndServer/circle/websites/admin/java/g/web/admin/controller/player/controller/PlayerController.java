package g.web.admin.controller.player.controller;

import g.common.security.AuthTool;
import g.model.DictEnum;
import g.model.SubSysCodeEnum;
import g.model.common.userbankcard.po.UserBankcard;
import g.model.admin.po.VSysAnnouncement;
import g.model.common.userbankcard.vo.UserBankcardListVo;
import g.model.common.userbankcard.vo.UserBankcardVo;
import g.model.admin.vo.VSysAnnouncementListVo;
import g.model.admin.vo.VSysAnnouncementVo;
import g.model.cache.CacheKey;
import g.model.enums.BankEnum;
import g.model.enums.BankTypeEnum;
import g.model.payaccount.vo.BankListVo;
import g.model.player.vo.VSysUserListVo;
import g.model.player.vo.VSysUserVo;
import g.service.common.IVSysAnnouncementService;
import g.web.admin.controller.announcement.form.VSysAnnouncementForm;
import g.web.admin.controller.announcement.form.VSysAnnouncementSearchForm;
import g.web.admin.controller.player.form.UserBankcardForm;
import g.web.admin.session.SessionManager;
import g.web.admin.sys.form.UpdatePasswordForm;
import g.web.admin.tools.ServiceTool;
import org.soul.commons.cache.CacheTool;
import org.soul.commons.dict.DictTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.locale.LocaleTool;
import org.soul.commons.net.ServletTool;
import org.soul.commons.support._Module;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.model.security.privilege.vo.SysUserVo;
import org.soul.web.controller.BaseCrudController;
import org.soul.web.validation.form.js.JsRuleCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * 总控-系统公告视图控制器
 *
 * @author orange
 * @time 2015-12-3 11:45:57
 */
@Controller
//region your codes 1
@RequestMapping("/player")
public class PlayerController extends BaseCrudController<IVSysAnnouncementService, VSysAnnouncementListVo, VSysAnnouncementVo, VSysAnnouncementSearchForm, VSysAnnouncementForm, VSysAnnouncement, Integer> {
//endregion your codes 1

    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/player/";
        //endregion your codes 2
    }

    //region your codes 3
    @Autowired
    private AuthTool authTool;

    private static final String INDEX = "player/Index";
    private static final String PLAYER_EDIT = "player/PlayerEdit";
    private static final String PLAYER_VIEW = "player/PlayerView";
    private static final String BANK_CARD_URI = "/player/view.include/BankCard";
    private static final String BANK_CARD_EDIT_URI = "/player/view.include/BankCardEdit";

    @Autowired
    private CacheTool cacheTool;

    //region your codes 3

    @RequestMapping("/playerList")
    public String userList(HttpServletRequest request,Model model,VSysUserListVo vSysUserListVo){
        vSysUserListVo.getSearch().setSubsysCode(SubSysCodeEnum.PLAYER.getCode());
        vSysUserListVo = ServiceTool.vSysUserService().search(vSysUserListVo);
        model.addAttribute("command",vSysUserListVo);

        Map<String, Serializable> status = DictTool.get(DictEnum.PLAYER_STATUS);
        model.addAttribute("playerStatus", status);
        return ServletTool.isAjaxSoulRequest(request) ? INDEX + "Partial" : INDEX;
    }


    /**
     * 玩家编辑页面
     * Created by jerry on 15-6-4.
     */
    @RequestMapping("/playerEdit")
    public String playerEdit(VSysUserVo objectVo, Model model) {
        objectVo = ServiceTool.vSysUserService().get(objectVo);
        model.addAttribute("validateRule", JsRuleCreator.create(UpdatePasswordForm.class));
        model.addAttribute("command", objectVo);
        return PLAYER_EDIT;
    }

    /**
     * 玩家保存
     * @return
     */
    @RequestMapping("/savePlayer")
    @ResponseBody
    public Map savePlayer(SysUserVo sysUserVo,Model model){
        sysUserVo.setSuccess(false);
        String newPwd = authTool.md5SysUserPassword(sysUserVo.getNewPassword(), sysUserVo.getResult().getUsername());
        sysUserVo.getResult().setPassword(newPwd);
        sysUserVo.setProperties(SysUser.PROP_PASSWORD,SysUser.PROP_NICKNAME);
        sysUserVo = ServiceTool.sysUserService().updateOnly(sysUserVo);
        if (sysUserVo.isSuccess()) {
            sysUserVo.setOkMsg(LocaleTool.tranMessage(_Module.COMMON, "update.success"));
        } else {
            sysUserVo.setErrMsg(LocaleTool.tranMessage(_Module.COMMON, "update.failed"));
        }
        Map map = new HashMap();
        map.put("msg", StringTool.isNotBlank(sysUserVo.getOkMsg()) ? sysUserVo.getOkMsg() : sysUserVo.getErrMsg());
        map.put("state", sysUserVo.isSuccess());
        return map;
    }

    /**
     * 嵌入在玩家列表的玩家信息详情页
     *
     * @return
     */
    @RequestMapping("/playerDetail")
    public String playerView(VSysUserVo vSysUserVo, Model model,HttpServletRequest request) {
        vSysUserVo = ServiceTool.vSysUserService().get(vSysUserVo);
        model.addAttribute("command", vSysUserVo);
        return PLAYER_VIEW;
    }
    /**
     * 玩家信息详情页-显示局部隐藏的字段
     *
     * @return
     *//*
    @RequestMapping("/playerViewDetail")
    public String playerViewDetail(VUserPlayerVo vUserPlayerVo, Model model, Boolean ajax) {
        vUserPlayerVo = doView(vUserPlayerVo, model);
        model.addAttribute("extendedLinks", vUserPlayerVo.getExtendedLinks());
        model.addAttribute("command", vUserPlayerVo);
        //站内信
        message(vUserPlayerVo, model);
        model.addAttribute("unencryption", SessionManager.checkPrivilegeStatus());
        return (ajax != null && ajax) ? PLAYER_INFO_URI : PLAYER_VIEW_URI;
    }*/



    /**
     * 玩家信息详情-银行卡
     *
     * @return
     */
    @RequestMapping("/view/bankCard")
    public String bankCard(UserBankcardListVo listVo, Model model) {

        listVo.getPaging().setPageSize(10);
        listVo = ServiceTool.userBankcardService().search(listVo);
        for (UserBankcard userBankcard : listVo.getResult()) {
            if (BankTypeEnum.WECHAT.getCode().equals(userBankcard.getBankType()) || BankTypeEnum.ALIPAY.getCode().equals(userBankcard.getBankType())) {
                userBankcard.setBankName(userBankcard.getBankType());
            }
        }
        model.addAttribute("bankMap", cacheTool.get(CacheKey.CACHE_KEY_BANK));
        model.addAttribute("test",cacheTool.get(CacheKey.CK_PLAYER_GROUP_LIMIT));
        model.addAttribute("command", listVo);
        return BANK_CARD_URI;
    }

    @RequestMapping("/view/bankEdit")
    public String bankEdit(UserBankcardVo objVo, Model model, BankListVo bankListVo) {
        objVo.getSearch().setIsDefault(true);
        objVo = ServiceTool.userBankcardService().search(objVo);
        model.addAttribute("command", objVo);
        bankListVo.getSearch().setType(BankEnum.TYPE_BANK.getCode());
        bankListVo.getSearch().setIsUse(true);
        bankListVo.setPaging(null);
        bankListVo = ServiceTool.bankService().search(bankListVo);
        //bankListVo.setResult();
        model.addAttribute("bankListVo", bankListVo);
        //表单校验
        model.addAttribute("validate", JsRuleCreator.create(UserBankcardForm.class));
        return BANK_CARD_EDIT_URI;
    }

    @RequestMapping("/view/bankCardSave")
    @ResponseBody
    public Map bankCardSave(UserBankcardVo objVo, BindingResult result) {
        UserBankcardVo query = new UserBankcardVo();
        query.getSearch().setId(objVo.getSearch().getId());
        UserBankcardVo _objVo = ServiceTool.userBankcardService().get(query);//银行卡修改前的信息
        _objVo.getResult().setIsDefault(false);
        _objVo.setProperties(UserBankcard.PROP_IS_DEFAULT);
        ServiceTool.userBankcardService().updateOnly(_objVo);//银行卡修改后需更新之前的银行卡信息状态

        //银行卡修改即是将原默认银行卡信息修改为历史使用，新增一条默认使用银行卡信息
        objVo.getResult().setId(null);
        objVo.getResult().setIsDefault(true);
        objVo.getResult().setUseStauts(false);
        objVo.getResult().setCreateTime(SessionManager.getDate().getNow());
        objVo.getResult().setUseCount(0);
        objVo.getResult().setUserId(_objVo.getResult().getUserId());
        objVo.getResult().setBankcardMasterName(_objVo.getResult().getBankcardMasterName());
        objVo.getResult().setBankType(_objVo.getResult().getBankType());
        ServiceTool.userBankcardService().insert(objVo);
        return this.getVoMessage(objVo);
    }
    //endregion your codes 3


}