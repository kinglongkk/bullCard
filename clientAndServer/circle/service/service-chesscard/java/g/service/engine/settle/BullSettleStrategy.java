package g.service.engine.settle;

import g.model.bet.BetResultEnum;
import g.model.bet.BetTypeEnum;
import g.model.bet.po.Bet;
import g.model.bet.po.IBetSettle;
import g.model.bet.vo.BetVo;
import g.model.match.po.MatchResult;
import g.service.bet.settle.judge.Judge;
import g.service.bet.settle.strategy.AbstractSettleStrategy;
import g.service.engine.settle.jedge.BullBao_Judge;
import g.service.engine.settle.jedge.Bull100_WL_Judge;

import java.util.List;
import java.util.Map;

/**
 * Created by Double on 2016/10/18.
 * 斗牛结算策略
 */
public class BullSettleStrategy extends AbstractSettleStrategy {

    @Override
    public BetVo settle(IBetSettle betSettle, BetResultEnum betResultEnum, boolean isReSettle) {
        BetVo vo = new BetVo();
        Bet bet = (Bet) betSettle;
        vo.setResult(bet);
        calc(betSettle, betResultEnum);
        return vo;
    }

    @Override
    public BetResultEnum judge(IBetSettle betSettle, List<MatchResult> matchResults) {
        Map<String, MatchResult> matchResultMap = listToMap(matchResults);
        Judge judge = byMatchResult(matchResultMap, betSettle);
        if (judge == null) {
            log.error("注单号:{0},比赛ID:{1},玩法:{2},赔率字段:{3}", betSettle.getBetNo(), betSettle.getMatchId(), betSettle.getBetType(), betSettle.getIorField());
        }
        return judge.judge();
    }

    private Judge byMatchResult(Map<String, MatchResult> matchResultMap, IBetSettle betSettle) {
        BetTypeEnum betTypeEnum = BetTypeEnum.enumOf(betSettle.getBetType());
        Judge judge = mappingJudge(betTypeEnum);
        if (judge == null) {
            return null;
        }
        judge.setMatchResults(matchResultMap);
        judge.setBetSettle(betSettle);
        return judge;
    }

    /**
     * 通过投注类型,获取判断输赢的"法官类"
     *
     * @param betTypeEnum
     * @return
     */
    private Judge mappingJudge(BetTypeEnum betTypeEnum) {
        switch (betTypeEnum) {
            case BULL_BAO:
                return new BullBao_Judge();
            case BULL_100:
                return new Bull100_WL_Judge();
        }
        return null;
    }

    @Override
    public boolean isContainSingleAmount(String betTypeEnum) {
        return false;
    }
}
