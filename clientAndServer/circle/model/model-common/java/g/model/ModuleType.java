package g.model;

import org.soul.commons.support.IModuleType;

/**
 * Created by longer on 7/8/15.
 * 模块类型
 */
public enum ModuleType implements IModuleType {
    PASSPORT_LOGIN("1", "通行证(登录)"),
    PASSPORT_LOGOUT("2", "通行证(退出)"),
    COMPANY_ACCOUNT_FREEZE("3", "公司入款账户-冻结"),
    COMPANY_ACCOUNT_UNFREEZE("4", "公司入款账户-解冻"),
    COMPANY_ACCOUNT_WARNING("5", "公司入款账户-预警"),
    COMPANY_ACCOUNT_LACKING("6", "公司入款账户-不足"),
    ONLINE_ACCOUNT_FREEZE("7", "线上支付账户-冻结"),
    ONLINE_ACCOUNT_UNFREEZE("8", "线上支付账户-解冻"),
    ONLINE_ACCOUNT_WARNING("9", "线上支付账户-预警"),
    ONLINE_ACCOUNT_LACKING("10", "线上支付账户-不足"),
    BACKWATER_SETTLEMENT_SUCCESS("11", "返水结算通过"),
    BACKWATER_SETTLEMENT_FAILURE("12", "返水结算拒绝"),
    Rebate_SETTLEMENT_SUCCESS("13", "返佣结算通过"),
    Rebate_SETTLEMENT_FAILURE("14", "返佣结算拒绝"),
    PASSPORT_LOGIN_FAIL("15", "通行证(登录失败)"),
    USER_FREEZE("16", "账号冻结"),
    USER_CANCEL_FREEZE("17", "取消账号冻结"),
    USER_DISABLE("18", "停用账号"),
    RESET_USER_PASSWORD("19", "重置登录密码"),
    RESET_USER_PERMISSIONPWD("20", "重置安全密码"),;

    private String code;
    private String trans;

    ModuleType(String code, String trans) {
        this.code = code;
        this.trans = trans;
    }


    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getTrans() {
        return trans;
    }
}
