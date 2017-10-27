package g.model.player.param;

import org.soul.model.security.privilege.po.SysUser;

import java.io.Serializable;

/**
 * Created by jerry on 16-4-5.
 */
public class GameTransactionParam implements Serializable {
    private static final long serialVersionUID = 7460114250918938971L;

    /**
     * 在游戏登录帐号,只能由5-16个英文、数字组成
     */
    private SysUser sysUser;
    /**
     * 存款金额
     */
    private double amount;

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

}
