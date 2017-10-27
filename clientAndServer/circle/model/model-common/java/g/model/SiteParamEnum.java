package g.model;

import org.soul.commons.param.IParamEnum;
import org.soul.commons.support.IModule;

/**
 * Created by longer on 7/1/15.
 */
public enum SiteParamEnum implements IParamEnum {
    SETTING_PRIVILAGE_PASS_TIME(Module.MASTER_SETTING,"privilage_pass_time","setting.privilage.pass.time"),//密码权限时效性设置
    SETTING_VISIT_MANAGEMENT_CENTER_STATUS(Module.MASTER_SETTING,"visit","visit.management.center"),//是否开启允许访问管理中心的IP
    SETTING_VISIT_SITE_STATUS(Module.MASTER_SETTING,"visit","visit.site"),//是否开启允许访问站点的IP
    SETTING_CAPTCHA_STYLE(Module.MASTER_SETTING,"captcha","style"),//验证码
    SETTING_CAPTCHA_EXCLUSIONS(Module.MASTER_SETTING,"captcha","exclusions"),//验证码排除内容

    WARMING_TONE_NOTICE(Module.MASTER_SETTING,"warming_tone_project","notice"),
    WARMING_TONE_WARM(Module.MASTER_SETTING,"warming_tone_project","warm"),
    SYS_TONE_WARM(Module.MASTER_SETTING,"sys_tone_warm",""),
    SYS_TONE_NOTICE(Module.MASTER_SETTING,"sys_tone_notice",""),
    SYS_BET_MIN(Module.MASTER_SETTING,"sys_bet_limit","sys_bet_min"),
    SYS_WIN_MAX(Module.MASTER_SETTING,"sys_bet_limit","sys_win_max"),
    YELLOW(Module.GAME,"warming_bet_money","yellow"),
    RED(Module.GAME,"warming_bet_money","red"),
    MIN_WITHDRAW_AMOUNT(Module.FUND,"withdraw","minWithdrawAmount"),
    MAX_WECHAT_WITHDRAW_AMOUNT(Module.FUND,"withdraw","maxWechatWithdrawAmount"),
    MAX_ALIPAY_WITHDRAW_AMOUNT(Module.FUND,"withdraw","maxAlipayWithdrawAmount"),
    MAX_BANK_WITHDRAW_AMOUNT(Module.FUND,"withdraw","maxBankWithdrawAmount"),
    WITHDRAW_DATA_HOURS(Module.FUND,"withdraw","dataHours"),
    TODO_WITHDRAW_SIZE(Module.FUND,"withdraw","todoWithdrawSize"),
    TODO_DEPOSIT_SIZE(Module.FUND,"deposit","todoDepositSize"),
    DEPOSIT_DATA_HOURS(Module.FUND,"deposit","dataHours"),
    MIN_DEPOSIT_AMOUNT(Module.FUND,"deposit","minDepositAmount"),
    FIRST_RECHARGE_AMOUNT(Module.FUND,"deposit","firstRechargeAmount"),
    RAKEBACK_POINT(Module.FUND,"report","rakebackPoint")
    ;
    SiteParamEnum(IModule module, String type, String code) {
        this.module = module;
        this.type = type;
        this.code = code;
    }

    private IModule module;
    private String type;
    private String code;

    @Override
    public IModule getModule() {
        return module;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getCode() {
        return code;
    }
}
