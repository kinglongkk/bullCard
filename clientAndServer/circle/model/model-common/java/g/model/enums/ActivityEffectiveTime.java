package g.model.enums;

import org.soul.commons.enums.EnumTool;
import org.soul.commons.enums.ICodeEnum;

/**
 * 有效时间
 */
public enum ActivityEffectiveTime implements ICodeEnum {
    ONEDAY("OneDay","1天内",1),
    TWODAYS("TwoDays","2天内",2),
    THREEDAYS("ThreeDays","3天内",3),
    SEVENDAYS("SevenDays","7天内",7),
    THIRTYDAYS("ThirtyDays","30天内",30)
    ;

    private String code;
    private String trans;
    private Integer val;

    ActivityEffectiveTime(String code, String trans,Integer val) {
        this.code = code;
        this.trans = trans;
        this.val = val;
    }
    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getTrans() {
        return trans;
    }

    public Integer getVal() {return val;}

    public static int getEffectiveTime(String effectiveTimeCode) {
        ActivityEffectiveTime activityEffectiveTimeEnum = EnumTool.enumOf(ActivityEffectiveTime.class, effectiveTimeCode);
        return activityEffectiveTimeEnum.getVal();
    }
}
