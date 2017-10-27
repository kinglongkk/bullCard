package g.model;

import org.soul.commons.enums.ICodeEnum;

/**
 * Created by cj on 15-7-22 下午4:48.
 */
public enum CommonStatusEnum implements ICodeEnum {
    PENDING("pending", "状态：待处理"),
    PROCESS("process", "状态：处理中"),
    SUCCESS("success", "状态：成功"),
    FAILURE("failure", "状态：失败"),
    REJECT("reject", "状态：拒绝"),
    LSSUING("lssuing", "状态：已发放"),
    PENDING_PAY("pending_pay", "状态:待支付"),
    OVER_TIME("over_time", "状态：超时"),
    DEAL_AUDIT_FAIL("deal_audit_fail", "状态:待处理-稽核失败");

    CommonStatusEnum(String code, String trans) {
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
