package g.model.playerstatistics.so;


import g.model.playerstatistics.po.PlayerSummery;

import java.util.Date;


/**
 * 玩家数据统计表查询对象
 *
 * @author lenovo
 * @time 2017-1-5 17:36:23
 */
//region your codes 1
public class PlayerSummerySo extends PlayerSummery {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 6314212302628658320L;
    //百分比
	private double percentage;

	//endregion your codes 3

	//region your codes 2

	/**
	 * 搜索开始时间
	 */
	private Date startTime;
	/**
	 * 搜索结束时间
	 */
	private Date endTime;

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

	@javax.persistence.Transient
	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
}