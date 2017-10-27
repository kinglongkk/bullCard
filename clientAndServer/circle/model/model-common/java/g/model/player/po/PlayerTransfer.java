package g.model.player.po;

import org.soul.commons.bean.IEntity;
import org.soul.model.common.Sortable;


/**
 * 玩家转账记录实体
 *
 * @author black
 * @time 2016-11-18 17:11:08
 */
//region your codes 1
public class PlayerTransfer implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -3897997928240241210L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_SOURCE_ORDER_NO = "sourceOrderNo";
	public static final String PROP_TRANSACTION_ID = "transactionId";
	public static final String PROP_AGENT_ID = "agentId";
	public static final String PROP_PLAYER_ID = "playerId";
	public static final String PROP_AMOUNT = "amount";
	public static final String PROP_GAME_CURRENCY = "gameCurrency";
	public static final String PROP_CREATE_TIME = "createTime";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Integer id;
	/** 外部订单号 */
	private String sourceOrderNo;
	/** 玩家交易表id */
	private Integer transactionId;
	/** 代理id */
	private Integer agentId;
	/** 玩家id */
	private Integer playerId;
	/** 交易金额 */
	private Double amount;
	/** 转换后金币 */
	private Double gameCurrency;
	/** 创建时间 */
	private java.util.Date createTime;
	//endregion

	
	//region constuctors
	public PlayerTransfer(){
	}

	public PlayerTransfer(Integer id){
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
	public String getSourceOrderNo() {
		return this.sourceOrderNo;
	}

	public void setSourceOrderNo(String value) {
		this.sourceOrderNo = value;
	}
	public Integer getTransactionId() {
		return this.transactionId;
	}

	public void setTransactionId(Integer value) {
		this.transactionId = value;
	}
	public Integer getAgentId() {
		return this.agentId;
	}

	public void setAgentId(Integer value) {
		this.agentId = value;
	}
	public Integer getPlayerId() {
		return this.playerId;
	}

	public void setPlayerId(Integer value) {
		this.playerId = value;
	}
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double value) {
		this.amount = value;
	}
	public Double getGameCurrency() {
		return this.gameCurrency;
	}

	public void setGameCurrency(Double value) {
		this.gameCurrency = value;
	}
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}