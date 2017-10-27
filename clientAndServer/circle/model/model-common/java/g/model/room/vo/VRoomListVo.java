package g.model.room.vo;

import g.model.enums.GameStatusEnum;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.room.po.VRoom;
import g.model.room.so.VRoomSo;


/**
 * 列表页值对象
 *
 * @author longer
 * @time 2016-12-6 16:03:23
 */
//region your codes 1
public class VRoomListVo extends BaseListVo<VRoom, VRoomSo, VRoomListVo.VRoomQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -2553939226961862949L;
    //endregion your codes 5

    /**
     *  列表查询逻辑
     */
    public static class VRoomQuery extends AbstractQuery<VRoomSo> {

        //region your codes 6
        private static final long serialVersionUID = -7561971746875905916L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            Criteria criteria = Criteria.and(
                    Criteria.add(VRoom.PROP_ID, Operator.EQ, this.searchObject.getId()),
                    Criteria.add(VRoom.PROP_GAME_ID, Operator.EQ, this.searchObject.getGameId()),
                    Criteria.add(VRoom.PROP_GAME_MODEL_ID, Operator.EQ, this.searchObject.getGameModelId()),
                    Criteria.add(VRoom.PROP_NAME, Operator.EQ, this.searchObject.getName()),
                    Criteria.add(VRoom.PROP_STATUS, Operator.EQ, GameStatusEnum.ENABLE.getCode()),
                    Criteria.add(VRoom.PROP_GAME_NAME, Operator.EQ, this.searchObject.getGameName()),
                    Criteria.add(VRoom.PROP_GAME_STATUS, Operator.EQ, GameStatusEnum.ENABLE.getCode()),
                    Criteria.add(VRoom.PROP_GAME_MODEL_NAME, Operator.EQ, this.searchObject.getGameModelName()),
                    Criteria.add(VRoom.PROP_GAME_MODEL_STATUS, Operator.EQ, GameStatusEnum.ENABLE.getCode())
            );
            return criteria;
            //endregion your codes 2
        }


        //region your codes 3
        public Criteria by(){
            return Criteria.and(
                    Criteria.add( VRoom.PROP_GAME_ID, Operator.EQ,searchObject.getGameId())
                    ,Criteria.add( VRoom.PROP_STATUS, Operator.EQ,searchObject.getStatus())
                    ,Criteria.add( VRoom.PROP_GAME_STATUS, Operator.EQ,searchObject.getGameStatus())
                    ,Criteria.add( VRoom.PROP_GAME_MODEL_STATUS, Operator.EQ,searchObject.getGameModelStatus())
            );

        }


        //endregion your codes 3
    }

    //region your codes 4

    //endregion your codes 4

}