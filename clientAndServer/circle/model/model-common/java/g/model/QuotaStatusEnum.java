package g.model;

import org.soul.commons.dict.IDictEnum;
import org.soul.commons.support.IModule;

/**
 * 代理额度状态
 * Created by black on 2016/12/16.
 */
public enum QuotaStatusEnum implements IDictEnum {

    NORMAL("1","启用"),
    ARREARAGE("2","欠费");

    private String code;

    private String name;

    QuotaStatusEnum(String code, String name){
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public IModule getModule() {
        return null;
    }

    @Override
    public String getType() {
        return null;
    }
}
