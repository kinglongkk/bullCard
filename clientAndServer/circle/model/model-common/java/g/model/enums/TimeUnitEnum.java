package g.model.enums;

import org.soul.commons.enums.EnumTool;
import org.soul.commons.enums.ICodeEnum;

/**
 * 时间单位通用枚举
 * Created by black on 2016/11/21.
 */
public enum TimeUnitEnum implements ICodeEnum {

    MONTH("60", "月"),
    WEEK("50", "周"),
    DAY ("10", "天"),
    HOUR ("20", "时"),
    MINUTE ("30", "分"),
    SECOND ("40", "秒"),
    ;

    private String code;
    private String trans;

    TimeUnitEnum(String code, String trans) {
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

    public static TimeUnitEnum enumOf(String code) {
        return EnumTool.enumOf(TimeUnitEnum.class, code);
    }
}
