package g.model.lottery.enums;

import org.soul.commons.enums.EnumTool;
import org.soul.commons.enums.ICodeEnum;

/**
 * Created by longer on 7/2/16.
 */
public enum SscBetItemSuffixEnum implements ICodeEnum{

    IndexA("A","和"),
    Index5("5","万"),
    Index4("4","仟"),
    Index3("3","佰"),
    Index2("2","拾"),
    Index1("1","个"),
    ;

    private String code;

    private String trans;

    SscBetItemSuffixEnum(String code, String trans) {
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

    public static SscBetItemSuffixEnum enumOf(String code) {
        return EnumTool.enumOf(SscBetItemSuffixEnum.class, code);
    }
}
