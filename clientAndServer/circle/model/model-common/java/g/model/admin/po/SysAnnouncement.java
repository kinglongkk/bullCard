package g.model.admin.po;


import org.soul.commons.bean.IEntity;

import java.util.Date;


/**
 * 系统公告实体
 *
 * @author River
 * @tableAuthor orange
 * @time 2015-12-18 12:00:28
 */
//region your codes 1
public class SysAnnouncement implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 4792035243406698309L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_PUBLISH_TIME = "publishTime";
	public static final String PROP_PUBLISH_USER_ID = "publishUserId";
	public static final String PROP_ANNOUNCEMENT_TYPE = "announcementType";
	public static final String PROP_PUBLISH_USER_NAME = "publishUserName";
	public static final String PROP_TIMING_SEND = "timingSend";
	public static final String PROP_RECIPIENT_USER_ID = "recipientUserId";
	public static final String PROP_SAVE_TIME = "saveTime";
	public static final String PROP_STATUS = "status";
	public static final String PROP_GROUP_CODE = "groupCode";
	public static final String PROP_NO_SHOW = "noShow";

	//endregion
	
	
	//region properties
	/** 主键 */
	private Integer id;
	/** 发布时间 */
	private java.util.Date publishTime;
	/** 发布人id */
	private Integer publishUserId;
	/** 公告类型common.announcement_type(一般公告,重要公告,个人公告) */
	private String announcementType;
	/** 发布人账号 */
	private String publishUserName;
	/** 是否定时发送 */
	private Boolean timingSend=false;
	/** 收件人Id */
	private Integer recipientUserId;
	/** 保存时间 */
	private Date saveTime;
	/** 状态:启用/禁用 */
	private String status;
	/** 是全局代理还是指定代理 */
	private String groupCode;
	/** 不在显示 */
	private Boolean noShow=false;
	//endregion

	
	//region constuctors
	public SysAnnouncement(){
	}

	public SysAnnouncement(Integer id){
		this.id = id;
	}
	//endregion


	//region getters and setters


	@org.soul.model.common.Sortable
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer value) {
		this.id = value;
	}
	public java.util.Date getPublishTime() {
		return this.publishTime;
	}

	public void setPublishTime(java.util.Date value) {
		this.publishTime = value;
	}
	public Integer getPublishUserId() {
		return this.publishUserId;
	}

	public void setPublishUserId(Integer value) {
		this.publishUserId = value;
	}
	public String getAnnouncementType() {
		return this.announcementType;
	}

	public void setAnnouncementType(String value) {
		this.announcementType = value;
	}
	public String getPublishUserName() {
		return this.publishUserName;
	}

	public void setPublishUserName(String value) {
		this.publishUserName = value;
	}
	public Boolean getTimingSend() {
		return this.timingSend;
	}

	public void setTimingSend(Boolean value) {
		this.timingSend = value;
	}

	public Integer getRecipientUserId() {
		return recipientUserId;
	}

	public void setRecipientUserId(Integer recipientUserId) {
		this.recipientUserId = recipientUserId;
	}

	public Date getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(Date saveTime) {
		this.saveTime = saveTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public Boolean getNoShow() {
		return noShow;
	}

	public void setNoShow(Boolean noShow) {
		this.noShow = noShow;
	}
//endregion

	//region your codes 2

	//endregion your codes 2

}