package g.service.engine.manager;

import g.service.chesscard.engine.model.SettleResult;

/**
 * Created by Jason on 16/10/10.
 */
public interface ISettleManager {
    /**
     * 新的结算方法
     *
     * @param deskId
     * @param matchId
     * @param preCards 先发的牌
     * @return
     */
    SettleResult newSettle(Integer deskId, Long matchId, String preCards);
}
