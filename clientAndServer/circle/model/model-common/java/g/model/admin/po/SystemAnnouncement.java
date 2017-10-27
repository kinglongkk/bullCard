package g.model.admin.po;


import org.soul.commons.bean.IEntity;


/**
 * 系统公告实体
 *
 * @author River
 * @tableAuthor orange
 * @time 2015-12-18 12:00:28
 */
//region your codes 1
public class SystemAnnouncement implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 4792035243406698309L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_RELEASE_MODE = "releaseMode";
	public static final String PROP_PUBLISH_TIME = "publishTime";
	public static final String PROP_PUBLISH_USER_ID = "publishUserId";
	public static final String PROP_ANNOUNCEMENT_TYPE = "announcementType";
	public static final String PROP_PUBLISH_PLATFORM = "publishPlatform";
	public static final String PROP_PUBLISH_USER_NAME = "publishUserName";
	public static final String PROP_OPERATE_TIME = "operateTime";
	public static final String PROP_API_ID = "apiId";
	public static final String PROP_GAME_ID = "gameId";
	public static final String PROP_SITE_ID = "siteId";
	public static final String PROP_PUBLISH_SITE_ID = "publishSiteId";
	public static final String PROP_TIMING_SEND = "timingSend";
	public static final String PROP_ANNOUNCEMENT_SUBTYPE = "announcementSubtype";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Integer id;
	/** 发布方式 */
	private String releaseMode;
	/** 发布时间 */
	private java.util.Date publishTime;
	/** 发布人id */
	private Integer publishUserId;
	/** 公告类型operation.announcement_type(系统公告gg,运营公告,平台公告,游戏公告) */
	private String announcementType;
	/** 发布平台operation.publish_platform(总控,运营商) */
	private String publishPlatform;
	/** 发布人账号 */
	private String publishUserName;
	/** 操作时间 */
	private java.util.Date operateTime;
	/** api主键，仅游戏公告该字段有值 */
	private Integer apiId;
	/** 游戏id */
	private Integer gameId;
	/** 接收站点id(为空即为下级所有站点) */
	private Integer siteId;
	/** 总控或运营商ID */
	private Integer publishSiteId;
	/** 是否定时发送 */
	private Boolean timingSend;
	/** 公告子类型（游戏公告：日常公告，维护公告） */
	private String announcementSubtype;
	//endregion

	
	//region constuctors
	public SystemAnnouncement(){
	}

	public SystemAnnouncement(Integer id){
		this.id = id;
	}
	//endregion


	//region getters and setters

	public Integer getPublishSiteId() {
		return publishSiteId;
	}

	public void setPublishSiteId(Integer publishSiteId) {
		this.publishSiteId = publishSiteId;
	}

	@org.soul.model.common.Sortable
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer value) {
		this.id = value;
	}
	public String getReleaseMode() {
		return this.releaseMode;
	}

	public void setReleaseMode(String value) {
		this.releaseMode = value;
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
	public String getPublishPlatform() {
		return this.publishPlatform;
	}

	public void setPublishPlatform(String value) {
		this.publishPlatform = value;
	}
	public String getPublishUserName() {
		return this.publishUserName;
	}

	public void setPublishUserName(String value) {
		this.publishUserName = value;
	}
	@org.soul.model.common.Sortable
	public java.util.Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(java.util.Date value) {
		this.operateTime = value;
	}
	public Integer getApiId() {
		return this.apiId;
	}

	public void setApiId(Integer value) {
		this.apiId = value;
	}
	public Integer getGameId() {
		return this.gameId;
	}

	public void setGameId(Integer value) {
		this.gameId = value;
	}
	public Integer getSiteId() {
		return this.siteId;
	}

	public void setSiteId(Integer value) {
		this.siteId = value;
	}
	public Boolean getTimingSend() {
		return this.timingSend;
	}

	public void setTimingSend(Boolean value) {
		this.timingSend = value;
	}
	public String getAnnouncementSubtype() {
		return this.announcementSubtype;
	}

	public void setAnnouncementSubtype(String value) {
		this.announcementSubtype = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}