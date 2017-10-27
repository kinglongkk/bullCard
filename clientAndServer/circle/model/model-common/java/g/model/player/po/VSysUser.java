package g.model.player.po;

import org.soul.commons.bean.IEntity;
import org.soul.commons.support.Nonpersistent;

import java.util.Date;

/**
 * Created by mark on 16-4-1.
 */
public class VSysUser implements IEntity<Integer> {
    public static final String PROP_ID = "id";
    public static final String PROP_AGENT_NAME = "agentName";
    public static final String PROP_USERNAME = "username";
    public static final String PROP_PASSWORD = "password";
    public static final String PROP_DEPT_ID = "deptId";
    public static final String PROP_STATUS = "status";
    public static final String PROP_CREATE_USER = "createUser";
    public static final String PROP_CREATE_TIME = "createTime";
    public static final String PROP_UPDATE_USER = "updateUser";
    public static final String PROP_UPDATE_TIME = "updateTime";
    public static final String PROP_DEFAULT_LOCALE = "defaultLocale";
    public static final String PROP_DEFAULT_TIMEZONE = "defaultTimezone";
    public static final String PROP_SUBSYS_CODE = "subsysCode";
    public static final String PROP_USER_TYPE = "userType";
    public static final String PROP_BUILT_IN = "builtIn";
    public static final String PROP_SITE_ID = "siteId";
    public static final String PROP_OWNER_ID = "ownerId";
    public static final String PROP_FREEZE_TYPE = "freezeType";
    public static final String PROP_FREEZE_START_TIME = "freezeStartTime";
    public static final String PROP_FREEZE_END_TIME = "freezeEndTime";
    public static final String PROP_FREEZE_CODE = "freezeCode";
    public static final String PROP_LOGIN_TIME = "loginTime";
    public static final String PROP_LOGIN_IP = "loginIp";
    public static final String PROP_LAST_ACTIVE_TIME = "lastActiveTime";
    public static final String PROP_USE_LINE = "useLine";
    public static final String PROP_LAST_LOGIN_TIME = "lastLoginTime";
    public static final String PROP_LAST_LOGIN_IP = "lastLoginIp";
    public static final String PROP_TOTAL_ONLINE_TIME = "totalOnlineTime";
    public static final String PROP_NICKNAME = "nickname";
    public static final String PROP_REAL_NAME = "realName";
    public static final String PROP_BIRTHDAY = "birthday";
    public static final String PROP_SEX = "sex";
    public static final String PROP_CONSTELLATION = "constellation";
    public static final String PROP_COUNTRY = "country";
    public static final String PROP_NATION = "nation";
    public static final String PROP_REGISTER_IP = "registerIp";
    public static final String PROP_AVATAR_URL = "avatarUrl";
    public static final String PROP_PERMISSION_PWD = "permissionPwd";
    public static final String PROP_IDCARD = "idcard";
    public static final String PROP_DEFAULT_CURRENCY = "defaultCurrency";
    public static final String PROP_REGISTER_SITE = "registerSite";
    public static final String PROP_REGION = "region";
    public static final String PROP_CITY = "city";
    public static final String PROP_MEMO = "memo";
    public static final String PROP_PASSWORD_LEVEL = "passwordLevel";
    public static final String PROP_LOGIN_IP_DICT_CODE = "loginIpDictCode";
    public static final String PROP_LAST_LOGIN_IP_DICT_CODE = "lastLoginIpDictCode";
    public static final String PROP_REGISTER_IP_DICT_CODE = "registerIpDictCode";
    public static final String PROP_LOGIN_ERROR_TIMES = "loginErrorTimes";
    public static final String PROP_FREEZE_TITLE = "freezeTitle";
    public static final String PROP_FREEZE_CONTENT = "freezeContent";
    public static final String PROP_LAST_LOGOUT_TIME = "lastLogoutTime";
    public static final String PROP_FREEZE_USER = "freezeUser";
    public static final String PROP_DISABLED_USER = "disabledUser";
    public static final String PROP_DISABLED_TIME = "disabledTime";
    public static final String PROP_FREEZE_TIME = "freezeTime";
    public static final String PROP_ACCOUNT_FREEZE_REMARK = "accountFreezeRemark";
    public static final String PROP_THEME_ID = "theme_id";
    public static final String PROP_REFERRALS = "referrals";
    public static final String PROP_LUCENCY = "lucency";
    public static final String PROP_WALLET_BALANCE = "walletBalance";
    public static final String PROP_ON_LINE_ID = "onLineId";
    public static final String PROP_PLAYER_GROUP_ID = "playerGroupId";
    public static final String KEY = "key";
    public static final String PROP_RECHARGE_TOTAL = "rechargeTotal";
    public static final String PROP_WITHDRAW_TOTAL = "withdrawTotal";
    public static final String PROP_FREEZING_FUNDS_BALANCE = "freezingFundsBalance";


    //endregion


    //region properties
    /** 主键 */
    private Integer id;
    /** 所属账号 */
    private String agentName;
    /** 账号 */
    private String username;
    /** 用户密码 */
    private String password;
    /** 部门 id */
    private Integer deptId;
    /** 状态,枚举:SysUserStatus,[1, 正常],[2, 停用],[3, 冻结(不记录表)],[4, 未激活/未审核],[5,审核失败] */
    private String status;
    /** 创建用户id */
    private Integer createUser;
    /** 创建时间 */
    private java.util.Date createTime;
    /** 更新用户id */
    private Integer updateUser;
    /** 更新时间 */
    private java.util.Date updateTime;
    /** 默认本地化信息：两位小写语言代码_两位大写国家代码 */
    private String defaultLocale;
    /** 默认时区 */
    private String defaultTimezone;
    /** 子系统编号 mcenter 站长子账号 mcenterTopAgent 总代 mcenterAgent 代理 pcenter玩家 */
    private String subsysCode;
    /** 用户类型：0运维，1运营，11运营子账号，2站长，21站长子账号，22总代，221总代子账号，23代理，231代理子账号，24玩家 */
    private String userType;
    /** 是否系统内置 */
    private Boolean builtIn;
    /** 站长ID */
    private Integer siteId;
    /** 所有者id */
    private Integer ownerId;
    /** 冻结类型(1:自动冻结 2:手动冻结) */
    private String freezeType;
    /** 冻结开始时间 */
    private java.util.Date freezeStartTime;
    /** 冻结结束时间,当前时间在冻结结束区间内,状态为:冻结,3000年默认为:永久冻结 */
    private java.util.Date freezeEndTime;
    /** 冻结原因i18代码,关联表:sys_user_freeze_reason */
    private String freezeCode;
    /** 本次登录时间 */
    private java.util.Date loginTime;
    /** 本次登录IP */
    private Long loginIp;
    /** 最后活跃时间 */
    private java.util.Date lastActiveTime;
    /** 本次使用线路 */
    private String useLine;
    /** 上次登录时间 */
    private java.util.Date lastLoginTime;
    /** 上次登录IP */
    private Long lastLoginIp;
    /** 累计在线时长 */
    private Long totalOnlineTime;
    /** 昵称 */
    private String nickname;
    /** 真实姓名 */
    private String realName;
    /** 生日 */
    private java.util.Date birthday;
    /** 性别，字典类型sex(common模块) */
    private String sex;
    /** 星座 */
    private String constellation;
    /** 国家，字典类型country(common模块) */
    private String country;
    /** 民族，字典类型nation(common模块) */
    private String nation;
    /** 注册ip */
    private Long registerIp;
    /** 头像地址 */
    private String avatarUrl;
    /** 权限密码 */
    private String permissionPwd;
    /** 身份证号 */
    private String idcard;
    /** 默认币种代码，字典类型currency(common模块) */
    private String defaultCurrency;
    /** 注册网站地址 */
    private String registerSite;
    /** 省/地区，字典类型province(common模块) */
    private String region;
    /** 城市 */
    private String city;
    /** 备注 */
    private String memo;
    /** 密码级别:高 30, 中,20 低,10 */
    private String passwordLevel;
    /** 登录IP地区字典代码 */
    private String loginIpDictCode;
    /** 上次登录IP地区字典代码 */
    private String lastLoginIpDictCode;
    /** 注册IP地区字典代码 */
    private String registerIpDictCode;
    /** 登录错误次数 */
    private Integer loginErrorTimes;
    /** 账号冻结标题 */
    private String freezeTitle;
    /** 账号冻结内容 */
    private String freezeContent;
    /** 上次退出时间 */
    private java.util.Date lastLogoutTime;
    /** 冻结操作人id */
    private Integer freezeUser;
    /** 停用操作人id */
    private Integer disabledUser;
    /** 停用时间 */
    private java.util.Date disabledTime;
    /** 冻结操作时间 */
    private java.util.Date freezeTime;
    /** 账号冻结备注 */
    private String accountFreezeRemark;
    /** 主题id */
    private Integer themeId;
    /** 介绍人 */
    private String referrals;
    private Integer lucency;
    /** 密钥 */
    private String key;
    /** 钱包余额 */
    private Double walletBalance;
    /** 在线id */
    private Long onLineId;
    /** 玩家分组ID */
    private Integer playerGroupId;
    /** 累计充值总额 */
    private Double rechargeTotal;
    /** 累计提现总额 */
    private Double withdrawTotal;
    /** 冻结资金余额 */
    private Double freezingFundsBalance;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDefaultLocale() {
        return defaultLocale;
    }

    public void setDefaultLocale(String defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    public String getDefaultTimezone() {
        return defaultTimezone;
    }

    public void setDefaultTimezone(String defaultTimezone) {
        this.defaultTimezone = defaultTimezone;
    }

    public String getSubsysCode() {
        return subsysCode;
    }

    public void setSubsysCode(String subsysCode) {
        this.subsysCode = subsysCode;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Boolean getBuiltIn() {
        return builtIn;
    }

    public void setBuiltIn(Boolean builtIn) {
        this.builtIn = builtIn;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getFreezeType() {
        return freezeType;
    }

    public void setFreezeType(String freezeType) {
        this.freezeType = freezeType;
    }

    public Date getFreezeStartTime() {
        return freezeStartTime;
    }

    public void setFreezeStartTime(Date freezeStartTime) {
        this.freezeStartTime = freezeStartTime;
    }

    public Date getFreezeEndTime() {
        return freezeEndTime;
    }

    public void setFreezeEndTime(Date freezeEndTime) {
        this.freezeEndTime = freezeEndTime;
    }

    public String getFreezeCode() {
        return freezeCode;
    }

    public void setFreezeCode(String freezeCode) {
        this.freezeCode = freezeCode;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Long getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(Long loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(Date lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    public String getUseLine() {
        return useLine;
    }

    public void setUseLine(String useLine) {
        this.useLine = useLine;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Long getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(Long lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Long getTotalOnlineTime() {
        return totalOnlineTime;
    }

    public void setTotalOnlineTime(Long totalOnlineTime) {
        this.totalOnlineTime = totalOnlineTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public Long getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(Long registerIp) {
        this.registerIp = registerIp;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPermissionPwd() {
        return permissionPwd;
    }

    public void setPermissionPwd(String permissionPwd) {
        this.permissionPwd = permissionPwd;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(String defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public String getRegisterSite() {
        return registerSite;
    }

    public void setRegisterSite(String registerSite) {
        this.registerSite = registerSite;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPasswordLevel() {
        return passwordLevel;
    }

    public void setPasswordLevel(String passwordLevel) {
        this.passwordLevel = passwordLevel;
    }

    public String getLoginIpDictCode() {
        return loginIpDictCode;
    }

    public void setLoginIpDictCode(String loginIpDictCode) {
        this.loginIpDictCode = loginIpDictCode;
    }

    public String getLastLoginIpDictCode() {
        return lastLoginIpDictCode;
    }

    public void setLastLoginIpDictCode(String lastLoginIpDictCode) {
        this.lastLoginIpDictCode = lastLoginIpDictCode;
    }

    public String getRegisterIpDictCode() {
        return registerIpDictCode;
    }

    public void setRegisterIpDictCode(String registerIpDictCode) {
        this.registerIpDictCode = registerIpDictCode;
    }

    public Integer getLoginErrorTimes() {
        return loginErrorTimes;
    }

    public void setLoginErrorTimes(Integer loginErrorTimes) {
        this.loginErrorTimes = loginErrorTimes;
    }

    public String getFreezeTitle() {
        return freezeTitle;
    }

    public void setFreezeTitle(String freezeTitle) {
        this.freezeTitle = freezeTitle;
    }

    public String getFreezeContent() {
        return freezeContent;
    }

    public void setFreezeContent(String freezeContent) {
        this.freezeContent = freezeContent;
    }

    public Date getLastLogoutTime() {
        return lastLogoutTime;
    }

    public void setLastLogoutTime(Date lastLogoutTime) {
        this.lastLogoutTime = lastLogoutTime;
    }

    public Integer getFreezeUser() {
        return freezeUser;
    }

    public void setFreezeUser(Integer freezeUser) {
        this.freezeUser = freezeUser;
    }

    public Integer getDisabledUser() {
        return disabledUser;
    }

    public void setDisabledUser(Integer disabledUser) {
        this.disabledUser = disabledUser;
    }

    public Date getDisabledTime() {
        return disabledTime;
    }

    public void setDisabledTime(Date disabledTime) {
        this.disabledTime = disabledTime;
    }

    public Date getFreezeTime() {
        return freezeTime;
    }

    public void setFreezeTime(Date freezeTime) {
        this.freezeTime = freezeTime;
    }

    public String getAccountFreezeRemark() {
        return accountFreezeRemark;
    }

    public void setAccountFreezeRemark(String accountFreezeRemark) {
        this.accountFreezeRemark = accountFreezeRemark;
    }

    public Integer getThemeId() {
        return themeId;
    }

    public void setThemeId(Integer themeId) {
        this.themeId = themeId;
    }

    public String getReferrals() {
        return referrals;
    }

    public void setReferrals(String referrals) {
        this.referrals = referrals;
    }

    public Integer getLucency() {
        return lucency;
    }

    public void setLucency(Integer lucency) {
        this.lucency = lucency;
    }

    public Double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(Double walletBalance) {
        this.walletBalance = walletBalance;
    }

    public Long getOnLineId() {
        return onLineId;
    }

    public void setOnLineId(Long onLineId) {
        this.onLineId = onLineId;
    }

    public Integer getPlayerGroupId() {
        return playerGroupId;
    }

    public void setPlayerGroupId(Integer playerGroupId) {
        this.playerGroupId = playerGroupId;
    }

    public Double getRechargeTotal() {
        return rechargeTotal;
    }

    public void setRechargeTotal(Double rechargeTotal) {
        this.rechargeTotal = rechargeTotal;
    }

    public Double getWithdrawTotal() {
        return withdrawTotal;
    }

    public void setWithdrawTotal(Double withdrawTotal) {
        this.withdrawTotal = withdrawTotal;
    }

    public Double getFreezingFundsBalance() {
        return freezingFundsBalance;
    }

    public void setFreezingFundsBalance(Double freezingFundsBalance) {
        this.freezingFundsBalance = freezingFundsBalance;
    }

    private static final String PLAYER_STATUS_ACCOUNT_FREEZE = "3";
    private static final String PLAYER_STATUS_DISABLED = "2";
    private static final String PLAYER_STATUS_ENABLE = "1";

    @Nonpersistent
    public String getPlayerStatus() {
        //account freeze status
        Date freezeStartTime = this.getFreezeStartTime();
        Date freezeEndTime = this.getFreezeEndTime();
        if (freezeStartTime != null && freezeEndTime != null) {
            Date now = new Date();
            if (freezeEndTime.after(now) && freezeStartTime.before(now)) {
                if (PLAYER_STATUS_DISABLED.equals(status)) {
                    return PLAYER_STATUS_DISABLED;
                }else{
                    return PLAYER_STATUS_ACCOUNT_FREEZE;
                }
            }
        }
        //money freeze status
        String status = this.getStatus();
        if (status == null) {
            return PLAYER_STATUS_ENABLE;
        }
        if (PLAYER_STATUS_ENABLE.equals(status)) {
            return PLAYER_STATUS_ENABLE;
        } else if (PLAYER_STATUS_DISABLED.equals(status)) {
            return PLAYER_STATUS_DISABLED;
        }

        return PLAYER_STATUS_ENABLE;
    }

    /*是否账户冻结*/
    private boolean accountfreeze;

    @Nonpersistent
    public boolean isAccountfreeze() {
        return this.getFreezeEndTime() == null ? false:this.getFreezeEndTime().getTime() > new Date().getTime();
    }

    //endregion
}
