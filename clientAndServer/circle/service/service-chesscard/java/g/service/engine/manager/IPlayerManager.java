package g.service.engine.manager;

import g.model.bet.po.IBetSettle;
import g.model.player.po.UserPlayer;
import g.service.chesscard.engine.manager.CoinPiple;
import g.service.chesscard.engine.model.Player;

import java.util.List;

/**
 * Created by Jason on 2016/11/16.
 */
public interface IPlayerManager {

    /**
     * 取用户对象*
     *
     * @param id
     * @return
     */
    Player get(Integer id);

    /**
     * 获取用户对象
     *
     * @return
     */
    UserPlayer getById(Integer userId);

    /**
     * 初始化   登录时候
     *
     * @param userPlayer
     */
    void init(UserPlayer userPlayer);

    /**
     * 根据userIds获取用户列表
     *
     * @param userIds
     * @return
     */
    List<UserPlayer> getByIds(List<Integer> userIds);

    /**
     * 指量更新金币
     *
     * @param betSettles
     */
    CoinPiple batchUpdateCoin(List<? extends IBetSettle> betSettles);


}
