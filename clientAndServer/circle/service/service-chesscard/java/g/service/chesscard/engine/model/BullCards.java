package g.service.chesscard.engine.model;

/**
 * 牛牛牌组,5张牌
 */
public class BullCards implements Cloneable {

    private int[] cards = new int[5];
    /**
     * 牛值
     */
    private int cardsPoint = 0;
    /**
     * 最大的牌
     */
    private int biggestCard;
    /**
     * 倍数
     */
    private int multiple;
    /**
     * 跟庄家对比牌大小
     */
    private boolean bigger = false;

    public BullCards() {
    }

    public void setCards(int[] cards) {
        this.cards = cards;
    }

    public int[] getCards() {
        return cards;
    }

    public int getCardsPoint() {
        return cardsPoint;
    }

    public void setCardsPoint(int cardsPoint) {
        this.cardsPoint = cardsPoint;
    }

    public int getBiggestCard() {
        return biggestCard;
    }

    public void setBiggestCard(int biggestCard) {
        this.biggestCard = biggestCard;
    }

    public int getMultiple() {
        return multiple;
    }

    public void setMultiple(int multiple) {
        this.multiple = multiple;
    }

    public boolean isBigger() {
        return bigger;
    }

    public void setBigger(boolean bigger) {
        this.bigger = bigger;
    }

    /**
     * 通过计算是否符合需要的牛几
     * @param bullBit 需要的牌值,按位取
     * @return 是否拿到
     */
    public boolean calcBull(int bullBit) {
        int count2 = 0;//任意2张牌的求余值
        int count5 = 0;//前5张牌的求余值
        int cardPoint = 0;
        biggestCard = 0;
        //先算出5张牌的求余值A,若任意俩张的求余值B=A,则为有牛
        for (int i = 0; i < 5; i++) {
            int card = cards[i];
            if (card > biggestCard) {
                biggestCard = card;
            }
            cardPoint = (card >> 2) + 1;
            count5 += cardPoint >= 10 ? 0 : cardPoint;
        }
        count5 = count5 % 10;
        for (int i = 0; i < 4; i++) {
            cardPoint = (cards[i] >> 2) + 1;
            int cart1 = cardPoint >= 10 ? 0 : cardPoint;
            for (int j = i + 1; j < 5; j++) {
                cardPoint = (cards[j] >> 2) + 1;
                int cart2 = cardPoint >= 10 ? 0 : cardPoint;
                count2 = (cart1 + cart2) % 10;
                if (count2 == count5) {//有牛
                    if (count5 == 0) {
                        count5 = 10;//牛牛
                    }
                    this.cardsPoint = count5;
                    if((bullBit&(1<<count5))!=0){//得到需要的牌才去换位置
                        swap(j, 4);//把有牛的3张牌拿到前面
                        swap(i, 3);
                        return true;
                    }
                    return false;//牛N
                }
            }
        }
        this.cardsPoint = 0;
        return (bullBit&1)!=0;//没牛
    }

    private void swap(int i1, int i2) {
        int tmp = cards[i1];
        cards[i1] = cards[i2];
        cards[i2] = tmp;
    }

    /**
     * 其他牌和庄家牌比较,是否更大
     */
    public boolean compare(BullCards bankCards) {
        if (bankCards.cardsPoint < cardsPoint || (bankCards.cardsPoint == cardsPoint && biggestCard > bankCards.biggestCard)) {
            bigger = true;
        } else bigger = false;
        return bigger;
    }

    public BullCards clone() {
        BullCards cards = null;
        try {
            cards = (BullCards) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return cards;
    }

    public static final String[] colorStrs = {"♦", "♣", "♥", "♠"};
    public static final String[] numStrs = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("牛");
        sb.append(cardsPoint).append(", 牌组:[");
        for (int i = 0; i < cards.length; i++) {
            if (i != 0) sb.append(", ");
            int card = cards[i];
            sb.append(colorStrs[card & 3]).append(numStrs[card >> 2]);
        }
        sb.append("]");
        return sb.toString();
    }
}
