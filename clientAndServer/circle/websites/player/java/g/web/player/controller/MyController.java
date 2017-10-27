package g.web.player.controller;

import g.common.security.AuthTool;
import g.model.admin.po.BetLimit;
import g.model.admin.so.VUserGroupBetLimitSo;
import g.model.bet.BetTypeEnum;
import g.model.cache.CacheKey;
import g.model.common.Const;
import g.model.common.RegExpConstants;
import g.model.common.userbankcard.po.UserBankcard;
import g.model.common.userbankcard.vo.UserBankcardListVo;
import g.model.common.userbankcard.vo.UserBankcardVo;
import g.model.enums.BankEnum;
import g.model.enums.BankTypeEnum;
import g.model.payaccount.po.Bank;
import g.model.payaccount.vo.BankListVo;
import g.model.player.po.UserPlayer;
import g.model.player.po.VSysUser;
import g.model.player.vo.VSysUserVo;
import g.service.bet.IBetLimitUserGroupService;
import g.service.common.IUserPlayerService;
import g.service.common.VSysUserService;
import g.service.payaccount.IBankService;
import g.service.player.IUserBankcardService;
import g.web.player.session.SessionManager;
import org.soul.commons.cache.CacheTool;
import org.soul.commons.collections.CollectionTool;
import org.soul.commons.currency.CurrencyTool;
import org.soul.commons.lang.DateTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.net.IpTool;
import org.soul.data.mapper.security.privilege.SysUserMapper;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.model.security.privilege.vo.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by longer on 7/23/16.
 */
@Controller
@RequestMapping("/my")
public class MyController {

    @Autowired
    private AuthTool authTool;

    @Autowired
    private CacheTool cacheTool;

    @Autowired
    private VSysUserService vSysUserService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private IBankService bankService;

    @Autowired
    private IUserBankcardService userBankcardService;

    @Autowired
    private IUserPlayerService userPlayerService;

    @Autowired
    private IBetLimitUserGroupService betLimitUserGroupService;

    @RequestMapping("/balance")
    @ResponseBody
    public Map balance(VSysUserVo vo) {
        vo.getSearch().setId(SessionManager.getUserId());
        vo = vSysUserService.get(vo);

        String balance = CurrencyTool.formatCurrency(vo.getResult().getWalletBalance() == null ? 0 : vo.getResult().getWalletBalance());
        Map<String, Object> map = new HashMap<>();
        map.put(VSysUser.PROP_USERNAME, vo.getResult().getUsername());
        map.put(VSysUser.PROP_WALLET_BALANCE, balance);

        //显示我的邀请码
        UserPlayer userPlayer = userPlayerService.selectUserPlayerInfoById(SessionManager.getUserId());
        map.put("invitationCode", userPlayer.getInvitationCode());
        return map;
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "app/my/My";
    }

    @RequestMapping(value = "/account", method = RequestMethod.POST)
    @ResponseBody
    public Map account(VSysUserVo vo) {
        vo.getSearch().setId(SessionManager.getUserId());
        vo = vSysUserService.get(vo);
        Map<String, Object> map = new HashMap<>();
        map.put(VSysUser.PROP_USERNAME, vo.getResult().getUsername());
        map.put(VSysUser.PROP_LAST_LOGIN_IP,
                vo.getResult().getLastLoginIp() != null ? IpTool.ipv4LongToString(vo.getResult().getLastLoginIp()) : "");
        map.put(VSysUser.PROP_LAST_LOGIN_TIME,
                vo.getResult().getLastLoginTime() != null ? DateTool.formatDate(vo.getResult().getLastLoginTime(), Const.Default_Locale, Const.TimeZone_BJ, DateTool.FMT_HYPHEN_DAY_CLN_SECOND) : "");
        return map;
    }

    /**
     * 个人取款资料
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/banks", method = RequestMethod.POST)
    @ResponseBody
    public Map banks(VSysUserVo vo) {
        Map<String, Object> map = new HashMap<>();
        UserBankcardListVo userBankcardListVo = new UserBankcardListVo();
        userBankcardListVo.getSearch().setUserId(SessionManager.getUserId());
        List<UserBankcard> bankcards = userBankcardService.byUserIdAndIsDefault(userBankcardListVo);
        if (bankcards.size() != defaultBankTypes.size()) {
            Map<String, UserBankcard> userBankcardMap = CollectionTool.toEntityMap(bankcards, UserBankcard.PROP_BANK_TYPE, String.class);

            Collection<String> lost = CollectionTool.subtract(defaultBankTypes, userBankcardMap.keySet());
            for (String lostBankType : lost) {
                UserBankcard userBankcard = new UserBankcard();
                userBankcard.setBankType(lostBankType);
                bankcards.add(userBankcard);
            }
        }
        for (UserBankcard bankcard : bankcards) {
            bankcard.setBankTypeDesc(BankTypeEnum.enumOf(bankcard.getBankType()).getTrans());
            transBankName(bankcard);
        }
        map.put("banks", bankcards);
        return map;
    }

    private void transBankName(UserBankcard userBankcard) {
        if (userBankcard != null && StringTool.isNotBlank(userBankcard.getBankName())) {
            Map<String, Bank> banCache = cacheTool.get(CacheKey.CACHE_KEY_BANK);
            Bank bank = banCache.get(userBankcard.getBankName());
            if (bank != null) {
                userBankcard.setBankNameDesc(bank.getBankShortName2());
            }
        }
    }

    /**
     * 指定可用的取款类型
     */
    @RequestMapping(value = "/bank/info", method = RequestMethod.POST)
    @ResponseBody
    public Map bankInfo(String bankType) {
        String confirmKey = SessionManager.getConfirmKey();
        Map<String, Object> map = new HashMap<>();
        map.put("bankType", bankType);
        map.put("confirmKey", confirmKey);
        map.put("bankTypeDesc", BankTypeEnum.enumOf(bankType).getTrans());
        if (BankTypeEnum.BANK.getCode().equals(bankType)) {
            BankListVo bankListVo = new BankListVo();
            bankListVo.getSearch().setIsUse(true);
            bankListVo.getSearch().setType(BankEnum.TYPE_BANK.getCode());
            bankListVo.setProperties(Bank.PROP_ID, Bank.PROP_BANK_NAME, Bank.PROP_BANK_ICON, Bank.PROP_BANK_SHORT_NAME);
            List<Map<String, Object>> properties = bankService.searchProperties(bankListVo);
            map.put("bankList", properties);
        }
        return map;
    }

    /**
     * 获取用户账号
     */
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    @ResponseBody
    public Map password() {
        String userName = SessionManager.getUserName();
        Map map = new HashMap();
        map.put("username", userName);
        return map;
    }

    /**
     * 修改用户密码
     */
    @RequestMapping(value = "/bank/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public Map updatePassword(String oldPassword, String newPassword) {
        SysUser sysUser = SessionManager.getUser();
        SysUserVo sysUserVo = new SysUserVo();
        sysUserVo.setResult(sysUser);
        String username = sysUserVo.getResult().getUsername();
        String confirmPassword = sysUserVo.getResult().getPassword();
        String confirmPassword2 = authTool.md5SysUserPassword(oldPassword, username);
        String confirmNewPassword = authTool.md5SysUserPassword(newPassword, username);
        Pattern pSecurityPwd = Pattern.compile(RegExpConstants.LOGIN_PWD);
        Matcher mSecurityPwd = pSecurityPwd.matcher(newPassword);
        Map<String, Object> map = new HashMap<>();
        if (!(confirmPassword.equals(confirmPassword2))) {
            map.put("message", "密码错误,请输入正确的密码！");
            map.put("confirmOK", false);
            return map;
        }
        if (confirmPassword.equals(confirmNewPassword)) {
            map.put("message", "新密码与旧密码一致,请输入新密码！");
            map.put("confirmOK", false);
            return map;
        }
        if (!(mSecurityPwd.find())){
            map.put("message", "新密码输入有误！");
            map.put("confirmOK", false);
            return map;
        }else {
            //更新
            sysUser.setPassword(confirmNewPassword);
            boolean flag = sysUserMapper.updateOnly(sysUser,SysUser.PROP_PASSWORD);
            if (flag) {
                map.put("confirmOK", true);
                return map;
            }
        }
        return null;
    }

    /**
     * 设置取款资料之前进行密码验证
     */
    @RequestMapping(value = "/bank/confirm",method = RequestMethod.POST)
    @ResponseBody
    public Map bankConfirm(String password){
        SysUser sysUser = SessionManager.getUser();
        SysUserVo sysUserVo = new SysUserVo();
        sysUserVo.setResult(sysUser);
        String username = sysUserVo.getResult().getUsername();
        String confirmPassword = sysUserVo.getResult().getPassword();
        String confirmPassword2 = authTool.md5SysUserPassword(password,username);
        Map<String, Object> map = new HashMap<>();
        if (confirmPassword.equals(confirmPassword2)){
            map.put("confirmOK", true);
        }else {
            map.put("message","密码错误,请输入正确的密码?");
            map.put("confirmOK", false);
        }
        return map;
    }

    /**
     * 生成个验证需要的key
     */
    @RequestMapping(value = "/bank/haveKey",method = RequestMethod.POST)
    @ResponseBody
    public Map haveConfirmKey(){
        Map map = new HashMap();
        String confirmKey = Math.random()+"";
        SessionManager.setConfirmKey(confirmKey);
        map.put("success",true);
        return map;
    }

    /**
     * 所有可用的取款类型
     */
    @RequestMapping(value = "/bank/set",method = RequestMethod.POST)
    @ResponseBody
    public Map bankSet(UserBankcard userBankcard,String confirmKey){
        userBankcard.setUserId(SessionManager.getUserId());
        UserBankcardVo userBankcardVo = new UserBankcardVo();
        userBankcardVo.setResult(userBankcard);
        if (!BankTypeEnum.BANK.getCode().equals(userBankcard.getBankType())) {
            userBankcard.setBankcardMasterName(userBankcard.getBankcardNumber());//为应对此字段非空
        }
        String confirmKey2 = SessionManager.getConfirmKey();
        int rs =userBankcardService.saveWhenNotExist(userBankcardVo);
        Map<String, Object> map = new HashMap<>(2);
        if (confirmKey == null && confirmKey2 == null){
            map.put("message","非法操作");
            map.put("state", false);
        }
        if (rs > 0 && (confirmKey2.equals(confirmKey))) {
            map.put("state", true);
        } else {
            map.put("message","绑定失败,请确认是否已经绑定?");
            map.put("state", false);
        }
        return map;
    }


    private static Set<String> defaultBankTypes = new HashSet<>();
    static {
        defaultBankTypes.add(BankTypeEnum.ALIPAY.getCode());
        defaultBankTypes.add(BankTypeEnum.WECHAT.getCode());
        defaultBankTypes.add(BankTypeEnum.BANK.getCode());
    }


    @RequestMapping(value = "/betLimit",method = RequestMethod.POST)
    @ResponseBody
    public Map betLimit(VSysUserVo vo) {
        vo = new VSysUserVo();
        vo.getSearch().setId(SessionManager.getUser().getId());
        vo = vSysUserService.get(vo);
        Integer groupId = vo.getResult().getPlayerGroupId();
        Map<String,VUserGroupBetLimitSo> vUserGroupBetLimitMap = CacheTool.get(CacheKey.CK_PLAYER_GROUP_LIMIT);

        //TODO:from cache
        List<BetLimit> betLimits = new ArrayList<>(3);
        for (BetTypeEnum betTypeEnum : BetTypeEnum.values()) {
            BetLimit betLimit = new BetLimit();
            betLimit.setBetType(betTypeEnum.getTrans());
            VUserGroupBetLimitSo vUserGroupBetLimitSo = vUserGroupBetLimitMap.get(CacheKey.getCacheKey(String.valueOf(groupId), betTypeEnum.getCode()));
            betLimit.setBetMin(vUserGroupBetLimitSo.getBetMin());
            betLimit.setBetMax(vUserGroupBetLimitSo.getBetMax());
            betLimit.setItemMax(vUserGroupBetLimitSo.getItemMax());
            betLimits.add(betLimit);
        }

        Map<String,Object> map = new HashMap<>();
        map.put("betLimits",betLimits);
        return map;
    }


}
