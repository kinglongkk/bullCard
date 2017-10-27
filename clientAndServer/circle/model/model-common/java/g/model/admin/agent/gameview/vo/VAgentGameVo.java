package g.model.admin.agent.gameview.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.admin.agent.gameview.po.VAgentGame;
import g.model.admin.agent.gameview.so.VAgentGameSo;


/**
 * 值对象
 *
 * @author black
 * @time 2016-12-19 11:01:49
 */
//region your codes 1
public class VAgentGameVo extends BaseObjectVo<VAgentGame, VAgentGameSo, VAgentGameVo.VAgentGameQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 4616160039015595671L;
    //endregion your codes 5

    /**
     *  查询逻辑
     */
    public static class VAgentGameQuery extends AbstractQuery<VAgentGameSo> {

        //region your codes 6
        private static final long serialVersionUID = 6520109883569092783L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            return null;
            //endregion your codes 2
        }

        //region your codes 3

        //endregion your codes 3

    }

    //region your codes 4

    //endregion your codes 4

}