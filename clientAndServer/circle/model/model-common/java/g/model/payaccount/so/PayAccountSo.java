package g.model.payaccount.so;

import g.model.payaccount.po.PayAccount;


/**
 * 收款帐号-mark查询对象
 *
 * @author tom
 * @time 2016-7-4 16:37:33
 */
//region your codes 1
public class PayAccountSo extends PayAccount {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 6328042299555248799L;

	private long rechargeNum;
	private double	rechargeAmount;
	//endregion your codes 3

	//region your codes 2

	public long getRechargeNum() {
		return rechargeNum;
	}

	public void setRechargeNum(long rechargeNum) {
		this.rechargeNum = rechargeNum;
	}

	public double getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(double rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	//endregion your codes 2
}