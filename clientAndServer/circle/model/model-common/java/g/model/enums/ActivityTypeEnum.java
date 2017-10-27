package g.model.enums;

import org.soul.commons.enums.ICodeEnum;

/**
 * 活动类型
 */
public enum ActivityTypeEnum implements ICodeEnum {
    FIRST_DEPOSIT("FIRST_DEPOSIT","首存送"),
    REGIST_SEND("REGIST_SEND","注册送"),
    DEPOSIT_SEND("DEPOSIT_SEND","存就送"),
    RELIEF_FUND("RELIEF_FUND","救济金"),
    LOTTERY("LOTTERY","抽奖"),
    SHARE("SHARE","分享");

    private String code;
    private String trans;

    ActivityTypeEnum(String code,String trans) {
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
}
