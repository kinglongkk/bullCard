package g.model.lottery.enums;

import org.soul.commons.enums.EnumTool;
import org.soul.commons.enums.ICodeEnum;

/**
 * Created by longer on 7/2/16.
 */
public enum SscBetItemPrefixEnum implements ICodeEnum {

    B("B", "大"),
    S("S","小"),
    E("E","双"),
    O("O","单"),
//    D("D", "龙(Dragon)"),
//    T("T", "虎(Tiger)"),
//    P("P", "质(Prime)"),
//    C("C", "合(Composite)"),
    N0("0", "零"),
    N1("1", "一"),
    N2("2", "二"),
    N3("3", "三"),
    N4("4", "四"),
    N5("5", "五"),
    N6("6", "六"),
    N7("7", "七"),
    N8("8", "八"),
    N9("9", "九"),
    ;

    private String code;

    private String trans;

    SscBetItemPrefixEnum(String code, String trans) {
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

    public static SscBetItemPrefixEnum enumOf(String code) {
        return EnumTool.enumOf(SscBetItemPrefixEnum.class, code);
    }

}
