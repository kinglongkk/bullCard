package g.model.warning.vo;


import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.warning.po.PlayerWarningControl;
import g.model.warning.so.PlayerWarningControlSo;


/**
 * 玩家实时监控控制表列表页值对象
 *
 * @author lenovo
 * @time 2017-2-21 14:12:56
 */
//region your codes 1
public class PlayerWarningControlListVo extends BaseListVo<PlayerWarningControl, PlayerWarningControlSo, PlayerWarningControlListVo.PlayerWarningControlQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 5092252426327323174L;
    //endregion your codes 5

    /**
     *  玩家实时监控控制表列表查询逻辑
     */
    public static class PlayerWarningControlQuery extends AbstractQuery<PlayerWarningControlSo> {

        //region your codes 6
        private static final long serialVersionUID = 7869525192573075255L;
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