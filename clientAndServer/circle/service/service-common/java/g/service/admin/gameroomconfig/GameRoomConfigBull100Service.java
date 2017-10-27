package g.service.admin.gameroomconfig;

import g.data.admin.gameroomconfig.GameRoomConfigBull100Mapper;
import g.model.admin.gameroomconfig.po.GameRoomConfigBull100;
import g.model.admin.gameroomconfig.vo.GameRoomConfigBull100ListVo;
import g.model.admin.gameroomconfig.vo.GameRoomConfigBull100Vo;
import g.model.gameroom.po.GameRoom;
import g.model.gameroom.vo.GameRoomListVo;
import g.model.gameroom.vo.GameRoomVo;
import g.service.gameroom.IGameRoomService;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.query.Paging;
import org.soul.service.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 游戏房间配置信息表服务
 *
 * @author lenovo
 * @time 2016-12-17 17:20:19
 */
public class GameRoomConfigBull100Service extends BaseService<GameRoomConfigBull100Mapper, GameRoomConfigBull100ListVo, GameRoomConfigBull100Vo, GameRoomConfigBull100, Integer> implements IGameRoomConfigBull100Service {

    @Autowired
    private IGameRoomService gameRoomService;

    @Override
    public GameRoomConfigBull100 getGameRoomConfigBull100(Integer roomId) {
        return this.mapper.getGameRoomConfigBull100(roomId);
    }
//endregion your codes 1

    //region your codes 2
    public GameRoomConfigBull100ListVo selectRoomList(GameRoomConfigBull100ListVo listVo) {

        Map<String, Object> queryParams = listVo.getQueryParams();
        String where = (String)queryParams.get("where");
        if (StringTool.isBlank(where)) {
            where = "1=1";
            queryParams.put("where", where);
        }
        List<GameRoomConfigBull100> result = this.mapper.selectRoomList(queryParams);
        //分页操作
        Paging paging = listVo.getPaging();
        if (paging != null){
            paging.setTotalCount(this.mapper.selectRoomListRecords(queryParams));
            paging.cal();
        }
        listVo.setResult(result);
        return listVo;
    }
    //endregion your codes 2

    @Override
    public void updateRoomConfig(GameRoomConfigBull100 vo) {
        this.mapper.update(vo);
    }

    @Override
    public List<GameRoomConfigBull100> queryRoomList(int modelid) {

        List<GameRoomConfigBull100> confList = new ArrayList<GameRoomConfigBull100>();
        GameRoomListVo gameRoomListVo = new GameRoomListVo();
        gameRoomListVo.getSearch().setGameModelId(modelid);
        List<GameRoom> list = gameRoomService.search(gameRoomListVo).getResult();
        for(GameRoom gameRoom:list) {
            confList.add(this.mapper.getGameRoomConfigBull100(gameRoom.getId()));
        }


        return confList;
    }
}