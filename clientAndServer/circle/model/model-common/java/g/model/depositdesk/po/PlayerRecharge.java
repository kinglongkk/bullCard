package g.model.depositdesk.po;


import org.soul.commons.bean.IEntity;
import org.soul.model.common.Sortable;


/**
 * 玩家充值表-mark实体
 *
 * @author tom
 * @tableAuthor cherry
 * @time 2016-7-14 9:27:05
 */
//region your codes 1
public class PlayerRecharge implements IEntity<Long> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -8621500867421455900L;
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
	public static final String PROP_RECHARGE_STATUS = "rechargeStatus";
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
	public static final String PROP_IS_FIRST_FAVORABLE = "isFirstFavorable";
	public static final String PROP_RECHARGE_ADDRESS = "rechargeAddress";
	//endregion
	
	
	//region properties
	/** 资金充值表 */
	private Long id;
	/** 玩家ID */
	private Integer playerId;
	/** 玩家交易表id */
	private Long playerTransactionId;
	/** 交易号 */
	private String transactionNo;
	/** 存款金额 */
	private Double rechargeAmount;
	/** 存款备注 */
	private String rechargeRemark;
	/** 统计该笔订单总的优惠赠送多少钱 */
	private Double favorableTotalAmount;
	/** 是否使用手续费 */
	private Boolean isCounterFee;
	/** 手续费:0：收取 1：返回 */
	private Boolean counterFeePath;
	/** 手续费方式 0比例 1固定 */
	private Boolean cfRatioOrFixed;
	/** 手续费 */
	private Double counterFee;
	/** 是否充值稽核 */
	private Boolean isAuditRecharge;
	/** 充值方式  字典

model fund

type recharge_type



 */
	private String rechargeType;
	/** 是否关联订单 */
	private Boolean isRelatedOrder;
	/** 关联订单主键(这边实际是业务来划定主具体表) */
	private Integer relatedOrderId;
	/** 充值者银行 ，

如果是充值卡充值的则记录充值卡

字典银行账户 [module common dic_type bank][module common dic_type third_party_account] [module common dict_type recharge_card] */
	private String payerBank;
	/** 付款者名 */
	private String payerName;
	/** 付款者的银行卡号，如果是AMT 或网银转帐则记录订单号 点卡充值 则记录点卡号 */
	private String payerBankcard;
	/** 订单创建时间 */
	private java.util.Date createTime;
	/** 字典model fund type recharge_status 待处理、成功、失败 */
	private String rechargeStatus;
	/** 审核时间 */
	private java.util.Date checkTime;
	/** 审核人 */
	private Integer checkUserId;
	/** 审核备注原因 */
	private String checkRemark;
	/** 审核状态:字典model fund

type check_status 无需审核,待审核，审核通过， 已驳回 */
	private String checkStatus;
	/** 银行订单号 */
	private String bankOrder;
	/** 回执时间 */
	private java.util.Date returnTime;
	/** 支付银行类型：字典表pay_account_account_type */
	private String payerBankType;
	/** 是否首充 */
	private Boolean isFirstRecharge;
	/** 手续费备注 */
	private String counterfeeRemark;
	/** 是否使用优惠 */
	private Boolean isFavorable;
	/** 收款账号外键 */
	private Integer payAccountId;
	/** 关联订单流水号 */
	private String relatedTransactionNo;
	/** 手续费输入值 */
	private Double cfRatioOrFixedValue;
	/** 回充方式:字典 model fund type return_recharge */
	private Integer returnRecharge;
	/** 是否回充 */
	private Boolean isReturnRecharge;
	/** 人工申请原因 */
	private String artificialReasonContent;
	/** 存款父类型 字典：module fund dic_type recharge_type_parent:在线支付、公司入款   */
	private String rechargeTypeParent;
	/** 存款总额（已经换算为站长汇率） */
	private Double rechargeTotalAmount;
	/** 存款审核失败原因标题 */
	private String failureTitle;
	/** 手动存款申请原因标题 */
	private String artificialReasonTitle;
	/** 玩家填写收款账号 */
	private String receiveAccount;
	/** 玩家填写收款姓名 */
	private String receiveName;
	/** 收款账号类型（第三方账户、银行账户） */
	private String masterBankType;
	/** 是否首存优惠 */
	private Boolean isFirstFavorable;
	/** 交易地点 */
	private String rechargeAddress;
	//endregion

	
	//region constuctors
	public PlayerRecharge(){
	}

	public PlayerRecharge(Long id){
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
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	public String getRechargeStatus() {
		return this.rechargeStatus;
	}

	public void setRechargeStatus(String value) {
		this.rechargeStatus = value;
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
	public Boolean getIsFirstFavorable() {
		return this.isFirstFavorable;
	}

	public void setIsFirstFavorable(Boolean value) {
		this.isFirstFavorable = value;
	}
	public String getRechargeAddress() {
		return this.rechargeAddress;
	}

	public void setRechargeAddress(String value) {
		this.rechargeAddress = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}