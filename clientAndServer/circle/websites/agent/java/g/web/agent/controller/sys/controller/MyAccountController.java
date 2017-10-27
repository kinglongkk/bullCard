package g.web.agent.controller.sys.controller;

import g.common.security.AuthTool;
import g.model.DictEnum;
import g.model.admin.vo.MyAccountVo;
import g.model.admin.vo.UpdatePasswordVo;
import g.model.admin.vo.UpdateUserInfoVo;
import g.model.common.notice.enums.ContactWayStatus;
import g.model.common.notice.enums.ContactWayType;
import g.web.agent.controller.form.UpdatePasswordForm;
import g.web.agent.controller.sys.form.MyAccountForm;
import g.web.agent.controller.sys.form.MyAccountLogoForm;
import g.web.agent.session.SessionManager;
import g.web.agent.tools.ServiceTool;
import org.soul.commons.collections.CollectionTool;
import org.soul.commons.collections.ListTool;
import org.soul.commons.collections.MapTool;
import org.soul.commons.dict.DictTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.locale.LocaleTool;
import org.soul.iservice.msg.notice.INoticeContactWayService;
import org.soul.model.msg.notice.po.NoticeContactWay;
import org.soul.model.msg.notice.vo.NoticeContactWayListVo;
import org.soul.model.msg.notice.vo.NoticeContactWayVo;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.model.security.privilege.vo.SysUserVo;
import org.soul.web.validation.form.annotation.FormModel;
import org.soul.web.validation.form.js.JsRuleCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 代理账号控制器
 * Created by black on 2017/7/4.
 */
@Controller
@RequestMapping("/agentAccount")
public class MyAccountController {

    private static final String INDEX = "sys/Index";
    private static final String PASS = "sys/UpdatePassword";
    private static final String INFO = "sys/UpdatePersonInfo";
    private static final String LOGO = "sys/UploadHeadPortrait";

    @Autowired
    private AuthTool authTool;

    @Autowired
    private INoticeContactWayService wayService;

    /**
     * 我的账户
     * @param model
     * @return
     */
    @RequestMapping("/myAccount")
    public String myAccount(Model model, MyAccountVo myAccountVo) {

        myAccountVo._setUserId(SessionManager.getUserId());
        myAccountVo = ServiceTool.myAccountService().getMyAccount(myAccountVo);
        model.addAttribute("command", myAccountVo);
        //获取用户类型
        model.addAttribute("type", SessionManager.getUserType().getCode());
        return INDEX;
    }

    /**
     * 修改账户密码--打开页面
     * @param model
     * @return
     */
    @RequestMapping("/toUpdatePassword")
    public String toUpdatePassword(Model model) {

        model.addAttribute("rule", JsRuleCreator.create(UpdatePasswordForm.class));
        return PASS;
    }

    /**
     * 修改账户密码--保存修改
     *
     * @param updatePasswordVo
     * @return
     */
    @RequestMapping("/updatePassword")
    @ResponseBody
    public Map updatePassword(UpdatePasswordVo updatePasswordVo, @FormModel() @Valid UpdatePasswordForm form, BindingResult result) {

        Map map = MapTool.newHashMap();
        if (result.hasErrors()) {
            map.put("state", false);
            map.put("msg", LocaleTool.tranMessage("content", "myAccount.updatePassword.failed"));
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
            map.put("msg", LocaleTool.tranMessage("content", "myAccount.updatePassword.success"));
        } else {
            map.put("msg", LocaleTool.tranMessage("content", "myAccount.updatePassword.failed"));
        }
        return map;
    }

    /**
     * 修改資料--打开页面
     * @param model
     * @return
     */
    @RequestMapping("/toUpdatePersonInfo")
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

        Map<String, List<NoticeContactWay>> contactWays = wayService.fetchByUserId(listVo);
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
        }

        sysUserVo = ServiceTool.sysUserService().get(vo);
        model.addAttribute("command", sysUserVo);
        return INFO;
    }

    /**
     * 修改資料--保存修改
     *
     * @param vo
     * @return
     */
    @RequestMapping("/updatePersonInfo")
    @ResponseBody
    public Map updatePersonInfo(UpdateUserInfoVo vo, @FormModel() @Valid MyAccountForm form, BindingResult result) {

        Map map = MapTool.newHashMap();
        if (result.hasErrors()) {
            map.put("state", false);
            map.put("msg", LocaleTool.tranMessage("content", "myAccount.updatePassword.failed"));
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
                wayService.updateOnly(newVo);
            } else {
                //新增了一个
                newPo.setUserId(SessionManager.getUserId());
                newPo.setContactType(ContactWayType.CELLPHONE.getCode());
                newPo.setContactValue(phone);
                newPo.setStatus(ContactWayStatus.NORMAL.getCode());
                newPo.setPriority(0);
                newVo.setResult(newPo);
                wayService.insert(newVo);
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
                wayService.updateOnly(newVo);
            } else {
                //新增了一个
                newPo.setUserId(SessionManager.getUserId());
                newPo.setContactType(ContactWayType.EMAIL.getCode());
                newPo.setContactValue(email);
                newPo.setStatus(ContactWayStatus.NORMAL.getCode());
                newPo.setPriority(0);
                newVo.setResult(newPo);
                wayService.insert(newVo);
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
            map.put("msg", LocaleTool.tranMessage("content", "myAccount.updatePassword.success"));
        } else {
            map.put("msg", LocaleTool.tranMessage("content", "myAccount.updatePassword.failed"));
        }
        return map;
    }

    /**
     * 上传头像--打开页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/toUploadHeadPortrait")
    public String toUploadHeadPortrait(Model model) {

        SysUserVo vo = new SysUserVo();
        vo.getSearch().setId(SessionManager.getUserId());
        //表单校验
        model.addAttribute("validate", JsRuleCreator.create(MyAccountLogoForm.class));
        return LOGO;
    }

    /**
     * 上传头像--保存
     * @param vo
     * @return
     */
    @RequestMapping("/uploadHeadPortrait")
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
            map.put("msg", LocaleTool.tranMessage("content", "myAccount.updatePassword.success"));
        } else {
            map.put("msg", LocaleTool.tranMessage("content", "myAccount.updatePassword.failed"));
        }
        return map;
    }

}
