package g.model.common.notice.enums;

import org.soul.commons.enums.EnumTool;
import org.soul.model.msg.notice.enums.INoticeManualEventType;

/**
 * Created by kevice on 7/20/15.
 */
public enum ManualNoticeEvent implements INoticeManualEventType {
    BALANCE_FREEZON("BALANCE_FREEZON", "余额冻结"),
    FORCE_KICK_OUT("FORCE_KICK_OUT", "强制踢出"),
    DEPOSIT_AUDIT_FAIL("DEPOSIT_AUDIT_FAIL", "充值审核失败"),
    PLAYER_ACCOUNT_STOP("PLAYER_ACCOUNT_STOP","玩家账号停用"),
    PLAYER_ACCOUNT_FREEZON("PLAYER_ACCOUNT_FREEZON","玩家账号冻结"),
    PLAYER_WITHDRAWAL_AUDIT_FAIL("PLAYER_WITHDRAWAL_AUDIT_FAIL", "玩家取款审核失败"),
    REFUSE_PLAYER_WITHDRAWAL("REFUSE_PLAYER_WITHDRAWAL", "拒绝玩家取款"),
    REFUSE_RETURN_RABBET("REFUSE_RETURN_RABBET", "拒绝返水"),
    REFUSE_RETURN_COMMISSION("REFUSE_RETURN_COMMISSION", "拒绝返佣"),
    TEST("TEST","测试"),
    DEPOSIT_FOR_PLAYER("DEPOSIT_FOR_PLAYER", "玩家负数余额归零"),
    DEDUCT_MONEY_FROM_PLAYER("DEDUCT_MONEY_FROM_PLAYER", "扣除玩家款项"),
    PREFERENCE_AUDIT_FAIL("PREFERENCE_AUDIT_FAIL", "优惠审批失败"),
    DOMAIN_CHECK("DOMAIN_CHECK","域名审核"),
    GROUP_SEND("GROUP_SEND", "信息群发"),
    ACENTER_ACCOUNT_FREEZON("ACENTER_ACCOUNT_FREEZON","代理账号冻结"),
    TCENTER_ACCOUNT_FREEZON("TCENTER_ACCOUNT_FREEZON","总代账号冻结"),
    ACENTER_ACCOUNT_STOP("ACENTER_ACCOUNT_STOP","代理账号停用"),
    TCENTER_ACCOUNT_STOP("TCENTER_ACCOUNT_STOP","总代账号停用"),
    AGENT_WITHDRAWAL_AUDIT_FAIL("AGENT_WITHDRAWAL_AUDIT_FAIL","代理取款审核失败"),
    REFUSE_AGENT_WITHDRAWAL("REFUSE_AGENT_WITHDRAWAL","拒绝代理取款"),
    SALE_AUDIT_FAIL("SALE_AUDIT_FAIL","优惠审核失败"),
    MANUAL_WITHDRAWAL("MANUAL_WITHDRAWAL","手动取款")
    ;

    private String code;
    private String desc;

    ManualNoticeEvent(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getTrans() {
        return desc;
    }

    public static ManualNoticeEvent enumOf(String code) {
        return EnumTool.enumOf(ManualNoticeEvent.class, code);
    }
}
