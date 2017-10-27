package g.model;

/**
 * @author: tom
 * @date: 15-6-25
 */
public enum StatusEnum {

    // 状态
    NORMAL("1","正常"),
    STOP("2","停用"),
    FROZEN("3","冻结"),

    // 角色：启用、禁用
    ROLE_ENABLE("1","启用"),
    ROLE_UNABLE("2","禁用");

    private String code;

    private String name;

    StatusEnum(String code, String name){
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
