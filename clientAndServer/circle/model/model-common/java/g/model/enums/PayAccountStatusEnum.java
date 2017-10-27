package g.model.enums;

import org.soul.commons.enums.ICodeEnum;

/**
 * 状态枚举
 */
public enum PayAccountStatusEnum implements ICodeEnum {
    //(1使用中；2已停用；3被冻结；4已删除) */
    USING("1", "使用中","using"),
    DISABLED("2", "已停用","disabled"),
    FREEZE("3", "被冻结","freeze"),
    DELETED("4", "已删除","deleted");

    private String code;
    private String trans;
    private String dictCode;

    PayAccountStatusEnum(String code, String trans, String dictCode) {
        this.code = code;
        this.trans = trans;
        this.dictCode = dictCode;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getTrans() {
        return trans;
    }

    public void setTrans(String trans) {
        this.trans = trans;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }
}