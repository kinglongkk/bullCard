package g.model.chesscard.enums;

import org.soul.commons.enums.EnumTool;
import org.soul.commons.enums.ICodeEnum;

/**
 * Created by longer on 7/2/16.
 */
public enum Bull100BetItemPrefixEnum implements ICodeEnum {

    SPADE("SPADE", "黑桃"),
    HEART("HEART", "红桃"),
    CLUB("CLUB", "梅花"),
    DIAMOND("DIAMOND", "方片"),
    ;

    private String code;

    private String trans;

    Bull100BetItemPrefixEnum(String code, String trans) {
        this.code = code;
        this.trans = trans;
    }
    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getTrans() {
        return trans;
    }

    public static Bull100BetItemPrefixEnum enumOf(String code) {
        return EnumTool.enumOf(Bull100BetItemPrefixEnum.class, code);
    }

}
