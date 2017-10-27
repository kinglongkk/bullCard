package g.model.gamemodel.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.gamemodel.po.GameModel;
import g.model.gamemodel.so.GameModelSo;


/**
 * 游戏表值对象
 *
 * @author lenovo
 * @time 2016-8-25 14:20:28
 */
//region your codes 1
public class GameModelVo extends BaseObjectVo<GameModel, GameModelSo, GameModelVo.GameModelQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 8616486976925304201L;
    //endregion your codes 5

    /**
     *  游戏表查询逻辑
     */
    public static class GameModelQuery extends AbstractQuery<GameModelSo> {

        //region your codes 6
        private static final long serialVersionUID = 7024910165478697415L;
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