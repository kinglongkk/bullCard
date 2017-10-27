package g.model.withdrawdesk.po;

import org.soul.commons.bean.IEntity;
import org.soul.model.common.Sortable;


/**
 * 提现记录表实体
 *
 * @author tom
 * @tableAuthor orange
 * @time 2016-7-13 16:16:43
 */
//region your codes 1
public class PlayerWithdraw implements IEntity<Long> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 5150532812057944531L;
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
	public static final String PROP_AUDIT_TYPE = "auditType";
	//endregion
	
	
	//region properties
	/**  */
	private Long id;
	/** 玩家ID */
	private Integer playerId;
	/** 玩家交易表对应id */
	private Long playerTransactionId;
	/** 交易号 */
	private String transactionNo;
	/** 当前账户总资产 */
	private Double currentAccountAmount;
	/** 截止归零金额 */
	private Double currentReturnZeroAmount;
	/** 直接剩余回冲金额 */
	private Double currentBackflushAmount;
	/** 充值货币 字典

 model common type

 currency */
	private String withdrawMonetary;
	/** 提现金额 */
	private Double withdrawAmount;
	/**  */
	private String withdrawRemark;
	/** 扣除优惠 */
	private Double deductFavorable;
	/** 手续费 */
	private Double counterFee;
	/** 扣除行政费 */
	private Double administrativeFee;
	/** 是否稽核扣除 */
	private Boolean isDeductAudit;
	/** 扣除稽核充值总金额(提现时先扣,驳回补回去) */
	private Double deductAuditRechargeAmount;
	/** 扣除稽核充值基数 */
	private Double deductAuditRechargeIndex;
	/** 扣除稽核优惠充值总金额(提现时先扣,驳回补回去) */
	private Double deductAuditFavorableAmount;
	/** 扣除稽核优惠基数 */
	private Double deductAuditFavorableIndex;
	/** 订单类型 字典

model fund

type withdraw_type



提现类型:首次提现、提现 、


手动提现—重复提现


手动提现—误充值


手动提现—手动负数回充


手动提现—手动申请提现


手动提现—扣除非法交易


手动提现—放弃充值优惠


手动提现—其它提现 */
	private String withdrawType;
	/** 创建时间 */
	private java.util.Date createTime;
	/** 玩家的收款银行 */
	private String payeeBank;
	/** 玩家收款的银行卡 */
	private String payeeBankcard;
	/** 玩家收款名 */
	private String payeeName;
	/** 字典状态 model fund

type  withdraw_status (待处理/待处理-稽核失败/成功/ 失败/拒绝/待提交/取消订单) */
	private String withdrawStatus;
	/** 字典model fund

type check_status 无需审核 未审核 已审核 已驳回 */
	private String checkStatus;
	/** 审核时间 */
	private java.util.Date checkTime;
	/** 审核人 */
	private Integer checkUserId;
	/** 审核备注原因 */
	private String checkRemark;
	/** 是否清除稽核 */
	private Boolean isClearAudit;
	/** 是否回充提醒提醒 */
	private Boolean isWarn;
	/** 审核截止时间 */
	private java.util.Date checkClosingTime;
	/** 父订单类型 字典

model  fund

type withdraw_type_parent

玩家提现


手动提现 */
	private String withdrawTypeParent;
	/** 提现实际金额 */
	private Double withdrawActualAmount;
	/** 打款时间 */
	private java.util.Date playMoneyTime;
	/** 打款人 */
	private Integer playMoneyUser;
	/** reason失败原因模板内容 */
	private String reasonContent;
	/** 人工申请原因 */
	private String artificialReasonContent;
	/** 是否锁定1：是2：否  字典 fund  is_lock  */
	private Integer isLock;
	/** 锁定人id */
	private Integer lockPersonId;
	/** 是否满足稽核点 */
	private Boolean isSatisfyAudit;
	/** 人工申请原因标题 */
	private String artificialReasonTitle;
	/** 原因标题 */
	private String reasonTitle;
	/** 自动稽核/手动稽核：model:fund;type:audit_type */
	private String auditType;
	//endregion

	
	//region constuctors
	public PlayerWithdraw(){
	}

	public PlayerWithdraw(Long id){
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
	public String getAuditType() {
		return this.auditType;
	}

	public void setAuditType(String value) {
		this.auditType = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}