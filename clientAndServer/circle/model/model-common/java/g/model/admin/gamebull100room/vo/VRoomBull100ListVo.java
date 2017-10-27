package g.model.admin.gamebull100room.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.admin.gamebull100room.po.VRoomBull100;
import g.model.admin.gamebull100room.so.VRoomBull100So;


/**
 * 列表页值对象
 *
 * @author lenovo
 * @time 2016-12-24 15:43:30
 */
//region your codes 1
public class VRoomBull100ListVo extends BaseListVo<VRoomBull100, VRoomBull100So, VRoomBull100ListVo.VRoomBull100Query> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -4412042451442700098L;
    //endregion your codes 5

    /**
     *  列表查询逻辑
     */
    public static class VRoomBull100Query extends AbstractQuery<VRoomBull100So> {

        //region your codes 6
        private static final long serialVersionUID = 5428943114521454634L;
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