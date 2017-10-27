package g.model;

import org.soul.commons.enums.EnumTool;
import org.soul.commons.enums.ICodeEnum;

/**
 * Created by tom on 16-4-1.
 */
public enum StatusCodeEnum implements ICodeEnum {
    SUCCESS("100","Success"),
    VALIDATE_FAILED("101","Validate Failed"),
    VALIDATE_CODE("102","Empty/Null Currency Code"),
    VALIDATE_LEGAL("103","Validate Legal Failed"),
    INVALID_TIMESTAMP("201","Invalid Timestamp"),
    INTERNAL_SYSTEM_ERROR("301","Internal System Error");

    private String code;
    private String trans;

    StatusCodeEnum(String code, String trans) {
        this.code = code;
        this.trans = trans;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getTrans() {
        return trans;
    }

    public void setTrans(String trans) {
        this.trans = trans;
    }

    public static StatusCodeEnum enumOf(String code) {
        return EnumTool.enumOf(StatusCodeEnum.class, code);
    }
}
