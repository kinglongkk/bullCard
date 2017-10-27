package g.model.withdrawdesk.enums;

import org.soul.commons.dict.IDictEnum;
import org.soul.commons.support.IModule;

/**
 * 提现类型
 */
public enum WithdrawTypeEnum implements IDictEnum {
    PLAYER("player", "玩家提现"),
    ARTIFICIAL("artificial", "人工提现"),

    FIRST("first","首次提现"),
    NORMAL("normal","提现"),
    REPEAT_DRAW("repeat_draw","重复提现"),
    ERROR_RECHARGE("error_recharge","误充值"),
    NEGATIVE("negative","手动负数回充"),
    ARTIFICIAL_DRAW("artificial_draw","手动申请提现"),
    ILLEGAL_TRADE("Illegal_trade","扣除非法交易"),
    ABANDON_FAVOURABLE("abandon_favourable","放弃充值优惠"),
    OTHER("other","其它提现"),
    AUTO_NEGATIVE("auto_negative", "自动负数回充");

    String code;
    private String desc;

    private WithdrawTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
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
