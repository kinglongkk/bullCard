package g.model.chesscard.enums;

import g.model.bet.IBetItem;
import org.soul.commons.enums.EnumTool;
import org.soul.commons.enums.ICodeEnum;

/**
 * Created by Jason on 16/8/29.
 */
public enum BullBaoItemPrefixEnum implements ICodeEnum {
    N0("0","没牛"),
    N1("1","牛一"),
    N2("2","牛二"),
    N3("3","牛三"),
    N4("4","牛四"),
    N5("5","牛五"),
    N6("6","牛六"),
    N7("7","牛七"),
    N8("8","牛八"),
    N9("9","牛九"),
    NN("10","牛牛"),
    ;



    private String code;
    private String trans;

    private BullBaoItemPrefixEnum(String code, String trans) {
        this.code = code;
        this.trans = trans;
    }

    public String getTrans() {
        return trans;
    }

    public String getCode() {
        return code;
    }

    public static BullBaoItemPrefixEnum enumOf(String code) {
        return EnumTool.enumOf(BullBaoItemPrefixEnum.class, code);
    }

    public String toString(){
        return code;
    }

    public String toDesc(){
        return code;
    }

}
