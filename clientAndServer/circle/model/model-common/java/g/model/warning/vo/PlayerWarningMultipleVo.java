package g.model.warning.vo;


import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.warning.po.PlayerWarningMultiple;
import g.model.warning.so.PlayerWarningMultipleSo;


/**
 * 说明预警异常用户赢得金额跟投注金额倍数值对象
 *
 * @author lenovo
 * @time 2017-2-25 16:06:12
 */
//region your codes 1
public class PlayerWarningMultipleVo extends BaseObjectVo<PlayerWarningMultiple, PlayerWarningMultipleSo, PlayerWarningMultipleVo.PlayerWarningMultipleQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -1397018912612429398L;
    //endregion your codes 5

    /**
     *  说明预警异常用户赢得金额跟投注金额倍数查询逻辑
     */
    public static class PlayerWarningMultipleQuery extends AbstractQuery<PlayerWarningMultipleSo> {

        //region your codes 6
        private static final long serialVersionUID = -3488955874803030142L;
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