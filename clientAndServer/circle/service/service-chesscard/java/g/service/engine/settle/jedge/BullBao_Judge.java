package g.service.engine.settle.jedge;

import g.model.bet.BetItem;
import g.model.bet.BetResultEnum;
import g.model.bet.BetTypeEnum;
import g.model.bet.IBetItem;
import g.model.bet.po.IBetSettle;
import g.model.match.po.MatchResult;
import g.service.bet.settle.judge.Judge;
import g.service.chesscard.engine.EngineConst;
import g.service.chesscard.engine.model.CardsType;
import g.service.chesscard.enums.ParamEnum;
import g.service.engine.manager.IParamManager;
import org.soul.commons.spring.utils.SpringTool;

import java.util.Map;

/**
 * Created by Double on 2016/10/18.
 * 点数
 */
public class BullBao_Judge extends Judge {

    @Override
    public BetResultEnum judge() {
        IBetSettle betSettle = this.getBetSettle();
        String betItemStr = betSettle.getIorField();
        IBetItem betItem = BetItem.fromString(BetTypeEnum.BULL_BAO, betItemStr);
        int item = Integer.valueOf(betItem.getPrefixEnum().getCode());

        MatchResult matchResult = this.getMatchResult(CardsType.BANKER.getCode());
        int point = matchResult.getHostPoint();
        //TODO:Double Stone 考虑如何注入
        IParamManager paramManager = (IParamManager) SpringTool.getBean("paramManager");
        Map<String, String> beis = paramManager.get(ParamEnum.BULL_BAO_BEI);
        Double bei = Double.valueOf(beis.get(betItem.getPrefixEnum().getCode()));
        betSettle.setWaterPoint(EngineConst.WATER_POINT);
        this.setBetSettle(betSettle);

        if (item == point) {
            betSettle.setPoint(bei);
            return BetResultEnum.WIN;
        } else {
            betSettle.setPoint(1d);
            return BetResultEnum.LOSE;
        }
    }
}
