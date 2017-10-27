package g.model.playerstatistics.so;


import g.model.playerstatistics.po.PlayerDataStatistics;

import java.util.Date;


/**
 * 玩家数据统计表查询对象
 *
 * @author lenovo
 * @time 2017-1-5 14:06:09
 */
//region your codes 1
public class PlayerDataStatisticsSo extends PlayerDataStatistics {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -9050654921570763382L;
	//endregion your codes 3

	//region your codes
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 查询类型
	 */
	private String selectType;
	/**
	 * 用户身份
	 */
	private String userType;
	/**
	 * 所有者id
	 */
	private Integer ownerId;
	/**
	 * 是否详情查询
	 */
	private String isDetail;

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

	public String getSelectType() {
		return selectType;
	}

	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public String getIsDetail() {
		return isDetail;
	}

	public void setIsDetail(String isDetail) {
		this.isDetail = isDetail;
	}

	//endregion your codes 2
}