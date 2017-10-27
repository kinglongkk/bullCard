package g.service.chesscard.engine.factory;

import g.model.enums.GameModelCodeEnum;
import g.service.engine.evaluator.IEvaluator;
import g.service.engine.listener.IMatchListener;
import g.service.engine.manager.IDeskManager;
import g.service.engine.manager.IRoomManager;

/**
 * Created by longer on 2016/12/19.
 */
public interface IModelFactory {

    /**
     * 根据玩法 获取房间管理器
     *
     * @param modelCode
     * @return
     */
    IRoomManager getRoomManager(String modelCode);

    /**
     * 根据房间ID 获取房间管理器
     *
     * @param roomId
     * @return
     */
    IRoomManager getRoomManager(Integer roomId);

    /**
     * 根据玩法 获取房间管理器
     *
     * @param deskId
     * @return
     */
    IDeskManager getDeskManager(Integer deskId);

    /**
     * 添加房间号 vs 房间管理器
     *
     * @param roomId
     * @param roomManager
     */
    void addRoomManager(Integer roomId, IRoomManager roomManager);

    /**
     * 添加桌子号 vs 桌子管理器
     *
     * @param roomId
     * @param deskId
     * @param deskManager
     */
    void addDeskManager(Integer roomId, Integer deskId, IDeskManager deskManager);

    /**
     * 通过桌子号获取桌子管理器
     *
     * @param deskId
     * @return
     */
    Integer getRoomId(Integer deskId);

    /**
     * 根据玩法 获取发牌机
     */
    IEvaluator getEvaluator(GameModelCodeEnum gameModel);

    /**
     * 根据桌子ID获取赛事处理器
     *
     * @param deskId
     * @return
     */
    IMatchListener getMatchListener(Integer deskId);

}
