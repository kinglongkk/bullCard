package g.service.engine.manager;

import g.service.chesscard.engine.model.Desk;
import g.service.chesscard.engine.model.Player;
import g.service.chesscard.engine.model.Room;

import java.util.Set;

/**
 * Created by Double on 2016/9/29.
 */
public interface IDeskManager {

    /**
     * 初始化
     * @param room
     */
    void init(Room room);

    /**
     * 获取桌子对象
     *
     * @param deskId
     * @return
     */
    Desk byId(Integer deskId);

    /**
     * 给玩家分配桌子
     *
     * @param player
     * @param roomId
     * @return
     */
    Desk allocate(Player player, int roomId);

    /**
     * 销毁全部桌子
     */
    void destroy(Integer deskId);

    /**
     * 通过用户ID获取桌子
     * @param userId
     * @return
     */
    Desk byUserId(Integer userId);

    /**
     * 获取桌子下所有玩家ID列表
     * @param deskId
     * @return
     */
    Set<String> getDeskUsers(Integer deskId);

    /**
     * 获取庄家ID
     * @param deskId
     * @return
     */
    Integer getDealerId(Integer deskId);
}
