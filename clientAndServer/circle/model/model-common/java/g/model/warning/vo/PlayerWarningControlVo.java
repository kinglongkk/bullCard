package g.model.warning.vo;


import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.warning.po.PlayerWarningControl;
import g.model.warning.so.PlayerWarningControlSo;


/**
 * 玩家实时监控控制表值对象
 *
 * @author lenovo
 * @time 2017-2-21 14:12:56
 */
//region your codes 1
public class PlayerWarningControlVo extends BaseObjectVo<PlayerWarningControl, PlayerWarningControlSo, PlayerWarningControlVo.PlayerWarningControlQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -4559998057652374825L;
    //endregion your codes 5

    /**
     *  玩家实时监控控制表查询逻辑
     */
    public static class PlayerWarningControlQuery extends AbstractQuery<PlayerWarningControlSo> {

        //region your codes 6
        private static final long serialVersionUID = -2344554902629716362L;
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