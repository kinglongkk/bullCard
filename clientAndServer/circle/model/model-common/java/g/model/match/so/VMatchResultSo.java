package g.model.match.so;

import g.model.match.po.VMatchResult;

import java.util.Date;


/**
 * 查询对象
 *
 * @author longer
 * @time May 10, 2016 8:14:28 PM
 */
//region your codes 1
public class VMatchResultSo extends VMatchResult {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -9002084271235194434L;
	//endregion your codes 3

	//region your codes 2

	private Date startTime;

	private Date endTime;

	private Integer gids;

	/**1：已结算,2:未结算,3:取消*/
	private String status;

	public Integer getGids() {
		return gids;
	}

	public void setGids(Integer gids) {
		this.gids = gids;
	}

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	//endregion your codes 2
}