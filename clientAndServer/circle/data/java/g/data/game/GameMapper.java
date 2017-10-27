package g.data.game;

import g.model.game.po.Game;
import org.soul.data.rdb.mybatis.IBaseMapper;

import java.util.List;
import java.util.Map;


/**
 * 游戏表数据访问对象
 *
 * @author lenovo
 * @time 2016-8-25 14:17:12
 */
//region your codes 1
public interface GameMapper extends IBaseMapper<Game, Integer> {
//endregion your codes 1


    /**
     * 逻辑删除game
     * @param map
     * @return boolean
     */
    boolean updateGameIsDelete(Map<String, Object> map);

    /**
     * 获取游戏列表
     * @return
     */
    List<Game> getGameList();

    /**
     * 查询代理商未代理的游戏
     * @param map
     * @return
     */
    List<Game> selectGameWithoutAgent (Map map);

    /**
     * 查询代理商代理的游戏
     * @param map
     * @return
     */
    List<Game> selectAgentGame(Map map);

    /**
     * 查找游戏一级类型
     * @return
     */
    List<Game> selectFirstType();
}