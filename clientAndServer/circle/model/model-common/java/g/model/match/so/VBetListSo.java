package g.model.match.so;


import g.model.bet.po.VBetList;

import java.util.Date;

/**
 * 注单查询对象
 *
 * @author tom
 * @time 2016-6-7 19:50:30
 */
//region your codes 1
public class VBetListSo extends VBetList {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -1829430312003934264L;
	//endregion your codes 3

	//region your codes 2
	// 开赛时间范围
	private Date beginTimeFrom;
	private Date beginTimeTo;
	// 下注时间范围
	private Date betTimeFrom;
	private Date betTimeTo;

	public Date getBeginTimeFrom() {
		return beginTimeFrom;
	}

	public void setBeginTimeFrom(Date beginTimeFrom) {
		this.beginTimeFrom = beginTimeFrom;
	}

	public Date getBeginTimeTo() {
		return beginTimeTo;
	}

	public void setBeginTimeTo(Date beginTimeTo) {
		this.beginTimeTo = beginTimeTo;
	}

	public Date getBetTimeFrom() {
		return betTimeFrom;
	}

	public void setBetTimeFrom(Date betTimeFrom) {
		this.betTimeFrom = betTimeFrom;
	}

	public Date getBetTimeTo() {
		return betTimeTo;
	}

	public void setBetTimeTo(Date betTimeTo) {
		this.betTimeTo = betTimeTo;
	}

	//endregion your codes 2
}