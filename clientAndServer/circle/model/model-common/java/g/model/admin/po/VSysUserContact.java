package g.model.admin.po;

import org.soul.commons.bean.IEntity;
import org.soul.commons.security.anno.CryptEntity;
import org.soul.commons.security.key.CryptoKey;
import org.soul.model.common.Sortable;


/**
 * 系统公告视图实体
 *
 * @author orange
 * @time 2015-12-18 11:08:43
 */
//region your codes 1
@CryptEntity(password = CryptoKey.KEY_NOTICE_CONTACT_WAY, fields = {
		VSysUserContact.PROP_QQ,
		VSysUserContact.PROP_MSN,
		VSysUserContact.PROP_MAIL,
		VSysUserContact.PROP_SKYPE,
		VSysUserContact.PROP_MOBILE_PHONE,
})
public class VSysUserContact implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 161211983231394568L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_USERNAME = "username";
	public static final String PROP_STATUS = "status";
	public static final String PROP_SUBSYS_CODE = "subsysCode";
	public static final String PROP_MOBILE_PHONE = "mobilePhone";
	public static final String PROP_MAIL = "mail";
	public static final String PROP_QQ = "qq";
	public static final String PROP_MSN = "msn";
	public static final String PROP_SKYPE = "skype";
	public static final String PROP_MOBILE_PHONE_WAY_STATUS = "mobilePhoneWayStatus";
	public static final String PROP_MAIL_WAY_STATUS = "mailWayStatus";
	public static final String PROP_QQ_WAY_STATUS = "qqWayStatus";
	public static final String PROP_MSN_WAY_STATUS = "msnWayStatus";
	public static final String PROP_SKYPE_WAY_STATUS = "skypeWayStatus";
	//endregion


	//region properties
	/** 主键 */
	private Integer id;
	/** 账号 */
	private String username;
	/** 状态 */
	private String status;
	private String subsysCode;
	/** 手机号 */
	private String mobilePhone;
	/** 邮箱 */
	private String mail;
	/** qq */
	private String qq;
	/** msn */
	private String msn;
	/** skype */
	private String skype;
	/** 手机状态 */
	private String mobilePhoneWayStatus;
	/** 邮件状态 */
	private String mailWayStatus;
	/** qq状态 */
	private String qqWayStatus;
	/** msn状态 */
	private String msnWayStatus;
	/** skype状态 */
	private String skypeWayStatus;
	//endregion

	//region getters and setters
	@Sortable
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer value) {
		this.id = value;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMailWayStatus() {
		return mailWayStatus;
	}

	public void setMailWayStatus(String mailWayStatus) {
		this.mailWayStatus = mailWayStatus;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getMobilePhoneWayStatus() {
		return mobilePhoneWayStatus;
	}

	public void setMobilePhoneWayStatus(String mobilePhoneWayStatus) {
		this.mobilePhoneWayStatus = mobilePhoneWayStatus;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getMsnWayStatus() {
		return msnWayStatus;
	}

	public void setMsnWayStatus(String msnWayStatus) {
		this.msnWayStatus = msnWayStatus;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getQqWayStatus() {
		return qqWayStatus;
	}

	public void setQqWayStatus(String qqWayStatus) {
		this.qqWayStatus = qqWayStatus;
	}

	public String getSkype() {
		return skype;
	}

	public void setSkype(String skype) {
		this.skype = skype;
	}

	public String getSkypeWayStatus() {
		return skypeWayStatus;
	}

	public void setSkypeWayStatus(String skypeWayStatus) {
		this.skypeWayStatus = skypeWayStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getSubsysCode() {

		return subsysCode;
	}

	public void setSubsysCode(String subsysCode) {
		this.subsysCode = subsysCode;
	}
	//endregion

	//region your codes 2
	//endregion your codes 2

}