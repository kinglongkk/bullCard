package g.model.account.po;

import org.soul.commons.bean.IEntity;
import org.soul.commons.lang.DateTool;
import org.soul.commons.support.Nonpersistent;
import org.soul.model.security.privilege.po.SysUserStatus;
import g.model.common.Const;
import g.model.common.FreezeType;

import java.util.Date;


/**
 * 账号管理实体
 *
 * @author tom
 * @time 2016-04-18 12:41:36
 */
//region your codes 1
public class VAccountManage implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -3831937012229187493L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_USERNAME = "username";
	public static final String PROP_STATUS = "status";
	public static final String PROP_FREEZE_TYPE = "freezeType";
	public static final String PROP_FREEZE_START_TIME = "freezeStartTime";
	public static final String PROP_FREEZE_END_TIME = "freezeEndTime";
	public static final String PROP_FREEZE_USERNAME = "freezeUsername";
	public static final String PROP_FREEZE_TIME = "freezeTime";
	public static final String PROP_DISABLED_USERNAME = "disabledUsername";
	public static final String PROP_DISABLED_TIME = "disabledTime";
	public static final String PROP_CREATE_TIME = "createTime";
	public static final String PROP_ISONLINE = "isonline";
	public static final String PROP_FREEZE_CONTENT = "freezeContent";
	//endregion


	//region properties
	/**  */
	private Integer id;
	/**  */
	private String username;
	/**  */
	private String status;
	/**  */
	private String freezeType;
	/**  */
	private java.util.Date freezeStartTime;
	/**  */
	private java.util.Date freezeEndTime;
	/**  */
	private String freezeUsername;
	/**  */
	private java.util.Date freezeTime;
	/**  */
	private String disabledUsername;
	/**  */
	private java.util.Date disabledTime;
	/**  */
	private java.util.Date createTime;
	/**  */
	private Long isonline;
	/**  */
	private String freezeContent;

	private String userType;
	//endregion


	//region constuctors
	public VAccountManage(){
	}

	public VAccountManage(Integer id){
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
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String value) {
		this.username = value;
	}
	public String getStatus() {
		return this.status;
	}

	public String getFreezeType() {
		return this.freezeType;
	}

	public void setFreezeType(String value) {
		this.freezeType = value;
	}
	public java.util.Date getFreezeStartTime() {
		return this.freezeStartTime;
	}

	public void setFreezeStartTime(java.util.Date value) {
		this.freezeStartTime = value;
	}
	public java.util.Date getFreezeEndTime() {
		return this.freezeEndTime;
	}


	public String getFreezeUsername() {
		return this.freezeUsername;
	}

	public void setFreezeUsername(String value) {
		this.freezeUsername = value;
	}
	public java.util.Date getFreezeTime() {
		return this.freezeTime;
	}

	public void setFreezeTime(java.util.Date value) {
		this.freezeTime = value;
	}
	public String getDisabledUsername() {
		return this.disabledUsername;
	}

	public void setDisabledUsername(String value) {
		this.disabledUsername = value;
	}
	public java.util.Date getDisabledTime() {
		return this.disabledTime;
	}

	public void setDisabledTime(java.util.Date value) {
		this.disabledTime = value;
	}
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	public Long getIsonline() {
		return this.isonline;
	}

	public void setIsonline(Long value) {
		this.isonline = value;
	}
	public String getFreezeContent() {
		return this.freezeContent;
	}

	public void setFreezeContent(String value) {
		this.freezeContent = value;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	//endregion

	//region your codes 2
	public void setStatus(String value) {
		this.status = value;
		if (!SysUserStatus.DISABLED.getCode().equals(getStatus())) {
			if (getFreezeEndTime()!=null && getFreezeEndTime().compareTo(new Date())>0 && getFreezeStartTime()!=null && getFreezeStartTime().compareTo(new Date())<0) {
				this.status = SysUserStatus.LOCKED.getCode();
			}
			if (FreezeType.AUTO.getCode().equals(getFreezeType()) || Const.Platform_Forever_Date.equals(DateTool.formatDate(getFreezeEndTime(),DateTool.FMT_HYPHEN_DAY))) {
				this.setIsAllowOperate(false);
			}
		}
	}

	public void setFreezeEndTime(java.util.Date value) {
		this.freezeEndTime = value;
		setTimeInterval(DateTool.hoursBetween(getFreezeEndTime(),getFreezeStartTime())+"");
	}

	private Boolean isAllowOperate = true;

	private String timeInterval;

	@Nonpersistent
	public Boolean getIsAllowOperate() {
		return isAllowOperate;
	}

	public void setIsAllowOperate(Boolean isAllowOperate) {
		this.isAllowOperate = isAllowOperate;
	}

	@Nonpersistent
	public String getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(String timeInterval) {
		this.timeInterval = timeInterval;
	}
//endregion your codes 2

}