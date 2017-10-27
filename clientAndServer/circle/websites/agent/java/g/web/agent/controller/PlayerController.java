package g.web.agent.controller;

import g.common.security.AuthTool;
import g.model.DictEnum;
import g.model.SubSysCodeEnum;
import g.model.player.vo.VSysUserListVo;
import g.model.player.vo.VSysUserVo;
import g.web.agent.controller.form.UpdatePasswordForm;
import g.web.agent.session.SessionManager;
import g.web.agent.tools.ServiceTool;
import org.soul.commons.dict.DictTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.locale.LocaleTool;
import org.soul.commons.net.ServletTool;
import org.soul.commons.spring.utils.SpringTool;
import org.soul.commons.support._Module;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.model.security.privilege.vo.SysUserVo;
import org.soul.web.validation.form.js.JsRuleCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
public class PlayerController  {
//endregion your codes 1


    //region your codes 3
    @Autowired
    private AuthTool authTool;

    private static final String INDEX = "player/admin/Index";
    private static final String PLAYER_EDIT = "player/admin/PlayerEdit";
    private static final String PLAYER_VIEW = "player/admin/PlayerView";

    //region your codes 3

    @RequestMapping("/playerList")
    public String userList(HttpServletRequest request,Model model,VSysUserListVo vSysUserListVo){
        vSysUserListVo.getSearch().setSubsysCode(SubSysCodeEnum.PLAYER.getCode());
        vSysUserListVo.getSearch().setOwnerId(SessionManager.getUser().getId());
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
     * 修改账户密码--检查密码
     *
     * @param password
     * @return
     */
    @RequestMapping(value = "/checkPassword")
    @ResponseBody
    public String checkPassword(@RequestParam("password") String password) {

        SysUser user = SessionManager.getUser();
        if (authTool == null) {
            authTool = SpringTool.getBean(AuthTool.class);
        }
        String inputPassword = authTool.md5SysUserPassword(password, user.getUsername());
        SysUserVo vo = new SysUserVo();
        SysUserVo sysUserVo;
        vo.getSearch().setId(user.getId());
        sysUserVo = ServiceTool.sysUserService().get(vo);
        return sysUserVo.getResult().getPassword().equals(inputPassword) ? "true" : "false";
    }


    //endregion your codes 3

}