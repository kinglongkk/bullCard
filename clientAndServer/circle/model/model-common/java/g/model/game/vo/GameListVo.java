package g.model.game.vo;

import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.game.po.Game;
import g.model.game.so.GameSo;


/**
 * 游戏表列表页值对象
 *
 * @author lenovo
 * @time 2016-8-25 14:17:12
 */
//region your codes 1
public class GameListVo extends BaseListVo<Game, GameSo, GameListVo.GameQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -605158132805403415L;
    //endregion your codes 5

    /**
     *  游戏表列表查询逻辑
     */
    public static class GameQuery extends AbstractQuery<GameSo> {

        //region your codes 6
        private static final long serialVersionUID = -3266022471776706857L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {

            Criteria criteria = Criteria.and(
                    Criteria.add(Game.PROP_ID, Operator.EQ, this.searchObject.getId()),
                    Criteria.add(Game.PROP_NAME, Operator.LIKE, this.searchObject.getName()),
                    Criteria.add(Game.PROP_CODE, Operator.LIKE, this.searchObject.getCode()),
                    Criteria.add(Game.PROP_STATUS, Operator.EQ, this.searchObject.getStatus()),
                    Criteria.add(Game.PROP_FIRST_TYPE, Operator.LIKE, this.searchObject.getFirstType()),
                    Criteria.add(Game.PROP_TYPE, Operator.LIKE, this.searchObject.getType())
            );
            criteria.addAnd(Game.PROP_IS_DELETED, Operator.EQ, false);
            return criteria;
        }

    }

}