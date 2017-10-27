package g.model.admin.po;

import org.soul.commons.bean.IEntity;
import org.soul.commons.lang.DateTool;
import org.soul.commons.support.Nonpersistent;
import org.soul.model.common.Sortable;

import java.util.Date;


/**
 * 系统公告视图实体
 *
 * @author orange
 * @time 2015-12-18 11:08:43
 */
//region your codes 1
public class VSysAnnouncement implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 161211983231394568L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_PUBLISH_TIME = "publishTime";
	public static final String PROP_PUBLISH_USER_ID = "publishUserId";
	public static final String PROP_LOCAL = "local";
	public static final String PROP_TITLE = "title";
	public static final String PROP_CONTENT = "content";
	public static final String PROP_ANNOUNCEMENT_TYPE = "announcementType";
	public static final String PROP_PUBLISH_USER_NAME = "publishUserName";
	public static final String PROP_TIMING_SEND = "timingSend";
	public static final String PROP_RECIPIENT_USER_ID = "recipientUserId";
	public static final String PROP_SAVE_TIME = "saveTime";
	public static final String PROP_STATUS = "status";
	public static final String PROP_GROUP_CODE = "groupCode";
	public static final String PROP_NO_SHOW = "noShow";
	public static final String PROP_READ_USER_ID = "readUserId";

	//endregion
	
	
	//region properties
	/** 主键 */
	private Integer id;
	/** 发布时间 */
	private Date publishTime;
	/** 发布人id */
	private Integer publishUserId;
	/** 语言版本(site_language) */
	private String local;
	/** 标题 */
	private String title;
	/** 内容 */
	private String content;
	/** 公告类型 */
	private String announcementType;
	/** 发布账号 */
	private String publishUserName;
	/** 是否定时发送 */
	private Boolean timingSend;
	/** 收件人Id */
	private Integer recipientUserId;
	/** 保存时间 */
	private Date saveTime;
	/** 状态:启用/禁用 */
	private String status;
	/** 是全局代理还是指定代理 */
	private String groupCode;
	/** 不在显示 */
	private Boolean noShow;

	private String readUserId;
	//endregion


	//region constuctors
	public VSysAnnouncement(){
	}

	public VSysAnnouncement(Integer id){
		this.id = id;
	}
	//endregion


	//region getters and setters
	@Sortable
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer value) {
		this.id = value;
	}
	@Sortable
	public Date getPublishTime() {
		return this.publishTime;
	}

	public void setPublishTime(Date value) {
		this.publishTime = value;
		this.publishStatus = DateTool.minutesBetween(publishTime, new Date());
	}
	public Integer getPublishUserId() {
		return this.publishUserId;
	}

	public void setPublishUserId(Integer value) {
		this.publishUserId = value;
	}
	public String getLocal() {
		return this.local;
	}

	public void setLocal(String value) {
		this.local = value;
	}
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String value) {
		this.title = value;
	}
	public String getContent() {
		return this.content;
	}

	public void setContent(String value) {
		this.content = value;
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

	public String getReadUserId() {
		return readUserId;
	}

	public void setReadUserId(String readUserId) {
		this.readUserId = readUserId;
	}
	//endregion

	//region your codes 2
	//公告发布状态
	private Long publishStatus;
	@Nonpersistent
	public Long getPublishStatus() {
		return DateTool.minutesBetween(getPublishTime(), new Date());
	}

	public void setPublishStatus(Long publishStatus) {
		this.publishStatus = publishStatus;
	}

	private boolean read;//自定义属性:是否已读
	@Nonpersistent
	public boolean getRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}
	//endregion your codes 2

}