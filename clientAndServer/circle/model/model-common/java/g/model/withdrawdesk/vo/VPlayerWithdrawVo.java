package g.model.withdrawdesk.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.withdrawdesk.po.VPlayerWithdraw;
import g.model.withdrawdesk.so.VPlayerWithdrawSo;


/**
 * 玩家交易表视图 edit by river重建值对象
 *
 * @author tom
 * @time 2016-7-14 11:55:05
 */
//region your codes 1
public class VPlayerWithdrawVo extends BaseObjectVo<VPlayerWithdraw, VPlayerWithdrawSo, VPlayerWithdrawVo.VPlayerWithdrawQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -2140166650614516853L;
    //endregion your codes 5

    /**
     *  玩家交易表视图 edit by river重建查询逻辑
     */
    public static class VPlayerWithdrawQuery extends AbstractQuery<VPlayerWithdrawSo> {

        //region your codes 6
        private static final long serialVersionUID = -3845350259669685224L;
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