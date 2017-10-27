package g.model;

import org.soul.commons.enums.EnumTool;
import org.soul.commons.enums.ICodeEnum;

public enum SubSysCodeEnum implements ICodeEnum {
    ADMIN("admin","游戏后台管理"),
    AGENT("agent","代理"),
    PLAYER("player","玩家");

    private String code;
    private String trans;

    SubSysCodeEnum(String code, String trans) {
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

    public static SubSysCodeEnum enumOf(String code) {
        return EnumTool.enumOf(SubSysCodeEnum.class, code);
    }
}