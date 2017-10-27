package g.service.chesscard.engine.model;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by tony on 2016/10/26.
 * 牌组拥有者类
 */
public class CardsHolder {

    private Map<CardsType, BullCards> cardsMap = new HashMap<>();

    private BullCards[] primitiveCardsList;

    /**
     * 获取原始牌组
     */
    public BullCards[] getPrimitiveCards() {
        return primitiveCardsList;
    }

    /**
     * 设置原始牌组
     */
    public void setPrimitiveCards(BullCards[] primitiveCards) {
        this.primitiveCardsList = primitiveCards;
    }

    private long profit = 0;

    /**
     * 设置盈利
     *
     * @param profit
     */
    public void setProfit(long profit) {
        this.profit = profit;
    }

    /**
     * 获取盈利
     *
     * @return profit
     */
    public long getProfit() {
        return profit;
    }

    /**
     * 获取牌组
     *
     * @param cardsType
     * @return BullCards
     */
    public BullCards getCards(CardsType cardsType) {
        return cardsMap.get(cardsType);
    }

    /**
     * 设置牌组
     *
     * @param cardsType
     * @param cards
     */
    public void setCards(CardsType cardsType, BullCards cards) {
        cardsMap.put(cardsType, cards);
    }

}
