package g.model.admin.gamebull100room.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.admin.gamebull100room.po.VRoomBull100;
import g.model.admin.gamebull100room.so.VRoomBull100So;


/**
 * 值对象
 *
 * @author lenovo
 * @time 2016-12-24 15:43:30
 */
//region your codes 1
public class VRoomBull100Vo extends BaseObjectVo<VRoomBull100, VRoomBull100So, VRoomBull100Vo.VRoomBull100Query> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 4768670548588741484L;
    //endregion your codes 5

    /**
     *  查询逻辑
     */
    public static class VRoomBull100Query extends AbstractQuery<VRoomBull100So> {

        //region your codes 6
        private static final long serialVersionUID = 8350679828591617578L;
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