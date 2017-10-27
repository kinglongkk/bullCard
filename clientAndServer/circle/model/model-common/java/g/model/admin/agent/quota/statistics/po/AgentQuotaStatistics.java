package g.model.admin.agent.quota.statistics.po;


import org.soul.commons.bean.IEntity;


/**
 * 额度日志管理实体
 *
 * @author black
 * @time 2016-12-15 11:23:25
 */
//region your codes 1
public class AgentQuotaStatistics implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -8321558525234364065L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_AGENT_ID = "agentId";
	public static final String PROP_GAME_ID = "gameId";
	public static final String PROP_QUOTA = "quota";
	public static final String PROP_DATE = "date";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Integer id;
	/** 代理id */
	private Integer agentId;
	/** 游戏id */
	private Integer gameId;
	/** 额度 */
	private Double quota;
	/** 日期 */
	private java.util.Date date;
	//endregion

	
	//region constuctors
	public AgentQuotaStatistics(){
	}

	public AgentQuotaStatistics(Integer id){
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
	public Integer getAgentId() {
		return this.agentId;
	}

	public void setAgentId(Integer value) {
		this.agentId = value;
	}
	public Integer getGameId() {
		return this.gameId;
	}

	public void setGameId(Integer value) {
		this.gameId = value;
	}
	public Double getQuota() {
		return this.quota;
	}

	public void setQuota(Double value) {
		this.quota = value;
	}
	public java.util.Date getDate() {
		return this.date;
	}

	public void setDate(java.util.Date value) {
		this.date = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}