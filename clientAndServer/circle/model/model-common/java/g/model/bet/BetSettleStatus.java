package g.model.bet;

import org.soul.commons.enums.EnumTool;
import org.soul.commons.enums.ICodeEnum;

/**
 * Created by longer on 5/5/16.
 */
public enum BetSettleStatus implements ICodeEnum {

    UN_SETTLE("10","未结算"),
    SETTLED("20","已结算");

    private String code;
    private String trans;

    BetSettleStatus(String code, String trans) {
        this.code = code;

        this.trans = trans;
    }

    public String getTrans() {
        return trans;
    }

    public String getCode() {
        return code;
    }

    public static BetSettleStatus enutmOf(String code) {
        return EnumTool.enumOf(BetSettleStatus.class, code);
    }
}