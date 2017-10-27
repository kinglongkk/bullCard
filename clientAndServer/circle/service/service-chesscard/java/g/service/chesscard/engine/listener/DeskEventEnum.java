package g.service.chesscard.engine.listener;

import org.soul.commons.enums.ICodeEnum;

/**
 * Created by Double on 2016/9/30.
 */
public enum DeskEventEnum implements ICodeEnum {

    NEW("NEW", "新开桌子"),
    START_MATCH("START_MATCH", ""),
    STOP_MATCH("STOP_MATCH", "比赛结束"),
    DESTROY("DESTROY", "销毁桌子");

    private String code;
    private String desc;

    DeskEventEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getTrans() {
        return desc;
    }
}
