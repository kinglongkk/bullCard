package g.model.gameroom.vo;

import g.model.playerstatistics.po.PlayerDataStatistics;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.gameroom.po.VGameRoom;
import g.model.gameroom.so.VGameRoomSo;


/**
 * 列表页值对象
 *
 * @author lenovo
 * @time 2017-2-14 16:52:54
 */
//region your codes 1
public class VGameRoomListVo extends BaseListVo<VGameRoom, VGameRoomSo, VGameRoomListVo.VGameRoomQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 5143245073525585031L;
    //endregion your codes 5

    /**
     *  列表查询逻辑
     */
    public static class VGameRoomQuery extends AbstractQuery<VGameRoomSo> {

        //region your codes 6
        private static final long serialVersionUID = 5984572676722556088L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            Criteria criteria =  Criteria.and(
                    Criteria.add(VGameRoom.PROP_STATUS, Operator.EQ, this.searchObject.getStatus()),
                    Criteria.add(VGameRoom.PROP_NAME,Operator.LIKE,this.searchObject.getName()),
                    Criteria.add(VGameRoom.PROP_GAMENAME,Operator.LIKE,this.searchObject.getGamename()),
                    Criteria.add(VGameRoom.PROP_MODELNAME,Operator.LIKE,this.searchObject.getModelname())
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