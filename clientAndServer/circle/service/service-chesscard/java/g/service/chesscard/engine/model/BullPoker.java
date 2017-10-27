package g.service.chesscard.engine.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * 一副斗牛扑克牌
 */
public class BullPoker {
    /**
     * 52张基本的牌
     */
    private static ThreadLocal<ArrayCardsList<Integer>> localCards = new ThreadLocal<>();
    /**
     * 初始化后的牌
     */
    private ArrayList<Integer> allCards;
    private Random random = new Random();

    public BullPoker() {
        allCards = createCards();
    }

    private ArrayList<Integer> createCards() {
        ArrayCardsList<Integer> cards = localCards.get();
        if (cards == null) {
            cards = new ArrayCardsList<>(52);
            for (int i = 0; i < 52; i++) {
                cards.add(i);
            }
        }
        cards.shuffle();
        ArrayList<Integer> newPokers = new ArrayList<>(cards);
        return newPokers;
    }

    /**
     * 随机抽取一组斗牛牌
     */
    public BullCards getCards() {
        BullCards bullCards = new BullCards();
        int[] cards = bullCards.getCards();
        for (int i = cards.length - 1; i >= 0; i--) {
            cards[i] = allCards.remove(allCards.size() - 1);
        }
        bullCards.calcBull(2047);
        return bullCards;
    }

    /**
     * 通过牛值随机抽取一组斗牛牌 , remove表示是否从所有剩余的牌中删除,为否则不能再使用该对象抽牌
     */
    public BullCards getCards(int bull, boolean remove) {
        BullCards bullCards = new BullCards();
        int[] cards = bullCards.getCards();
        long getIndexs = 0;
        int ranIdx = 0;
        int count = 1000000;
        for (; count > 0; count--) {
            for5cards:
            for (int i = 0; i < 5; i++) {
                ranCard:
                for (; count > 0; count--) {
                    ranIdx = random.nextInt(allCards.size());
                    if ((getIndexs & (1L << ranIdx)) != 0) {//这张牌是否抽到过
                        continue;
                    }
                    getIndexs = getIndexs | (1L << ranIdx);
                    break;
                }
                if (count == 0) {
                    throw new RuntimeException("遍历次数过多!");
                }
                cards[i] = allCards.get(ranIdx);
            }
            if(bullCards.calcBull(1<<bull))break;
            getIndexs = 0;
        }
        if (count == 0) {
            throw new RuntimeException("遍历次数过多!");
        }
        if (remove) {
            for (int i = allCards.size() - 1; i >= 0; i--) {
                if (((1L << i) & getIndexs) != 0) {
                    allCards.remove(i);
                }
            }
        }
        return bullCards;
    }

    /**
     * 随机抽取一张斗牛牌
     */
    public int getCard() {
        return allCards.remove(allCards.size() - 1);
    }


    public static BullCards[] generateCards(int count) {
        BullPoker bullPoker = new BullPoker();
        BullCards[] bcs = new BullCards[count];
        for (int i = 0; i < count; i++) {
            bcs[i] = bullPoker.getCards();
        }
        return bcs;
    }
}
