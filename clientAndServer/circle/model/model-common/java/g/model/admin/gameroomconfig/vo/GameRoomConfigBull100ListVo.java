package g.model.admin.gameroomconfig.vo;

import g.model.admin.gameroomconfig.po.GameRoomConfigBull100;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.admin.gameroomconfig.so.GameRoomConfigBull100So;


/**
 * 游戏房间配置信息表列表页值对象
 *
 * @author lenovo
 * @time 2016-12-17 17:20:19
 */
//region your codes 1
public class GameRoomConfigBull100ListVo extends BaseListVo<GameRoomConfigBull100, GameRoomConfigBull100So, GameRoomConfigBull100ListVo.GameRoomConfigBull100Query> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -3064408301525302787L;
    //endregion your codes 5

    /**
     *  游戏房间配置信息表列表查询逻辑
     */
    public static class GameRoomConfigBull100Query extends AbstractQuery<GameRoomConfigBull100So> {

        //region your codes 6
        private static final long serialVersionUID = 6079574925767761284L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            Criteria criteria = Criteria.and(
                    Criteria.add(GameRoomConfigBull100.PROP_ID, Operator.EQ, this.searchObject.getId()),
                    Criteria.add(GameRoomConfigBull100.PROP_ROOM_ID, Operator.EQ, this.searchObject.getRoomId())
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