package g.web.common.subaccount.controller;

import g.service.common.IVSubAccountService;
import g.model.DictEnum;
import g.model.ModuleType;
import g.model.SubSysCodeEnum;
import g.model.UserTypeEnum;
import g.model.agent.po.VSubAccount;
import g.model.agent.vo.VSubAccountListVo;
import g.model.agent.vo.VSubAccountVo;
import g.model.setting.po.SysRole;
import g.model.setting.po.VSysRole;
import g.model.setting.vo.SysRoleListVo;
import g.web.common.ServiceToolBase;
import g.web.common.SessionManagerCommon;
import g.web.common.subaccount.form.VSubAccountForm;
import g.web.common.subaccount.form.VSubAccountSearchForm;
import g.web.common.subaccount.form.VSubAccountRoleForm;
import org.soul.commons.bean.Pair;
import org.soul.commons.dict.DictTool;
import org.soul.commons.enums.EnumTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.net.ServletTool;
import org.soul.commons.query.Criterion;
import org.soul.commons.query.enums.Operator;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.model.security.privilege.po.SysUserRole;
import org.soul.model.security.privilege.po.SysUserStatus;
import org.soul.model.security.privilege.vo.SysRoleVo;
import org.soul.model.security.privilege.vo.SysUserListVo;
import org.soul.model.security.privilege.vo.SysUserRoleListVo;
import org.soul.model.sys.vo.SysAuditLogListVo;
import org.soul.web.controller.BaseCrudController;
import org.soul.web.session.SessionManagerBase;
import org.soul.web.validation.form.js.JsRuleCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * 子账户视图控制器
 *
 * @author jeff
 * @time 2015-10-20 10:49:13
 */
@Controller
//region your codes 1
@RequestMapping("/subAccount")
public class VSubAccountController extends BaseCrudController<IVSubAccountService, VSubAccountListVo, VSubAccountVo, VSubAccountSearchForm, VSubAccountForm, VSubAccount, Integer> {

    private static final Log log = LogFactory.getLog(VSubAccountController.class);

//endregion your codes 1

    private static List<Pair> keys;
    static {
        keys = new ArrayList<>();
        keys.add(new Pair("search.operator", "角色账号"));
        keys.add(new Pair("search.ip", "IP 地址"));
    }

    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/subaccount/";
        //endregion your codes 2
    }

    //region your codes 3
    @Value("${subsys.code}")
    private String subSysCode;

    @Override
    protected VSubAccountListVo doList(VSubAccountListVo listVo, VSubAccountSearchForm form, BindingResult result, Model model) {
        listVo.getSearch().setUserType(getUserTypeCode());
        listVo.getSearch().setOwnerId(getOwnerId());
        listVo.getSearch().setSubSysCode(subSysCode);//此处可有可无
        listVo = super.doList(listVo, form, result, model);
        listVo.setSubSysCode(subSysCode);
        return listVo;
    }

    @Override
    protected VSubAccountVo doCreate(VSubAccountVo objectVo, Model model) {
        objectVo.setSubSysCode(subSysCode);
        objectVo.getSearch().setSubSysCode(subSysCode);
//        objectVo.setQuestions(DictTool.get(DictEnum.SETTING_MASTER_QUESTION1));
        objectVo.setSex(DictTool.get(DictEnum.COMMON_SEX));
        objectVo = super.doCreate(objectVo, model);
        return getService().preCreateOrEdit(objectVo);
    }

    @Override
    protected VSubAccountVo doEdit(VSubAccountVo objectVo, Model model) {
        objectVo.getSearch().setSubSysCode(subSysCode);
        objectVo.setSubSysCode(subSysCode);
        objectVo.setSex(DictTool.get(DictEnum.COMMON_SEX));
        objectVo.setQuestions(DictTool.get(DictEnum.SETTING_MASTER_QUESTION1));
        return super.doEdit(objectVo, model);
    }

    @Override
    protected VSubAccountVo doUpdate(VSubAccountVo objectVo) {
        List<Integer> listParam = new ArrayList<Integer>();
        listParam.add(objectVo.getSysUser().getId());
        return super.doUpdate(objectVo);
    }

    @Override
    protected VSubAccountVo doSave(VSubAccountVo objectVo) {
        objectVo.getSysUser().setUserType(getUserTypeCode());
        objectVo.getSysUser().setStatus(SysUserStatus.NORMAL.getCode());
        objectVo.getSysUser().setCreateTime(new Date());
        objectVo.getSysUser().setSubsysCode(subSysCode);
        objectVo.getSysUser().setOwnerId(getOwnerId());
        objectVo.getSysUser().setSiteId(SessionManagerCommon.getUser().getSiteId());
        objectVo.setCurrentLoginUser(SessionManagerCommon.getUser());
        return super.doSave(objectVo);
    }

    @RequestMapping("/checkUsernameExist")
    @ResponseBody
    public String checkUsernameExist(@RequestParam("sysUser.username") String userName,@RequestParam("subSysCode") String subSysCode,@RequestParam("sysUser.id") Integer id){
        if(StringTool.isBlank(userName) || StringTool.isBlank(subSysCode)){
            return "false";
        }
        SysUserListVo sysUserListVo = new SysUserListVo();
        sysUserListVo.getQuery().setCriterions(new Criterion[]{
                new Criterion(SysUser.PROP_USERNAME, Operator.EQ, userName),
                new Criterion(SysUser.PROP_SUBSYS_CODE, Operator.EQ, subSysCode),
                new Criterion(SysUser.PROP_ID, Operator.NE, id)
        });
        long count = ServiceToolBase.sysUserService().count(sysUserListVo);
        return (count < 1 ) + "";
    }

    @RequestMapping("/getRoles")
    public String getRoles(VSubAccountListVo vSubAccountListVo,Model model){
        vSubAccountListVo.getSearch().setSubSysCode(subSysCode);
        List<Map<String,Object>> roles = getService().getRoles(vSubAccountListVo);
        model.addAttribute("roles",roles);
        model.addAttribute("vSubAccountListVo", vSubAccountListVo);
        return getViewBasePath()+"UserRoles";
    }

    @RequestMapping("/resetRole")
    @ResponseBody
    public Map resetRole(VSubAccountVo vSubAccountVo){
        getService().resetRole(vSubAccountVo);
        return getVoMessage(vSubAccountVo);
    }

    @RequestMapping("/subAccountDelete")
    @ResponseBody
    public Map subAccountDelete(VSubAccountVo vSubAccountVo){
//        getService().batchUpdateOnly();
        /*vSubAccountVo.getSearch().setStatus(SysUserStatus.AUDIT_FAIL.getCode());
        vSubAccountVo = getService().changeStatusByIds(vSubAccountVo);*/
        // 子账号允许物理删除 by cogo
        getService().deleteSubAccounts(vSubAccountVo);
        return getVoMessage(vSubAccountVo);
    }

    @RequestMapping("/role")
    public String role(SysRoleListVo sysRoleListVo,Model model,HttpServletRequest request){
        sysRoleListVo.getSearch().setSubsysCode(subSysCode);
        sysRoleListVo = getService().settingRole(sysRoleListVo);

        //查看我的权限：点击链接至站长的权限预览页面 modify by eagle
        if (sysRoleListVo.isReadOnly()) {
            sysRoleListVo.setUserId(SessionManagerCommon.getUserId());
            List<Integer> roleIds = ServiceToolBase.vSubAccountService().searchRoleIds(sysRoleListVo);
            for (Integer roleId : roleIds) {
                for (Iterator<VSysRole> iterator=sysRoleListVo.getvSysRoles().iterator();iterator.hasNext();) {
                    VSysRole vSysRole = iterator.next();
                    if (vSysRole != null && !roleId.equals(vSysRole.getId())) {
                        sysRoleListVo.getvSysRoles().remove(iterator);
                    }
                }
            }

        } else {
            //编辑
            sysRoleListVo.setValidateRule(JsRuleCreator.create(VSubAccountRoleForm.class, "result"));
        }
        model.addAttribute("sysRoleListVo",sysRoleListVo);
        model.addAttribute("resourceKey", getResourceKey());
        return ServletTool.isAjaxSoulRequest(request)?getViewBasePath()+"role/IndexPartial":getViewBasePath()+"role/Index";
    }

    @RequestMapping("/getRoleResource")
    public String getRoleResource(){

        return getViewBasePath().concat("role/IndexPart");
    }

    @RequestMapping("/deleteRole")
    @ResponseBody
    public Map deleteRole(SysRoleVo sysRoleVo){
        ServiceToolBase.sysRoleService().delete(sysRoleVo);
        return getVoMessage(sysRoleVo);
    }

    @RequestMapping("/resetDefault")
    @ResponseBody
    public Map resetDefault(VSubAccountVo vSubAccountVo){
        vSubAccountVo = getService().roleResetDefault(vSubAccountVo);
        return getVoMessage(vSubAccountVo);
    }

    @RequestMapping("/getRoleUsingUser")
    @ResponseBody
    public Long getRoleUsingUser(SysUserRoleListVo sysRoleResourceListVo){
        sysRoleResourceListVo.getQuery().setCriterions(new Criterion[]{
                new Criterion(SysUserRole.PROP_ROLE_ID, Operator.EQ,sysRoleResourceListVo.getSearch().getRoleId())
        });
        return ServiceToolBase.sysUserRoleService().count(sysRoleResourceListVo);
    }

    @RequestMapping("/roleRename")
    public String roleRename(SysRoleVo sysRoleVo,Model model){
        sysRoleVo = ServiceToolBase.sysRoleService().get(sysRoleVo);
        sysRoleVo.setValidateRule(JsRuleCreator.create(VSubAccountRoleForm.class, "result"));
        model.addAttribute("sysRoleVo", sysRoleVo);
        return getViewBasePath()+"role/RoleRename";
    }

    @RequestMapping("/changeRoleName")
    @ResponseBody
    public Map changeRoleName(SysRoleVo sysRoleVo){
        sysRoleVo.setProperties(SysRole.PROP_NAME);
        ServiceToolBase.sysRoleService().updateOnly(sysRoleVo);
        return getVoMessage(sysRoleVo);
    }

    @RequestMapping("/logList")
    protected String logList(SysAuditLogListVo listVo, Model model, HttpServletRequest request) {
        model.addAttribute("moduleTypes", EnumTool.getEnumList(ModuleType.class));
        model.addAttribute("opType", DictTool.get(DictEnum.Log_OpType));//操作类型
        model.addAttribute("now", SessionManagerBase.getDate().getNow());
        model.addAttribute("keys", keys);
        model.addAttribute("hasReturn", request.getParameter("hasReturn"));
        beforQuery(listVo, model);

        listVo = getService().queryLogs(listVo);
        model.addAttribute("command", listVo);
        if (ServletTool.isAjaxSoulRequest(request)) {
            return getViewBasePath() + "log/IndexPartial";
        } else {
            return getViewBasePath() + "log/Index";
        }
    }

    private UserTypeEnum getSubAccountUserType(){
        UserTypeEnum userTypeEnum = null;
        SubSysCodeEnum subSysCodeEnum = SubSysCodeEnum.enumOf(subSysCode);
        switch (subSysCodeEnum){
            case AGENT:
                userTypeEnum = UserTypeEnum.AGENT_SUB;
                break;
            case ADMIN:
                userTypeEnum = UserTypeEnum.ADMIN_SUB;
                break;
            default:
                log.debug("当前中心配置subSysCode:{0},根据subSysCode 取子帐号userType出错",subSysCode);
        }
        return userTypeEnum;
    }

    private String getUserTypeCode() {
        if (getSubAccountUserType() == null) {
            return "";
        }
        return getSubAccountUserType().getCode();
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

    private String getResourceKey() {
        String trans = null;
        SubSysCodeEnum subSysCodeEnum = SubSysCodeEnum.enumOf(getSubSysCode());
        switch (subSysCodeEnum){
            case ADMIN:
                trans =  "系统设置";
                break;
            case AGENT:
                trans = "账号管理";
                break;
        }
        return trans;
    }

    public String getSubSysCode() {
        return subSysCode;
    }

    public void setSubSysCode(String subSysCode) {
        this.subSysCode = subSysCode;
    }

    private void beforQuery(SysAuditLogListVo listVo, Model model) {
        List<Pair> roleKeys = new ArrayList<>();
        roleKeys.add(new Pair(UserTypeEnum.ADMIN.getCode(),"管理员"));
        roleKeys.add(new Pair(UserTypeEnum.ADMIN_SUB.getCode(),"管理员-子帐号"));
        model.addAttribute("roleKeys",roleKeys);
    }

    private String aaa(){
        System.out.println("aaaaaaaaaa");
        return null;
    }

    //endregion your codes 3

}