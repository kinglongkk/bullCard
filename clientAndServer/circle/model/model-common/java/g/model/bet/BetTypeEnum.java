package g.model.bet;

import org.soul.commons.enums.EnumTool;
import org.soul.commons.enums.ICodeEnum;

/**
 * Created by longer on 7/2/16.
 */
public enum BetTypeEnum implements ICodeEnum{
    BULL_100("BULL_100","百人"),
    BULL_BAO("BULL_BAO","押宝"),
    BULL_CLA("BULL_CLA","经典"),
    ;

    private String code;

    private String trans;

    BetTypeEnum(String code, String trans) {
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

    public static BetTypeEnum enumOf(String code) {
        return EnumTool.enumOf(BetTypeEnum.class, code);
    }

}
