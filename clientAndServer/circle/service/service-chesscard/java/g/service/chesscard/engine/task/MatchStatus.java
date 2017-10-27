package g.service.chesscard.engine.task;

import org.soul.commons.enums.ICodeEnum;

/**
 * Created by Double on 2016/9/30.
 */
public enum MatchStatus implements ICodeEnum {

    INIT("INIT", "初始化中"),
    RUNNING("RUNNING", "进行中"),
    STOP("STOP", "停止比赛"),
    SETTLED("SETTLED", "结算中"),
    FINISHED("FINISHED", "完成(无后续)");

    private String code;
    private String trans;

    MatchStatus(String code, String trans) {
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
