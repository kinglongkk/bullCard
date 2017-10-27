package g.model.gameroom.vo;


import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.gameroom.po.PlayerAiControl;
import g.model.gameroom.so.PlayerAiControlSo;


/**
 * Ai玩家设置值对象
 *
 * @author lenovo
 * @time 2017-2-15 15:59:48
 */
//region your codes 1
public class PlayerAiControlVo extends BaseObjectVo<PlayerAiControl, PlayerAiControlSo, PlayerAiControlVo.PlayerAiControlQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 6814621998435833936L;
    //endregion your codes 5

    /**
     *  Ai玩家设置查询逻辑
     */
    public static class PlayerAiControlQuery extends AbstractQuery<PlayerAiControlSo> {

        //region your codes 6
        private static final long serialVersionUID = 5566469868132481348L;
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