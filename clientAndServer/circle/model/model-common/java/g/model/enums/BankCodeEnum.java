package g.model.enums;

import org.soul.commons.enums.ICodeEnum;

/**
 * Created by fei on 16-5-13.
 */
public enum BankCodeEnum implements ICodeEnum {

    FAST_WECHAT("wechatpay", "微信极速存款"),
    FAST_ALIPAY("alipay", "支付宝极速存款"),
    MOBAO_WECHAT("mobao_wx", "魔宝微信支付"),
    MOBAO_ALIPAY("mobao_zfb", "魔宝支付宝支付"),

    OTHER("other", "其他");

    private String code;
    private String trans;

    BankCodeEnum(String code, String trans) {
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
}
