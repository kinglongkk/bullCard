package g.model.warning.vo;


import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.warning.po.PlayerWarningMultiple;
import g.model.warning.so.PlayerWarningMultipleSo;


/**
 * 说明预警异常用户赢得金额跟投注金额倍数列表页值对象
 *
 * @author lenovo
 * @time 2017-2-25 16:06:12
 */
//region your codes 1
public class PlayerWarningMultipleListVo extends BaseListVo<PlayerWarningMultiple, PlayerWarningMultipleSo, PlayerWarningMultipleListVo.PlayerWarningMultipleQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 6094640014367580700L;
    //endregion your codes 5

    /**
     *  说明预警异常用户赢得金额跟投注金额倍数列表查询逻辑
     */
    public static class PlayerWarningMultipleQuery extends AbstractQuery<PlayerWarningMultipleSo> {

        //region your codes 6
        private static final long serialVersionUID = -1289003228581855847L;
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