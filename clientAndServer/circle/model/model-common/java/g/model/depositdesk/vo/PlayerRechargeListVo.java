package g.model.depositdesk.vo;

import g.model.agent.po.VAgentManage;
import org.soul.commons.bean.Pair;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.locale.LocaleTool;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Direction;
import org.soul.commons.query.sort.Sort;
import org.soul.model.security.privilege.po.SysUserStatus;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.depositdesk.po.PlayerRecharge;
import g.model.depositdesk.so.PlayerRechargeSo;


/**
 * 玩家充值表-mark列表页值对象
 *
 * @author tom
 * @time 2016-7-14 9:27:05
 */
//region your codes 1
public class PlayerRechargeListVo extends BaseListVo<PlayerRecharge, PlayerRechargeSo, PlayerRechargeListVo.PlayerRechargeQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -773952949031337420L;
    //endregion your codes 5

    /**
     *  玩家充值表-mark列表查询逻辑
     */
    public static class PlayerRechargeQuery extends AbstractQuery<PlayerRechargeSo> {

        //region your codes 6
        private static final long serialVersionUID = 7756705871235771316L;
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
//
//            return criteria;
            return null;
            //endregion your codes 2
        }


        //region your codes 3

        @Override
        public Sort getDefaultSort() {
            return Sort.add(PlayerRecharge.PROP_CREATE_TIME, Direction.DESC);
        }


        //endregion your codes 3
    }

    //region your codes 4

    public List<Pair> searchList() {
        List<Pair> searchList = new ArrayList<>();
//        searchList.add(new Pair("search." + PlayerRecharge.PROP_PLAYER_NICKNAME, "NickName"));
//        searchList.add(new Pair("search." + PlayerRecharge.PROP_RECHARGE_AMOUNT, "Amount"));
////        searchList.add(new Pair("search." + PlayerRecharge.PROP_CREATE_TIME, "Time"));
//        searchList.add(new Pair("search." + PlayerRecharge.PROP_TRANSACTION_NO, "Order"));
//        searchList.add(new Pair("search." + PlayerRecharge.PROP_PAY_ACCOUNT_NAME, "Account"));
        return searchList;
    }

    //endregion your codes 4

}