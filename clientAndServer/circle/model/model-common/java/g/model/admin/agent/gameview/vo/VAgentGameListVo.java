package g.model.admin.agent.gameview.vo;

import g.model.admin.agent.gameview.po.VAgentGame;
import g.model.admin.agent.gameview.so.VAgentGameSo;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;


/**
 * 列表页值对象
 *
 * @author black
 * @time 2016-12-19 11:01:49
 */
//region your codes 1
public class VAgentGameListVo extends BaseListVo<VAgentGame, VAgentGameSo, VAgentGameListVo.VAgentGameQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 5517776241270268044L;
    //endregion your codes 5

    /**
     *  列表查询逻辑
     */
    public static class VAgentGameQuery extends AbstractQuery<VAgentGameSo> {

        //region your codes 6
        private static final long serialVersionUID = -772291400353452424L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            Criteria criteria = Criteria.and(

                    Criteria.add(VAgentGame.PROP_ID, Operator.EQ, this.searchObject.getId()),
                    Criteria.add(VAgentGame.PROP_NAME,Operator.EQ, this.searchObject.getName()),
                    Criteria.add(VAgentGame.PROP_STATUS, Operator.EQ, this.searchObject.getStatus()),
                    Criteria.add(VAgentGame.PROP_TYPE, Operator.EQ, this.searchObject.getType()),
                    Criteria.add(VAgentGame.PROP_FIRST_TYPE, Operator.EQ, this.searchObject.getFirstType()),
                    Criteria.add(VAgentGame.PROP_AGENT_ID, Operator.EQ, this.searchObject.getAgentId())
            );
            criteria.addAnd(VAgentGame.PROP_IS_DELETED, Operator.EQ, false);
            return criteria;
            //endregion your codes 2
        }


        //region your codes 3

        //endregion your codes 3
    }

    //region your codes 4

    //endregion your codes 4

}