package g.model.enums;

import org.soul.commons.enums.EnumTool;
import org.soul.commons.enums.ICodeEnum;

/**
 * Created by longer on 6/29/16.
 */
public enum ChessCardEnum implements ICodeEnum {

    //TODO: Double 修改成: bullfight
    DOU_NIU("DOU_NIU","斗牛"),
    ;

    private String code;
    private String trans;

    ChessCardEnum(String code, String trans) {
        this.code = code;
        this.trans = trans;
    }

    public String getTrans() {
        return trans;
    }

    public String getCode() {
        return code;
    }

    public static ChessCardEnum enumOf(String code) {
        return EnumTool.enumOf(ChessCardEnum.class, code);
    }

}
