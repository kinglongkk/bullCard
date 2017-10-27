package g.web.player.controller;

import g.common.security.AuthTool;
import g.model.SubSysCodeEnum;
import g.model.UserTypeEnum;
import g.model.activityapply.po.ActivityPlayerApply;
import g.model.activityapply.vo.ActivityPlayerApplyVo;
import g.model.activitymessage.po.ActivityWayRelation;
import g.model.activitymessage.vo.ActivityMessageVo;
import g.model.common.Const;
import g.model.common.RegExpConstants;
import g.model.enums.ActivityTypeEnum;
import g.model.player.po.UserPlayer;
import g.model.player.vo.UserPlayerVo;
import g.service.activityapply.IActivityPlayerApplyService;
import g.service.activitymessage.IActivityMessageService;
import g.service.activitymessage.IActivityWayRelationService;
import g.service.common.IUserPlayerService;
import g.service.player.IRegisterService;
import org.soul.commons.init.context.CommonContext;
import org.soul.commons.lang.string.RandomStringTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.query.Criterion;
import org.soul.commons.query.enums.Operator;
import org.soul.iservice.security.privilege.ISysUserService;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.model.security.privilege.po.SysUserStatus;
import org.soul.model.security.privilege.vo.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by longer on 15-4-29.
 */
//@Controller
//@RequestMapping("/passport/register")
public class RegisterController {

    @Autowired
    private AuthTool authTool;

    private static final String INDEX_URI = "index";

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private IRegisterService registerService;

    @Autowired
    private IUserPlayerService userPlayerService;

    @Autowired
    private IActivityMessageService activityMessageService;

    @Autowired
    private IActivityWayRelationService activityWayRelationService;

    @Autowired
    private IActivityPlayerApplyService activityPlayerApplyService;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "/passport/register";
    }

    //注册
    @RequestMapping(method = RequestMethod.POST)
    public String register(@RequestParam(required = false,value = "invitationCode")String invitationCode,
                           SysUser sysUser, HttpServletRequest request) {
         //获取参数
        String userName = sysUser.getUsername();
        String userPassword = sysUser.getPassword();

        //参数回显
        request.setAttribute("userName", userName);

        //将获取的参数进行验证
        if(!(verifyPlayer(userName, userPassword))){
            request.setAttribute("result","输入有误，请重新输入");
            return "/passport/register";
        }

        //判断用户名是否存在
        if (sysUserService.isExists(getSearchVo(userName))){
                request.setAttribute("result","该用户已存在");
                return "/passport/register";
        }

        String registerSite = sysUser.getRegisterSite();
        Integer agentId = Const.DEFAULT_AGENT_ID;
        if (StringTool.isNotBlank(registerSite)){
            SysUserVo sysUserVo = new SysUserVo();
            sysUserVo.getSearch().setRegisterSite(registerSite);
            sysUserVo.getQuery().setCriterions(
                    new Criterion[] {
                            new Criterion(SysUser.PROP_REGISTER_SITE, Operator.EQ,registerSite)
                    }
            );
            sysUserVo = sysUserService.search(sysUserVo);
            if (sysUserVo.getResult() != null) {
                agentId = sysUserVo.getResult().getId();
            }
        }

        String avatarUrl = "images/head/comm_1_icon_head_0"+ RandomStringTool.randomNumeric(1)+".jpg";

        SysUserVo sysUserVo = getPo(userName, userPassword, agentId, avatarUrl);
        registerService.register(sysUserVo);

        checkCapacityDNShareActivity(invitationCode,sysUserVo.getResult().getId());

        request.setAttribute("result", "注册成功请登录");
        return "/passport/login";

    }

    //进行验证的方法(使用正则表达式)
    private boolean verifyPlayer(String userName,String userPassword){
        if (StringTool.isBlank(userName)) {
            throw new IllegalArgumentException("用户名不能为空！");
        }
        if (StringTool.isBlank(userPassword)){
            throw new IllegalArgumentException("登录密码不能为空!");
        }
        Pattern pCellPhone = Pattern.compile(RegExpConstants.CELL_PHONE);
        Matcher mCellPhone = pCellPhone.matcher(userName);
        Pattern pSecurityPwd = Pattern.compile(RegExpConstants.LOGIN_PWD);
        Matcher mSecurityPwd = pSecurityPwd.matcher(userPassword);
        if(!(mCellPhone.find() && mSecurityPwd.find())){
            return false;
        }
        return true;
    }

    //获取SysUserVo对象(只包含userName属性)
    private SysUserVo getSearchVo(String userName) {
        SysUserVo sysUserVo = new SysUserVo();
        sysUserVo.getSearch().setUsername(userName);
        sysUserVo.getSearch().setSiteId(CommonContext.get().getSiteId());
        sysUserVo.getSearch().setSubsysCode(SubSysCodeEnum.PLAYER.getCode());
        return sysUserVo;
    }

    //获取SysUserVo对象(包含userName和userPassword属性)
    private SysUserVo getPo(String userName, String userPassword, Integer agentId, String avatarUrl){
        SysUserVo sysUserVo = new SysUserVo();
        SysUser sysUser = new SysUser();
        sysUserVo.setResult(sysUser);
        sysUserVo.getResult().setUsername(userName);
        sysUserVo.getResult().setPassword(authTool.md5SysUserPassword(userPassword,userName));
        sysUserVo.getResult().setOwnerId(agentId);
        sysUserVo.getResult().setSiteId(CommonContext.get().getSiteId());
        sysUserVo.getResult().setSubsysCode(SubSysCodeEnum.PLAYER.getCode());
        sysUserVo.getResult().setBuiltIn(Boolean.FALSE);
        sysUserVo.getResult().setDefaultLocale(Const.Default_Locale.toString());
        sysUserVo.getResult().setDefaultTimezone(Const.TimeZone_BJ.getID());
        sysUserVo.getResult().setStatus(SysUserStatus.NORMAL.getCode());
        sysUserVo.getResult().setUserType(UserTypeEnum.PLAYER.getCode());
        sysUserVo.getResult().setCreateTime(new Date());
        sysUserVo.getResult().setAvatarUrl(avatarUrl);
        return sysUserVo;
    }

    private UserPlayerVo getUserPlayerVo(String invitationCode){
        UserPlayerVo userPlayerVo = new UserPlayerVo();
        userPlayerVo.getQuery().setCriterions(new Criterion[]{
            new Criterion(UserPlayer.PROP_INVITATION_CODE,Operator.EQ,invitationCode)
        });
        return userPlayerService.search(userPlayerVo);
    }

    private SysUserVo getSysUserVo(Integer userId){
        SysUserVo sysUserVo = new SysUserVo();
        sysUserVo.getSearch().setId(userId);
        return sysUserService.get(sysUserVo);
    }

    private void checkCapacityDNShareActivity(String invitationCode,Integer relationUserId){
        UserPlayerVo userPlayerVo = getUserPlayerVo(invitationCode);
        if(userPlayerVo.getResult() != null && userPlayerVo.getResult().getId() != null){
            int userId = userPlayerVo.getResult().getId();
            SysUserVo sysUserVo = getSysUserVo(userId);
            ActivityWayRelation activityWayRelation = activityWayRelationService.checkCapacityDNShareActivity(userId);
            if(activityWayRelation != null){

                ActivityMessageVo activityMessageVo = new ActivityMessageVo();
                activityMessageVo.getSearch().setId(activityWayRelation.getActivityMessageId());
                activityMessageVo = activityMessageService.get(activityMessageVo);

                ActivityPlayerApplyVo activityPlayerApplyVo = new ActivityPlayerApplyVo();
                ActivityPlayerApply activityPlayerApply = new ActivityPlayerApply();
                activityPlayerApply.setActivityTypeCode(ActivityTypeEnum.SHARE.getCode());
                activityPlayerApply.setActivityMessageId(activityMessageVo.getResult().getId());
                activityPlayerApply.setUserName(sysUserVo.getResult().getUsername());
                activityPlayerApply.setApplyTime(new Date());
                activityPlayerApply.setIsRealize(false);
                activityPlayerApply.setUserId(userId);
                activityPlayerApply.setRegisterTime(sysUserVo.getResult().getCreateTime());
                activityPlayerApply.setBalanceStartTime(activityMessageVo.getResult().getStartTime());
                activityPlayerApply.setBalanceEndTime(activityMessageVo.getResult().getEndTime());
                activityPlayerApply.setRelationPlayerId(relationUserId);
                activityPlayerApplyVo.setResult(activityPlayerApply);
                activityPlayerApplyVo.setProperties(
                        ActivityPlayerApply.PROP_ID,
                        ActivityPlayerApply.PROP_STARTTIME,
                        ActivityPlayerApply.PROP_ENDTIME,
                        ActivityPlayerApply.PROP_ACTIVITY_CLASSIFY_KEY,
                        ActivityPlayerApply.PROP_IS_DELETED
                );
                activityPlayerApplyService.insertExclude(activityPlayerApplyVo);
            }
        }
    }

}
