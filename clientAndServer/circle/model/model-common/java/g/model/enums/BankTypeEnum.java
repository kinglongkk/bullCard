package g.model.enums;

import org.soul.commons.enums.EnumTool;
import org.soul.commons.enums.ICodeEnum;

/**
 * Created by fei on 16-5-13.
 */
public enum BankTypeEnum implements ICodeEnum {

    WECHAT("wechat", "微信"),
    ALIPAY("alipay", "支付宝"),
    BANK("bank", "银行卡");

    private String code;
    private String trans;

    BankTypeEnum(String code, String trans) {
        this.code =code;
        this.trans = trans;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getTrans() {
        return trans;
    }

    public void setTrans(String trans) {
        this.trans = trans;
    }

    public static BankTypeEnum enumOf(String code) {
        return EnumTool.enumOf(BankTypeEnum.class, code);
    }

}
