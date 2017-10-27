package g.model.withdrawdesk.vo;


import g.model.depositdesk.po.PlayerRecharge;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.withdrawdesk.po.PlayerWithdraw;
import g.model.withdrawdesk.so.PlayerWithdrawSo;


/**
 * 提现记录表列表页值对象
 *
 * @author tom
 * @time 2016-7-13 16:16:43
 */
//region your codes 1
public class PlayerWithdrawListVo extends BaseListVo<PlayerWithdraw, PlayerWithdrawSo, PlayerWithdrawListVo.PlayerWithdrawQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 2810165499050429057L;
    //endregion your codes 5

    /**
     *  提现记录表列表查询逻辑
     */
    public static class PlayerWithdrawQuery extends AbstractQuery<PlayerWithdrawSo> {

        //region your codes 6
        private static final long serialVersionUID = -5196910760111047510L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
//            Criteria criteria = Criteria.or(Criteria.add(PlayerRecharge.PROP_PLAYER_NICKNAME, Operator.ILIKE, this.searchObject.getPlayerNickname()),
//                    Criteria.add(PlayerRecharge.PROP_RECHARGE_AMOUNT, Operator.EQ, this.searchObject.getRechargeAmount()),
//                    Criteria.add(PlayerRecharge.PROP_CHECK_TIME, Operator.EQ, this.searchObject.getCreateTime()),
//                    Criteria.add(PlayerRecharge.PROP_PAY_ACCOUNT_NAME, Operator.ILIKE, this.searchObject.getPayAccountName()),
////                    Criteria.addAnd(PlayerRecharge.PROP_CREATE_TIME, Operator.GE, this.searchObject.getBeginTimeFrom()),
////                    Criteria.addAnd(PlayerRecharge.PROP_CREATE_TIME, Operator.LE, this.searchObject.getBeginTimeTo()),
//                    Criteria.add(PlayerRecharge.PROP_TRANSACTION_NO, Operator.ILIKE, this.searchObject.getTransactionNo()));
//
//            String status = this.searchObject.getRechargeStatus();
//            if (StringTool.isNotBlank(status)) {
//                criteria.addAnd(PlayerRecharge.PROP_RECHARGE_STATUS, Operator.EQ, status);
//            }

            return null;
            //endregion your codes 2
        }


        //region your codes 3

        //endregion your codes 3
    }

    //region your codes 4

    //endregion your codes 4

}