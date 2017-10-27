package g.model.enums;

import org.soul.commons.enums.ICodeEnum;

/**
 * Created by tom on 15-9-6.
 */
public enum PayAccountType implements ICodeEnum {
    COMPANY_ACCOUNT("1","公司入款"),
    ONLINE_ACCOUNT("2","线上支付"),
    ;

    PayAccountType(String code, String trans) {
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
