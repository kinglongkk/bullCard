package g.model.enums;

import org.soul.commons.enums.ICodeEnum;

/**
 * 优惠形式代码
 */
public enum ActivityPreferentialFormEnum implements ICodeEnum {
    PERCENTAGE_HANDSEL("percentage_handsel","比例彩金"),
    REGULAR_HANDSEL("regular_handsel","固定彩金"),
    NOT_HANDSEL("not_handsel","非彩金"),
    ;

    private String code;
    private String trans;
    private String operate;

    ActivityPreferentialFormEnum(String code, String trans) {
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
}
