package g.model.agent.po;


import org.soul.commons.bean.IEntity;
import org.soul.commons.security.anno.CryptEntity;
import org.soul.commons.security.key.CryptoKey;
import org.soul.model.common.Sortable;
import org.soul.model.security.privilege.po.SysUserStatus;

import java.util.Date;


/**
 * 代理管理实体
 *
 * @author tom
 */
@CryptEntity(password = CryptoKey.KEY_NOTICE_CONTACT_WAY, fields = {
		VAgentManage.PROP_QQ,
		VAgentManage.PROP_MSN,
		VAgentManage.PROP_MAIL,
		VAgentManage.PROP_SKYPE,
		VAgentManage.PROP_MOBILE_PHONE,
})
public class VAgentManage implements IEntity<Integer> {

	//region your codes 3
	private static final long serialVersionUID = 3910424221124783651L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_USERNAME = "username";
	public static final String PROP_REAL_NAME = "realName";
	public static final String PROP_NICKNAME = "nickname";
	public static final String PROP_USER_TYPE = "userType";
	public static final String PROP_LAST_ACTIVE_TIME = "lastActiveTime";
	public static final String PROP_LAST_LOGIN_IP = "lastLoginIp";
	public static final String PROP_PLAYER_NUM = "playerNum";
	public static final String PROP_MAIL = "mail";
	public static final String PROP_SEX = "sex";
	public static final String PROP_COUNTRY = "country";
	public static final String PROP_NATION = "nation";
	public static final String PROP_CREATE_TIME = "createTime";
	public static final String PROP_LAST_LOGIN_TIME = "lastLoginTime";
	public static final String PROP_QQ = "qq";
	public static final String PROP_MSN = "msn";
	public static final String PROP_SKYPE = "skype";
	public static final String PROP_STATUS = "status";
	public static final String PROP_MOBILE_PHONE = "mobilePhone";
	public static final String PROP_FREEZE_END_TIME = "freezeEndTime";
	public static final String PROP_FREEZE_START_TIME = "freezeStartTime";
	//endregion

	//region properties
	/** 代理主键 */
	private Integer id;
	/** 账号 */
	private String username;
	/** 别名 */
	private String nickname;
	/** 真实姓名 */
	private String realName;
	/** 代理类型 */
	private String userType;
	/** 最后活跃时间 */
	private Date lastActiveTime;
	/** 上次登陆ip */
	private Long lastLoginIp;
	/** 更新时间 */
	private Date updateTime;
	/** 更新者 */
	private String updateUser;
	/** 玩家总数 */
	private Long playerNum;
	/** 手机 */
	private String mobilePhone;
	/** 邮箱 */
	private String mail;
	/** 性别 */
	private String sex;
	/** 国家 */
	private String country;
	/** 民族 */
	private String nation;
	/** 注册时间 */
	private Date createTime;
	/** 上次登录时间 */
	private Date lastLoginTime;
	/** QQ */
	private String qq;
	/** MSN */
	private String msn;
	/** Skype */
	private String skype;
	/** 状态 */
	private String status;
	/** 冻结结束时间 */
	private java.util.Date freezeEndTime;
	/** 冻结开始时间 */
	private java.util.Date freezeStartTime;
	//endregion


	//region constuctors
	public VAgentManage(){
	}

	public VAgentManage(Integer id){
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Date getLastActiveTime() {
		return lastActiveTime;
	}

	public void setLastActiveTime(Date lastActiveTime) {
		this.lastActiveTime = lastActiveTime;
	}

    public Long getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(Long lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	@Sortable
	public Long getPlayerNum() {
		return playerNum;
	}

	public void setPlayerNum(Long playerNum) {
		this.playerNum = playerNum;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	@Sortable
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getSkype() {
		return skype;
	}

	public void setSkype(String skype) {
		this.skype = skype;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getFreezeStartTime() {
		return freezeStartTime;
	}

	public void setFreezeStartTime(Date freezeStartTime) {
		this.freezeStartTime = freezeStartTime;
	}

	public Date getFreezeEndTime() {
		return freezeEndTime;
	}

	public void setFreezeEndTime(Date freezeEndTime) {
		this.freezeEndTime = freezeEndTime;
	}
	//endregion

	public void calStatus() {
		if (!SysUserStatus.DISABLED.equals(getStatus())) {
			if (getFreezeStartTime()!=null && getFreezeStartTime().compareTo(new Date())<0 && getFreezeEndTime()!=null && getFreezeEndTime().compareTo(new Date())>0) {
				this.status = SysUserStatus.LOCKED.getCode();
			}
		}
	}
}