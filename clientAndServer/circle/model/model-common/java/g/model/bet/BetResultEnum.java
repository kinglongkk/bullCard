package g.model.bet;

import org.soul.commons.enums.EnumTool;
import org.soul.commons.enums.ICodeEnum;

/**
 * Created by longer on 5/5/16.
 */
public enum BetResultEnum implements ICodeEnum {
    WIN("WIN","赢"),
    LOSE("LOSE","输"),
    HE ("HE","平"),
    WIN_HALF("WIN_HALF","赢半"),
    LOSE_HALF("LOSE_HALF","输半");

    private String code;
    private String trans;

    BetResultEnum(String code, String trans) {
        this.code = code;

        this.trans = trans;
    }

    public String getTrans() {
        return trans;
    }

    public String getCode() {
        return code;
    }

    public static BetResultEnum enutmOf(String code) {
        return EnumTool.enumOf(BetResultEnum.class, code);
    }


}