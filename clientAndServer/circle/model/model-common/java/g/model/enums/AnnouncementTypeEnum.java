package g.model.enums;

import org.soul.commons.enums.ICodeEnum;

/**
 * @author fly
 * @time 2015-10-22 11:58
 */
public enum AnnouncementTypeEnum implements ICodeEnum {
    GENERAL("general", "一般公告"),
    IMPORTANCE("importance", "重要公告"),
    PERSONAL("personal","个人公告"),
    ALL_AGENT("allAgent","全局代理商"),
    APPOINT_AGENT("appointAgent","指定代理商"),
    ENABLE("enable","启用"),
    DISABLE("disable","取消");

    private String code;
    private String trans;

    AnnouncementTypeEnum(String code, String trans) {
        this.code = code;
        this.trans = trans;
    }

    public String getTrans() {
        return trans;
    }

    public String getCode() {
        return code;
    }
}