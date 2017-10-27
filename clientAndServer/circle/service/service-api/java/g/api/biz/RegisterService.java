package g.api.biz;

import g.api.base.BaseService;
import g.api.service.IGameRegisterService;
import g.common.security.AuthTool;
import g.model.StatusEnum;
import g.model.SubSysCodeEnum;
import g.model.UserTypeEnum;
import g.model.admin.agent.message.po.UserAgent;
import g.model.admin.nickname.vo.UserNicknameListVo;
import g.model.agent.vo.UserPlayerGroupVo;
import g.model.api.enums.ResultStatusEnums;
import g.model.api.param.Params;
import g.model.api.param.RegisterParams;
import g.model.api.result.RegisterResults;
import g.model.api.result.Results;
import g.model.common.RegExpConstants;
import g.model.player.po.UserPlayer;
import g.model.player.vo.UserPlayerVo;
import g.service.admin.nickname.UserNicknameService;
import g.service.avatarurl.UserAvatarUrlService;
import g.service.common.UserPlayerGroupService;
import g.service.common.UserPlayerService;
import org.soul.commons.lang.string.RandomStringTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.model.security.privilege.vo.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.regex.Pattern;

public class RegisterService extends BaseService implements IGameRegisterService {

    private final Integer USERNAME_MAX_LEN = 20 ;
    private final Integer USERNAME_MIN_LEN = 6 ;
    private final Integer PASSWORD_MIN_LEN = 7 ;
    private final Integer PASSWORD_MAX_LEN = 20 ;

    @Autowired
    private AuthTool authTool;

    @Autowired
    private UserPlayerGroupService userPlayerGroupService;

    @Autowired
    private UserPlayerService userPlayerService;

    @Autowired
    private UserNicknameService nicknameService;

    @Autowired
    private UserAvatarUrlService userAvatarUrlService;

    @Override
    @Transactional
    public Results register(RegisterParams params) {

        RegisterResults result = new RegisterResults();
        if (!validatePreConditions(params)) {
            throwBusinessException(ResultStatusEnums.USER_OR_PASSWORD_INVALID);
        }
        this.log.info("注册用户：{0}", params.getUserAccount());
        UserAgent userAgent = getUserAgent(params.getMerchantNo());
        if (userAgent == null) {
            throwBusinessException(ResultStatusEnums.AGENT_NOT_EXISTS);
        }
        SysUser agentUser = getSysAgent(userAgent.getAgentId());
        if (agentUser == null) {
            throwBusinessException(ResultStatusEnums.AGENT_NOT_EXISTS);
        }
        //player不存在，添加前缀
        String playerAccount = getPlayerAccount(userAgent.getId(), params.getUserAccount());
        SysUser player = getPlayer(playerAccount, userAgent.getAgentId());
        if (player != null){
            throwBusinessException(ResultStatusEnums.USER_EXISTS);
        }
        params.setUserAccount(playerAccount);
        SysUserVo sysUserVo = new SysUserVo();
        sysUserVo = saveSysUser(params, sysUserVo, agentUser);
        if (!sysUserVo.isSuccess()) {
            throwBusinessException(ResultStatusEnums.OTHER_ERR);
        }
        return result;
    }

    @Override
    public boolean validatePreConditions(Params params) {

        RegisterParams registerParam = (RegisterParams)params;
        boolean nickname;
        if (StringTool.isEmpty(registerParam.getNickname())) {
            nickname = true;
        } else {
            nickname = Pattern.matches(RegExpConstants.NICK_NAME, registerParam.getNickname());
        }
        if (StringTool.isNotEmpty(registerParam.getUserAccount())
                && StringTool.isNotEmpty(registerParam.getUserPwd())
                && registerParam.getUserAccount().length() <= USERNAME_MAX_LEN
                && registerParam.getUserAccount().length() >= USERNAME_MIN_LEN
                && registerParam.getUserPwd().length() <= PASSWORD_MAX_LEN
                && registerParam.getUserPwd().length() >= PASSWORD_MIN_LEN
                && Pattern.matches(RegExpConstants.LOGIN_USERNAME,registerParam.getUserAccount())
                && Pattern.matches(RegExpConstants.LOGIN_PWD,registerParam.getUserPwd())
                && nickname){
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    private SysUserVo saveSysUser(RegisterParams params, SysUserVo sysUserVo ,SysUser agentUser) {

        SysUser result = new SysUser();
        result.setStatus(StatusEnum.NORMAL.getCode());
        result.setUsername(params.getUserAccount());
        result.setPassword(authTool.md5SysUserPassword(params.getUserPwd(), params.getUserAccount()));
        result.setDefaultLocale(agentUser.getDefaultLocale());
        result.setDefaultTimezone(agentUser.getDefaultTimezone());
        String nickname = params.getNickname();
        //若昵称为空，则在昵称库里面随机分配一个昵称
        if (StringTool.isBlank(nickname)) {
            while (StringTool.isBlank(nickname)) {
                nickname = nicknameService.selectUserNickname();
                if (StringTool.isNotBlank(nickname)) {
                    result.setNickname(nickname);
                    break;
                }
            }
        } else {
            UserNicknameListVo listVo = new UserNicknameListVo();
            listVo.getSearch().setNickname(nickname);
            listVo = nicknameService.search(listVo);
            //若是昵称库里面没有，则添加进昵称库
            if (listVo.getResult().isEmpty()) {
                nicknameService.addNewNickname(nickname);
            }
        }
        result.setNickname(nickname);
        result.setBuiltIn(false);
        result.setSubsysCode(SubSysCodeEnum.PLAYER.getCode());
        result.setUserType(UserTypeEnum.PLAYER.getCode());
        result.setCreateTime(new Date());
        result.setCreateUser(agentUser.getId());
        result.setOwnerId(agentUser.getId());
        result.setSiteId(siteId);
        //设置用户头像
        String headImage = null;
        while (headImage == null) {
            headImage = userAvatarUrlService.selectUserAvatarUrl();
            if (StringTool.isNotBlank(headImage)) {
                result.setAvatarUrl(headImage);
               break;
            }
        }
        sysUserVo.setResult(result);
        SysUserVo player = this.sysUserService.insert(sysUserVo);
        UserPlayerGroupVo userPlayerGroupVo = new UserPlayerGroupVo();
        //default admin id is 0
        userPlayerGroupVo.getSearch().setCreateUser(0);
        userPlayerGroupVo = userPlayerGroupService.getDefaultGroup(userPlayerGroupVo);
        Integer defaultGroupId = userPlayerGroupVo.getResult() != null ? userPlayerGroupVo.getResult().getId() : null;
        //玩家信息扩展，设置默认值
        UserPlayerVo userPlayerVo = new UserPlayerVo();
        UserPlayer userPlayer = new UserPlayer();
        userPlayer.setId(player.getResult().getId());
        userPlayer.setWalletBalance(0d);
        userPlayer.setRechargeCount(0);
        userPlayer.setRechargeTotal(0d);
        userPlayer.setRechargeMaxAmount(0d);
        userPlayer.setWithdrawCount(0);
        userPlayer.setWithdrawTotal(0d);
        userPlayer.setFreezingFundsBalance(0d);
        userPlayer.setPlayerGroupId(defaultGroupId);
        userPlayer.setInvitationCode(RandomStringTool.randomAlphanumeric(8));
        userPlayer.setIsAi(false);
        userPlayerVo.setResult(userPlayer);
        userPlayerService.insert(userPlayerVo);
        return player;
    }

}
