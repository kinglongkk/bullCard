package g.model.enums;

import org.soul.commons.enums.EnumTool;
import org.soul.commons.enums.ICodeEnum;

/**
 * 游戏状态通用枚举
 * Created by black on 2016/11/21.
 */
public enum GameStatusEnum implements ICodeEnum {

    ENABLE ("10", "启用"),
    FORBIDDEN ("20", "禁用"),
    SAFEGUARD ("30", "维护"),
    ;

    private String code;
    private String trans;

    GameStatusEnum(String code, String trans) {
        this.code = code;
        this.trans = trans;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getTrans() {
        return trans;
    }

    public static GameStatusEnum enumOf(String code) {
        return EnumTool.enumOf(GameStatusEnum.class, code);
    }
}
