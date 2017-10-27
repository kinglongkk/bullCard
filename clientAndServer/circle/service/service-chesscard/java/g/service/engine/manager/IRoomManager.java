package g.service.engine.manager;

import g.model.room.po.VRoom;
import g.service.chesscard.engine.model.Room;

import java.util.List;

/**
 * Created by Double on 2016/9/29.
 */
public interface IRoomManager {

    /**
     * 获取房间模型
     *
     * @param roomId
     * @return
     */
    Room get(Integer roomId);

    /**
     * 保存房间
     *
     * @param room
     */
    void setById(VRoom room);

    /**
     * 初始化
     *
     * @param room
     */
    void init(VRoom room);

    /**
     * 销毁全部房间
     */
    void destroy();

    /**
     * 退出房间
     *
     * @param userId
     * @return
     */
    void exit(Integer userId);

    /**
     * 获取房间列表
     *
     * @param modelId
     * @return
     */
    List<VRoom> getRoomsByModelId(Integer modelId);

}
