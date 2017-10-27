package g.model.match.so;

import g.model.match.po.MatchResult;

import java.util.Date;


/**
 * 比赛结果表查询对象
 *
 * @author longer
 * @time May 17, 2016 4:38:24 PM
 */
//region your codes 1
public class MatchResultSo extends MatchResult {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 1885997449542552535L;
	//endregion your codes 3

	//region your codes 2

	Integer[] matchIds;

	private String rType;
	/** 比赛类型:[FT=足球...] */
	private String ballType;

	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;

	public Integer[] getMatchIds() {
		return matchIds;
	}

	public void setMatchIds(Integer[] matchIds) {
		this.matchIds = matchIds;
	}

	public String getBallType() {
		return ballType;
	}

	public void setBallType(String ballType) {
		this.ballType = ballType;
	}

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

	public String getrType() {
		return rType;
	}

	public void setrType(String rType) {
		this.rType = rType;
	}

	//endregion your codes 2
}