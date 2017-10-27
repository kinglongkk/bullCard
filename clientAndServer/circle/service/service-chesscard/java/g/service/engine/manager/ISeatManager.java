package g.service.engine.manager;

import g.service.chesscard.engine.model.Desk;
import g.service.chesscard.engine.model.GameDeskSeat;
import g.service.chesscard.engine.model.Player;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 座位管理器
 * Created by Jason
 */
public interface ISeatManager {

    /**
     * 加载
     * @param desk
     */
    void init(Desk desk);

    /**
     * 销毁全部座位
     */
    void destroy();

    /**
     * 获取桌子上的在座位列表
     *
     * @param deskId
     * @return
     */
    List<GameDeskSeat> getSeatList(Integer deskId);

    /**
     * 获取桌子上座位与用户的对应关系
     *
     * @param deskId
     * @return key      seatNo
     *         value    userId
     */
    Map<String, String> getSeatUser(Integer deskId);

    /**
     * 获取桌子上空的座位
     * @param deskId
     * @return
     */
    Set<String> getSeatVoid(Integer deskId);

    /**
     * 入座
     *
     * @param desk
     * @param player
     * @return 座位号
     */
    Integer atSeat(Desk desk, Player player);

    /**
     * 离座
     *
     * @param gameDeskSeat
     * @return 之前入座的座位号
     */
    void outSeat(GameDeskSeat gameDeskSeat);


}

