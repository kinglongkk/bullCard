package g.service.chesscard.engine.poker;

import g.service.chesscard.engine.model.BullCards;

/**
 * Created by longer on 2016/12/14.
 */
public interface IPokerGen {

    /**
     * 按需生成牌组
     *
     * @param groupSize 指定生成几组牌
     * @param cardSize  指定每组牌几张
     * @return
     */
    BullCards[] genPoker(int groupSize, int cardSize);

}
