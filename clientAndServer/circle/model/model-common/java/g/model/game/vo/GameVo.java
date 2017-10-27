package g.model.game.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.game.po.Game;
import g.model.game.so.GameSo;


/**
 * 游戏表值对象
 *
 * @author lenovo
 * @time 2016-8-25 14:17:12
 */
//region your codes 1
public class GameVo extends BaseObjectVo<Game, GameSo, GameVo.GameQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -4861232251354594020L;
    //endregion your codes 5

    /**
     *  游戏表查询逻辑
     */
    public static class GameQuery extends AbstractQuery<GameSo> {

        //region your codes 6
        private static final long serialVersionUID = 7189921034428417046L;
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