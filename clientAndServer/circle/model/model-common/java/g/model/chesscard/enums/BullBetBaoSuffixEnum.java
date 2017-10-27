package g.model.chesscard.enums;

import org.soul.commons.enums.EnumTool;
import org.soul.commons.enums.ICodeEnum;

/**
 * Created by longer on 7/2/16.
 */
public enum BullBetBaoSuffixEnum implements ICodeEnum {

    W("W", "赢"),
    L("L", "输"),
    ;

    private String code;

    private String trans;

    BullBetBaoSuffixEnum(String code, String trans) {
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

    public static BullBetBaoSuffixEnum enumOf(String code) {
        return EnumTool.enumOf(BullBetBaoSuffixEnum.class, code);
    }

}
