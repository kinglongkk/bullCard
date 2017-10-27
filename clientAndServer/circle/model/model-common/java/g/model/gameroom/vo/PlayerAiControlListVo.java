package g.model.gameroom.vo;


import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.gameroom.po.PlayerAiControl;
import g.model.gameroom.so.PlayerAiControlSo;


/**
 * Ai玩家设置列表页值对象
 *
 * @author lenovo
 * @time 2017-2-15 15:59:48
 */
//region your codes 1
public class PlayerAiControlListVo extends BaseListVo<PlayerAiControl, PlayerAiControlSo, PlayerAiControlListVo.PlayerAiControlQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 3465389052764376069L;
    //endregion your codes 5

    /**
     *  Ai玩家设置列表查询逻辑
     */
    public static class PlayerAiControlQuery extends AbstractQuery<PlayerAiControlSo> {

        //region your codes 6
        private static final long serialVersionUID = 2978030506210809477L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
//            Criteria criteria =  Criteria.and(
//                    Criteria.add(PlayerAiControl.PROP_ROOM_ID, Operator.EQ, this.searchObject.getRoomId())
//            );
//            return criteria;
            return null;
        }


        //region your codes 3

        //endregion your codes 3
    }

    //region your codes 4

    //endregion your codes 4

}