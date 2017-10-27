package g.service.bet.settle.strategy;

import g.model.bet.BetResultEnum;
import g.model.bet.BetSettleStatus;
import g.model.bet.BetStatus;
import g.model.bet.po.Bet;
import g.model.bet.po.IBetSettle;
import g.model.bet.vo.BetVo;
import g.model.match.po.MatchResult;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by longer on 6/30/16.
 */
public abstract class AbstractSettleStrategy {

    protected static Log log = LogFactory.getLog(AbstractSettleStrategy.class);

    /**
     * 判断输赢
     * @return
     */
    public abstract BetResultEnum judge(IBetSettle betSettle,List<MatchResult> matchResults);

    /**
     * 赛果列表转换成以"赛果项"为Key的Map
     * @param matchResults
     * @return
     */
    protected Map<String, MatchResult> listToMap(List<MatchResult> matchResults) {
        Map<String,MatchResult> matchResultMap = new HashMap<>();
        for (MatchResult matchResult : matchResults) {
            matchResultMap.put(matchResult.getItemType(),matchResult);
        }
        return matchResultMap;
    }

    /**
     * 执行结算
     * @param betSettle
     * @param betResultEnum
     * @param isReSettle
     */
    public BetVo settle(IBetSettle betSettle, BetResultEnum betResultEnum, boolean isReSettle) {
        BetVo vo = new BetVo();
        Bet bet = new Bet();
        vo.setResult(bet);
        bet.setId(betSettle.getBetId());
        bet.setBetNo(betSettle.getBetNo());
        bet.setSysUserId(betSettle.getSysUserId());
        bet.setStatus(BetStatus.CONFIRMD.getCode());
        bet.setSettleStatus(BetSettleStatus.SETTLED.getCode());
        bet.setResult(betResultEnum.getCode());
        bet.setSave(betSettle.isSave());
        if (isReSettle){
            vo.setSingleAmountOld(betSettle.getSingleAmount());
            vo.setProfitAmountOld(betSettle.getProfitAmount());
        }

        calc(betSettle,betResultEnum);
        return vo;
    }

    /**
     * 计算投注的最终的余额
     * @param betSettle
     * @param betResultEnum
     */
    protected void calc(IBetSettle betSettle,BetResultEnum betResultEnum) {
        Double singleAmount = betSettle.getSingleAmount();
        Double ior = betSettle.getPoint();
        Double profitAmount = 0.0;
        Double effectiveAmount = 0.0;
        Double incBalance = 0.0;
        boolean isIorContain = isContainSingleAmount(betSettle.getBetType());
        //TODO:Jason 赔率需要根据事实调整
        switch (betResultEnum) {
            case WIN:
                profitAmount = singleAmount * (isIorContain ? ior-1 : ior);
                effectiveAmount = singleAmount;
                incBalance = singleAmount + profitAmount;
                break; //四舍五入
            case LOSE:
                profitAmount = -singleAmount;
                effectiveAmount = singleAmount;
                incBalance = 0.0;
                break;
            case HE :
                profitAmount = 0.0;
                effectiveAmount = 0.0;
                incBalance = singleAmount;
                break;
            case WIN_HALF:
                profitAmount = (singleAmount / 2) * (isIorContain ? ior-1 : ior);
                effectiveAmount = singleAmount / 2;
                incBalance = singleAmount / 2 + profitAmount;
                break; //四舍五入
            case LOSE_HALF:
                profitAmount = 0.0;
                effectiveAmount = -singleAmount / 2;
                incBalance = singleAmount / 2;
                break; //四舍五入
        }

        betSettle.setProfitAmount(profitAmount);
        betSettle.setEffectiveAmount(effectiveAmount);
        betSettle.setIncBalance(incBalance);
    }

    /**
     * 赔率是否包括本金
     * @param betTypeEnum
     * @return
     */
    public abstract boolean isContainSingleAmount(String betTypeEnum);
}
