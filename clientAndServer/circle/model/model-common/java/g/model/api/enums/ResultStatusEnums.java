package g.model.api.enums;

/**
 * api 端提示信息枚举
 * Created by black on 2016/11/18.
 */
public enum ResultStatusEnums {

    SUCCESS("100","成功"),

    VALIDATE_LEGAL_FAILED("101","请求合法验证失败"),
    VALIDATE_PARAMS_FAILED ("102","参数有误"),
    AGENT_NOT_EXISTS("103", "代理不存在"),
    ANALYSIS_PARAMS_FAILED ("104","参数解析失败"),
    AMOUNT_ERROR("105","交易值必须大于0"),
    AGENT_HAS_ARREARAGE("106", "取款失败"),

    USER_EXISTS("201", "用户已存在"),
    USER_NOT_EXIST("202", "用户不存在"),
    USER_OR_PASSWORD_ERR("203", "用户帐号或密码不匹配"),
    USER_HASNT_LOGIN("204", "用户未登录"),
    USER_LOGOUT("205", "用户登出"),
    USER_KICK_OUT_FAILED("206", "用户登出失败"),
    USER_OR_PASSWORD_INVALID("207", "无效账户或密码"),

    UNSUPPORT_CURRENCY("301", "不支持的币种"),
    REPEAT_TRANSFER("302", "重复转账"),
    INSUFFICIENT_BALANCE("303", "余额不足"),
    TRANS_ID_NOT_EXIST("304", "外部交易号不存在"),
    PLATFORM_TRANS_ID_NOT_EXIST("305", "平台交易号不存在"),
    TRAN_ID_HAS_FINSHED("306", "重复交易"),
    WAITING_HANDLING("307", "等待处理"),
    REFUSE_OPERATION("308", "拒绝操作"),

    NO_GAME_ID("401", "没有这款游戏"),
    BUSY("402", "游戏平台忙碌中"),
    NETWORK_ERR("403", "游戏平台网络连接不上"),
    MAINTENANCE("404", "游戏平台维护中"),

    OTHER_ERR("501", "系统错误");

    private String code;
    private String msg;

    ResultStatusEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
