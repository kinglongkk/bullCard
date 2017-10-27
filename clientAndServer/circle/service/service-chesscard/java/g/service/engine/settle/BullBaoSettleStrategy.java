package g.service.engine.settle;

import g.model.bet.BetResultEnum;
import g.model.bet.po.IBetSettle;

/**
 * Created by Double on 2016/10/18.
 * 斗牛结算策略--百人大战
 */
public class BullBaoSettleStrategy extends BullSettleStrategy {

    @Override
    protected void calc(IBetSettle betSettle, BetResultEnum betResultEnum) {
        Double singleAmount = betSettle.getSingleAmount();
        Double fee = betSettle.getWaterPoint();
        Double ior = betSettle.getPoint();
        Double profitAmount = 0.0;
        Double effectiveAmount = 0.0;
        Double incBalance = 0.0;
        Double incCoin = 0.0;
        Double incBankerCoin = 0.0;
        long water = 0;
        switch (betResultEnum) {
            case WIN:
                profitAmount = singleAmount * ior * fee;
                water = (long) (singleAmount * ior - profitAmount);
                effectiveAmount = singleAmount;
                incBalance = profitAmount;
                incCoin = singleAmount + profitAmount;
                incBankerCoin = -singleAmount * ior;
                break; //四舍五入
            case LOSE:
                profitAmount = -singleAmount;
                effectiveAmount = singleAmount;
                incBalance = profitAmount;
                incCoin = 0d;
                incBankerCoin = singleAmount * ior;
                break;
        }

        betSettle.setProfitAmount(profitAmount);
        betSettle.setEffectiveAmount(effectiveAmount);
        betSettle.setIncBalance(incBalance);
        betSettle.setIncCoin(incCoin);
        betSettle.setIncBankerCoin(incBankerCoin);
        betSettle.setWaterAmount(water);
    }
}
