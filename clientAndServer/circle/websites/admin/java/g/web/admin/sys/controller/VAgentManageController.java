package g.web.admin.sys.controller;

import g.common.security.AuthTool;
import g.model.DictEnum;
import g.model.SubSysCodeEnum;
import g.model.UserTypeEnum;
import g.model.agent.po.VAgentManage;
import g.model.agent.vo.VAgentManageListVo;
import g.model.agent.vo.VAgentManageVo;
import g.model.common.Const;
import g.service.admin.IAgentManagerService;
import g.service.admin.IVAgentManageService;
import g.web.admin.session.SessionManager;
import g.web.admin.sys.form.VAgentManageForm;
import g.web.admin.sys.form.VAgentManageSearchForm;
import g.web.common.ServiceToolBase;
import org.soul.commons.dict.DictTool;
import org.soul.commons.lang.string.RandomStringTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.query.Criterion;
import org.soul.commons.query.enums.Operator;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.model.security.privilege.po.SysUserStatus;
import org.soul.model.security.privilege.vo.SysUserListVo;
import org.soul.model.security.privilege.vo.SysUserVo;
import org.soul.web.controller.BaseCrudController;
import org.soul.web.session.SessionManagerBase;
import org.soul.web.validation.form.annotation.FormModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author: tom
 * @date: 16-4-12
 */
@Controller
@RequestMapping("/vAgentManage")
public class VAgentManageController extends BaseCrudController<IVAgentManageService, VAgentManageListVo, VAgentManageVo, VAgentManageSearchForm, VAgentManageForm, VAgentManage, Integer> {

    @Autowired
    private AuthTool authTool;

    @Autowired
    private IAgentManagerService agentManagerService;

    @Override
    protected String getViewBasePath() {

        return "/sys/agent/";
    }

    @Override
    public String list(VAgentManageListVo listVo, VAgentManageSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {

        Map<String, Serializable> status = DictTool.get(DictEnum.USER_STATUS);
        status.remove(SysUserStatus.AUDIT_FAIL.getCode());
        model.addAttribute("status", status);
        return super.list(listVo, form, result, model, request, response);
    }

    @Override
    protected VAgentManageVo doCreate(VAgentManageVo objectVo, Model model) {

        objectVo.setSex(DictTool.get(DictEnum.COMMON_SEX));
        Map<String, Serializable> status = DictTool.get(DictEnum.USER_STATUS);
        status.remove(SysUserStatus.AUDIT_FAIL.getCode());
        status.remove(SysUserStatus.INACTIVE.getCode());
        objectVo.setStatus(status);

        Map<String, Serializable> userType = DictTool.get(DictEnum.COMMON_USER_TYPE);
        userType.remove(UserTypeEnum.ADMIN.getCode());
        userType.remove(UserTypeEnum.ADMIN_SUB.getCode());
        userType.remove(UserTypeEnum.AGENT_PLAYER.getCode());
        userType.remove(UserTypeEnum.PLAYER.getCode());
        objectVo.setUserType(userType);
        objectVo.setCountry(DictTool.get(DictEnum.COMMON_COUNTRY));
        return super.doCreate(objectVo, model);
    }

    @Override
    protected VAgentManageVo doEdit(VAgentManageVo objectVo, Model model) {

        objectVo.setSex(DictTool.get(DictEnum.COMMON_SEX));
        Map<String, Serializable> status = DictTool.get(DictEnum.USER_STATUS);
        status.remove(SysUserStatus.AUDIT_FAIL.getCode());
        status.remove(SysUserStatus.INACTIVE.getCode());
        objectVo.setStatus(status);

        Map<String, Serializable> userType = DictTool.get(DictEnum.COMMON_USER_TYPE);
        userType.remove(UserTypeEnum.ADMIN.getCode());
        userType.remove(UserTypeEnum.ADMIN_SUB.getCode());
        userType.remove(UserTypeEnum.AGENT_PLAYER.getCode());
        userType.remove(UserTypeEnum.PLAYER.getCode());
        objectVo.setUserType(userType);
        objectVo.setCountry(DictTool.get(DictEnum.COMMON_COUNTRY));
        getService().editAgent(objectVo);
        return objectVo;
    }

    @RequestMapping("/update")
    @ResponseBody
    public Map update(VAgentManageVo objectVo, @FormModel("result") @Valid VAgentManageForm form, BindingResult result) {

        if (!result.hasErrors()) {
            SysUser sysUser = objectVo.getSysUser();
            sysUser.setUpdateUser(SessionManager.getUserId());
            sysUser.setUpdateTime(new Date());
            objectVo = getService().updateAgent(objectVo);
            return getVoMessage(objectVo);
        }
        return null;
    }

    @Override
    protected VAgentManageVo doSave(VAgentManageVo objectVo) {

        objectVo.getSysUser().setPassword(authTool.md5SysUserPassword(objectVo.getSysUser().getPassword(), objectVo.getSysUser().getUsername()));
        objectVo.getSysUser().setAvatarUrl("images/head/comm_1_icon_head_0"+ RandomStringTool.randomNumeric(1)+".jpg");
        objectVo.getSysUser().setCreateTime(new Date());
        objectVo.getSysUser().setOwnerId(getOwnerId());
        objectVo.getSysUser().setCreateUser(SessionManager.getUserId());
        objectVo.getSysUser().setCreateTime(new Date());
        objectVo.getSysUser().setBuiltIn(false);
        objectVo.getSysUser().setSubsysCode(SubSysCodeEnum.AGENT.getCode());
        objectVo.getSysUser().setSiteId(Const.Default_Site_Id);
        //做为代理唯一的推广码
        objectVo.getSysUser().setRegisterSite(RandomStringTool.randomAlphabetic(6));

        SysUserVo sysUserVo = new SysUserVo();
        sysUserVo.setResult(objectVo.getSysUser());
        objectVo.setSuccess(agentManagerService.insertAgent(sysUserVo));
        return objectVo;
    }

    @RequestMapping("/checkUsernameExist")
    @ResponseBody
    public String checkUsernameExist(@RequestParam("sysUser.username") String userName,@RequestParam("sysUser.id") Integer id){

        if(StringTool.isBlank(userName)){
            return "false";
        }
        SysUserListVo sysUserListVo = new SysUserListVo();
        sysUserListVo.getQuery().setCriterions(new Criterion[]{
                new Criterion(SysUser.PROP_USERNAME, Operator.EQ, userName),
                new Criterion(SysUser.PROP_ID, Operator.NE, id)
        });
        long count = ServiceToolBase.sysUserService().count(sysUserListVo);
        return (count < 1 ) + "";
    }

    private Integer getOwnerId(){

        SysUser sysUser = SessionManagerBase.getUser();
        UserTypeEnum userTypeEnum = UserTypeEnum.enumOf(sysUser.getUserType());
        switch (userTypeEnum){
            case ADMIN_SUB:
                return sysUser.getOwnerId();
            case ADMIN:
                return sysUser.getId();
            case AGENT_SUB:
                return sysUser.getOwnerId();
            case AGENT:
                return sysUser.getId();
            default:
                return SessionManagerBase.getSiteUserId();
        }
    }
}
