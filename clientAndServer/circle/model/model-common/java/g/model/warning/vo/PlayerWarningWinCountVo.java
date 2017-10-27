package g.model.warning.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.warning.po.PlayerWarningWinCount;
import g.model.warning.so.PlayerWarningWinCountSo;


/**
 * 说明预警异常用户赢得连续派彩次数值对象
 *
 * @author lenovo
 * @time 2017-2-27 15:00:30
 */
//region your codes 1
public class PlayerWarningWinCountVo extends BaseObjectVo<PlayerWarningWinCount, PlayerWarningWinCountSo, PlayerWarningWinCountVo.PlayerWarningWinCountQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 783823421943601249L;
    //endregion your codes 5

    /**
     *  说明预警异常用户赢得连续派彩次数查询逻辑
     */
    public static class PlayerWarningWinCountQuery extends AbstractQuery<PlayerWarningWinCountSo> {

        //region your codes 6
        private static final long serialVersionUID = 5532057055351678271L;
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