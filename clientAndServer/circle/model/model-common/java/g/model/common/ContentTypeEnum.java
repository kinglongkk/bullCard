package g.model.common;

import org.soul.commons.enums.ICodeEnum;

/**
 * ContentCheckEnum
 *
 * @author River
 * @date 15-11-16 下午12:03
 */

public enum ContentTypeEnum implements ICodeEnum{
    LOGO("1", "LOGO","logo"),
    DOCUMENT("2", "文案","document"),
    ACVITITY("3", "优惠","acvitity");

    private String code;
    private String trans;
    private String dictCode;

    ContentTypeEnum(String code, String trans, String dictCode) {
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
