package g.web.common.privilege.controller;

import g.model.common.PrivilegeStatusEnum;
import g.web.common.cache.ParamTool;
import g.web.common.ServiceToolBase;
import g.web.common.passport.captcha.CaptchaUrlEnum;
import g.web.common.privilege.form.PrivilegeForm;
import g.web.common.privilege.form.UpdatePrivilegesPasswordWithoutRemoteForm;
import org.soul.commons.collections.MapTool;
import org.soul.commons.lang.DateTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.locale.LocaleTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.support._Module;
import org.soul.model.common.BaseVo;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.model.security.privilege.vo.SysUserVo;
import org.soul.model.session.SessionKey;
import org.soul.web.session.SessionManagerBase;
import org.soul.web.validation.form.annotation.FormModel;
import org.soul.web.validation.form.js.JsRuleCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import g.common.security.AuthTool;
import g.model.SiteParamEnum;
import g.web.common.SessionManagerCommon;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cj on 15-7-1 下午1:57.
 */
@Controller
@RequestMapping(value = "/privilege")
public class PrivilegeController {

    @Autowired
    private AuthTool authTool;

    private static final Log log = LogFactory.getLog(PrivilegeController.class);


    private static final String CHECK_PRIVILEGE_URI = "/privilege/PrivilegeCheck";
    private static final String LOCK_PRIVILEGE_URI = "/privilege/PrivilegeLock";
    private static final String SET_PRIVILEGE_URI = "/privilege/SetPrivilegePassword";

    /**
     * 检查权限密码状态
     *
     * @return
     */
    @RequestMapping("/checkPrivilege")
    @ResponseBody
    public Map checkPrivilege() {
        final HashMap<Object, Object> map = new HashMap<>(2);
        final String userId = SessionManagerCommon.getUserId() + "";
        if (StringTool.isBlank(SessionManagerCommon.getUser().getPermissionPwd())) {
            //未设置权限密码
            map.put("state", PrivilegeStatusEnum.CODE_96.getCode());
            return map;
        }
        Map privilegeUsers = SessionManagerCommon.getPrivilegeStatus();
        if (MapTool.isEmpty(privilegeUsers)) {
            //未验证，弹出验证框
            map.put("state", PrivilegeStatusEnum.CODE_0.getCode());
            return map;
        }
        Map data = (Map) privilegeUsers.get(userId);
        final String state = (String) data.get("state");
        if (state.equals(PrivilegeStatusEnum.STATUS_OK.getCode())) {
            //上次验证成功，判断是否在不需要输入密码的时间内。
            //TODO cj get from settings.暂时设为5分钟
            final Date lastTime = (Date) data.get("time");
            long l = DateTool.minutesBetween(new Date(), lastTime);
            String paramValue = ParamTool.getSysParam(SiteParamEnum.SETTING_PRIVILAGE_PASS_TIME).getParamValue();
            Integer privilegeTime = 0;
            if (StringTool.isNotBlank(paramValue)) {
                privilegeTime = Integer.parseInt(paramValue);
            }
            if (l < privilegeTime) {
                //还在允许的时间范围内
                map.put("state", PrivilegeStatusEnum.CODE_100.getCode());
            } else {
                //已超时，重新验证
                SessionManagerCommon.clearPrivilegeStatus();
                map.put("state", PrivilegeStatusEnum.CODE_0.getCode());
            }
        } else if (state.equals(PrivilegeStatusEnum.STATUS_LOCK.getCode())) {
            //锁定中，判断是否还在锁定的时间内
            final Date lastTime = (Date) data.get("time");
            //TODO cj get from settings.暂时设为3小时
            long l = DateTool.hoursBetween(new Date(), lastTime);
            if (l <= 3) {
                //还在锁定的时间内
                map.put("state", PrivilegeStatusEnum.CODE_99.getCode());
            } else {
                //不在锁定的时间内，重新验证
                SessionManagerCommon.clearPrivilegeStatus();
                map.put("state", PrivilegeStatusEnum.CODE_0.getCode());
            }
        } else {
            //输入错误的状态中，返回状态及错误次数
            final Integer times = (Integer) data.get("times");
            map.put("state", PrivilegeStatusEnum.CODE_98.getCode());
            map.put("times", times);
        }
        map.put("title", LocaleTool.tranView("common", "securityPassword"));
        return map;
    }

    /**
     * 显示输入密码页面
     *
     * @return
     */
    @RequestMapping("/showCheckPrivilege")
    public String showCheckPrivilege(Model modal) {
        Map privilegeUsers = SessionManagerCommon.getPrivilegeStatus();
        //TODO cj get system configed times.暂时设为5次
        Integer leftTimes = 5;
        if (MapTool.isNotEmpty(privilegeUsers)) {
            final String userId = SessionManagerCommon.getUserId() + "";
            final Map data = (Map) privilegeUsers.get(userId);
            if (MapTool.isNotEmpty(data)) {
                leftTimes = 5 - (data.get("times") == null ? 0 : Integer.parseInt(data.get("times").toString()));
            }
        }
        modal.addAttribute("leftTimes", leftTimes);
        modal.addAttribute("rule", JsRuleCreator.create(PrivilegeForm.class));
        return CHECK_PRIVILEGE_URI;
    }

    /**
     * 显示密码锁定页面
     *
     * @param model
     * @return
     */
   /* @RequestMapping("/showLockPrivilege")
    public String showLockPrivilege(Model model) {
        final String userId = SessionManagerCommon.getUserId() + "";
        Map privilegeUsers = SessionManagerCommon.getPrivilegeStatus();
        final Map data = (Map) privilegeUsers.get(userId);
        final String state = (String) data.get("state");
        if (state.equals(PrivilegeStatusEnum.STATUS_LOCK.getCode())) {
            //TODO cj get setting lock time.暂时设为3小时
            final Date lastTime = (Date) data.get("time");
            long seconds = DateTool.secondsBetween(new Date(), lastTime);
            long hour = DateTool.hoursBetween(new Date(), lastTime);
            long min = DateTool.minutesBetween(new Date(), lastTime);
            model.addAttribute("hour", (3 - hour - 1));
            model.addAttribute("min", (60 - min - 1));
            model.addAttribute("sec", (60 - (seconds - min * 60) - 1));
        }
        SiteCustomerServiceVo serviceVo = new SiteCustomerServiceVo();
        serviceVo.getSearch().setSiteId(SessionManagerBase.getSiteId());
        String customer = LocaleTool.tranMessage("setting", "notice.param.customer");
        SiteCustomerService defaultCustomService = ServiceToolBase.siteCustomerServiceService().getDefaultCustomService(serviceVo);
        if(defaultCustomService!=null){
            customer = "<a href=\""+defaultCustomService.getParameter()+"\" target=\"_blank\">"+customer+"</a>";
        }
        model.addAttribute("customer",customer);
        return LOCK_PRIVILEGE_URI;
    }*/

    /**
     * 验证输入的密码
     *
     * @param request
     * @param code
     * @return
     */
    @RequestMapping("/valiPrivilege")
    @ResponseBody
    public Map valiPrivilege(HttpServletRequest request, @FormModel() @Valid PrivilegeForm form, String code, String valiCode, BindingResult result) {
        HashMap map = new HashMap(2);
        if (result.hasErrors()) {
            map.put("state", false);
            map.put("msg", LocaleTool.tranMessage("setting", "myAccount.updatePassword.failed"));
            return map;
        }
        final String userId = SessionManagerCommon.getUserId() + "";
        final HashMap<Object, Object> temp = new HashMap<>(2);
        final int TRY_TIMES = 5;
        try {
            Map privilegeUsers = SessionManagerCommon.getPrivilegeStatus();
            String inputCode = authTool.md5SysUserPermission(code, SessionManagerCommon.getUserName());
            if (inputCode.equals(SessionManagerCommon.getPrivilegeCode())) {
                String msg = LocaleTool.tranMessage("privilege", "input.success");
                //第二次以上重试时，判断图片验证码
                if (privilegeUsers != null) {
                    final Map data = (Map) privilegeUsers.get(userId);
                    if (MapTool.isNotEmpty(data)) {
                        int times = Integer.parseInt(data.get("times").toString());
                        if (times > 1) {
                            if (StringTool.isBlank(valiCode)) {
                                msg = LocaleTool.tranMessage("privilege", "captcha.input");
                                map.put("msg", msg);
                                map.put("stateCode", PrivilegeStatusEnum.CODE_98.getCode());
                                map.put("state", false);
                                return map;
                            } else {
                                if(!checkValiCode(valiCode,request)) {
                                    msg = LocaleTool.tranMessage("privilege", "captcha.wrong");
                                    map.put("msg", msg);
                                    map.put("stateCode", PrivilegeStatusEnum.CODE_97.getCode());
                                    map.put("state", false);
                                    return map;
                                } else {
                                    map.put("stateCode", PrivilegeStatusEnum.CODE_100.getCode());
                                    map.put("state", true);
                                }
                            }
                        }
                    }
                }
                //map.put("msg", msg);
                if (privilegeUsers != null) {
                    privilegeUsers.clear();
                }
                //正确输入,保存状态及时间
                temp.put("state", PrivilegeStatusEnum.STATUS_OK.getCode());
                temp.put("time", new Date());
                privilegeUsers = new HashMap();
                privilegeUsers.put(userId, temp);
                SessionManagerCommon.setPrivilegeStatus(privilegeUsers);

                //返回页面消息
                //map.put("msg", msg);
                map.put("state", true);
                map.put("stateCode", PrivilegeStatusEnum.CODE_100.getCode());
            } else {
                if (MapTool.isEmpty(privilegeUsers) || privilegeUsers.get(userId) == null) {
                    //第一次
                    temp.put("state", PrivilegeStatusEnum.STATUS_ERRORS.getCode());
                    temp.put("times", 1);
                    privilegeUsers = new HashMap();
                    privilegeUsers.put(userId, temp);
                    SessionManagerCommon.setPrivilegeStatus(privilegeUsers);

                    //返回页面消息
                    map.put("msg", LocaleTool.tranMessage("privilege", "input.wrong"));
                    map.put("stateCode", PrivilegeStatusEnum.CODE_98.getCode());
                    map.put("state", false);
                    map.put("leftTimes", TRY_TIMES - 1);
                } else {
                    final Map data = (Map) privilegeUsers.get(userId);
                    int times = Integer.parseInt(data.get("times").toString());
                    //TODO cj get system configed times.暂时设为5次
                    if (times >= TRY_TIMES - 1) {
                        //错误达到上限,保存状态及时间
                        privilegeUsers.clear();
                        temp.put("state", PrivilegeStatusEnum.STATUS_LOCK.getCode());
                        temp.put("time", new Date());
                        privilegeUsers.put(userId, temp);
                        SessionManagerCommon.setPrivilegeStatus(privilegeUsers);

                        //返回页面消息
                        map.put("msg", LocaleTool.tranMessage("privilege", "input.freeze"));
                        map.put("stateCode", PrivilegeStatusEnum.CODE_99.getCode());
                        map.put("state", false);
                        return map;
                    } else {
                        //未达到错误上限
                        temp.put("state", PrivilegeStatusEnum.STATUS_ERRORS.getCode());
                        temp.put("times", ++times);
                        privilegeUsers.put(userId, temp);
                        SessionManagerCommon.setPrivilegeStatus(privilegeUsers);

                        //返回页面消息
                        map.put("msg", LocaleTool.tranMessage("privilege", "input.wrong"));
                        map.put("stateCode", PrivilegeStatusEnum.CODE_98.getCode());
                        map.put("state", false);
                        map.put("retry", true);
                        map.put("leftTimes", TRY_TIMES - times);
                    }
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
        return map;
    }

    @RequestMapping(value = "setPrivilegePassword", method = RequestMethod.GET)
    public String setPrivilegePassword(Model model, SysUserVo sysUserVo) {
        sysUserVo.setValidateRule(JsRuleCreator.create(UpdatePrivilegesPasswordWithoutRemoteForm.class, "result"));
        model.addAttribute("command", sysUserVo);
        return SET_PRIVILEGE_URI;
    }

    @RequestMapping(value = "setPrivilegePassword", method = RequestMethod.POST)
    @ResponseBody
    public Map setPrivilegePassword(SysUserVo sysUserVo, @FormModel("result") @Valid UpdatePrivilegesPasswordWithoutRemoteForm form, BindingResult result) {
        if (result.hasErrors()) {
            return null;
        }
        /*设置id*/
        sysUserVo.getResult().setId(SessionManagerCommon.getUser().getId());
        sysUserVo.setProperties(SysUser.PROP_PERMISSION_PWD);
        /*密码加密*/
        String md5Pwd = authTool.md5SysUserPermission(sysUserVo.getResult().getPermissionPwd(), SessionManagerCommon.getUser().getUsername());
        sysUserVo.getResult().setPermissionPwd(md5Pwd);
        sysUserVo = ServiceToolBase.sysUserService().updateOnly(sysUserVo);
        /*改变session中user的权限密码*/
        if (sysUserVo.isSuccess()) {
            SysUser sysUser = SessionManagerBase.getUser();
            sysUser.setPermissionPwd(sysUserVo.getResult().getPermissionPwd());
            SessionManagerBase.setUser(sysUser);
        }

        return getVoMessage(sysUserVo);
    }

    protected Map getVoMessage(BaseVo baseVo) {
        Map<String, Object> map = new HashMap<>(2);
        if (baseVo.isSuccess() && StringTool.isBlank(baseVo.getOkMsg())) {
            baseVo.setOkMsg(LocaleTool.tranMessage(_Module.COMMON, "save.success"));

        } else if (!baseVo.isSuccess() && StringTool.isBlank(baseVo.getErrMsg())) {
            baseVo.setErrMsg(LocaleTool.tranMessage(_Module.COMMON, "save.failed"));
        }
        map.put("msg", StringTool.isNotBlank(baseVo.getOkMsg()) ? baseVo.getOkMsg() : baseVo.getErrMsg());
        map.put("state", baseVo.isSuccess());
        return map;
    }

    /**
     * 验证吗remote验证
     *
     * @param valiCode
     * @param request
     * @return
     */
    @RequestMapping("/checkValiCode")
    @ResponseBody
    public boolean checkValiCode(@RequestParam("valiCode") String valiCode, HttpServletRequest request) {
        if (StringTool.isEmpty(valiCode)) {
            return false;
        }
        return valiCode.equalsIgnoreCase((String) request.getSession().getAttribute(SessionKey.S_CAPTCHA_PREFIX + CaptchaUrlEnum.CODE_PRIVILEGE.getSuffix()));
    }
}
