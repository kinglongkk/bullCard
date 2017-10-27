package g.service.chesscard.enums;


import g.model.Module;
import org.soul.commons.param.IParamEnum;
import org.soul.commons.support.IModule;

/**
 * WsSession参数
 * Created by MK on 2017/1/10.
 */
public enum SessionEnum{

    IS_AI("isAi", "是否AI");//是否AI

    private String key;
    private String comment;

    SessionEnum(String key, String comment) {
        this.key = key;
        this.comment = comment;
    }

    public String getKey() {
        return key;
    }

    public String getComment() {
        return comment;
    }

}
