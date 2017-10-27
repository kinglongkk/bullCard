package g.model.admin.agent.game.vo;

import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.admin.agent.game.po.UserAgentGame;
import g.model.admin.agent.game.so.UserAgentGameSo;

/**
 * 代理游戏列表页值对象
 *
 * @author black
 * @time 2016-12-5 12:01:04
 */
public class UserAgentGameListVo extends BaseListVo<UserAgentGame, UserAgentGameSo, UserAgentGameListVo.UserAgentGameQuery> {

    private static final long serialVersionUID = 8969473534918988516L;

    /**
     *  代理游戏列表查询逻辑
     */
    public static class UserAgentGameQuery extends AbstractQuery<UserAgentGameSo> {

        private static final long serialVersionUID = 6855638856902673969L;

        @Override
        public Criteria getCriteria() {

            Criteria criteria = Criteria.and(

                    Criteria.add(UserAgentGame.PROP_ID, Operator.EQ, this.searchObject.getId()),
                    Criteria.add(UserAgentGame.PROP_AGENT_ID, Operator.EQ, this.searchObject.getAgentId()),
                    Criteria.add(UserAgentGame.PROP_GAME_ID, Operator.EQ, this.searchObject.getGameId())
            );
            return criteria;
        }

    }

}