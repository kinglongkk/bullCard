package g.model.gameroom.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.gameroom.po.VGameRoom;
import g.model.gameroom.so.VGameRoomSo;


/**
 * 值对象
 *
 * @author lenovo
 * @time 2017-2-14 16:52:54
 */
//region your codes 1
public class VGameRoomVo extends BaseObjectVo<VGameRoom, VGameRoomSo, VGameRoomVo.VGameRoomQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -324513812658623387L;
    //endregion your codes 5

    /**
     *  查询逻辑
     */
    public static class VGameRoomQuery extends AbstractQuery<VGameRoomSo> {

        //region your codes 6
        private static final long serialVersionUID = -4647741648148795470L;
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