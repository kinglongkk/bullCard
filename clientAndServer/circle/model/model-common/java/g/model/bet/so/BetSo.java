package g.model.bet.so;


import g.model.bet.po.Bet;

import java.util.Date;
import java.util.List;

/**
 * 查询对象
 *
 * @author longer
 * @time Apr 26, 2016 4:56:53 PM
 */
//region your codes 1
public class BetSo extends Bet {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -6906313737246725637L;
	//endregion your codes 3

	//region your codes 2
	/**
	 * 是否自动接受较佳赔率
	 */
	private String autoOdd;

	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;

	public String getAutoOdd() {
		return autoOdd;
	}

	public void setAutoOdd(String autoOdd) {
		this.autoOdd = autoOdd;
	}

	@Override
	public String toString() {
		return "[rtype:"+getRtype()+";singleAmount:"+getSingleAmount()+"]";
	}
//endregion your codes 2

	private List<Long> ids;

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
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

//endregion your codes 2
}