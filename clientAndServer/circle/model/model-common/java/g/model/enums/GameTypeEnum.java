package g.model.enums;

import org.soul.commons.enums.EnumTool;
import org.soul.commons.enums.ICodeEnum;

/**
 * Created by longer on 6/29/16.
 */
public enum GameTypeEnum implements ICodeEnum {

    SPORT("SPORT","体育"),
    LOTTERY("LOTTERY","彩票"),
    CHESS_CARD("CHESS_CARD","棋牌"),
    ;

    private String code;
    private String trans;

    GameTypeEnum(String code, String trans) {
        this.code = code;
        this.trans = trans;
    }

    public String getTrans() {
        return trans;
    }

    public String getCode() {
        return code;
    }

    public static GameTypeEnum enumOf(String code) {
        return EnumTool.enumOf(GameTypeEnum.class, code);
    }

}
