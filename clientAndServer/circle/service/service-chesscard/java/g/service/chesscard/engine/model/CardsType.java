package g.service.chesscard.engine.model;

import g.model.chesscard.enums.Bull100BetItemPrefixEnum;
import org.soul.commons.enums.EnumTool;
import org.soul.commons.enums.ICodeEnum;

/**
 * Created by tony on 2016/10/26.
 * 牌组枚举
 */

public enum CardsType implements ICodeEnum {

    BANKER("BANKER", "庄家"),
    SPADE(Bull100BetItemPrefixEnum.SPADE.getCode(), Bull100BetItemPrefixEnum.SPADE.getTrans()),
    HEART(Bull100BetItemPrefixEnum.HEART.getCode(), Bull100BetItemPrefixEnum.HEART.getTrans()),
    CLUB(Bull100BetItemPrefixEnum.CLUB.getCode(), Bull100BetItemPrefixEnum.CLUB.getTrans()),
    DIAMOND(Bull100BetItemPrefixEnum.DIAMOND.getCode(), Bull100BetItemPrefixEnum.DIAMOND.getTrans()),;

    private String code;

    private String trans;

    CardsType(String code, String trans) {
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

    public static CardsType enumOf(String code) {
        return EnumTool.enumOf(CardsType.class, code);
    }

}
