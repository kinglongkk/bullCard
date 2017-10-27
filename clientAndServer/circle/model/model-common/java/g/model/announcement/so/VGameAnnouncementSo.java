package g.model.announcement.so;

import g.model.announcement.po.GameAnnouncement;

import java.util.Date;


/**
 * 游戏公告视图查询对象
 *
 * @author lacne
 * @time 2016-10-28 10:53:18
 */
//region your codes 1
public class VGameAnnouncementSo extends GameAnnouncement {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -6280203621216146457L;
	//endregion your codes 3

	//region your codes 2
	private Date startTime;
	private Date endTime;

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	//endregion your codes 2
}