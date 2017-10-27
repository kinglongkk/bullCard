package g.model.admin.so;


import g.model.admin.po.VSysAnnouncement;

import java.util.Date;


/**
 * 系统公告视图查询对象
 *
 * @author orange
 * @time 2015-11-26 20:20:37
 */
//region your codes 1
public class VSysAnnouncementSo extends VSysAnnouncement {
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