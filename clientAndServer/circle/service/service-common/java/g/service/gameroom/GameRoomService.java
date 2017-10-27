package g.service.gameroom;

import java.util.List;

import g.model.admin.gameroomconfig.vo.GameRoomConfigBull100Vo;
import g.service.admin.gameroomconfig.GameRoomConfigBull100Service;
import g.service.admin.gameroomconfig.IGameRoomConfigBull100Service;
import org.soul.service.support.BaseService;
import g.data.gameroom.GameRoomMapper;
import g.service.gameroom.IGameRoomService;
import g.model.gameroom.po.GameRoom;
import g.model.gameroom.vo.GameRoomListVo;
import g.model.gameroom.vo.GameRoomVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


/**
 * 游戏房间表服务
 *
 * @author lenovo
 * @time 2017-2-14 16:57:58
 */
//region your codes 1
public class GameRoomService extends BaseService<GameRoomMapper, GameRoomListVo, GameRoomVo, GameRoom, Integer> implements IGameRoomService {

    @Autowired
    public IGameRoomConfigBull100Service gameRoomConfigBull100Service;

    @Override
    public GameRoom getGameRom(Integer id) {
      return  this.mapper.get(id);
    }


//endregion your codes 1

    //region your codes 2
    @Override
    @Transactional
    public GameRoomVo addGameRoom(GameRoomVo gameRoomVo, GameRoomConfigBull100Vo gameRoomConfVo) {
        if(gameRoomVo.getResult().getId()==null || "".equals(gameRoomVo.getResult().getId())){
            boolean bl = this.mapper.insert(gameRoomVo.getResult());
            if(bl) {
                gameRoomConfVo.getResult().setRoomId(gameRoomVo.getResult().getId());
                gameRoomConfVo = gameRoomConfigBull100Service.insert(gameRoomConfVo);
                gameRoomVo.setSuccess(gameRoomConfVo.isSuccess());
            }
        }else{
            GameRoomVo roomVo = new GameRoomVo();
            GameRoom gameRoom = new GameRoom();
            gameRoom.setId(gameRoomVo.getResult().getId());
            roomVo.setResult(gameRoom);
            roomVo = this.search(roomVo);

            roomVo.getResult().setGameId(gameRoomVo.getResult().getGameId());
            roomVo.getResult().setGameModelId(gameRoomVo.getResult().getGameModelId());
            roomVo.getResult().setName(gameRoomVo.getResult().getName());
            roomVo.getResult().setStatus(gameRoomVo.getResult().getStatus());
            roomVo.getResult().setDescription(gameRoomVo.getResult().getDescription());
            roomVo.getResult().setMaxLimitPlayerNumber(gameRoomVo.getResult().getMaxLimitPlayerNumber());
            roomVo.getResult().setMinLimitPlayerBlance(gameRoomVo.getResult().getMinLimitPlayerBlance());
            gameRoomVo = this.update(roomVo);
            gameRoomVo.setSuccess(true);
        }
        return gameRoomVo;
    }

    //endregion your codes 2

}