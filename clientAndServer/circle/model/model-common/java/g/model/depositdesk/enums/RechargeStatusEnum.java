package g.model.depositdesk.enums;

import org.soul.commons.dict.IDictEnum;
import org.soul.commons.support.IModule;

/**
 * 充值状态
 * <p/>
 * Created by tom on 15-6-25.
 */
public enum RechargeStatusEnum implements IDictEnum {
    DEAL("1", "label-orange", "待处理"),
    SUCCESS("2", "label-green", "成功"),
    FAIL("3", "", "失败"),
    PENDING_PAY("4", "label-orange", "待支付"),
    ONLINE_SUCCESS("5", "label-green", "成功"),
    ONLINE_FAIL("6", "", "失败"),
    OVER_TIME("7","label-timeout","超时")
    ;

    private final String code;
    private String css;
    private String desc;

    private RechargeStatusEnum(String code, String css, String desc) {
        this.code = code;
        this.css = css;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getCss() {
        return css;
    }

    @Override
    public IModule getModule() {
        return null;
    }

    @Override
    public String getType() {
        return null;
    }
}
