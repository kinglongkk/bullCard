package g.service.admin.agent.game;

import org.soul.iservice.support.IBaseService;
import g.model.admin.agent.game.po.UserAgentGame;
import g.model.admin.agent.game.vo.UserAgentGameListVo;
import g.model.admin.agent.game.vo.UserAgentGameVo;

import java.util.Map;

/**
 * 代理游戏服务接口
 *
 * @author black
 * @time 2016-12-5 12:01:04
 */
public interface IUserAgentGameService extends IBaseService<UserAgentGameListVo, UserAgentGameVo, UserAgentGame, Integer> {

    /**
     * 新增代理游戏
     * @param map
     * @return
     */
    boolean insertAgentGame(Map map);
}