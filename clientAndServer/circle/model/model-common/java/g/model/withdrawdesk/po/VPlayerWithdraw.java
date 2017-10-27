package g.model.withdrawdesk.po;

import org.soul.commons.bean.IEntity;
import org.soul.model.common.Sortable;


/**
 * 玩家交易表视图 edit by river重建实体
 *
 * @author tom
 * @time 2016-7-14 11:55:05
 */
//region your codes 1
public class VPlayerWithdraw implements IEntity<Long> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -3031173995559796386L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_PLAYER_ID = "playerId";
	public static final String PROP_PLAYER_TRANSACTION_ID = "playerTransactionId";
	public static final String PROP_TRANSACTION_NO = "transactionNo";
	public static final String PROP_CURRENT_ACCOUNT_AMOUNT = "currentAccountAmount";
	public static final String PROP_CURRENT_RETURN_ZERO_AMOUNT = "currentReturnZeroAmount";
	public static final String PROP_CURRENT_BACKFLUSH_AMOUNT = "currentBackflushAmount";
	public static final String PROP_WITHDRAW_MONETARY = "withdrawMonetary";
	public static final String PROP_WITHDRAW_AMOUNT = "withdrawAmount";
	public static final String PROP_WITHDRAW_REMARK = "withdrawRemark";
	public static final String PROP_DEDUCT_FAVORABLE = "deductFavorable";
	public static final String PROP_COUNTER_FEE = "counterFee";
	public static final String PROP_ADMINISTRATIVE_FEE = "administrativeFee";
	public static final String PROP_IS_DEDUCT_AUDIT = "isDeductAudit";
	public static final String PROP_DEDUCT_AUDIT_RECHARGE_AMOUNT = "deductAuditRechargeAmount";
	public static final String PROP_DEDUCT_AUDIT_RECHARGE_INDEX = "deductAuditRechargeIndex";
	public static final String PROP_DEDUCT_AUDIT_FAVORABLE_AMOUNT = "deductAuditFavorableAmount";
	public static final String PROP_DEDUCT_AUDIT_FAVORABLE_INDEX = "deductAuditFavorableIndex";
	public static final String PROP_WITHDRAW_TYPE = "withdrawType";
	public static final String PROP_CREATE_TIME = "createTime";
	public static final String PROP_PAYEE_BANK = "payeeBank";
	public static final String PROP_PAYEE_BANKCARD = "payeeBankcard";
	public static final String PROP_PAYEE_NAME = "payeeName";
	public static final String PROP_WITHDRAW_STATUS = "withdrawStatus";
	public static final String PROP_CHECK_STATUS = "checkStatus";
	public static final String PROP_CHECK_TIME = "checkTime";
	public static final String PROP_CHECK_USER_ID = "checkUserId";
	public static final String PROP_CHECK_REMARK = "checkRemark";
	public static final String PROP_IS_CLEAR_AUDIT = "isClearAudit";
	public static final String PROP_IS_WARN = "isWarn";
	public static final String PROP_CHECK_CLOSING_TIME = "checkClosingTime";
	public static final String PROP_WITHDRAW_TYPE_PARENT = "withdrawTypeParent";
	public static final String PROP_WITHDRAW_ACTUAL_AMOUNT = "withdrawActualAmount";
	public static final String PROP_PLAY_MONEY_TIME = "playMoneyTime";
	public static final String PROP_PLAY_MONEY_USER = "playMoneyUser";
	public static final String PROP_REASON_CONTENT = "reasonContent";
	public static final String PROP_ARTIFICIAL_REASON_CONTENT = "artificialReasonContent";
	public static final String PROP_IS_LOCK = "isLock";
	public static final String PROP_LOCK_PERSON_ID = "lockPersonId";
	public static final String PROP_IS_SATISFY_AUDIT = "isSatisfyAudit";
	public static final String PROP_ARTIFICIAL_REASON_TITLE = "artificialReasonTitle";
	public static final String PROP_REASON_TITLE = "reasonTitle";
	public static final String PROP_SUCCESS_COUNT = "successCount";
	public static final String PROP_CLOSING_TIME = "closingTime";
	public static final String PROP_REMARK = "remark";
	public static final String PROP_USERNAME = "username";
	public static final String PROP_CHECK_USER_NAME = "checkUserName";
	public static final String PROP_PLAYER_GROUP_ID = "playerGroupId";
	public static final String PROP_REGION = "region";
	public static final String PROP_NATION = "nation";
	public static final String PROP_COUNTRY = "country";
	public static final String PROP_CITY = "city";
	public static final String PROP_REAL_NAME = "realName";
	public static final String PROP_REGISTER_TIME = "registerTime";
	public static final String PROP_STATUS = "status";
	public static final String PROP_RISK_MARKER = "riskMarker";
	public static final String PROP_GROUP_NAME = "groupName";
	public static final String PROP_AGENT_NAME = "agentName";
	public static final String PROP_GENERAL_AGENT_NAME = "generalAgentName";
	public static final String PROP_LOCK_PERSON_NAME = "lockPersonName";
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
	private Double currentAccountAmount;
	/**  */
	private Double currentReturnZeroAmount;
	/**  */
	private Double currentBackflushAmount;
	/**  */
	private String withdrawMonetary;
	/**  */
	private Double withdrawAmount;
	/**  */
	private String withdrawRemark;
	/**  */
	private Double deductFavorable;
	/**  */
	private Double counterFee;
	/**  */
	private Double administrativeFee;
	/**  */
	private Boolean isDeductAudit;
	/**  */
	private Double deductAuditRechargeAmount;
	/**  */
	private Double deductAuditRechargeIndex;
	/**  */
	private Double deductAuditFavorableAmount;
	/**  */
	private Double deductAuditFavorableIndex;
	/**  */
	private String withdrawType;
	/**  */
	private java.util.Date createTime;
	/**  */
	private String payeeBank;
	/**  */
	private String payeeBankcard;
	/**  */
	private String payeeName;
	/**  */
	private String withdrawStatus;
	/**  */
	private String checkStatus;
	/**  */
	private java.util.Date checkTime;
	/**  */
	private Integer checkUserId;
	/**  */
	private String checkRemark;
	/**  */
	private Boolean isClearAudit;
	/**  */
	private Boolean isWarn;
	/**  */
	private java.util.Date checkClosingTime;
	/**  */
	private String withdrawTypeParent;
	/**  */
	private Double withdrawActualAmount;
	/**  */
	private java.util.Date playMoneyTime;
	/**  */
	private Integer playMoneyUser;
	/**  */
	private String reasonContent;
	/**  */
	private String artificialReasonContent;
	/**  */
	private Integer isLock;
	/**  */
	private Integer lockPersonId;
	/**  */
	private Boolean isSatisfyAudit;
	/**  */
	private String artificialReasonTitle;
	/**  */
	private String reasonTitle;
	/**  */
	private Integer successCount;
	/**  */
	private Double closingTime;
	/**  */
	private String remark;
	/**  */
	private String username;
	/**  */
	private String checkUserName;
	/**  */
	private Integer playerGroupId;
	/**  */
	private String region;
	/**  */
	private String nation;
	/**  */
	private String country;
	/**  */
	private String city;
	/**  */
	private String realName;
	/**  */
	private java.util.Date registerTime;
	/**  */
	private String status;
	/**  */
	private Integer riskMarker;
	/**  */
	private String groupName;
	/**  */
	private String agentName;
	/**  */
	private String generalAgentName;
	/**  */
	private String lockPersonName;
	//endregion

	
	//region constuctors
	public VPlayerWithdraw(){
	}

	public VPlayerWithdraw(Long id){
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
	@org.soul.model.common.Sortable
	public String getTransactionNo() {
		return this.transactionNo;
	}

	public void setTransactionNo(String value) {
		this.transactionNo = value;
	}
	public Double getCurrentAccountAmount() {
		return this.currentAccountAmount;
	}

	public void setCurrentAccountAmount(Double value) {
		this.currentAccountAmount = value;
	}
	public Double getCurrentReturnZeroAmount() {
		return this.currentReturnZeroAmount;
	}

	public void setCurrentReturnZeroAmount(Double value) {
		this.currentReturnZeroAmount = value;
	}
	public Double getCurrentBackflushAmount() {
		return this.currentBackflushAmount;
	}

	public void setCurrentBackflushAmount(Double value) {
		this.currentBackflushAmount = value;
	}
	public String getWithdrawMonetary() {
		return this.withdrawMonetary;
	}

	public void setWithdrawMonetary(String value) {
		this.withdrawMonetary = value;
	}
	public Double getWithdrawAmount() {
		return this.withdrawAmount;
	}

	public void setWithdrawAmount(Double value) {
		this.withdrawAmount = value;
	}
	public String getWithdrawRemark() {
		return this.withdrawRemark;
	}

	public void setWithdrawRemark(String value) {
		this.withdrawRemark = value;
	}
	public Double getDeductFavorable() {
		return this.deductFavorable;
	}

	public void setDeductFavorable(Double value) {
		this.deductFavorable = value;
	}
	public Double getCounterFee() {
		return this.counterFee;
	}

	public void setCounterFee(Double value) {
		this.counterFee = value;
	}
	public Double getAdministrativeFee() {
		return this.administrativeFee;
	}

	public void setAdministrativeFee(Double value) {
		this.administrativeFee = value;
	}
	public Boolean getIsDeductAudit() {
		return this.isDeductAudit;
	}

	public void setIsDeductAudit(Boolean value) {
		this.isDeductAudit = value;
	}
	public Double getDeductAuditRechargeAmount() {
		return this.deductAuditRechargeAmount;
	}

	public void setDeductAuditRechargeAmount(Double value) {
		this.deductAuditRechargeAmount = value;
	}
	public Double getDeductAuditRechargeIndex() {
		return this.deductAuditRechargeIndex;
	}

	public void setDeductAuditRechargeIndex(Double value) {
		this.deductAuditRechargeIndex = value;
	}
	public Double getDeductAuditFavorableAmount() {
		return this.deductAuditFavorableAmount;
	}

	public void setDeductAuditFavorableAmount(Double value) {
		this.deductAuditFavorableAmount = value;
	}
	public Double getDeductAuditFavorableIndex() {
		return this.deductAuditFavorableIndex;
	}

	public void setDeductAuditFavorableIndex(Double value) {
		this.deductAuditFavorableIndex = value;
	}
	public String getWithdrawType() {
		return this.withdrawType;
	}

	public void setWithdrawType(String value) {
		this.withdrawType = value;
	}
	@org.soul.model.common.Sortable
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	public String getPayeeBank() {
		return this.payeeBank;
	}

	public void setPayeeBank(String value) {
		this.payeeBank = value;
	}
	public String getPayeeBankcard() {
		return this.payeeBankcard;
	}

	public void setPayeeBankcard(String value) {
		this.payeeBankcard = value;
	}
	public String getPayeeName() {
		return this.payeeName;
	}

	public void setPayeeName(String value) {
		this.payeeName = value;
	}
	public String getWithdrawStatus() {
		return this.withdrawStatus;
	}

	public void setWithdrawStatus(String value) {
		this.withdrawStatus = value;
	}
	public String getCheckStatus() {
		return this.checkStatus;
	}

	public void setCheckStatus(String value) {
		this.checkStatus = value;
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
	public Boolean getIsClearAudit() {
		return this.isClearAudit;
	}

	public void setIsClearAudit(Boolean value) {
		this.isClearAudit = value;
	}
	public Boolean getIsWarn() {
		return this.isWarn;
	}

	public void setIsWarn(Boolean value) {
		this.isWarn = value;
	}
	public java.util.Date getCheckClosingTime() {
		return this.checkClosingTime;
	}

	public void setCheckClosingTime(java.util.Date value) {
		this.checkClosingTime = value;
	}
	public String getWithdrawTypeParent() {
		return this.withdrawTypeParent;
	}

	public void setWithdrawTypeParent(String value) {
		this.withdrawTypeParent = value;
	}
	public Double getWithdrawActualAmount() {
		return this.withdrawActualAmount;
	}

	public void setWithdrawActualAmount(Double value) {
		this.withdrawActualAmount = value;
	}
	public java.util.Date getPlayMoneyTime() {
		return this.playMoneyTime;
	}

	public void setPlayMoneyTime(java.util.Date value) {
		this.playMoneyTime = value;
	}
	public Integer getPlayMoneyUser() {
		return this.playMoneyUser;
	}

	public void setPlayMoneyUser(Integer value) {
		this.playMoneyUser = value;
	}
	public String getReasonContent() {
		return this.reasonContent;
	}

	public void setReasonContent(String value) {
		this.reasonContent = value;
	}
	public String getArtificialReasonContent() {
		return this.artificialReasonContent;
	}

	public void setArtificialReasonContent(String value) {
		this.artificialReasonContent = value;
	}
	public Integer getIsLock() {
		return this.isLock;
	}

	public void setIsLock(Integer value) {
		this.isLock = value;
	}
	public Integer getLockPersonId() {
		return this.lockPersonId;
	}

	public void setLockPersonId(Integer value) {
		this.lockPersonId = value;
	}
	public Boolean getIsSatisfyAudit() {
		return this.isSatisfyAudit;
	}

	public void setIsSatisfyAudit(Boolean value) {
		this.isSatisfyAudit = value;
	}
	public String getArtificialReasonTitle() {
		return this.artificialReasonTitle;
	}

	public void setArtificialReasonTitle(String value) {
		this.artificialReasonTitle = value;
	}
	public String getReasonTitle() {
		return this.reasonTitle;
	}

	public void setReasonTitle(String value) {
		this.reasonTitle = value;
	}
	public Integer getSuccessCount() {
		return this.successCount;
	}

	public void setSuccessCount(Integer value) {
		this.successCount = value;
	}
	public Double getClosingTime() {
		return this.closingTime;
	}

	public void setClosingTime(Double value) {
		this.closingTime = value;
	}
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String value) {
		this.remark = value;
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
	public Integer getPlayerGroupId() {
		return this.playerGroupId;
	}

	public void setPlayerGroupId(Integer value) {
		this.playerGroupId = value;
	}
	public String getRegion() {
		return this.region;
	}

	public void setRegion(String value) {
		this.region = value;
	}
	public String getNation() {
		return this.nation;
	}

	public void setNation(String value) {
		this.nation = value;
	}
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String value) {
		this.country = value;
	}
	public String getCity() {
		return this.city;
	}

	public void setCity(String value) {
		this.city = value;
	}
	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String value) {
		this.realName = value;
	}
	public java.util.Date getRegisterTime() {
		return this.registerTime;
	}

	public void setRegisterTime(java.util.Date value) {
		this.registerTime = value;
	}
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String value) {
		this.status = value;
	}
	public Integer getRiskMarker() {
		return this.riskMarker;
	}

	public void setRiskMarker(Integer value) {
		this.riskMarker = value;
	}
	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String value) {
		this.groupName = value;
	}
	public String getAgentName() {
		return this.agentName;
	}

	public void setAgentName(String value) {
		this.agentName = value;
	}
	public String getGeneralAgentName() {
		return this.generalAgentName;
	}

	public void setGeneralAgentName(String value) {
		this.generalAgentName = value;
	}
	public String getLockPersonName() {
		return this.lockPersonName;
	}

	public void setLockPersonName(String value) {
		this.lockPersonName = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}