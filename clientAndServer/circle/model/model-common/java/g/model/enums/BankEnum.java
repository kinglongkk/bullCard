package g.model.enums;

import org.soul.commons.enums.ICodeEnum;

public enum BankEnum implements ICodeEnum {

    TYPE_BANK("1", "银行"),
    TYPE_THIRD("2", "第三方"),
    TYPE_ONLINE("3", "在线支付");

    private String code;
    private String trans;

    BankEnum(String code, String trans) {
        this.code = code;
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
}