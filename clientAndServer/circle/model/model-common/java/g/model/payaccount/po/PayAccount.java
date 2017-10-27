package g.model.payaccount.po;


import org.soul.commons.bean.IEntity;


/**
 * 收款帐号-mark实体
 *
 * @author mark
 * @tableAuthor lorne
 * @time 2016-7-13 19:16:05
 */
//region your codes 1
public class PayAccount implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 3968173803787740334L;
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
	public static final String PROP_EFFECTIVE_MINUTES = "effectiveMinutes";
	public static final String PROP_SINGLE_DEPOSIT_MIN = "singleDepositMin";
	public static final String PROP_SINGLE_DEPOSIT_MAX = "singleDepositMax";
	public static final String PROP_FROZEN_TIME = "frozenTime";
	public static final String PROP_CHANNEL_JSON = "channelJson";
	public static final String PROP_FULL_RANK = "fullRank";
	public static final String PROP_CUSTOM_BANK_NAME = "customBankName";
	public static final String PROP_OPEN_ACOUNT_NAME = "openAcountName";
	public static final String PROP_QR_CODE_URL = "qrCodeUrl";
	//endregion
	
	
	//region properties
	/**  */
	private Integer id;
	/** 帐户名称 */
	private String payName;
	/** 账号 */
	private String account;
	/** 姓名 */
	private String fullName;
	/** 停用金额 */
	private Integer disableAmount;
	/** Key */
	private String payKey;
	/** 状态 */
	private String status;
	/** 创建时间 */
	private java.util.Date createTime;
	/** 创建人 */
	private Integer createUser;
	/** 帐户类型 */
	private String type;
	/** 账户类型（1银行账户；2第三方账户）(字典表pay_account_account_type) */
	private String accountType;
	/** 渠道(bank表的bank_name） */
	private String bankCode;
	/** 支付URL地址 */
	private String payUrl;
	/** 代号 */
	private String code;
	/** 累计入款次数 */
	private Integer depositCount;
	/** 累计入款金额 */
	private Double depositTotal;
	/** 一个周期内累计入款次数 */
	private Integer depositDefaultCount;
	/** 一个周期内累计入款金额 */
	private Double depositDefaultTotal;
	/** 有效分钟数 */
	private Integer effectiveMinutes;
	/** 单笔存款最少 */
	private Integer singleDepositMin;
	/** 单笔存款最大 */
	private Integer singleDepositMax;
	/** 冻结时间 */
	private java.util.Date frozenTime;
	/** 第三方接口的参数json[{column:"字段",value:"值"}] */
	private String channelJson;
	/** 全部层级 */
	private Boolean fullRank;
	/** 第三方自定义名称 */
	private String customBankName;
	/** 开户行 */
	private String openAcountName;
	/** 第三方入款账户二维码图片路径 */
	private String qrCodeUrl;
	//endregion

	
	//region constuctors
	public PayAccount(){
	}

	public PayAccount(Integer id){
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
	@org.soul.model.common.Sortable
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
	@org.soul.model.common.Sortable
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String value) {
		this.status = value;
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
	@org.soul.model.common.Sortable
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
	@org.soul.model.common.Sortable
	public Integer getDepositCount() {
		return this.depositCount;
	}

	public void setDepositCount(Integer value) {
		this.depositCount = value;
	}
	@org.soul.model.common.Sortable
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
	public Integer getEffectiveMinutes() {
		return this.effectiveMinutes;
	}

	public void setEffectiveMinutes(Integer value) {
		this.effectiveMinutes = value;
	}
	@org.soul.model.common.Sortable
	public Integer getSingleDepositMin() {
		return this.singleDepositMin;
	}

	public void setSingleDepositMin(Integer value) {
		this.singleDepositMin = value;
	}
	@org.soul.model.common.Sortable
	public Integer getSingleDepositMax() {
		return this.singleDepositMax;
	}

	public void setSingleDepositMax(Integer value) {
		this.singleDepositMax = value;
	}
	public java.util.Date getFrozenTime() {
		return this.frozenTime;
	}

	public void setFrozenTime(java.util.Date value) {
		this.frozenTime = value;
	}
	public String getChannelJson() {
		return this.channelJson;
	}

	public void setChannelJson(String value) {
		this.channelJson = value;
	}
	public Boolean getFullRank() {
		return this.fullRank;
	}

	public void setFullRank(Boolean value) {
		this.fullRank = value;
	}
	public String getCustomBankName() {
		return this.customBankName;
	}

	public void setCustomBankName(String value) {
		this.customBankName = value;
	}
	public String getOpenAcountName() {
		return this.openAcountName;
	}

	public void setOpenAcountName(String value) {
		this.openAcountName = value;
	}
	public String getQrCodeUrl() {
		return this.qrCodeUrl;
	}

	public void setQrCodeUrl(String value) {
		this.qrCodeUrl = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}