package g.model.activitymessage.po;

import org.soul.commons.bean.IEntity;
import java.util.Date;

/**
 * 游戏信息表实体
 *
 * @author black
 * @time 2016-9-5 14:46:12
 */
//region your codes 1
public class ActivityMessage implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 9195217796817218935L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_STARTTIME = "startTime";
	public static final String PROP_ENDTIME = "endTime";
	public static final String PROP_CREATE_TIME = "createTime";
	public static final String PROP_CREATE_USERID = "createUserId";
	public static final String PROP_CREATE_USERNAME = "createUserName";
	public static final String PROP_ACTIVITY_CLASSIFY_KEY = "activityClassifyKey";
	public static final String PROP_ACTIVITY_TYPE_CODE = "activityTypeCode";
	public static final String PROP_IS_DISPLAY = "isDisplay";
	public static final String PROP_IS_DELETED = "isDeleted";
	public static final String PROP_UPDATE_USERID = "updateUserId";
	public static final String PROP_UPDATE_USERNAME = "updateUserName";
	public static final String PROP_UPDATE_TIME = "updateTime";
	public static final String PROP_ORDER_NUM = "orderNum";
	public static final String PROP_STATUS = "status";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Integer id;
	/** 开始时间 */
	private java.util.Date startTime;
	/** 结束时间 */
	private java.util.Date endTime;
	/** 创建时间 */
	private java.util.Date createTime;
	/** 创建人id */
	private Integer createUserId;
	/** 创建人用户名 */
	private String createUserName;
	/** 活动分类 */
	private String activityClassifyKey;
	/** 活动类型代码 */
	private String activityTypeCode;
	/** 是否展示 */
	private Boolean isDisplay;
	/** 是否删除 */
	private Boolean isDeleted;
	/** 更新人id */
	private Integer updateUserId;
	/** 更新人用户名 */
	private String updateUserName;
	/** 更新时间 */
	private java.util.Date updateTime;
	/** 排序编号 */
	private Integer orderNum;
	/** 活动状态 */
	private String status;
	//endregion

	
	//region constuctors
	public ActivityMessage(){
	}

	public ActivityMessage(Integer id){
		this.id = id;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getActivityClassifyKey() {
		return activityClassifyKey;
	}

	public void setActivityClassifyKey(String activityClassifyKey) {
		this.activityClassifyKey = activityClassifyKey;
	}

	public String getActivityTypeCode() {
		return activityTypeCode;
	}

	public void setActivityTypeCode(String activityTypeCode) {
		this.activityTypeCode = activityTypeCode;
	}

	public Boolean getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(Boolean isDisplay) {
		this.isDisplay = isDisplay;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}