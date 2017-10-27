package g.model.depositdesk.vo;


import g.model.payaccount.po.PayAccount;
import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.depositdesk.po.PlayerRecharge;
import g.model.depositdesk.so.PlayerRechargeSo;


/**
 * 玩家充值表-mark值对象
 *
 * @author tom
 * @time 2016-7-14 9:27:05
 */
//region your codes 1
public class PlayerRechargeVo extends BaseObjectVo<PlayerRecharge, PlayerRechargeSo, PlayerRechargeVo.PlayerRechargeQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 3124719164817748419L;
    //ip
    private Long ip;
    private PayAccount payAccount;
    private Double firstRechargeAmount;
    //endregion your codes 5

    /**
     *  玩家充值表-mark查询逻辑
     */
    public static class PlayerRechargeQuery extends AbstractQuery<PlayerRechargeSo> {

        //region your codes 6
        private static final long serialVersionUID = -3299872933046066049L;
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

    public Long getIp() {
        return ip;
    }

    public void setIp(Long ip) {
        this.ip = ip;
    }

    public PayAccount getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(PayAccount payAccount) {
        this.payAccount = payAccount;
    }

    public Double getFirstRechargeAmount() {
        return firstRechargeAmount;
    }

    public void setFirstRechargeAmount(Double firstRechargeAmount) {
        this.firstRechargeAmount = firstRechargeAmount;
    }

    //endregion your codes 4

}