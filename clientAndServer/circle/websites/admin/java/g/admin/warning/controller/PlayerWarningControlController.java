package g.admin.warning.controller;

import g.admin.warning.form.PlayerWarningControlForm;
import g.model.game.vo.GameListVo;
import g.web.admin.session.SessionManager;
import org.soul.commons.lang.GenericTool;
import org.soul.commons.net.ServletTool;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.web.controller.BaseCrudController;
import g.service.warning.IPlayerWarningControlService;
import g.model.warning.po.PlayerWarningControl;
import g.model.warning.vo.PlayerWarningControlListVo;
import g.model.warning.vo.PlayerWarningControlVo;
import g.admin.warning.form.PlayerWarningControlSearchForm;
import org.soul.web.validation.form.annotation.FormModel;
import org.soul.web.validation.form.js.JsRuleCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.Map;


/**
 * 玩家实时监控控制表控制器
 *
 * @author lenovo
 * @time 2017-2-21 14:12:56
 */
@Controller
//region your codes 1
@RequestMapping("/playerWarningControl")
public class PlayerWarningControlController extends BaseCrudController<IPlayerWarningControlService, PlayerWarningControlListVo, PlayerWarningControlVo, PlayerWarningControlSearchForm, PlayerWarningControlForm, PlayerWarningControl, Integer> {
//endregion your codes 1

    @Autowired
    private IPlayerWarningControlService playerWarningControlService;

    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/warning/set/";
        //endregion your codes 2
    }

    @Override
    public String edit(PlayerWarningControlVo objectVo, Integer id, Model model, HttpServletRequest request, HttpServletResponse response) {
        PlayerWarningControl warning = playerWarningControlService.getWarningControl();
       // playerWarningControlService.
        model.addAttribute("command", warning);
        return "/warning/set/Edit";

    }


    @RequestMapping("/saveAi")
    @ResponseBody
    public Map persist(PlayerWarningControlVo aiVo, @FormModel("result") @Valid PlayerWarningControlForm form, BindingResult result) {
        SysUser sysUser = SessionManager.getUser();
        //保存之前先把表里已有的数据的状态变为失效  数据表值保存一条有效数据
        playerWarningControlService.updateWarningStatus(sysUser.getId());
        if(aiVo.getResult().getId() == null){
            aiVo.getResult().setCreateUser(sysUser.getUsername());
            aiVo.getResult().setCreateTime(new Date());
            aiVo.getResult().setUpdateUser(sysUser.getUsername());
            aiVo.getResult().setUpdateTime(new Date());
            aiVo.getResult().setStatus("10");
        }
        return super.persist(aiVo, form, result);
    }
}