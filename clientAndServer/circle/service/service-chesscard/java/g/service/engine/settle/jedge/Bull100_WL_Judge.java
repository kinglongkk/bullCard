package g.service.engine.settle.jedge;

import g.model.bet.BetItem;
import g.model.bet.BetResultEnum;
import g.model.bet.BetTypeEnum;
import g.model.bet.IBetItem;
import g.model.chesscard.enums.BullBetBaoSuffixEnum;
import g.model.bet.po.IBetSettle;
import g.model.match.po.MatchResult;
import g.service.bet.settle.judge.Judge;
import g.service.chesscard.DouniuMultiple;
import g.service.chesscard.engine.EngineConst;
import org.soul.commons.lang.string.StringTool;

/**
 * Created by Double on 2016/10/18.
 * 百人输赢
 */
public class Bull100_WL_Judge extends Judge {

    @Override
    public BetResultEnum judge() {
        //TODO: Jason 实现
        IBetSettle betSettle = this.getBetSettle();
        BetTypeEnum betTypeEnum = BetTypeEnum.enumOf(betSettle.getBetType());
        String betItem = betSettle.getIorField();
        IBetItem item = BetItem.fromString(betTypeEnum, betItem);
        String subfix = item.getSuffixEnum().getCode();
        MatchResult matchResult = this.getMatchResult(item.getPrefixEnum().getCode());
        int bankerPoint = matchResult.getHostPoint();
        Integer bankerMultiple = DouniuMultiple.getDouniuMultiple(bankerPoint);
        int playerPoint = matchResult.getClientPoint();
        Integer playerMultiple = DouniuMultiple.getDouniuMultiple(playerPoint);
        Integer multiple = bankerMultiple > playerMultiple ? bankerMultiple : playerMultiple;
        betSettle.setPoint(multiple.doubleValue());
        betSettle.setWaterPoint(EngineConst.WATER_POINT);
        this.setBetSettle(betSettle);
        String judge = matchResult.getOutcome();
        if (StringTool.equals("0", judge)) { //当前牌型是输
            if (StringTool.equals(subfix, BullBetBaoSuffixEnum.W.getCode())) {
                return BetResultEnum.LOSE;
            } else if (StringTool.equals(subfix, BullBetBaoSuffixEnum.L.getCode())) {
                return BetResultEnum.WIN;
            }
        } else { //当前牌型是赢
            if (StringTool.equals(subfix, BullBetBaoSuffixEnum.W.getCode())) {
                return BetResultEnum.WIN;
            } else if (StringTool.equals(subfix, BullBetBaoSuffixEnum.L.getCode())) {
                return BetResultEnum.LOSE;
            }
        }
        return null;
    }
}
