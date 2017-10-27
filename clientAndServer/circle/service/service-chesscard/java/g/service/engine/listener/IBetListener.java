package g.service.engine.listener;

import g.service.chesscard.engine.listener.BetEvent;

/**
 * Created by Double on 2016/10/8.
 */
public interface IBetListener {

    /**
     * 投注前
     *
     * @param betEvent
     */
    void beforeBet(BetEvent betEvent);

    /**
     * 投注成功事件
     *
     * @param betEvent
     */
    void onBet(BetEvent betEvent);
}
