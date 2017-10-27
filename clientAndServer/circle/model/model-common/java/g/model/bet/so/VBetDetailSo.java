package g.model.bet.so;

import g.model.bet.po.VBetDetail;

import java.util.Date;


/**
 * 查询对象
 *
 * @author mark
 * @time 2016-7-12 14:22:28
 */
//region your codes 1
public class VBetDetailSo extends VBetDetail {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 4566700372236331477L;

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

	@Override
	public Date getEndTime() {
		return endTime;
	}

	@Override
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	//endregion your codes 2
}