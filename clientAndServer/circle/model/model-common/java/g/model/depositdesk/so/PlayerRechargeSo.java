package g.model.depositdesk.so;

import java.util.Date;

import g.model.depositdesk.po.PlayerRecharge;


/**
 * 玩家充值表-mark查询对象
 *
 * @author tom
 * @time 2016-7-14 9:27:05
 */
//region your codes 1
public class PlayerRechargeSo extends PlayerRecharge {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -894899277963537728L;
	// 下注时间范围
	private Date createTimeFrom;
	private Date createTimeTo;
	//endregion your codes 3

	//region your codes 2

	public Date getCreateTimeFrom() {
		return createTimeFrom;
	}

	public void setCreateTimeFrom(Date createTimeFrom) {
		this.createTimeFrom = createTimeFrom;
	}

	public Date getCreateTimeTo() {
		return createTimeTo;
	}

	public void setCreateTimeTo(Date createTimeTo) {
		this.createTimeTo = createTimeTo;
	}


	//endregion your codes 2
}