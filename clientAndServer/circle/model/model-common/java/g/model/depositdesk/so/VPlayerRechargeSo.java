package g.model.depositdesk.so;

import g.model.depositdesk.po.VPlayerRecharge;

import java.util.Date;


/**
 * 查询对象
 *
 * @author tom
 * @time 2016-7-14 9:46:35
 */
//region your codes 1
public class VPlayerRechargeSo extends VPlayerRecharge {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -4828176847777946005L;
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