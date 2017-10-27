package g.model.bet.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.bet.po.VBetSettle;
import g.model.bet.so.VBetSettleSo;


/**
 * 注单结算值对象
 *
 * @author longer
 * @time May 19, 2016 1:42:07 PM
 */
//region your codes 1
public class VBetSettleVo extends BaseObjectVo<VBetSettle, VBetSettleSo, VBetSettleVo.VBetSettleQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -8689454953272781265L;
    //endregion your codes 5

    /**
     *  注单结算查询逻辑
     */
    public static class VBetSettleQuery extends AbstractQuery<VBetSettleSo> {

        //region your codes 6
        private static final long serialVersionUID = 9124850104961851508L;
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