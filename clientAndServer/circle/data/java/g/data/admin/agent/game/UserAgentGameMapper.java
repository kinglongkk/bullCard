package g.data.admin.agent.game;

import g.model.admin.agent.game.po.UserAgentGame;
import org.soul.data.rdb.mybatis.IBaseMapper;

import java.util.Map;


/**
 * 代理游戏数据访问对象
 *
 * @author black
 * @time 2016-12-5 12:01:04
 */
public interface UserAgentGameMapper extends IBaseMapper<UserAgentGame, Integer> {

    /**
     * 新增代理游戏
     * @param map
     * @return
     */
    boolean insertAgentGame(Map map);

}