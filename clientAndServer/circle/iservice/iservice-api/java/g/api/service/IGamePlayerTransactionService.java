package g.api.service;

import g.model.player.po.PlayerTransaction;

/**
 * 玩家预转账操作
 * Created by black on 2017/3/28.
 */
public interface IGamePlayerTransactionService {

    /**
     * 将玩家的预转账信息发送给服务端
     * @param playerTransaction
     */
    void sendTransactionMessage(PlayerTransaction playerTransaction);
}
