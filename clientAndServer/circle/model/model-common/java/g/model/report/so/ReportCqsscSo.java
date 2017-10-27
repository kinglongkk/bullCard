package g.model.report.so;

import g.model.report.po.ReportCqssc;

import java.util.Date;


/**
 * 查询对象
 *
 * @author tom
 * @time 2016-8-1 15:34:20
 */
//region your codes 1
public class ReportCqsscSo extends ReportCqssc {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -2409928630892570491L;
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