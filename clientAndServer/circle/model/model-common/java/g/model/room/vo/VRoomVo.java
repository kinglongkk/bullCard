package g.model.room.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.room.po.VRoom;
import g.model.room.so.VRoomSo;


/**
 * 值对象
 *
 * @author longer
 * @time 2016-12-6 16:03:23
 */
//region your codes 1
public class VRoomVo extends BaseObjectVo<VRoom, VRoomSo, VRoomVo.VRoomQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 3468242463960108960L;
    //endregion your codes 5

    /**
     *  查询逻辑
     */
    public static class VRoomQuery extends AbstractQuery<VRoomSo> {

        //region your codes 6
        private static final long serialVersionUID = 2038130181257730283L;
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