package g.model;

import org.soul.commons.dict.IDictEnum;
import org.soul.commons.support.IModule;

/**
 * 额度预警枚举
 * Created by black on 2016/12/13.
 */
public enum QuotaWarnTypeEnum implements IDictEnum {

    RED(80, "RED", "红色"),
    YELLOW(70, "YELLOW", "黄色"),
    BLUE(60, "BLUE", "蓝色"),
    GREEN(60, "GREEN", "无");

    private Integer code;
    private String value;
    private String desc;

    QuotaWarnTypeEnum(Integer code, String value, String desc) {
        this.code = code;
        this.value = value;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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
