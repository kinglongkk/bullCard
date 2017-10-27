package g.model.withdrawdesk.enums;

import org.soul.commons.dict.IDictEnum;
import org.soul.commons.support.IModule;

/**
 * 提现状态
 *
 * Created by Orange
 */
public enum WithdrawStatusEnum implements IDictEnum {
    DEAL("1", "label-orange", "待处理"),
    DEALAUDITFAIL("2","label-orange","待处理-稽核失败"),
    SUCCESS("4", "label-green", "成功"),
    FAIL("5", "", "失败"),
    REFUSE("6", "", "拒绝"),
    PENDING_SUB("7","","待提交"),
    CANCELLATION_OF_ORDERS("8","","取消订单");

    private final String code;
    private String css;
    private String desc;

    private WithdrawStatusEnum(String code, String css, String desc) {
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
