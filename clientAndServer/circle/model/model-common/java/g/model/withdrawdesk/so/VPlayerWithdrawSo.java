package g.model.withdrawdesk.so;

import g.model.withdrawdesk.po.VPlayerWithdraw;

import java.util.Date;


/**
 * 玩家交易表视图 edit by river重建查询对象
 *
 * @author tom
 * @time 2016-7-14 11:55:05
 */
//region your codes 1
public class VPlayerWithdrawSo extends VPlayerWithdraw {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 3955747943433019160L;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	//endregion your codes 3

	//region your codes 2

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


	//endregion your codes 2
}