package g.admin.sys.controller;

import g.common.security.AuthTool;
import g.model.DictEnum;
import g.model.admin.vo.MyAccountVo;
import g.model.admin.vo.UpdatePasswordVo;
import g.model.admin.vo.UpdateUserInfoVo;
import g.model.common.notice.enums.ContactWayStatus;
import g.model.common.notice.enums.ContactWayType;
import g.web.admin.session.SessionManager;
import g.web.admin.sys.form.*;
import g.web.admin.tools.ServiceTool;
import org.soul.commons.collections.CollectionTool;
import org.soul.commons.collections.ListTool;
import org.soul.commons.collections.MapTool;
import org.soul.commons.dict.DictTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.locale.LocaleTool;
import org.soul.model.msg.notice.po.NoticeContactWay;
import org.soul.model.msg.notice.vo.NoticeContactWayListVo;
import org.soul.model.msg.notice.vo.NoticeContactWayVo;
import org.soul.model.passport.vo.PassportVo;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.model.security.privilege.vo.SysUserVo;
import org.soul.web.validation.form.annotation.FormModel;
import org.soul.web.validation.form.js.JsRuleCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * 控制器
 * <p>
 * Created by ke using soul-code-generator on 2015-7-9 9:40:01
 */
@Controller
//region your codes 1
@RequestMapping("/sys/account")
public class MyAccountController {
    @Autowired
    private AuthTool authTool;
    //endregion your codes 1


    protected String getViewBasePath() {
        //region your codes 2
        return "/sys/myAccount/";
        //endregion your codes 2
    }


    /**
     * 我的账户
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/index")
    public String myAccount(Model model, MyAccountVo myAccountVo) {
        myAccountVo._setUserId(SessionManager.getUserId());
        myAccountVo = ServiceTool.myAccountService().getMyAccount(myAccountVo);
        model.addAttribute("command", myAccountVo);
        //获取用户类型
        model.addAttribute("type", SessionManager.getUserType().getCode());
        return this.getViewBasePath() + "Index";
    }

    /**
     * 上传头像--打开页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/toUploadHeadPortrait")
    public String toUploadHeadPortrait(Model model) {
        SysUserVo vo = new SysUserVo();
        vo.getSearch().setId(SessionManager.getUserId());
        //表单校验
        model.addAttribute("validate", JsRuleCreator.create(MyAccountAvatarForm.class));
        return this.getViewBasePath() + "UploadHeadPortrait";
    }

    /**
     * 上传头像--保存
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/uploadHeadPortrait")
    @ResponseBody
    public Map uploadHeadPortrait(SysUserVo vo) {
        Map map = MapTool.newHashMap();
        Integer userId = SessionManager.getUserId();
        vo.getResult().setId(userId);
        List<String> properties = ListTool.newArrayList();
        properties.add(SysUser.PROP_AVATAR_URL);
        vo.setProperties(properties.toArray(new String[1]));
        boolean success;
        success = ServiceTool.sysUserService().updateOnly(vo).isSuccess();
        map.put("state", success);
        if (success) {
            map.put("msg", LocaleTool.tranMessage("common", "myAccount.updatePassword.success"));
        } else {
            map.put("msg", LocaleTool.tranMessage("common", "myAccount.updatePassword.failed"));
        }
        return map;
    }

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
        String inputPassword = authTool.md5SysUserPassword(password, user.getUsername());

        SysUserVo vo = new SysUserVo();
        SysUserVo sysUserVo;
        vo.getSearch().setId(user.getId());
        sysUserVo = ServiceTool.sysUserService().get(vo);
        return sysUserVo.getResult().getPassword().equals(inputPassword) ? "true" : "false";
    }

    /**
     * 修改账户密码--打开页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/toUpdatePassword")
    public String toUpdatePassword(Model model) {
        model.addAttribute("rule", JsRuleCreator.create(UpdatePasswordForm.class));
        model.addAttribute("type", 1);
        return this.getViewBasePath() + "UpdatePassword";
    }

    /**
     * 修改账户密码--保存修改
     *
     * @param updatePasswordVo
     * @return
     */
    @RequestMapping(value = "/updatePassword")
    @ResponseBody
    public Map updatePassword(UpdatePasswordVo updatePasswordVo, @FormModel() @Valid UpdatePasswordForm form, BindingResult result) {
        Map map = MapTool.newHashMap();
        if (result.hasErrors()) {
            map.put("state", false);
            map.put("msg", LocaleTool.tranMessage("common", "myAccount.updatePassword.failed"));
            return map;
        }
        Integer userId = SessionManager.getUserId();
        SysUserVo sysUserVo = new SysUserVo();
        SysUser sysUser = new SysUser();
        sysUser.setId(userId);
        String newPwd = authTool.md5SysUserPassword(updatePasswordVo.getNewPassword(), SessionManager.getUserName());
        sysUser.setPassword(newPwd);
        sysUserVo.setResult(sysUser);
        List<String> properties = ListTool.newArrayList();
        properties.add(SysUser.PROP_PASSWORD);
        sysUserVo.setProperties(properties.toArray(new String[1]));
        boolean success;
        success = ServiceTool.sysUserService().updateOnly(sysUserVo).isSuccess();
        map.put("state", success);
        if (success) {
            /*修改成功修改session中的密码*/
            SysUser sessionUser = SessionManager.getUser();
            sessionUser.setPassword(newPwd);
            SessionManager.setUser(sessionUser);
            map.put("msg", LocaleTool.tranMessage("common", "myAccount.updatePassword.success"));
        } else {
            map.put("msg", LocaleTool.tranMessage("common", "myAccount.updatePassword.failed"));
        }
        return map;
    }

    /**
     * 修改权限密码--打开页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/toUpdatePrivilegePassword")
    public String toUpdatePrivilegePassword(Model model) {
        model.addAttribute("type", 2);
        String privilegeCode = SessionManager.getPrivilegeCode();
        if (privilegeCode == null) {
            model.addAttribute("rule", JsRuleCreator.create(UpdatePrivilegesPasswordWithoutRemoteForm.class));
        } else {
            model.addAttribute("rule", JsRuleCreator.create(UpdatePrivilegesPasswordForm.class));
        }
        model.addAttribute("first", privilegeCode == null);
        return this.getViewBasePath() + "UpdatePassword";
    }


    /**
     * 修改权限密码--检查密码
     *
     * @param password
     * @return
     */
    @RequestMapping(value = "/checkPrivilegePassword")
    @ResponseBody
    public String checkPrivilegePassword(@RequestParam("password") String password) {
        SysUser user = SessionManager.getUser();
        String inputPassword = authTool.md5SysUserPermission(password, user.getUsername());
        String privilegeCode = SessionManager.getPrivilegeCode();
        return privilegeCode.equals(inputPassword) ? "true" : "false";
    }

    /**
     * 修改权限密码--保存修改
     *
     * @param updatePasswordVo
     * @return
     */
    @RequestMapping(value = "/updatePrivilegePassword")
    @ResponseBody
    public Map updatePrivilegePassword(UpdatePasswordVo updatePasswordVo) {
        Map map = MapTool.newHashMap();
        Integer userId = SessionManager.getUserId();
        SysUserVo sysUserVo = new SysUserVo();
        SysUser sysUser = new SysUser();
        sysUser.setId(userId);
        String newPwd = authTool.md5SysUserPermission(updatePasswordVo.getNewPassword(), SessionManager.getUserName());
        sysUser.setPermissionPwd(newPwd);
        sysUserVo.setResult(sysUser);
        List<String> properties = ListTool.newArrayList();
        properties.add(SysUser.PROP_PERMISSION_PWD);
        sysUserVo.setProperties(properties.toArray(new String[1]));
        boolean success;
        success = ServiceTool.sysUserService().updateOnly(sysUserVo).isSuccess();
        map.put("state", success);
        if (success) {
            /*修改成功修改session中的密码*/
            SysUser sessionUser = SessionManager.getUser();
            sessionUser.setPermissionPwd(newPwd);
            SessionManager.setUser(sessionUser);
            map.put("msg", LocaleTool.tranMessage("common", "myAccount.updatePassword.success"));
            SessionManager.clearPrivilegeStatus();
        } else {
            map.put("msg", LocaleTool.tranMessage("common", "myAccount.updatePassword.failed"));
        }
        return map;
    }


    /**
     * 修改資料--打开页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/toUpdatePersonInfo")
    public String toUpdatePersonInfo(Model model) {
        model.addAttribute("rule", JsRuleCreator.create(MyAccountForm.class));
        //Dict sex
        Map<String, Serializable> sexes = DictTool.get(DictEnum.COMMON_SEX);
        model.addAttribute("sexes", sexes);
        //Dict constellations
        Map<String, Serializable> constellations = DictTool.get(DictEnum.COMMON_CONSTELLATION);
        model.addAttribute("constellations", constellations);

        Integer userId = SessionManager.getUserId();
        SysUserVo vo = new SysUserVo();
        vo.getSearch().setId(userId);
        SysUserVo sysUserVo;
        //联系方式
        NoticeContactWayListVo listVo = new NoticeContactWayListVo();
        listVo.getSearch().setUserId(userId);

        Map<String, List<NoticeContactWay>> contactWays = ServiceTool.noticeContactWayService().fetchByUserId(listVo);
        if (MapTool.isNotEmpty(contactWays)) {
            List<NoticeContactWay> phones = contactWays.get(ContactWayType.CELLPHONE.getCode());
            if (CollectionTool.isNotEmpty(phones)) {
                NoticeContactWay noticeContactWay = phones.get(0);
                model.addAttribute("phone", noticeContactWay.getContactValue());
                model.addAttribute("phoneId", noticeContactWay.getId());
            }
            List<NoticeContactWay> emails = contactWays.get(ContactWayType.EMAIL.getCode());
            if (CollectionTool.isNotEmpty(emails)) {
                NoticeContactWay noticeContactWay = emails.get(0);
                model.addAttribute("email", noticeContactWay.getContactValue());
                model.addAttribute("emailId", noticeContactWay.getId());
            }
            /*List<NoticeContactWay> skypes = contactWays.get(ContactWayType.SKYPE.getCode());
            if (CollectionTool.isNotEmpty(skypes)) {
                NoticeContactWay noticeContactWay = skypes.get(0);
                model.addAttribute("skype", noticeContactWay.getContactValue());
                model.addAttribute("skypeId", noticeContactWay.getId());
            }
            List<NoticeContactWay> msns = contactWays.get(ContactWayType.MSN.getCode());
            if (CollectionTool.isNotEmpty(msns)) {
                NoticeContactWay noticeContactWay = msns.get(0);
                model.addAttribute("msn", noticeContactWay.getContactValue());
                model.addAttribute("msnId", noticeContactWay.getId());
            }
            List<NoticeContactWay> qqs = contactWays.get(ContactWayType.QQ.getCode());
            if (CollectionTool.isNotEmpty(qqs)) {
                NoticeContactWay noticeContactWay = qqs.get(0);
                model.addAttribute("qq", noticeContactWay.getContactValue());
                model.addAttribute("qqId", noticeContactWay.getId());
            }*/
        }

        sysUserVo = ServiceTool.sysUserService().get(vo);
        model.addAttribute("command", sysUserVo);
        return this.getViewBasePath() + "UpdatePersonInfo";
    }

    /**
     * 修改資料--保存修改
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/updatePersonInfo")
    @ResponseBody
    public Map updatePersonInfo(UpdateUserInfoVo vo, @FormModel() @Valid MyAccountForm form, BindingResult result) {
        Map map = MapTool.newHashMap();
        if (result.hasErrors()) {
            map.put("state", false);
            map.put("msg", LocaleTool.tranMessage("agent", "myAccount.updatePassword.failed"));
            return map;
        }
        //保存mobile
        String phone = vo.getPhone();
        Integer phoneId = vo.getPhoneId();
        if (StringTool.isNotBlank(phone) || phoneId != null) {
            NoticeContactWayVo newVo = new NoticeContactWayVo();
            NoticeContactWay newPo = new NoticeContactWay();
            if (phoneId != null) {
                //更新
                newPo.setId(phoneId);
                newPo.setContactValue(phone);
                newVo.setResult(newPo);
                newVo.setProperties(NoticeContactWay.PROP_CONTACT_VALUE);
                if (String.valueOf(PassportVo.MASTER).equals(SessionManager.getEntrance())) {
                    //站长
                    newVo._setDataSourceId(SessionManager.getSiteParentId());
                }
                ServiceTool.noticeContactWayService().updateOnly(newVo);
            } else {
                //新增了一个
                newPo.setUserId(SessionManager.getUserId());
                newPo.setContactType(ContactWayType.CELLPHONE.getCode());
                newPo.setContactValue(phone);
                newPo.setStatus(ContactWayStatus.NORMAL.getCode());
                newPo.setPriority(0);
                newVo.setResult(newPo);
                if (String.valueOf(PassportVo.MASTER).equals(SessionManager.getEntrance())) {
                    //站长
                    newVo._setDataSourceId(SessionManager.getSiteParentId());
                }
                ServiceTool.noticeContactWayService().insert(newVo);
            }
        }

        //保存email
        String email = vo.getEmail();
        Integer emailId = vo.getEmailId();
        if (StringTool.isNotBlank(email) || emailId != null) {
            NoticeContactWayVo newVo = new NoticeContactWayVo();
            NoticeContactWay newPo = new NoticeContactWay();
            if (emailId != null) {
                //更新
                newPo.setId(emailId);
                newPo.setContactValue(email);
                newVo.setResult(newPo);
                newVo.setProperties(NoticeContactWay.PROP_CONTACT_VALUE);
                if (String.valueOf(PassportVo.MASTER).equals(SessionManager.getEntrance())) {
                    //站长
                    newVo._setDataSourceId(SessionManager.getSiteParentId());
                }
                ServiceTool.noticeContactWayService().updateOnly(newVo);
            } else {
                //新增了一个
                newPo.setUserId(SessionManager.getUserId());
                newPo.setContactType(ContactWayType.EMAIL.getCode());
                newPo.setContactValue(email);
                newPo.setStatus(ContactWayStatus.NORMAL.getCode());
                newPo.setPriority(0);
                newVo.setResult(newPo);
                if (String.valueOf(PassportVo.MASTER).equals(SessionManager.getEntrance())) {
                    //站长
                    newVo._setDataSourceId(SessionManager.getSiteParentId());
                }
                ServiceTool.noticeContactWayService().insert(newVo);
            }
        }

        //保存user信息
        SysUserVo sysUserVo = new SysUserVo();
        sysUserVo.setResult(new SysUser());
        sysUserVo.getResult().setId(SessionManager.getUserId());
        sysUserVo.getResult().setNickname(vo.getNickname());
        sysUserVo.getResult().setSex(vo.getSex());
        sysUserVo.getResult().setBirthday(vo.getBirthday());
        sysUserVo.getResult().setConstellation(vo.getConstellation());
        List<String> properties = ListTool.newArrayList();
        properties.add(SysUser.PROP_NICKNAME);
        properties.add(SysUser.PROP_SEX);
        properties.add(SysUser.PROP_BIRTHDAY);
        properties.add(SysUser.PROP_CONSTELLATION);
        sysUserVo.setProperties(properties.toArray(new String[1]));
        boolean success;
        success = ServiceTool.sysUserService().updateOnly(sysUserVo).isSuccess();
        map.put("state", success);
        if (success) {
            map.put("msg", LocaleTool.tranMessage("common", "myAccount.updatePassword.success"));
        } else {
            map.put("msg", LocaleTool.tranMessage("common", "myAccount.updatePassword.failed"));
        }
        return map;
    }
    //endregion your codes 3
}