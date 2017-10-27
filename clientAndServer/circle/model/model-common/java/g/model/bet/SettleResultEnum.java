package g.model.bet;

import org.soul.commons.enums.ICodeEnum;

/**
 * Created by lenovo on 2016/9/21.
 * TODO:Double To Jason，此类要删除，消息请统一存放
 */
@Deprecated
public enum SettleResultEnum implements ICodeEnum {

    SUCCESS("0","结算成功"),
    NOT_MATCH("1","没有赛事"),
    NOT_BET("2","没有注单"),
    ;

    private String code;
    private String trans;

    SettleResultEnum(String code, String trans) {
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
