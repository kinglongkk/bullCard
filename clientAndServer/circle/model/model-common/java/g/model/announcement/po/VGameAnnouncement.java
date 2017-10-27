
package g.model.announcement.po;

import org.soul.commons.bean.IEntity;
import org.soul.commons.lang.DateTool;
import org.soul.commons.support.Nonpersistent;

import java.util.Date;


/**
 * 游戏公告表实体
 *
 * @author lenovo
 * @time 2016-10-28 10:53:18
 */
//region your codes 1
public class VGameAnnouncement implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -4041997851320552654L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_LOCAL = "local";
	public static final String PROP_PUBLISH_TIME = "publishTime";
	public static final String PROP_PUBLISH_USER_ID = "publishUserId";
	public static final String PROP_PUBLISH_USER_NAME = "publishUserName";
	public static final String PROP_ANNOUNCEMENT_TYPE = "announcementType";
	public static final String PROP_SAVE_TIME = "saveTime";
	public static final String PROP_GAME_ID = "gameId";
	public static final String PROP_GAME_NAME = "gameName";
	public static final String PROP_TITLE = "title";
	public static final String PROP_CONTENT = "content";
	public static final String PROP_VALIDITY_START_TIME = "validityStartTime";
	public static final String PROP_VALIDITY_END_TIME = "validityEndTime";
	public static final String PROP_REPEAT = "repeat";
	public static final String PROP_REPEAT_TIME = "repeatTime";
	public static final String PROP_REPEAT_UNIT = "repeatUnit";
	//endregion


	//region properties
	/** 主键 */
	private Integer id;
	/** 发布时间 */
	private java.util.Date publishTime;
	/** 语言版本(site_language) */
	private String local;
	/** 发布人id */
	private Integer publishUserId;
	/** 发布人账号 */
	private String publishUserName;
	/** 通知方式 */
	private String announcementType;
	/** 保存时间 */
	private java.util.Date saveTime;
	/** 选择游戏id */
	private String gameId;
	/** 游戏名称 */
	private String gameName;
	/** 标题 */
	private String title;
	/** 内容 */
	private String content;
	/** 有效期起始 */
	private java.util.Date validityStartTime;
	/** 有效期结束 */
	private java.util.Date validityEndTime;
	/** 是否重复 */
	private Boolean repeat;
	/** 间隔时间 */
	private Integer repeatTime;
	/** 间隔时间单位 */
	private String repeatUnit;

	private String readUserId;
	//endregion


	//region constuctors
	public VGameAnnouncement(){
	}

	public VGameAnnouncement(Integer id){
		this.id = id;
	}
	//endregion


	//region getters and setters
	public Integer getId() {
		return this.id;
	}

	public static String getPropLocal() {
		return PROP_LOCAL;
	}

	public String getLocal() {
		return this.local;
	}

	public void setLocal(String value) {
		this.local = value;
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
	public String getPublishUserName() {
		return this.publishUserName;
	}

	public void setPublishUserName(String value) {
		this.publishUserName = value;
	}
	public String getAnnouncementType() {
		return this.announcementType;
	}

	public void setAnnouncementType(String value) {
		this.announcementType = value;
	}
	public java.util.Date getSaveTime() {
		return this.saveTime;
	}

	public void setSaveTime(java.util.Date value) {
		this.saveTime = value;
	}
	public String getGameId() {
		return this.gameId;
	}

	public void setGameId(String value) {
		this.gameId = value;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
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
	public java.util.Date getValidityStartTime() {
		return this.validityStartTime;
	}

	public void setValidityStartTime(java.util.Date value) {
		this.validityStartTime = value;
	}
	public java.util.Date getValidityEndTime() {
		return this.validityEndTime;
	}

	public void setValidityEndTime(java.util.Date value) {
		this.validityEndTime = value;
	}
	public Boolean getRepeat() {
		return this.repeat;
	}

	public void setRepeat(Boolean value) {
		this.repeat = value;
	}
	public Integer getRepeatTime() {
		return this.repeatTime;
	}

	public void setRepeatTime(Integer value) {
		this.repeatTime = value;
	}
	public String getRepeatUnit() {
		return this.repeatUnit;
	}

	public void setRepeatUnit(String value) {
		this.repeatUnit = value;
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