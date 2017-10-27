package g.model.gameroom.vo;


import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.gameroom.po.GameRoom;
import g.model.gameroom.so.GameRoomSo;


/**
 * 游戏房间表值对象
 *
 * @author lenovo
 * @time 2017-2-14 16:57:58
 */
//region your codes 1
public class GameRoomVo extends BaseObjectVo<GameRoom, GameRoomSo, GameRoomVo.GameRoomQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -4942895677815150062L;
    //endregion your codes 5

    /**
     *  游戏房间表查询逻辑
     */
    public static class GameRoomQuery extends AbstractQuery<GameRoomSo> {

        //region your codes 6
        private static final long serialVersionUID = -3595544907806170061L;
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