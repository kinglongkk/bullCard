package g.model.enums;

import org.soul.commons.enums.EnumTool;
import org.soul.commons.enums.ICodeEnum;

/**
 * Created by longer on 4/19/16.
 */
public enum BallTypeEnum implements ICodeEnum {

    FT("FT","足球"),
    BK("BK","篮球"),
    TN("TN","网球"),
    VB("VB","排球"),
    BM("BM","羽毛球"),
    TT("TT","乒乓球"),
    BS("BS","其他"),
    ;


    private String code;
    private String trans;

    BallTypeEnum(String code, String trans) {
        this.code = code;
        this.trans = trans;
    }

    public String getTrans() {
        return trans;
    }

    public String getCode() {
        return code;
    }

    public static BallTypeEnum enumOf(String code) {
        return EnumTool.enumOf(BallTypeEnum.class, code);
    }

}
