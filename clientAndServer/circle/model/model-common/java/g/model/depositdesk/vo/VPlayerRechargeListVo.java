package g.model.depositdesk.vo;

import g.model.depositdesk.po.PlayerRecharge;
import org.soul.commons.bean.Pair;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Direction;
import org.soul.commons.query.sort.Sort;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.depositdesk.po.VPlayerRecharge;
import g.model.depositdesk.so.VPlayerRechargeSo;

import java.util.ArrayList;
import java.util.List;


/**
 * 列表页值对象
 *
 * @author tom
 * @time 2016-7-14 9:46:35
 */
//region your codes 1
public class VPlayerRechargeListVo extends BaseListVo<VPlayerRecharge, VPlayerRechargeSo, VPlayerRechargeListVo.VPlayerRechargeQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -823381735401293303L;
    private Integer todoDepositSize;
    //endregion your codes 5

    /**
     *  列表查询逻辑
     */
    public static class VPlayerRechargeQuery extends AbstractQuery<VPlayerRechargeSo> {

        //region your codes 6
        private static final long serialVersionUID = -6207588390775249843L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            Criteria criteria = Criteria.and(Criteria.add(VPlayerRecharge.PROP_USERNAME, Operator.ILIKE, this.searchObject.getUsername()),
                    Criteria.add(PlayerRecharge.PROP_RECHARGE_AMOUNT, Operator.EQ, this.searchObject.getRechargeAmount()),
                    Criteria.add(PlayerRecharge.PROP_CHECK_TIME, Operator.EQ, this.searchObject.getCreateTime()),
                    Criteria.add(VPlayerRecharge.PROP_MASTER_NAME, Operator.ILIKE, this.searchObject.getMasterName()),
                    Criteria.add(PlayerRecharge.PROP_CREATE_TIME, Operator.GE, this.searchObject.getStartTime()),
                    Criteria.add(PlayerRecharge.PROP_CREATE_TIME, Operator.LE, this.searchObject.getEndTime()),
                    Criteria.add(VPlayerRecharge.PROP_TRANSACTION_NO, Operator.ILIKE, this.searchObject.getTransactionNo()),
                    Criteria.add(VPlayerRecharge.PROP_PLAYER_ID, Operator.EQ, this.searchObject.getPlayerId()));

            String status = this.searchObject.getRechargeStatus();
            if (StringTool.isNotBlank(status)) {
                criteria.addAnd(PlayerRecharge.PROP_RECHARGE_STATUS, Operator.EQ, status);
            }

            return criteria;
            //endregion your codes 2
        }

        @Override
        public Sort getDefaultSort() {
            return Sort.add(VPlayerRecharge.PROP_CREATE_TIME, Direction.DESC);
        }

        //region your codes 3

        //endregion your codes 3
    }

    //region your codes 4
    public List<Pair> searchList() {
        List<Pair> searchList = new ArrayList<>();
        searchList.add(new Pair("search." + VPlayerRecharge.PROP_USERNAME, "UserName"));
        searchList.add(new Pair("search." + VPlayerRecharge.PROP_RECHARGE_AMOUNT, "Amount"));
//        searchList.add(new Pair("search." + PlayerRecharge.PROP_CREATE_TIME, "Time"));
        searchList.add(new Pair("search." + VPlayerRecharge.PROP_TRANSACTION_NO, "Order"));
        searchList.add(new Pair("search." + VPlayerRecharge.PROP_MASTER_NAME, "PayAccount"));
        return searchList;
    }

    public Integer getTodoDepositSize() {
        return todoDepositSize;
    }

    public void setTodoDepositSize(Integer todoDepositSize) {
        this.todoDepositSize = todoDepositSize;
    }
    //endregion your codes 4

}