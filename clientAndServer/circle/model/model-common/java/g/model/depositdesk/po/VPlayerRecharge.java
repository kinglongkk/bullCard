package g.model.depositdesk.po;

import org.soul.commons.bean.IEntity;
import org.soul.model.common.Sortable;


/**
 * 实体
 *
 * @author tom
 * @time 2016-7-14 9:46:35
 */
//region your codes 1
public class VPlayerRecharge implements IEntity<Long> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -7541140345141948716L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_PLAYER_ID = "playerId";
	public static final String PROP_PLAYER_TRANSACTION_ID = "playerTransactionId";
	public static final String PROP_TRANSACTION_NO = "transactionNo";
	public static final String PROP_RECHARGE_AMOUNT = "rechargeAmount";
	public static final String PROP_RECHARGE_REMARK = "rechargeRemark";
	public static final String PROP_FAVORABLE_TOTAL_AMOUNT = "favorableTotalAmount";
	public static final String PROP_IS_COUNTER_FEE = "isCounterFee";
	public static final String PROP_COUNTER_FEE_PATH = "counterFeePath";
	public static final String PROP_CF_RATIO_OR_FIXED = "cfRatioOrFixed";
	public static final String PROP_COUNTER_FEE = "counterFee";
	public static final String PROP_IS_AUDIT_RECHARGE = "isAuditRecharge";
	public static final String PROP_RECHARGE_TYPE = "rechargeType";
	public static final String PROP_IS_RELATED_ORDER = "isRelatedOrder";
	public static final String PROP_RELATED_ORDER_ID = "relatedOrderId";
	public static final String PROP_PAYER_BANK = "payerBank";
	public static final String PROP_PAYER_NAME = "payerName";
	public static final String PROP_PAYER_BANKCARD = "payerBankcard";
	public static final String PROP_CREATE_TIME = "createTime";
	public static final String PROP_CHECK_TIME = "checkTime";
	public static final String PROP_CHECK_USER_ID = "checkUserId";
	public static final String PROP_CHECK_REMARK = "checkRemark";
	public static final String PROP_CHECK_STATUS = "checkStatus";
	public static final String PROP_BANK_ORDER = "bankOrder";
	public static final String PROP_RETURN_TIME = "returnTime";
	public static final String PROP_PAYER_BANK_TYPE = "payerBankType";
	public static final String PROP_IS_FIRST_RECHARGE = "isFirstRecharge";
	public static final String PROP_COUNTERFEE_REMARK = "counterfeeRemark";
	public static final String PROP_IS_FAVORABLE = "isFavorable";
	public static final String PROP_PAY_ACCOUNT_ID = "payAccountId";
	public static final String PROP_RELATED_TRANSACTION_NO = "relatedTransactionNo";
	public static final String PROP_CF_RATIO_OR_FIXED_VALUE = "cfRatioOrFixedValue";
	public static final String PROP_RETURN_RECHARGE = "returnRecharge";
	public static final String PROP_IS_RETURN_RECHARGE = "isReturnRecharge";
	public static final String PROP_ARTIFICIAL_REASON_CONTENT = "artificialReasonContent";
	public static final String PROP_RECHARGE_TYPE_PARENT = "rechargeTypeParent";
	public static final String PROP_RECHARGE_TOTAL_AMOUNT = "rechargeTotalAmount";
	public static final String PROP_FAILURE_TITLE = "failureTitle";
	public static final String PROP_ARTIFICIAL_REASON_TITLE = "artificialReasonTitle";
	public static final String PROP_RECEIVE_ACCOUNT = "receiveAccount";
	public static final String PROP_RECEIVE_NAME = "receiveName";
	public static final String PROP_MASTER_BANK_TYPE = "masterBankType";
	public static final String PROP_RECHARGE_STATUS = "rechargeStatus";
	public static final String PROP_USERNAME = "username";
	public static final String PROP_CHECK_USER_NAME = "checkUserName";
	public static final String PROP_MAIN_CURRENCY = "mainCurrency";
	public static final String PROP_NATION = "nation";
	public static final String PROP_RECHARGE_COUNT = "rechargeCount";
	public static final String PROP_MASTER_BANK_CARD = "masterBankCard";
	public static final String PROP_MASTER_NAME = "masterName";
	public static final String PROP_MASTER_BANKCARD_STATUS = "masterBankcardStatus";
	public static final String PROP_MASTER_BANK = "masterBank";
	public static final String PROP_DEFAULT_TIMEZONE = "defaultTimezone";
	//endregion
	
	
	//region properties
	/**  */
	private Long id;
	/**  */
	private Integer playerId;
	/**  */
	private Long playerTransactionId;
	/**  */
	private String transactionNo;
	/**  */
	private Double rechargeAmount;
	/**  */
	private String rechargeRemark;
	/**  */
	private Double favorableTotalAmount;
	/**  */
	private Boolean isCounterFee;
	/**  */
	private Boolean counterFeePath;
	/**  */
	private Boolean cfRatioOrFixed;
	/**  */
	private Double counterFee;
	/**  */
	private Boolean isAuditRecharge;
	/**  */
	private String rechargeType;
	/**  */
	private Boolean isRelatedOrder;
	/**  */
	private Integer relatedOrderId;
	/**  */
	private String payerBank;
	/**  */
	private String payerName;
	/**  */
	private String payerBankcard;
	/**  */
	private java.util.Date createTime;
	/**  */
	private java.util.Date checkTime;
	/**  */
	private Integer checkUserId;
	/**  */
	private String checkRemark;
	/**  */
	private String checkStatus;
	/**  */
	private String bankOrder;
	/**  */
	private java.util.Date returnTime;
	/**  */
	private String payerBankType;
	/**  */
	private Boolean isFirstRecharge;
	/**  */
	private String counterfeeRemark;
	/**  */
	private Boolean isFavorable;
	/**  */
	private Integer payAccountId;
	/**  */
	private String relatedTransactionNo;
	/**  */
	private Double cfRatioOrFixedValue;
	/**  */
	private Integer returnRecharge;
	/**  */
	private Boolean isReturnRecharge;
	/**  */
	private String artificialReasonContent;
	/**  */
	private String rechargeTypeParent;
	/**  */
	private Double rechargeTotalAmount;
	/**  */
	private String failureTitle;
	/**  */
	private String artificialReasonTitle;
	/**  */
	private String receiveAccount;
	/**  */
	private String receiveName;
	/**  */
	private String masterBankType;
	/**  */
	private String rechargeStatus;
	/**  */
	private String username;
	/**  */
	private String checkUserName;
	/**  */
	private String mainCurrency;
	/**  */
	private String nation;
	/**  */
	private Integer rechargeCount;
	/**  */
	private String masterBankCard;
	/**  */
	private String masterName;
	/**  */
	private String masterBankcardStatus;
	/**  */
	private String masterBank;
	/**  */
	private String defaultTimezone;
	//endregion

	
	//region constuctors
	public VPlayerRecharge(){
	}

	public VPlayerRecharge(Long id){
		this.id = id;
	}
	//endregion


	//region getters and setters
	public Long getId() {
		return this.id;
	}

	public void setId(Long value) {
		this.id = value;
	}
	public Integer getPlayerId() {
		return this.playerId;
	}

	public void setPlayerId(Integer value) {
		this.playerId = value;
	}
	public Long getPlayerTransactionId() {
		return this.playerTransactionId;
	}

	public void setPlayerTransactionId(Long value) {
		this.playerTransactionId = value;
	}
	@Sortable
	public String getTransactionNo() {
		return this.transactionNo;
	}

	public void setTransactionNo(String value) {
		this.transactionNo = value;
	}
	public Double getRechargeAmount() {
		return this.rechargeAmount;
	}

	public void setRechargeAmount(Double value) {
		this.rechargeAmount = value;
	}
	public String getRechargeRemark() {
		return this.rechargeRemark;
	}

	public void setRechargeRemark(String value) {
		this.rechargeRemark = value;
	}
	public Double getFavorableTotalAmount() {
		return this.favorableTotalAmount;
	}

	public void setFavorableTotalAmount(Double value) {
		this.favorableTotalAmount = value;
	}
	public Boolean getIsCounterFee() {
		return this.isCounterFee;
	}

	public void setIsCounterFee(Boolean value) {
		this.isCounterFee = value;
	}
	public Boolean getCounterFeePath() {
		return this.counterFeePath;
	}

	public void setCounterFeePath(Boolean value) {
		this.counterFeePath = value;
	}
	public Boolean getCfRatioOrFixed() {
		return this.cfRatioOrFixed;
	}

	public void setCfRatioOrFixed(Boolean value) {
		this.cfRatioOrFixed = value;
	}
	public Double getCounterFee() {
		return this.counterFee;
	}

	public void setCounterFee(Double value) {
		this.counterFee = value;
	}
	public Boolean getIsAuditRecharge() {
		return this.isAuditRecharge;
	}

	public void setIsAuditRecharge(Boolean value) {
		this.isAuditRecharge = value;
	}
	public String getRechargeType() {
		return this.rechargeType;
	}

	public void setRechargeType(String value) {
		this.rechargeType = value;
	}
	public Boolean getIsRelatedOrder() {
		return this.isRelatedOrder;
	}

	public void setIsRelatedOrder(Boolean value) {
		this.isRelatedOrder = value;
	}
	public Integer getRelatedOrderId() {
		return this.relatedOrderId;
	}

	public void setRelatedOrderId(Integer value) {
		this.relatedOrderId = value;
	}
	public String getPayerBank() {
		return this.payerBank;
	}

	public void setPayerBank(String value) {
		this.payerBank = value;
	}
	public String getPayerName() {
		return this.payerName;
	}

	public void setPayerName(String value) {
		this.payerName = value;
	}
	public String getPayerBankcard() {
		return this.payerBankcard;
	}

	public void setPayerBankcard(String value) {
		this.payerBankcard = value;
	}
	@Sortable
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	public java.util.Date getCheckTime() {
		return this.checkTime;
	}

	public void setCheckTime(java.util.Date value) {
		this.checkTime = value;
	}
	public Integer getCheckUserId() {
		return this.checkUserId;
	}

	public void setCheckUserId(Integer value) {
		this.checkUserId = value;
	}
	public String getCheckRemark() {
		return this.checkRemark;
	}

	public void setCheckRemark(String value) {
		this.checkRemark = value;
	}
	public String getCheckStatus() {
		return this.checkStatus;
	}

	public void setCheckStatus(String value) {
		this.checkStatus = value;
	}
	public String getBankOrder() {
		return this.bankOrder;
	}

	public void setBankOrder(String value) {
		this.bankOrder = value;
	}
	public java.util.Date getReturnTime() {
		return this.returnTime;
	}

	public void setReturnTime(java.util.Date value) {
		this.returnTime = value;
	}
	public String getPayerBankType() {
		return this.payerBankType;
	}

	public void setPayerBankType(String value) {
		this.payerBankType = value;
	}
	public Boolean getIsFirstRecharge() {
		return this.isFirstRecharge;
	}

	public void setIsFirstRecharge(Boolean value) {
		this.isFirstRecharge = value;
	}
	public String getCounterfeeRemark() {
		return this.counterfeeRemark;
	}

	public void setCounterfeeRemark(String value) {
		this.counterfeeRemark = value;
	}
	public Boolean getIsFavorable() {
		return this.isFavorable;
	}

	public void setIsFavorable(Boolean value) {
		this.isFavorable = value;
	}
	public Integer getPayAccountId() {
		return this.payAccountId;
	}

	public void setPayAccountId(Integer value) {
		this.payAccountId = value;
	}
	public String getRelatedTransactionNo() {
		return this.relatedTransactionNo;
	}

	public void setRelatedTransactionNo(String value) {
		this.relatedTransactionNo = value;
	}
	public Double getCfRatioOrFixedValue() {
		return this.cfRatioOrFixedValue;
	}

	public void setCfRatioOrFixedValue(Double value) {
		this.cfRatioOrFixedValue = value;
	}
	public Integer getReturnRecharge() {
		return this.returnRecharge;
	}

	public void setReturnRecharge(Integer value) {
		this.returnRecharge = value;
	}
	public Boolean getIsReturnRecharge() {
		return this.isReturnRecharge;
	}

	public void setIsReturnRecharge(Boolean value) {
		this.isReturnRecharge = value;
	}
	public String getArtificialReasonContent() {
		return this.artificialReasonContent;
	}

	public void setArtificialReasonContent(String value) {
		this.artificialReasonContent = value;
	}
	public String getRechargeTypeParent() {
		return this.rechargeTypeParent;
	}

	public void setRechargeTypeParent(String value) {
		this.rechargeTypeParent = value;
	}
	public Double getRechargeTotalAmount() {
		return this.rechargeTotalAmount;
	}

	public void setRechargeTotalAmount(Double value) {
		this.rechargeTotalAmount = value;
	}
	public String getFailureTitle() {
		return this.failureTitle;
	}

	public void setFailureTitle(String value) {
		this.failureTitle = value;
	}
	public String getArtificialReasonTitle() {
		return this.artificialReasonTitle;
	}

	public void setArtificialReasonTitle(String value) {
		this.artificialReasonTitle = value;
	}
	public String getReceiveAccount() {
		return this.receiveAccount;
	}

	public void setReceiveAccount(String value) {
		this.receiveAccount = value;
	}
	public String getReceiveName() {
		return this.receiveName;
	}

	public void setReceiveName(String value) {
		this.receiveName = value;
	}
	public String getMasterBankType() {
		return this.masterBankType;
	}

	public void setMasterBankType(String value) {
		this.masterBankType = value;
	}
	public String getRechargeStatus() {
		return this.rechargeStatus;
	}

	public void setRechargeStatus(String value) {
		this.rechargeStatus = value;
	}
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String value) {
		this.username = value;
	}
	public String getCheckUserName() {
		return this.checkUserName;
	}

	public void setCheckUserName(String value) {
		this.checkUserName = value;
	}
	public String getMainCurrency() {
		return this.mainCurrency;
	}

	public void setMainCurrency(String value) {
		this.mainCurrency = value;
	}
	public String getNation() {
		return this.nation;
	}

	public void setNation(String value) {
		this.nation = value;
	}
	public Integer getRechargeCount() {
		return this.rechargeCount;
	}

	public void setRechargeCount(Integer value) {
		this.rechargeCount = value;
	}
	public String getMasterBankCard() {
		return this.masterBankCard;
	}

	public void setMasterBankCard(String value) {
		this.masterBankCard = value;
	}
	public String getMasterName() {
		return this.masterName;
	}

	public void setMasterName(String value) {
		this.masterName = value;
	}
	public String getMasterBankcardStatus() {
		return this.masterBankcardStatus;
	}

	public void setMasterBankcardStatus(String value) {
		this.masterBankcardStatus = value;
	}
	public String getMasterBank() {
		return this.masterBank;
	}

	public void setMasterBank(String value) {
		this.masterBank = value;
	}
	public String getDefaultTimezone() {
		return this.defaultTimezone;
	}

	public void setDefaultTimezone(String value) {
		this.defaultTimezone = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}