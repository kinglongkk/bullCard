package g.model.enums;

import org.soul.commons.enums.ICodeEnum;

/**
 * Created by tom on 15-9-6.
 */
public enum PayAccountAccountType implements ICodeEnum {
    BANKACCOUNT("1", "银行账户"),
    THIRTY("2", "第三方账户"),
    WECHAT("3", "微信支付"),
    ALIPAY("4", "支付宝");

    PayAccountAccountType(String code, String trans) {
        this.code = code;
        this.trans = trans;
    }

    private String code;
    private String trans;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getTrans() {
        return trans;
    }
}
