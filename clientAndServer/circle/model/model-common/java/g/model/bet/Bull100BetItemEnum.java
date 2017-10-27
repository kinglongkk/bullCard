package g.model.bet;

import org.soul.commons.enums.EnumTool;
import org.soul.commons.enums.ICodeEnum;

import static g.model.chesscard.enums.Bull100BetItemPrefixEnum.*;
import static g.model.chesscard.enums.BullBetBaoSuffixEnum.*;

/**
 * Created by stone on 7/6/16.
 */
public enum Bull100BetItemEnum implements IBetItem ,ICodeEnum {

    /** 黑桃赢 */
    SPADE_W(SPADE, W, "黑桃赢"),
    /** 黑桃输 */
    SPADE_L(SPADE, L, "黑桃输"),
    /** 红桃赢 */
    HEART_W(HEART, W, "红桃赢"),
    /** 红桃输  */
    HEART_L(HEART, L, "红桃输"),
    /** 梅花赢  */
    CLUB_W(CLUB, W, "梅花赢"),
    /** 梅花输  */
    CLUB_L(CLUB, L, "梅花输"),
    /** 方片赢  */
    DIAMOND_W(DIAMOND, W, "方片赢"),
    /** 方片输  */
    DIAMOND_L(DIAMOND, L, "方片输"),
    ;

    private final String code, trans;

    private final ICodeEnum prefixEnum, suffixEnum;

    Bull100BetItemEnum(ICodeEnum prefixEnum, ICodeEnum suffixEnum, String trans) {
        this.prefixEnum = prefixEnum;
        this.suffixEnum = suffixEnum;
        this.trans = trans;
        this.code = prefixEnum.getCode() + "_" + suffixEnum.getCode();
    }

    @Override
    public String toString() {
        return code;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getTrans() {
        return trans;
    }

    public String toDesc() {
        return null;
    }

    public ICodeEnum getPrefixEnum() {
        return prefixEnum;
    }

    public ICodeEnum getSuffixEnum() {
        return suffixEnum;
    }

    public static Bull100BetItemEnum enumOf(String code) {
        return EnumTool.enumOf(Bull100BetItemEnum.class, code);
    }

}
