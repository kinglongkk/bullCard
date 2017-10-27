package g.service.admin.agent.game;

import g.data.admin.agent.game.UserAgentGameMapper;
import g.model.admin.agent.game.po.UserAgentGame;
import g.model.admin.agent.game.vo.UserAgentGameListVo;
import g.model.admin.agent.game.vo.UserAgentGameVo;
import org.soul.service.support.BaseService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


/**
 * 代理游戏服务
 *
 * @author black
 * @time 2016-12-5 12:01:04
 */
public class UserAgentGameService extends BaseService<UserAgentGameMapper, UserAgentGameListVo, UserAgentGameVo, UserAgentGame, Integer> implements IUserAgentGameService {

    @Transactional
    public boolean insertAgentGame(Map map) {

        return this.mapper.insertAgentGame(map);
    }

}