package g.service.chesscard.engine.evaluator;

import g.model.bet.BetItem;
import g.model.bet.BetTypeEnum;
import g.model.chesscard.mo.UserBet;
import g.service.chesscard.engine.model.*;
import g.service.chesscard.enums.ParamEnum;
import g.service.chesscard.util.Probability;
import g.service.engine.manager.IParamManager;
import org.soul.commons.spring.utils.SpringTool;

import java.util.*;

/**
 * Created by stone on 2017/1/9.
 * 押宝发牌机
 */
public class BullBaoEvaluator extends AbstractEvaluator {

    @Override
    public CardsHolder evaluate(List<UserBet> userBetList, Integer deskId, boolean isSystemDealer, String preCards, long maxLose) {
        int firstCard = Integer.parseInt(preCards);
        CardsHolder cardsHolder = filter(maxLose, userBetList, firstCard, isSystemDealer);
        return cardsHolder;
    }

    /**
     * 通过资金池限制规则选出符合的牌组
     */
    private CardsHolder filter(long maxLose, List<UserBet> userBetList, int firstCard, boolean isSystemDealer) {
        BullCards cards = null;
        CardsHolder cardsHolder = new CardsHolder();
        if (userBetList.size() > 0) {
            //合并所有坑的投注
            int[] allBets = new int[11];
            long sumBets = 0;//所有投注总和
            for (UserBet bet : userBetList) {
                int rate = 1;
                if(bet.isAi()){
                    if(isSystemDealer){//系统当庄时AI的投注不作为结算开牌依据
                        continue;
                    }else{
                        rate = -1;
                    }
                }else if(!isSystemDealer){//玩家当庄的玩家投注不作为开牌依据
                    continue;
                }
                int betIndex = Integer.valueOf(BetItem.fromString(BetTypeEnum.BULL_BAO, bet.getBetItem()).getPrefixEnum().getCode());
                allBets[betIndex] += bet.getCoin();
                sumBets += bet.getCoin();
            }
            //TODO:Double Stone 考虑如何注入
            IParamManager paramManager = (IParamManager) SpringTool.getBean("paramManager");
            Map<String, String> beis = paramManager.get(ParamEnum.BULL_BAO_BEI);

            ArrayList<Integer> okPoints = new ArrayList<>(11);//可以开的点数
            int[] profits = new int[11];
            ArrayList<Integer> rates = new ArrayList<>(11);
            for (int i = 0; i < allBets.length; i++) {
                double bei = Double.valueOf(beis.get(String.valueOf(i)));
                int profit = (int) (sumBets - (bei+1) * allBets[i]);
                profits[i] = profit;
                if (profit < 0 && -profit > maxLose) {//输太多超过允许值
                    continue;
                } else {
                    okPoints.add(i);
                    rates.add((int) (100000000/(bei+1)));
                }
            }
            Probability prob = new Probability(rates);
            //通过可以开的点数开出另外4张牌
            cards = BullBaoPoker.getCards(firstCard, okPoints.get(prob.nextIndex()));
            cardsHolder.setProfit(profits[cards.getCardsPoint()]);
        } else {//没人投注,完全随机开
            cards = BullBaoPoker.getCards(firstCard);
        }
//        System.out.println(cards);
        cardsHolder.setCards(CardsType.BANKER, cards);
        return cardsHolder;
    }
}
