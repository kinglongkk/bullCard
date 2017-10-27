package g.model.common;

import org.soul.commons.enums.ICodeEnum;

/**
 * Created by tom on 15-11-19.
 */
public enum PasswordLevelEnum implements ICodeEnum {
    STRONG("30","高"),
    NORMAL("20","中"),
    LOW("10","低"),
    ;

    /**
     * 密码强度等级
     */
    private String code;
    /**
     * 密码强度
     */
    private String trans;

    PasswordLevelEnum(String code, String trans){
        this.code = code;
        this.trans = trans;
    }

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getTrans() {
        return null;
    }
}
