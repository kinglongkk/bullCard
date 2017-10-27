package g.model.gameroom.vo;


import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.gameroom.po.PlayerAiRatioControl;
import g.model.gameroom.so.PlayerAiRatioControlSo;


/**
 * Ai与玩家配比设置列表页值对象
 *
 * @author lenovo
 * @time 2017-2-15 14:28:52
 */
//region your codes 1
public class PlayerAiRatioControlListVo extends BaseListVo<PlayerAiRatioControl, PlayerAiRatioControlSo, PlayerAiRatioControlListVo.PlayerAiRatioControlQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -9149511527129233112L;
    //endregion your codes 5

    /**
     *  Ai与玩家配比设置列表查询逻辑
     */
    public static class PlayerAiRatioControlQuery extends AbstractQuery<PlayerAiRatioControlSo> {

        //region your codes 6
        private static final long serialVersionUID = 6119611730882168344L;
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