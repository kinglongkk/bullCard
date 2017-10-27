package g.model.gamemodel.vo;

import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.gamemodel.po.GameModel;
import g.model.gamemodel.so.GameModelSo;


/**
 * 游戏表列表页值对象
 *
 * @author lenovo
 * @time 2016-8-25 14:20:28
 */
//region your codes 1
public class GameModelListVo extends BaseListVo<GameModel, GameModelSo, GameModelListVo.GameModelQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 3900674791025645953L;
    //endregion your codes 5

    /**
     *  游戏表列表查询逻辑
     */
    public static class GameModelQuery extends AbstractQuery<GameModelSo> {

        //region your codes 6
        private static final long serialVersionUID = -1043085794718824215L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {

            Criteria criteria = Criteria.and(

                    Criteria.add(GameModel.PROP_ID, Operator.EQ, this.searchObject.getId()),
                    Criteria.add(GameModel.PROP_GAME_ID, Operator.EQ, this.searchObject.getGameId()),
                    Criteria.add(GameModel.PROP_NAME, Operator.LIKE, this.searchObject.getName()),
                    Criteria.add(GameModel.PROP_ICON, Operator.EQ, this.searchObject.getIcon()),
                    Criteria.add(GameModel.PROP_CODE, Operator.LIKE, this.searchObject.getCode()),
                    Criteria.add(GameModel.PROP_STATUS, Operator.EQ, this.searchObject.getStatus())

            );
            return criteria;
        }


        //region your codes 3

        //endregion your codes 3
    }

    //region your codes 4

    //endregion your codes 4

}