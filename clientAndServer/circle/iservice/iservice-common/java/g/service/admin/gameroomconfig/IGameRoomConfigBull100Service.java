package g.service.admin.gameroomconfig;

import org.soul.iservice.support.IBaseService;
import g.model.admin.gameroomconfig.po.GameRoomConfigBull100;
import g.model.admin.gameroomconfig.vo.GameRoomConfigBull100ListVo;
import g.model.admin.gameroomconfig.vo.GameRoomConfigBull100Vo;

import java.util.List;
import java.util.Map;


/**
 * 游戏房间配置信息表服务接口
 *
 * @author lenovo
 * @time 2016-12-17 17:20:19
 */
public interface IGameRoomConfigBull100Service extends IBaseService<GameRoomConfigBull100ListVo, GameRoomConfigBull100Vo, GameRoomConfigBull100, Integer> {

    /**
     * 根据房间ID获取房间的配置信息
     * @param roomId
     * @return
     */
    GameRoomConfigBull100 getGameRoomConfigBull100(Integer roomId);
    //region your codes 2
    /**
     * 查询奖池基本信息
     * @param listVo
     * @return
     */
    GameRoomConfigBull100ListVo selectRoomList(GameRoomConfigBull100ListVo listVo);
    //endregion your codes 2

    /**
     * 更新实体
     * @param vo
     */
    void updateRoomConfig(GameRoomConfigBull100 vo);

    /**
     * 根据游戏模块ID，查询出当前模块下所有的房间配置
     * @param modelid       游戏模块ID
     * @return
     */
    List<GameRoomConfigBull100> queryRoomList(int modelid);
}