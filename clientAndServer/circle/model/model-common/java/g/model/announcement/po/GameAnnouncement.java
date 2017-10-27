package g.model.announcement.po;


import org.soul.commons.bean.IEntity;
import org.soul.model.common.Sortable;


/**
 * 游戏公告表实体
 *
 * @author lenovo
 * @time 2017-3-27 11:23:00
 */
//region your codes 1
public class GameAnnouncement implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -4041997851320552654L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_PUBLISH_TIME = "publishTime";
	public static final String PROP_PUBLISH_USER_ID = "publishUserId";
	public static final String PROP_PUBLISH_USER_NAME = "publishUserName";
	public static final String PROP_ANNOUNCEMENT_TYPE = "announcementType";
	public static final String PROP_SAVE_TIME = "saveTime";
	public static final String PROP_GAME_ID = "gameId";
	public static final String PROP_TITLE = "title";
	public static final String PROP_CONTENT = "content";
	public static final String PROP_VALIDITY_START_TIME = "validityStartTime";
	public static final String PROP_VALIDITY_END_TIME = "validityEndTime";
	public static final String PROP_REPEAT = "repeat";
	public static final String PROP_REPEAT_TIME = "repeatTime";
	public static final String PROP_REPEAT_UNIT = "repeatUnit";
	public static final String PROP_LOCAL = "local";
	public static final String PROP_GAME_NAME = "gameName";
	public static final String PROP_MSG_TYPE = "msgType";
	public static final String PROP_GAIN_GOLD_NUM = "gainGoldNum";
	public static final String PROP_IS_SEND = "isSend";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Integer id;
	/** 发布时间 */
	private java.util.Date publishTime;
	/** 发布人id */
	private Integer publishUserId;
	/** 发布人账号 */
	private String publishUserName;
	/** 公告类型[banner 跑马灯,popup 弹窗] */
	private String announcementType;
	/** 保存时间 */
	private java.util.Date saveTime;
	/** 选择游戏id */
	private String gameId;
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
	/**  */
	private String local;
	/**  */
	private String gameName;
	/** 产生消息的类型,后台手工添加消息为：BACKSTAGE */
	private String msgType;
	/** 获得金币数量 */
	private Long gainGoldNum;
	/** 是否已经发送过 0:未发送，1：已发送 */
	private Integer isSend;
	//endregion

	
	//region constuctors
	public GameAnnouncement(){
	}

	public GameAnnouncement(Integer id){
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
	public String getLocal() {
		return this.local;
	}

	public void setLocal(String value) {
		this.local = value;
	}
	public String getGameName() {
		return this.gameName;
	}

	public void setGameName(String value) {
		this.gameName = value;
	}
	public String getMsgType() {
		return this.msgType;
	}

	public void setMsgType(String value) {
		this.msgType = value;
	}
	public Long getGainGoldNum() {
		return this.gainGoldNum;
	}

	public void setGainGoldNum(Long value) {
		this.gainGoldNum = value;
	}
	public Integer getIsSend() {
		return this.isSend;
	}

	public void setIsSend(Integer value) {
		this.isSend = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}