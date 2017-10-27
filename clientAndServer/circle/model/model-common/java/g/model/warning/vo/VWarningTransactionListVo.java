package g.model.warning.vo;


import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.warning.po.VWarningTransaction;
import g.model.warning.so.VWarningTransactionSo;


/**
 * 列表页值对象
 *
 * @author lenovo
 * @time 2017-2-28 14:13:53
 */
//region your codes 1
public class VWarningTransactionListVo extends BaseListVo<VWarningTransaction, VWarningTransactionSo, VWarningTransactionListVo.VWarningTransactionQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -7478899445235274725L;
    //endregion your codes 5

    /**
     *  列表查询逻辑
     */
    public static class VWarningTransactionQuery extends AbstractQuery<VWarningTransactionSo> {

        //region your codes 6
        private static final long serialVersionUID = -5630653514218572544L;
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