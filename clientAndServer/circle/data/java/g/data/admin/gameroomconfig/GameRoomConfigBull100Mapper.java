package g.data.admin.gameroomconfig;

import g.model.admin.gameroomconfig.po.GameRoomConfigBull100;
import org.soul.data.rdb.mybatis.IBaseMapper;

import java.util.List;
import java.util.Map;


/**
 * 游戏房间配置信息表数据访问对象
 *
 * @author lenovo
 * @time 2016-12-17 17:20:19
 */
//region your codes 1
public interface GameRoomConfigBull100Mapper extends IBaseMapper<GameRoomConfigBull100, Integer> {

    /**
     * 根据房间id获取房间配置信息
     * @param roomId
     * @return
     */
    GameRoomConfigBull100 getGameRoomConfigBull100(Integer roomId);
    //region your codes 2

    /**
     * 查询奖池基本信息
     * @param map
     * @return
     */
    List<GameRoomConfigBull100> selectRoomList(Map map);

    /**
     * 查询奖池基本信息 总记录数
     * @param map
     * @return
     */
    Integer selectRoomListRecords(Map map);
    //endregion your codes 2

}