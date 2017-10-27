package g.service.game;

import g.data.game.GameMapper;
import g.model.game.po.Game;
import g.model.game.vo.GameListVo;
import g.model.game.vo.GameVo;
import org.soul.service.support.BaseService;

import java.util.List;
import java.util.Map;


/**
 * 游戏表服务
 *
 * @author lenovo
 * @time 2016-8-25 14:17:12
 */
//region your codes 1
public class GameService extends BaseService<GameMapper, GameListVo, GameVo, Game, Integer> implements IGameService {
//endregion your codes 1


    public boolean updateGameIsDelete(Map<String, Object> queryMap) {

        return this.mapper.updateGameIsDelete(queryMap);
    }

    @Override
    public List<Game> getGameList() {
        return this.mapper.getGameList();
    }

    public List<Game> selectGameWithoutAgent(Map map) {

        return this.mapper.selectGameWithoutAgent(map);
    }

    public List<Game> selectAgentGame(Map map) {

        return this.mapper.selectAgentGame(map);
    }

    public List<Game> selectFirstType(){

        return this.mapper.selectFirstType();
    }
}