package g.model.enums;

import org.soul.commons.enums.EnumTool;
import org.soul.commons.enums.ICodeEnum;

/**
 * Created by longer on 6/29/16.
 */
public enum LotteryTypeEnum implements ICodeEnum {

    CQSSC("CQSSC","重庆时时彩"),
    ;

    private String code;
    private String trans;

    LotteryTypeEnum(String code, String trans) {
        this.code = code;
        this.trans = trans;
    }

    public String getTrans() {
        return trans;
    }

    public String getCode() {
        return code;
    }

    public static LotteryTypeEnum enumOf(String code) {
        return EnumTool.enumOf(LotteryTypeEnum.class, code);
    }

}
