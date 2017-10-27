package g.web.agent.controller.player.controller;

import g.common.security.AuthTool;
import g.model.DictEnum;
import g.model.SubSysCodeEnum;
import g.model.admin.po.VSysAnnouncement;
import g.model.admin.vo.VSysAnnouncementListVo;
import g.model.admin.vo.VSysAnnouncementVo;
import g.model.cache.CacheKey;
import g.model.common.userbankcard.po.UserBankcard;
import g.model.common.userbankcard.vo.UserBankcardListVo;
import g.model.common.userbankcard.vo.UserBankcardVo;
import g.model.enums.BankEnum;
import g.model.enums.BankTypeEnum;
import g.model.payaccount.vo.BankListVo;
import g.model.player.po.VSysUser;
import g.model.player.vo.VSysUserListVo;
import g.model.player.vo.VSysUserVo;
import g.service.common.IVSysUserService;
import g.web.agent.controller.player.form.VSysUserForm;
import g.web.agent.controller.player.form.VSysUserSearchForm;
import g.web.agent.session.SessionManager;
import org.soul.commons.net.ServletTool;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.web.controller.BaseCrudController;
import org.soul.web.validation.form.annotation.FormModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
/**
 * 游戏玩家查询管理
 *
 * @author orange
 * @time 2015-12-3 11:45:57
 */
@Controller
@RequestMapping("/player")
public class PlayerUserController extends BaseCrudController<IVSysUserService, VSysUserListVo, VSysUserVo, VSysUserSearchForm, VSysUserForm, VSysUser, Integer> {
    @Override
    protected String getViewBasePath() {
        return "/player/";
    }

    @Autowired
    private IVSysUserService vSysUserService;

    /**
     * 玩家查询
     * @param request
     * @param form
     * @param result
     * @param model
     * @param vSysUserListVo
     * @return
     */
    @RequestMapping("/playerUserList")
    public String playerUserList(HttpServletRequest request, @FormModel("search") @Valid VSysUserSearchForm form, BindingResult result, Model model, VSysUserListVo vSysUserListVo){
        SysUser sysUser = SessionManager.getUser();
        vSysUserListVo.getSearch().setOwnerId(sysUser.getId());
        vSysUserListVo =vSysUserService.search(vSysUserListVo);
        model.addAttribute("command",vSysUserListVo);
        return ServletTool.isAjaxSoulRequest(request) ?  "/player/playeruser/indexPlayer" + "Partial" :  "/player/playeruser/indexPlayer";
    }

}