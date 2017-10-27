package g.service.chesscard.enums;

import org.soul.commons.enums.ICodeEnum;

/**
 * Created by Double on 2016/9/27.
 */
public enum RoomInOutEnum implements ICodeEnum {

    IN("in", "进入房间"),
    OUT("out", "退出房间"),;

    private String code;
    private String trans;

    RoomInOutEnum(String code, String trans) {
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
