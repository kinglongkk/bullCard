package g.model.bet;

import org.soul.commons.enums.EnumTool;
import org.soul.commons.enums.ICodeEnum;

/**
 * Created by longer on 5/5/16.
 */
public enum BetStatus implements ICodeEnum {

    UN_CONFIRMD("10","未确认"),
    CONFIRMD("20","确认"),
    CANCLED("30","取消");

    private String code;
    private String trans;

    BetStatus(String code, String trans) {
        this.code = code;

        this.trans = trans;
    }

    public String getTrans() {
        return trans;
    }

    public String getCode() {
        return code;
    }

    public static BetStatus enutmOf(String code) {
        return EnumTool.enumOf(BetStatus.class, code);
    }
}