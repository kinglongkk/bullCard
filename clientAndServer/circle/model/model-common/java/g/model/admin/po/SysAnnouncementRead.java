package g.model.admin.po;


import org.soul.commons.bean.IEntity;


/**
 * Sys_Announcement_read表
 *
 * @author orange
 * @time 2016-04-12 15:46:10
 */
//region your codes 1
public class SysAnnouncementRead implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_SYS_ANNOUNCEMENT_ID = "sysAnnouncementId";
	public static final String PROP_SYS_USER_ID = "sysUserId";
	//endregion


	//region properties
	/** 主键 */
	private Integer id;
	/** 玩家咨询回复表Id */
	private Integer sysAnnouncementId;
	/** 玩家ID */
	private Integer sysUserId;
	//endregion


	//region constuctors
	public SysAnnouncementRead(){
	}

	public SysAnnouncementRead(Integer id){
		this.id = id;
	}
	//endregion


	//region getters and setters
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer value) {
		this.id = value;
	}

	public Integer getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(Integer sysUserId) {
		this.sysUserId = sysUserId;
	}

	public Integer getSysAnnouncementId() {
		return sysAnnouncementId;
	}

	public void setSysAnnouncementId(Integer sysAnnouncementId) {
		this.sysAnnouncementId = sysAnnouncementId;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}