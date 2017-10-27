 package g.model.bet.vo;


import g.model.bet.po.Bet;
import g.model.bet.so.BetSo;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;


/**
 * 列表页值对象
 *
 * @author longer
 * @time Apr 26, 2016 4:56:53 PM
 */
//region your codes 1
public class BetListVo extends BaseListVo<Bet, BetSo, BetListVo.BetQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -8779568839028220779L;
    //endregion your codes 5
    //投注额
    private double singleAmountTotal;
    //交易量
    private double effectiveAmountTotal;
    //派彩金额
    private double profitAmountTotal;

    // 消息
    private String msg;
    // 是否登录
    private Boolean isLogin;
    /**
     *  列表查询逻辑
     */
    public static class BetQuery extends AbstractQuery<BetSo> {

        //region your codes 6
        private static final long serialVersionUID = 6433361774487236398L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            return Criteria.add(Bet.PROP_SETTLE_STATUS, Operator.EQ, searchObject.getSettleStatus())
                    .addAnd(Bet.PROP_SYS_USER_ID,Operator.EQ,searchObject.getSysUserId())
                    .addAnd(Bet.PROP_BET_TIME, Operator.GT, searchObject.getStartTime())
                    .addAnd(Bet.PROP_BET_TIME, Operator.LT, searchObject.getEndTime());
            //endregion your codes 2
        }


        //region your codes 3



        //endregion your codes 3
    }

    //region your codes 4

    public double getSingleAmountTotal() {
        return singleAmountTotal;
    }

    public void setSingleAmountTotal(double singleAmountTotal) {
        this.singleAmountTotal = singleAmountTotal;
    }

    public double getEffectiveAmountTotal() {
        return effectiveAmountTotal;
    }

    public void setEffectiveAmountTotal(double effectiveAmountTotal) {
        this.effectiveAmountTotal = effectiveAmountTotal;
    }

    public double getProfitAmountTotal() {
        return profitAmountTotal;
    }

    public void setProfitAmountTotal(double profitAmountTotal) {
        this.profitAmountTotal = profitAmountTotal;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Boolean isLogin) {
        this.isLogin = isLogin;
    }
//endregion your codes 4

}