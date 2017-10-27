package g.model.player.po;

import org.soul.commons.bean.IEntity;


/**
 * 玩家交易表实体
 *
 * @author jerry
 * @tableAuthor jerry
 * @time 2016-2-24 16:32:58
 */
//region your codes 1
public class PlayerTransaction implements IEntity<Long> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 2869906500285254646L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_TRANSACTION_NO = "transactionNo";
	public static final String PROP_TRANSACTION_TYPE = "transactionType";
	public static final String PROP_TRANSACTION_MONEY = "transactionMoney";
	public static final String PROP_BALANCE = "balance";
	public static final String PROP_STATUS = "status";
	public static final String PROP_PLAYER_ID = "playerId";
	public static final String PROP_SOURCE_ID = "sourceId";
	public static final String PROP_CREATE_TIME = "createTime";
	public static final String PROP_COMPLETION_TIME = "completionTime";
	public static final String PROP_FAILURE_REASON = "failureReason";
	public static final String PROP_REMARK = "remark";
	//endregion


	//region properties
	/** 主键 */
	private Long id;
	/** 交易号 */
	private String transactionNo;
	/** 交易类型(关联字典common.transaction_type):转入,转出,派彩 */
	private String transactionType;
	/** 交易金额 */
	private Double transactionMoney;
	/** 账户余额 */
	private Double balance;
	/** 状态：pending:预处理 success 处理成功 failure */
	private String status;
	/** 玩家id */
	private Integer playerId;
	/** 交易表不同类型分别对应表的来源id */
	private Long sourceId;
	/** 创建时间 */
	private java.util.Date createTime;
	/** 完成时间 */
	private java.util.Date completionTime;
	/** 失败原因 */
	private String failureReason;
	/** 备注 */
	private String remark;
	//endregion


	//region constuctors
	public PlayerTransaction(){
	}

	public PlayerTransaction(Long id){
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
	public String getTransactionNo() {
		return this.transactionNo;
	}

	public void setTransactionNo(String value) {
		this.transactionNo = value;
	}
	public String getTransactionType() {
		return this.transactionType;
	}

	public void setTransactionType(String value) {
		this.transactionType = value;
	}
	public Double getTransactionMoney() {
		return this.transactionMoney;
	}

	public void setTransactionMoney(Double value) {
		this.transactionMoney = value;
	}
	public Double getBalance() {
		return this.balance;
	}

	public void setBalance(Double value) {
		this.balance = value;
	}
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String value) {
		this.status = value;
	}
	public Integer getPlayerId() {
		return this.playerId;
	}

	public void setPlayerId(Integer value) {
		this.playerId = value;
	}
	public Long getSourceId() {
		return this.sourceId;
	}

	public void setSourceId(Long value) {
		this.sourceId = value;
	}
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	public java.util.Date getCompletionTime() {
		return this.completionTime;
	}

	public void setCompletionTime(java.util.Date value) {
		this.completionTime = value;
	}
	public String getFailureReason() {
		return this.failureReason;
	}

	public void setFailureReason(String value) {
		this.failureReason = value;
	}
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String value) {
		this.remark = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}