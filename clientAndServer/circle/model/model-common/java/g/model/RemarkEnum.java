package g.model;

/**
 * 备注枚举类
 * Created by shisongbin on 15-7-21.
 */
public enum RemarkEnum {

    //region common
    PLAYER("player", "玩家模块"),
    PLAYER_REMARK("player", "remark", "玩家备注"),
    PLAYER_FORCEKICK_OUT("player", "forceKickOut", "强制踢出"),
    PLAYER_USERBLOCK_IP("player", "userblockUp", "账号停用"),
    PLAYER_BALANCE_FREEZE("player", "balanceFreeze", "余额冻结"),
    PLAYER_USER_FREEZE("player", "userFreeze", "账号冻结"),

    FUND("fund", "资金管理模块"),
    FUND_RECHARGE("fund", "recharge", "存款订单"),
    FUND_WITHDRAW("fund", "withdraw", "资金提现"),
    FUND_ARTIFICIAL("fund", "artificial", "手动存提"),
    FUND_ARTIFICIAL_DEPOSIT("fund", "artificial_deposit", "手动存款"),
    FUND_ARTIFICIAL_DRAW("fund", "artificial_draw", "手动取款"),
    FUND_DESPOIT_SALE("fund", "despoit_sale", "存款优惠备注"),
    FUND_DESPOIT_FEE("fund", "despoit_fee", "存款手续费备注"),

    USER("user","用户模块"),
    USER_REMARK("user","remark","用户备注"),
    USER_USERBLOCK_IP("user", "userblockUp", "账号停用"),
    USER_USER_FREEZE("user", "userFreeze", "账号冻结"),

    OPERATION("operation", "运营模块"),
    OPERATION_BACKWATER("operation", "backwater", "运营-返水结算备注:修改实付返水"),
    OPERATION_REBAATE("operation", "rebate", "运营-返佣结算备注");

    private String model;//模块
    private String type;//备注类型
    private String trans;   //名称

    RemarkEnum(String model, String name) {
        this.model = model;
        this.trans = name;
    }

    RemarkEnum(String model, String type, String name) {
        this.model = model;
        this.type = type;
        this.trans = name;
    }

    public String getModel() {
        return model;
    }

    public String getTrans() {
        return trans;
    }

    public String getType() {
        return type;
    }
}
