package g.model.gameroom.vo;

import g.model.gameroom.po.GameRoom;
import g.model.gameroom.so.GameRoomSo;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;


/**
 * 游戏房间表列表页值对象
 *
 * @author lenovo
 * @time 2017-2-14 16:57:58
 */
//region your codes 1
public class GameRoomListVo extends BaseListVo<GameRoom, GameRoomSo, GameRoomListVo.GameRoomQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -8368150360580850698L;
    //endregion your codes 5

    /**
     *  游戏房间表列表查询逻辑
     */
    public static class GameRoomQuery extends AbstractQuery<GameRoomSo> {

        //region your codes 6
        private static final long serialVersionUID = -1530461885721201218L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            Criteria criteria = Criteria.and(
                    Criteria.add(GameRoom.PROP_ID, Operator.EQ, this.searchObject.getId()),
                    Criteria.add(GameRoom.PROP_GAME_ID, Operator.EQ, this.searchObject.getGameId()),
                    Criteria.add(GameRoom.PROP_GAME_MODEL_ID, Operator.EQ, this.searchObject.getGameModelId()),
                    Criteria.add(GameRoom.PROP_NAME, Operator.LIKE, this.searchObject.getName()),
                    Criteria.add(GameRoom.PROP_STATUS, Operator.EQ, this.searchObject.getStatus())
            );
            return criteria;
            //endregion your codes 2
        }


        //region your codes 3

        //endregion your codes 3
    }

    //region your codes 4

    //endregion your codes 4

}