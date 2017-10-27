package g.service.chesscard.engine.model;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 一副斗牛夺宝扑克牌
 * 每个线程只需要new一个BullBaoPoker就可以
 */
public class BullBaoPoker {

    /**
     * 随机抽取一张斗牛牌
     */
    public static int getCard() {
        return ThreadLocalRandom.current().nextInt(52);
    }


    /**
     * 通过已有的一张牌,随机抽取一组斗牛牌
     */
    public static BullCards getCards(int firstCard) {
        return getCardsByBullBit(firstCard, 2047);//所有牌都可以
    }

    /**
     * 通过一个牛值随机抽取一组斗牛牌
     */
    public static BullCards getCards(int firstCard, int bull) {
        return getCardsByBullBit(firstCard, 1 << bull);
    }

    /**
     * 通过一组可以生成的牛值随机抽取一组斗牛牌
     */
    public static BullCards getCards(int firstCard, ArrayList<Integer> bulls) {
        int bullBit = 0;
        for (int i = bulls.size() - 1; i >= 0; i--) {
            bullBit = bullBit | (1 << bulls.get(i)); //把所有可以生成的牛值按位塞到bullBit里
        }
        return getCardsByBullBit(firstCard, bullBit);
    }

    /**
     * 通过按位牛值随机抽取一组斗牛牌
     */
    private static BullCards getCardsByBullBit(int firstCard, int bullBit) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        BullCards bullCards = new BullCards();
        int[] cards = bullCards.getCards();
        cards[0] = firstCard;
        int next = 0;
        long getIndexs = 1L << firstCard;
        int ranIdx = 0;
        int count = 1000000;
        for (; count > 0; count--) {
            for5cards:
            for (int i = 1; i < 5; i++) {
                ranCard:
                for (; count > 0; count--) {
                    ranIdx = random.nextInt(52);
                    if ((getIndexs & (1L << ranIdx)) != 0) {//这张牌是否抽到过
                        continue;
                    }
                    getIndexs = getIndexs | (1L << ranIdx);
                    break;
                }
                if (count == 0) {
                    throw new RuntimeException("遍历次数过多!");
                }
                cards[i] = ranIdx;
            }
            if(bullCards.calcBull(bullBit))break;//可以与多个需要的牛值相匹配
            getIndexs = 1L << firstCard;
        }
        if (count == 0) {
            throw new RuntimeException("遍历次数过多!");
        }
        return bullCards;
    }

}
