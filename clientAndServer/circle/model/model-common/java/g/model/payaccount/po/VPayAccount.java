package g.model.payaccount.po;

import g.model.enums.PayAccountStatusEnum;
import org.soul.commons.bean.IEntity;


/**
 * 公司、线上入款账号视图实体
 *
 * @author mark
 * @tableAuthor edit by younger
 * @time 2016-7-13 20:37:09
 */
//region your codes 1
public class VPayAccount implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 4996360434416701015L;

	/** 账户状态label */
	private String statusLabel;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_PAY_NAME = "payName";
	public static final String PROP_ACCOUNT = "account";
	public static final String PROP_FULL_NAME = "fullName";
	public static final String PROP_DISABLE_AMOUNT = "disableAmount";
	public static final String PROP_PAY_KEY = "payKey";
	public static final String PROP_STATUS = "status";
	public static final String PROP_CREATE_TIME = "createTime";
	public static final String PROP_CREATE_USER = "createUser";
	public static final String PROP_TYPE = "type";
	public static final String PROP_ACCOUNT_TYPE = "accountType";
	public static final String PROP_BANK_CODE = "bankCode";
	public static final String PROP_PAY_URL = "payUrl";
	public static final String PROP_CODE = "code";
	public static final String PROP_DEPOSIT_COUNT = "depositCount";
	public static final String PROP_DEPOSIT_TOTAL = "depositTotal";
	public static final String PROP_DEPOSIT_DEFAULT_COUNT = "depositDefaultCount";
	public static final String PROP_DEPOSIT_DEFAULT_TOTAL = "depositDefaultTotal";
	public static final String PROP_SINGLE_DEPOSIT_MIN = "singleDepositMin";
	public static final String PROP_SINGLE_DEPOSIT_MAX = "singleDepositMax";
	public static final String PROP_EFFECTIVE_MINUTES = "effectiveMinutes";
	public static final String PROP_FULL_RANK = "fullRank";
	public static final String PROP_OPEN_ACOUNT_NAME = "openAcountName";
	public static final String PROP_RECHARGE_NUM = "rechargeNum";
	public static final String PROP_RECHARGE_AMOUNT = "rechargeAmount";
	public static final String PROP_LAST_RECHARGE = "lastRecharge";
	//endregion
	
	
	//region properties
	/**  */
	private Integer id;
	/**  */
	private String payName;
	/**  */
	private String account;
	/**  */
	private String fullName;
	/**  */
	private Integer disableAmount;
	/**  */
	private String payKey;
	/**  */
	private String status;
	/**  */
	private java.util.Date createTime;
	/**  */
	private Integer createUser;
	/**  */
	private String type;
	/**  */
	private String accountType;
	/**  */
	private String bankCode;
	/**  */
	private String payUrl;
	/**  */
	private String code;
	/**  */
	private Integer depositCount;
	/**  */
	private Double depositTotal;
	/**  */
	private Integer depositDefaultCount;
	/**  */
	private Double depositDefaultTotal;
	/**  */
	private Integer singleDepositMin;
	/**  */
	private Integer singleDepositMax;
	/**  */
	private Integer effectiveMinutes;
	/**  */
	private Boolean fullRank;
	/**  */
	private String openAcountName;
	/**  */
	private Long rechargeNum;
	/**  */
	private Double rechargeAmount;
	/**  */
	private java.util.Date lastRecharge;
	//endregion

	
	//region constuctors
	public VPayAccount(){
	}

	public VPayAccount(Integer id){
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
	public String getPayName() {
		return this.payName;
	}

	public void setPayName(String value) {
		this.payName = value;
	}
	public String getAccount() {
		return this.account;
	}

	public void setAccount(String value) {
		this.account = value;
	}
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String value) {
		this.fullName = value;
	}
	public Integer getDisableAmount() {
		return this.disableAmount;
	}

	public void setDisableAmount(Integer value) {
		this.disableAmount = value;
	}
	public String getPayKey() {
		return this.payKey;
	}

	public void setPayKey(String value) {
		this.payKey = value;
	}
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String value) {
		this.status = value;
		setStatusLabel(status);
	}
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	public Integer getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(Integer value) {
		this.createUser = value;
	}
	public String getType() {
		return this.type;
	}

	public void setType(String value) {
		this.type = value;
	}
	public String getAccountType() {
		return this.accountType;
	}

	public void setAccountType(String value) {
		this.accountType = value;
	}
	public String getBankCode() {
		return this.bankCode;
	}

	public void setBankCode(String value) {
		this.bankCode = value;
	}
	public String getPayUrl() {
		return this.payUrl;
	}

	public void setPayUrl(String value) {
		this.payUrl = value;
	}
	public String getCode() {
		return this.code;
	}

	public void setCode(String value) {
		this.code = value;
	}
	public Integer getDepositCount() {
		return this.depositCount;
	}

	public void setDepositCount(Integer value) {
		this.depositCount = value;
	}
	public Double getDepositTotal() {
		return this.depositTotal;
	}

	public void setDepositTotal(Double value) {
		this.depositTotal = value;
	}
	public Integer getDepositDefaultCount() {
		return this.depositDefaultCount;
	}

	public void setDepositDefaultCount(Integer value) {
		this.depositDefaultCount = value;
	}
	public Double getDepositDefaultTotal() {
		return this.depositDefaultTotal;
	}

	public void setDepositDefaultTotal(Double value) {
		this.depositDefaultTotal = value;
	}
	public Integer getSingleDepositMin() {
		return this.singleDepositMin;
	}

	public void setSingleDepositMin(Integer value) {
		this.singleDepositMin = value;
	}
	public Integer getSingleDepositMax() {
		return this.singleDepositMax;
	}

	public void setSingleDepositMax(Integer value) {
		this.singleDepositMax = value;
	}
	public Integer getEffectiveMinutes() {
		return this.effectiveMinutes;
	}

	public void setEffectiveMinutes(Integer value) {
		this.effectiveMinutes = value;
	}
	public Boolean getFullRank() {
		return this.fullRank;
	}

	public void setFullRank(Boolean value) {
		this.fullRank = value;
	}
	public String getOpenAcountName() {
		return this.openAcountName;
	}

	public void setOpenAcountName(String value) {
		this.openAcountName = value;
	}
	public Long getRechargeNum() {
		return this.rechargeNum;
	}

	public void setRechargeNum(Long value) {
		this.rechargeNum = value;
	}
	public Double getRechargeAmount() {
		return this.rechargeAmount;
	}

	public void setRechargeAmount(Double value) {
		this.rechargeAmount = value;
	}
	public java.util.Date getLastRecharge() {
		return this.lastRecharge;
	}

	public void setLastRecharge(java.util.Date value) {
		this.lastRecharge = value;
	}
	//endregion

	//region your codes 2

	public String getStatusLabel() {
		return statusLabel;
	}

	public void setStatusLabel(String status) {
		if (PayAccountStatusEnum.USING.getCode().equals(status)) {
			this.statusLabel = PayAccountStatusEnum.USING.getDictCode();
		} else if (PayAccountStatusEnum.DELETED.getCode().equals(status)) {
			this.statusLabel = PayAccountStatusEnum.DELETED.getDictCode();
		} else if (PayAccountStatusEnum.DISABLED.getCode().equals(status)) {
			this.statusLabel = PayAccountStatusEnum.DISABLED.getDictCode();
		} else if (PayAccountStatusEnum.FREEZE.getCode().equals(status)) {
			this.statusLabel = PayAccountStatusEnum.FREEZE.getDictCode();
		}
	}


	//endregion your codes 2

}