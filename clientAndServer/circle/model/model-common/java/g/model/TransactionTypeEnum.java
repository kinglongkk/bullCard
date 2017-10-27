package g.model;

import org.soul.commons.dict.IDictEnum;
import org.soul.commons.support.IModule;

/**
 * 资金类型
 */
public enum TransactionTypeEnum implements IDictEnum {
    WITHDRAWALS("WITHDRAWALS", "取款"),
    DEPOSIT("DEPOSIT", "存款"),
    PROFIT("PROFIT", "派彩"),
    RE_PROFIT("RE_PROFIT", "重新派彩"),
    BET("BET", "下注"),
    CANCEL_BET("CANCEL_BET", "消注"),
    SETTLE("SETTLE", "结算"),
    ;

    private final String code;
    private String desc;

    TransactionTypeEnum(String code, String desc) {
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
