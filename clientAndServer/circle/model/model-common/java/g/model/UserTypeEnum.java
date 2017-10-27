package g.model;

import org.soul.commons.enums.EnumTool;
import org.soul.commons.enums.ICodeEnum;

public enum UserTypeEnum implements ICodeEnum {
    ADMIN("10", "管理员"),
    ADMIN_SUB("11", "管理员子帐号"),
    AGENT("20", "代理"),
    AGENT_SUB("21", "代理子账号"),//
    AGENT_PLAYER("22", "玩家代理"), //是玩家,也是代理,但是菜单权限很少
    PLAYER("30", "玩家"), ;

    private String code;
    private String trans;

    UserTypeEnum(String code, String trans) {
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

    public static UserTypeEnum enumOf(String code) {
        return EnumTool.enumOf(UserTypeEnum.class, code);
    }

    /**
     * 是否需要检查菜单URL
     * @param userTypeEnum
     * @return
     */
    public static boolean isNeedCheckUrl(UserTypeEnum userTypeEnum){
        switch (userTypeEnum) {
            case ADMIN_SUB:
            case AGENT_SUB:
                return true;
        }
        return false;
    }

    /**
     * 是否拥有全权限(菜单,功能等)
     * @param userTypeEnum
     * @return
     */
    public static boolean isWholePerimssion(UserTypeEnum userTypeEnum) {
        switch (userTypeEnum) {
            case ADMIN:
            case AGENT:
            case PLAYER:
                return true;
        }
        return false;
    }

}