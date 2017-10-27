package g.service.gameroom;

import java.util.List;

import g.model.admin.gameroomconfig.vo.GameRoomConfigBull100Vo;
import org.soul.iservice.support.IBaseService;
import g.model.gameroom.po.GameRoom;
import g.model.gameroom.vo.GameRoomListVo;
import g.model.gameroom.vo.GameRoomVo;


/**
 * 游戏房间表服务接口
 *
 * @author lenovo
 * @time 2017-2-14 16:57:58
 */
//region your codes 1
public interface IGameRoomService extends IBaseService<GameRoomListVo, GameRoomVo, GameRoom, Integer> {
    //region your codes 2

    //endregion your codes 1

    //region your codes 2

    //endregion your codes 2

    /**
     * 获取游戏房间实体
     * @param id
     * @return
     */
    GameRoom getGameRom(Integer id);

    /**
     * 新增游戏房间
     * @param gameRoomVo      游戏房间
     * @param gameRoomConfVo        游戏房间配置
     */
    public GameRoomVo addGameRoom(GameRoomVo gameRoomVo, GameRoomConfigBull100Vo gameRoomConfVo);
}