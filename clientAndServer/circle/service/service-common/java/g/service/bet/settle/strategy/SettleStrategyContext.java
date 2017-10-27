package g.service.bet.settle.strategy;

import g.model.bet.po.IBetSettle;

import java.util.Map;

/**
 * Created by longer on 6/30/16.
 */
public class SettleStrategyContext {

    private Map<String,AbstractSettleStrategy> strategyMap = null;

    public AbstractSettleStrategy decide(IBetSettle betSettle){
        return strategyMap.get(betSettle.getBetType());
    }

    public Map<String, AbstractSettleStrategy> getStrategyMap() {
        return strategyMap;
    }

    public void setStrategyMap(Map<String, AbstractSettleStrategy> strategyMap) {
        this.strategyMap = strategyMap;
    }
}
