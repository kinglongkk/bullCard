package g.model.warning.po;


import org.soul.commons.bean.IEntity;
import org.soul.model.common.Sortable;


/**
 * 说明预警异常用户赢得金额跟投注金额倍数实体
 *
 * @author lenovo
 * @time 2017-2-25 16:06:12
 */
//region your codes 1
public class PlayerWarningMultiple implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -7137540039903090350L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_PLAYER_ID = "playerId";
	public static final String PROP_DATE = "date";
	public static final String PROP_USERNAME = "username";
	public static final String PROP_NICKNAME = "nickname";
	public static final String PROP_REGISTER_DATE = "registerDate";
	public static final String PROP_WIN_AMOUNT = "winAmount";
	public static final String PROP_BET_AMOUNT = "betAmount";
	public static final String PROP_STATUS = "status";
	public static final String PROP_MULTIPLE = "multiple";
	public static final String PROP_CREATE_TIME = "createTime";
	public static final String PROP_AGENT_USERNAME = "agentUsername";
	public static final String PROP_AGENT_ID = "agentId";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Integer id;
	/** 角色名称 */
	private Integer playerId;
	/** 状态 */
	private java.util.Date date;
	/** 创建用户id */
	private String username;
	/** 创建时间 */
	private String nickname;
	/** 创建用户id */
	private java.util.Date registerDate;
	/** 今日赢得金额 */
	private Double winAmount;
	/** 今日投注金额 */
	private Double betAmount;
	/** 1:黄色2:红色； */
	private String status;
	/** 赢得金额和投注金额倍数 */
	private Double multiple;
	/** 创建时间； */
	private java.util.Date createTime;
	//endregion
	/**代理商用户名**/
	private String agentUsername;
	/**代理商ID**/
	private Integer agentId;

	
	//region constuctors
	public PlayerWarningMultiple(){
	}

	public PlayerWarningMultiple(Integer id){
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
	public Integer getPlayerId() {
		return this.playerId;
	}

	public void setPlayerId(Integer value) {
		this.playerId = value;
	}
	public java.util.Date getDate() {
		return this.date;
	}

	public void setDate(java.util.Date value) {
		this.date = value;
	}
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String value) {
		this.username = value;
	}
	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String value) {
		this.nickname = value;
	}
	public java.util.Date getRegisterDate() {
		return this.registerDate;
	}

	public void setRegisterDate(java.util.Date value) {
		this.registerDate = value;
	}
	public Double getWinAmount() {
		return this.winAmount;
	}

	public void setWinAmount(Double value) {
		this.winAmount = value;
	}
	public Double getBetAmount() {
		return this.betAmount;
	}

	public void setBetAmount(Double value) {
		this.betAmount = value;
	}
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String value) {
		this.status = value;
	}
	public Double getMultiple() {
		return this.multiple;
	}

	public void setMultiple(Double value) {
		this.multiple = value;
	}
	@Sortable
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2


	public String getAgentUsername() {
		return agentUsername;
	}

	public void setAgentUsername(String agentUsername) {
		this.agentUsername = agentUsername;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
}