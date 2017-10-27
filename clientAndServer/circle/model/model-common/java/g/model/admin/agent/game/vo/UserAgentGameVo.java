package g.model.admin.agent.game.vo;

import g.model.admin.agent.game.po.UserAgentGame;
import g.model.admin.agent.game.so.UserAgentGameSo;
import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;


/**
 * 代理游戏值对象
 *
 * @author black
 * @time 2016-12-5 12:01:04
 */
public class UserAgentGameVo extends BaseObjectVo<UserAgentGame, UserAgentGameSo, UserAgentGameVo.UserAgentGameQuery> {

    private static final long serialVersionUID = -8767104690857414177L;

    /**
     *  代理游戏查询逻辑
     */
    public static class UserAgentGameQuery extends AbstractQuery<UserAgentGameSo> {

        private static final long serialVersionUID = 3520611846873849126L;

        @Override
        public Criteria getCriteria() {

            return null;
        }

    }

}