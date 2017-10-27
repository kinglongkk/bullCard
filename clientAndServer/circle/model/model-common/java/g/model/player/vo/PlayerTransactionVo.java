package g.model.player.vo;

import g.model.player.param.BalanceParam;
import g.model.player.so.PlayerTransactionSo;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import org.soul.model.security.privilege.po.SysUser;
import g.model.player.po.PlayerTransaction;


/**
 * 玩家交易表值对象
 * <p/>
 * Created by cheery using soul-code-generator on 2015-6-23 11:41:43
 */
public class PlayerTransactionVo extends BaseObjectVo<PlayerTransaction, PlayerTransactionSo, PlayerTransactionVo.PlayerTransactionQuery> {

    private static final long serialVersionUID = 3191286724751752192L;

    /**
     * 在游戏登录帐号,只能由5-16个英文、数字组成
     */
    private SysUser sysUser;
    /**
     * 存款金额
     */
    private double amount;
    /**
     * 外部交易订单号
     */
    private String sourceOrderNo;

    private BalanceParam balanceParam;

    public BalanceParam getBalanceParam() {
        return balanceParam;
    }

    public void setBalanceParam(BalanceParam balanceParam) {
        this.balanceParam = balanceParam;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSourceOrderNo() {
        return sourceOrderNo;
    }

    public void setSourceOrderNo(String sourceOrderNo) {
        this.sourceOrderNo = sourceOrderNo;
    }

    /**
     * 玩家交易表查询逻辑
     */
    public static class PlayerTransactionQuery extends AbstractQuery<PlayerTransactionSo> {

        private static final long serialVersionUID = -9223372036854775808L;

        @Override
        public Criteria getCriteria() {
            Criteria criteria = Criteria.add(PlayerTransaction.PROP_PLAYER_ID, Operator.EQ, this.searchObject.getPlayerId());
            return criteria;
        }

    }


}