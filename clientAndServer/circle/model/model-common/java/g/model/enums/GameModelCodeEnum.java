package g.model.enums;

import org.soul.commons.enums.EnumTool;
import org.soul.commons.enums.ICodeEnum;

/**
 * Created by Jason on 16/8/29.
 */
public enum GameModelCodeEnum  implements ICodeEnum {
    FIGHT("FIGHT","对战"),
    /** 投注 */
    BET("BET","投注"),
    /** 夺宝 */
    GRAB("GRAB","夺宝"),
    ;

    private String code;
    private String trans;

    GameModelCodeEnum(String code, String trans) {
        this.code = code;
        this.trans = trans;
    }

    public String getTrans() {
        return trans;
    }

    public String getCode() {
        return code;
    }

    public static GameModelCodeEnum enumOf(String code) {
        return EnumTool.enumOf(GameModelCodeEnum.class, code);
    }
}
