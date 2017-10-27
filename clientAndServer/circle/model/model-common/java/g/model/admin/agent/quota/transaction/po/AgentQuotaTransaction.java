package g.model.admin.agent.quota.transaction.po;

import org.soul.commons.bean.IEntity;

/**
 * 额度日志实体
 *
 * @author black
 * @time 2016-12-3 10:47:43
 */
public class AgentQuotaTransaction implements IEntity<Integer> {

	private static final long serialVersionUID = -2742940054325952187L;

	public static final String PROP_ID = "id";
	public static final String PROP_AGENT_ID = "agentId";
	public static final String PROP_DATE = "date";
	public static final String PROP_QUOTA = "quota";
	public static final String PROP_TYPE = "type";
	public static final String PROP_NICKNAME = "nickname";

	/** 主键 */
	private Integer id;
	/** 代理id */
	private Integer agentId;
	/** 时间 */
	private java.util.Date date;
	/** 额度 */
	private Double quota;
	/** 类型 */
	private String type;
	/** 昵称 */
	private String nickname;

	public AgentQuotaTransaction(){
	}

	public AgentQuotaTransaction(Integer id){
		this.id = id;
	}

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

	@org.soul.model.common.Sortable
	public java.util.Date getDate() {
		return this.date;
	}

	public void setDate(java.util.Date value) {
		this.date = value;
	}
	public Double getQuota() {
		return this.quota;
	}

	public void setQuota(Double value) {
		this.quota = value;
	}
	public String getType() {
		return this.type;
	}

	public void setType(String value) {
		this.type = value;
	}
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}