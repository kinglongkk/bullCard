package g.model.match.po;

import org.soul.commons.enums.ICodeEnum;

/**
 * Created by longer on 5/17/16.
 */
public enum MatchResultProviderEnum implements ICodeEnum {

    MANUAL("1","手工"),
    GATHER("2","采集");

    private String code;
    private String desc;

    MatchResultProviderEnum(String code,String desc){
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
