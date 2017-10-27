package g.service.engine.manager;

import g.service.chesscard.engine.model.Room;
import g.service.chesscard.engine.model.SettleResult;

/**
 * Created by solo on 2017/3/21.
 */
public interface IBroadcastMessageManager {

    /**
     * 处理游戏结算产生的信息，来生成需要发送的消息
     * @param settleResult
     * @param roomId
     */
    public void createMsgFromGameSettle(SettleResult settleResult, int roomId);
}
