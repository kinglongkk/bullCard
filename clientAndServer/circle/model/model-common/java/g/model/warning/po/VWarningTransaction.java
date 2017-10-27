package g.model.warning.po;


import org.soul.commons.bean.IEntity;
import org.soul.model.common.Sortable;


/**
 * 实体
 *
 * @author lenovo
 * @time 2017-2-28 14:13:53
 */
//region your codes 1
public class VWarningTransaction implements IEntity<Long> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -8508104225778677753L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_PLAYER_ID = "playerId";
	public static final String PROP_COMPLETION_TIME = "completionTime";
	public static final String PROP_USERNAME = "username";
	public static final String PROP_TRANSACTION_NO = "transactionNo";
	public static final String PROP_TRANSACTION_MONEY = "transactionMoney";
	public static final String PROP_BALANCE = "balance";
	public static final String PROP_GAME_CURRENCY = "gameCurrency";
	public static final String PROP_STATUS = "status";
	public static final String PROP_REMARK = "remark";
	public static final String PROP_TRANSACTION_TYPE="transactionType";
	//endregion
	
	
	//region properties
	/**  */
	private Long id;
	/**  */
	private Integer playerId;
	/**  */
	private java.util.Date completionTime;
	/**  */
	private String username;
	/**  */
	private String transactionNo;
	/**  */
	private Double transactionMoney;
	/**  */
	private Double balance;
	/**  */
	private Double gameCurrency;
	/**  */
	private String status;
	/**  */
	private String remark;
	private String transactionType;
	//endregion

	
	//region constuctors
	public VWarningTransaction(){
	}

	public VWarningTransaction(Long id){
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
	public java.util.Date getCompletionTime() {
		return this.completionTime;
	}

	public void setCompletionTime(java.util.Date value) {
		this.completionTime = value;
	}
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String value) {
		this.username = value;
	}
	public String getTransactionNo() {
		return this.transactionNo;
	}

	public void setTransactionNo(String value) {
		this.transactionNo = value;
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
	public Double getGameCurrency() {
		return this.gameCurrency;
	}

	public void setGameCurrency(Double value) {
		this.gameCurrency = value;
	}
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String value) {
		this.status = value;
	}
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String value) {
		this.remark = value;
	}
	//endregion

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}


	//region your codes 2

	//endregion your codes 2

}