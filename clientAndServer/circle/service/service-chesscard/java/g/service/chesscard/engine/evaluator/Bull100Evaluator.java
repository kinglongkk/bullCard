package g.service.chesscard.engine.evaluator;

import g.model.chesscard.mo.UserBet;
import g.model.chesscard.enums.BullBetBaoSuffixEnum;
import g.model.room.po.RoomPool;
import g.service.chesscard.DouniuMultiple;
import g.service.chesscard.engine.model.*;

import java.util.*;

/**
 * Created by tony on 2016/10/27.
 * 百人大战生成扑克评价审类
 */
public class Bull100Evaluator extends AbstractEvaluator {

    @Override
    public CardsHolder evaluate(List<UserBet> userBetList, Integer deskId, boolean isSystemDealer, String preCards, long maxLoseMoney) {
        BullCards[] cardsArray = BullPoker.generateCards(5);
        for (BullCards cards : cardsArray) {
            Integer bei = DouniuMultiple.getDouniuMultiple(cards.getCardsPoint());
            cards.setMultiple(bei);
        }
        CardsHolder cardsHolder = new CardsHolder();
        if (userBetList.size() > 0) {
            MergeBet mergeBet = mergeUserBet(userBetList, isSystemDealer);
            arrange(cardsHolder, cardsArray, mergeBet, 0, maxLoseMoney);
            cardsHolder.setPrimitiveCards(cardsArray);
            return cardsHolder;
        } else {
            int pos = 0;
            BullCards bankCards = null;
            for (CardsType cardsType : CardsType.values()) {
                if (cardsType == CardsType.BANKER) {
                    bankCards = cardsArray[pos];
                    cardsHolder.setCards(cardsType, bankCards);
                } else {
                    BullCards tempCards = cardsArray[pos];
                    tempCards.compare(bankCards);
                    cardsHolder.setCards(cardsType, tempCards);
                }
                pos++;
            }
            return cardsHolder;
        }
    }

    /**
     * 排列牌组
     */
    public boolean arrange(CardsHolder cardsHolder, BullCards[] list, MergeBet mb, int k, long maxLoseMoney) {
        if (k > 4) {
            int pos = 0;
            BullCards bankCards = null;
            cardsHolder.setProfit(0);
            for (CardsType cardsType : CardsType.values()) {
                if (cardsType == CardsType.BANKER) {
                    bankCards = list[pos];
                    cardsHolder.setCards(CardsType.BANKER, bankCards);
                } else {
                    BullCards tempCards = list[pos];
                    boolean isBig = tempCards.compare(bankCards);
                    int coin = mb.getBetCoin(isBig, cardsType) * (isBig ? tempCards.getMultiple() : bankCards.getMultiple());
                    cardsHolder.setCards(cardsType, tempCards);
                    cardsHolder.setProfit(cardsHolder.getProfit() + coin);
                }
                pos++;
            }
            if (maxLoseMoney + cardsHolder.getProfit() >= 0){
                return true;
            }
        } else {
            for (int i = k; i <= 4; i++) {
                swap(list, k, i);
                if(arrange(cardsHolder, list, mb, k + 1, maxLoseMoney)){
                    return true;
                }
                swap(list, k, i);
            }
        }
        return false;
    }

    private void swap(BullCards[] list, int k, int i) {
        BullCards c3 = list[k];
        list[k] = list[i];
        list[i] = c3;
    }

    /**
     * 合并投注
     */
    private MergeBet mergeUserBet(List<UserBet> userBets, boolean isSystemDealer) {
        MergeBet mergeBet = new MergeBet();
        mergeBet.merge(userBets, isSystemDealer);
        return mergeBet;
    }

    /**
     * 合并投注类
     */
    private static class MergeBet {

        private Map<CardsType, Integer> betMap = new HashMap<>();

        public MergeBet() {
            setBet(CardsType.SPADE, 0);
            setBet(CardsType.HEART, 0);
            setBet(CardsType.CLUB, 0);
            setBet(CardsType.DIAMOND, 0);
        }

        private void setBet(CardsType cardsType, int bet) {
            betMap.put(cardsType, bet);
        }

        /**
         * 获取投注金额以及方向
         */
        public int getBetCoin(boolean isBig, CardsType cardsType) {
            if (isBig) {
                return -betMap.get(cardsType);
            } else {
                return betMap.get(cardsType);
            }
        }

        /**
         * 合并投注
         */
        public void merge(List<UserBet> userBets, boolean isSystemDealer) {
            for (UserBet userBet : userBets) {
                int rate = 1;
                if(userBet.isAi()){
                    if(isSystemDealer){//系统当庄时AI的投注不作为结算开牌依据
                        continue;
                    }else{
                        rate = -1;
                    }
                }else if(!isSystemDealer){//玩家当庄的玩家投注不作为开牌依据
                    continue;
                }
                CardsType cardsType = CardsType.enumOf(userBet.getBetItemPrefix());
                if (isBetWin(userBet.getBetItemSubfix())) {
                    int coin = userBet.getCoin().intValue();
                    betMap.put(cardsType, (betMap.get(cardsType) + rate*coin));
                } else {
                    int coin = userBet.getCoin().intValue();
                    betMap.put(cardsType, (betMap.get(cardsType) - rate*coin));
                }
            }
        }

        /**
         * 判断投注输赢
         */
        private boolean isBetWin(String code) {
            if (code == null) {
                return false;
            }
            BullBetBaoSuffixEnum betWLItemSuffixEnum = BullBetBaoSuffixEnum.enumOf(code);
            switch (betWLItemSuffixEnum) {
                case W:
                    return true;
                case L:
                    return false;
            }
            return false;
        }

    }

    /**
     * 评审参数扩展类
     */
    public static class Bull100Params extends AbstractEvaluator.Params {

        private List<UserBet> userBets;

        private boolean isSystemBanker = true;

        /**
         * 是否为系统庄
         */
        public boolean isSystemBanker() {
            return isSystemBanker;
        }

        /**
         * 设置是否为系统庄
         */
        public void setSystemBanker(boolean systemBanker) {
            isSystemBanker = systemBanker;
        }

        /**
         * 获取用户投注
         */
        public List<UserBet> getUserBets() {
            return userBets;
        }

        /**
         * 设置用户投注
         */
        public void setUserBets(List<UserBet> userBets) {
            this.userBets = userBets;
        }
    }


}
