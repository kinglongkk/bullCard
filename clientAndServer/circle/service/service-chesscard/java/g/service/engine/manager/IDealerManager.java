package g.service.engine.manager;

import g.service.chesscard.engine.model.Desk;
import g.service.chesscard.engine.model.GameDealer;
import g.service.chesscard.engine.model.Player;

import java.util.List;
import java.util.Map;

/**
 * Created by Double on 2016/9/29.
 */
public interface IDealerManager {
    /**
     * 获取上庄列表
     *
     * @param deskId
     * @return
     */
    List<GameDealer> getDealerList(Integer deskId);

    /**
     * 申请上庄
     *
     * @param gameDealer
     * @return
     */
    String applyDealer(GameDealer gameDealer);

    /**
     * 上庄
     *
     * @param deskId
     * @return tony 临时调整：返回多个参数 down 下庄玩家id up 上庄玩家id
     */
    Map upDealer(Integer deskId);

    /**
     * 下庄 或 从下庄列表中删除
     *
     * @param player
     * @return
     */
    String downDealer(Player player);

    String downDealer(Player player, boolean request);

    /**
     * 上庄金币是否警告
     *
     * @param desk
     * @return 0不警告   1友情提示是否要续庄   2强制必须续庄
     */
    void dealerCoinIsWarning(Desk desk);

    /**
     * 续庄
     *
     * @param coin   需要续庄的金币数值
     * @return
     */
    String continueDealer(Player player, long coin);

    /**
     * 是否为庄家
     *
     * @param player
     * @return 0普通玩家   1在上庄列表   2庄家
     */
    Integer isDealer(Player player);

    /**
     * 设置下庄标识
     *
     * @param userId
     */
    void setDownDealerFlag(Integer userId, Integer deskId);

    /**
     * 获取下庄标识
     *
     * @param userId
     * @return
     */
    String getDownDealerFlag(Integer userId);

}
