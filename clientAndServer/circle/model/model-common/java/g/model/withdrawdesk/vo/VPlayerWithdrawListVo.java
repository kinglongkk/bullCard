package g.model.withdrawdesk.vo;

import g.model.depositdesk.po.PlayerRecharge;
import g.model.depositdesk.po.VPlayerRecharge;
import org.soul.commons.bean.Pair;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Direction;
import org.soul.commons.query.sort.Sort;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.withdrawdesk.po.VPlayerWithdraw;
import g.model.withdrawdesk.so.VPlayerWithdrawSo;

import java.util.ArrayList;
import java.util.List;


/**
 * 玩家交易表视图 edit by river重建列表页值对象
 *
 * @author tom
 * @time 2016-7-14 11:55:05
 */
//region your codes 1
public class VPlayerWithdrawListVo extends BaseListVo<VPlayerWithdraw, VPlayerWithdrawSo, VPlayerWithdrawListVo.VPlayerWithdrawQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 7865255874246562648L;
    private Integer todoWithdrawSize;
    //endregion your codes 5

    /**
     *  玩家交易表视图 edit by river重建列表查询逻辑
     */
    public static class VPlayerWithdrawQuery extends AbstractQuery<VPlayerWithdrawSo> {

        //region your codes 6
        private static final long serialVersionUID = 3943223232599899235L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            Criteria criteria = Criteria.and(Criteria.add(VPlayerWithdraw.PROP_USERNAME, Operator.ILIKE, this.searchObject.getUsername()),
                    Criteria.add(VPlayerWithdraw.PROP_WITHDRAW_AMOUNT, Operator.EQ, this.searchObject.getWithdrawAmount()),
                    Criteria.add(VPlayerWithdraw.PROP_TRANSACTION_NO, Operator.ILIKE, this.searchObject.getTransactionNo()),
                    Criteria.add(VPlayerWithdraw.PROP_PAYEE_BANK, Operator.ILIKE, this.searchObject.getPayeeBank()),
                    Criteria.add(PlayerRecharge.PROP_CREATE_TIME, Operator.GE, this.searchObject.getStartTime()),
                    Criteria.add(PlayerRecharge.PROP_CREATE_TIME, Operator.LE, this.searchObject.getEndTime()),
                    Criteria.add(VPlayerWithdraw.PROP_PAYEE_BANKCARD, Operator.ILIKE, this.searchObject.getPayeeBankcard()),
                    Criteria.add(VPlayerWithdraw.PROP_PAYEE_NAME, Operator.ILIKE, this.searchObject.getPayeeName()),
                    Criteria.add(VPlayerWithdraw.PROP_PLAYER_ID, Operator.EQ, this.searchObject.getPlayerId()));

            String status = this.searchObject.getWithdrawStatus();
            if (StringTool.isNotBlank(status)) {
                criteria.addAnd(VPlayerWithdraw.PROP_WITHDRAW_STATUS, Operator.EQ, status);
            }

            return criteria;
            //endregion your codes 2
        }


        //region your codes 3
        @Override
        public Sort getDefaultSort() {
            return Sort.add(VPlayerWithdraw.PROP_CREATE_TIME, Direction.DESC);
        }
        //endregion your codes 3
    }

    //region your codes 4
    public List<Pair> searchList() {
        List<Pair> searchList = new ArrayList<>();
        searchList.add(new Pair("search." + VPlayerWithdraw.PROP_USERNAME, "UserName"));
        searchList.add(new Pair("search." + VPlayerWithdraw.PROP_WITHDRAW_AMOUNT, "Amount"));
//        searchList.add(new Pair("search." + PlayerRecharge.PROP_CREATE_TIME, "Time"));
        searchList.add(new Pair("search." + VPlayerWithdraw.PROP_TRANSACTION_NO, "Order"));
        searchList.add(new Pair("search." + VPlayerWithdraw.PROP_PAYEE_BANK, "Bank"));
        searchList.add(new Pair("search." + VPlayerWithdraw.PROP_PAYEE_BANKCARD, "BankCard"));
        searchList.add(new Pair("search." + VPlayerWithdraw.PROP_PAYEE_NAME, "PayeeName"));
        return searchList;
    }

    public Integer getTodoWithdrawSize() {
        return todoWithdrawSize;
    }

    public void setTodoWithdrawSize(Integer todoWithdrawSize) {
        this.todoWithdrawSize = todoWithdrawSize;
    }
    //endregion your codes 4

}