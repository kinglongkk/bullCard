package g.service.chesscard.engine.model;

import g.model.bet.BetTypeEnum;
import g.model.poker.PokerEnum;
import g.service.chesscard.DouniuMultiple;

import java.util.*;

/**
 * 这个类暂时不用, 等以后优化需要
 * 斗牛抽取概率及按概率发牌
 * Created by MK on 2016/11/4.
 */
@Deprecated
public class MkPokers {

    /**
     * 52张基本的牌
     */
    private static ThreadLocal<ArrayList<Integer>> localCards = new ThreadLocal<>();
    private static int[] chance = {3351, 4010, 4657, 5316, 5963, 6646, 7293, 7952, 8599, 9258, 10000};

    /**
     * 获取概率命中的索引
     */
    public int getChanceIndex() {
        int ran1 = ran.nextInt(10000);
        for (int i = 0; i < chance.length; i++) {
            if (ran1 < chance[i]) {
                return i;
            }
        }
        return 0;
    }

    private static List<Integer> createCards() {
        ArrayList<Integer> cards = localCards.get();
        if (cards == null) {
            cards = new ArrayList<>(52);
            for (PokerEnum pokerEnum : PokerEnum.values()) {
                cards.add(pokerEnum.getNum());
            }
        }
        Collections.shuffle(cards);
        ArrayList<Integer> newPokers = new ArrayList<>(cards);
        return newPokers;
    }

    static int[] initNumCnts = new int[10];

    static {//初始化一副牌1-10每个牌面的数量
        initNumCnts = new int[10];
        for (int i = 0; i < 9; i++) {
            initNumCnts[i] = 4;
        }
        initNumCnts[9] = 16;
    }

    private static void getNumCnts(int[] numCnts) {
        System.arraycopy(initNumCnts, 0, numCnts, 0, 10);
    }

    int[] numCnts = new int[10];
    List<Integer> pokerList = null;
    ArrayList<BullCards> cardsList;
    int indexes[] = new int[5];
    int nums[] = new int[5];
    private int max = 0, len = 0, ranIdx = 0, numNo = 0;
    Random ran = new Random();

    public List<BullCards> generateCards(BetTypeEnum betTypeEnum) {
        pokerList = createCards();
        cardsList = new ArrayList<>();
        len = pokerList.size();
        getNumCnts(numCnts);
        for (int i = 0; i < 5; i++) {
            int point = getChanceIndex();
            BullCards cards = new BullCards();
            int multiple = DouniuMultiple.getDouniuMultiple(point);
            cards.setCardsPoint(point);
            cards.setMultiple(multiple);
            max = 0;//重置1组5张牌的最大牌索引
            ranIdx = ran.nextInt(len);
            if (point == 0) {
                getCardListNotPoint(cards);
            } else {
                getCardListPoint(cards, point);
            }
            cardsList.add(cards);
        }
        return cardsList;
    }

    /**
     * 生成有点数的一组牌
     */
    private void getCardListPoint(BullCards cards, int point) {
        point = point == 10 ? 0 : point;
        removePoker(0, true);
        ArrayList<Integer> rs = new ArrayList<>(5);
        for (int j = 1; j < 5; j++) {
            forPokers:
            for (int i = 0; i < len; i++) {//取出其余4张牌
                indexes[j] = pokerList.get(ranIdx);
                boolean gotRight = false;//拿到的牌是否符合要求
                switch (j) {
                    case 1: {
                        nums[1] = cardToNum(indexes[1]);
                        nums[2] = 10 - ((nums[0] + nums[1]) % 10);
                        gotRight = numCnts[nums[2] - 1] > (nums[1] == nums[2] ? 1 : 0);//第三张可以凑牛的牌必须有
                        break;
                    }
                    case 2: {
                        gotRight = cardToNum(indexes[2]) == nums[2];//取出第三张牌
                        break;
                    }
                    case 3: {
                        nums[3] = cardToNum(indexes[3]);
                        nums[4] = ((point + 19 - nums[3]) % 10) + 1;
                        gotRight = (nums[3] == nums[4] && numCnts[nums[3] - 1] >= 2) || (numCnts[nums[3] - 1] > 0 && numCnts[nums[4] - 1] > 0);
                        break;
                    }
                    case 4: {
                        gotRight = cardToNum(indexes[4]) == nums[4];
                        break;
                    }
                }
                if (gotRight) {//第三张可以凑牛的牌必须有
                    removePoker(j, j == 2);//只有第3张牌才需要设置indexes和nums
                    break forPokers;
                } else {
                    addRanIdx();
                }
            }
        }
        setCards(cards);
    }

    /**
     * 生成没有点数的一组牌
     */
    private void getCardListNotPoint(BullCards cards) {
        removePoker(0, true);
        removePoker(1, true);
        numNo = 0;
        for (int j = 2; j < 5; j++) {
            for (int i = 0; i < j - 1; i++) {
                numNo = numNo | (1 << ((10 - ((nums[i] + nums[j - 1]) % 10)) - 1));
            }
            for (int i = 0; i < len; i++) {//取出第3-5张牌
                indexes[j] = pokerList.get(ranIdx);
                nums[j] = cardToNum(indexes[j]);
                if (((1 << (nums[j] - 1)) & numNo) == 0) {//通过二进制位判断某一个牌面1-10是否不能被拿去凑没牛的牌
                    removePoker(j, false);
                    break;
                } else {
                    addRanIdx();
                }
            }
        }
        setCards(cards);
    }

    private void addRanIdx() {
        if (++ranIdx >= len) ranIdx = 0;
    }

    private void removePoker(int index, boolean set) {
        int rem = pokerList.remove(ranIdx);
        if (ranIdx >= --len) ranIdx = 0;
        if (set) {
            indexes[index] = rem;
            nums[index] = cardToNum(rem);
        }
        numCnts[nums[index] - 1]--;
        if (max < rem) max = rem;
    }

    /**
     * 获取牌值
     */
    private static int cardToNum(int card) {
        if (card >= PokerEnum.DIAMOND_10.getNum()) {
            return 10;
        } else {
            return (card >> 2) + 1;
        }
    }

    public void setCards(BullCards cards) {
        cards.setCards(indexes);
        cards.setBiggestCard(max);
    }

}
