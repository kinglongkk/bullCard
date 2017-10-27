package g.model.admin.agent.quotamanager.po;

import org.soul.commons.bean.IEntity;
import org.soul.commons.support.Nonpersistent;


/**
 * 实体
 *
 * @author black
 * @time 2016-12-12 15:27:29
 */
//region your codes 1
public class VAgentQuota implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 6930321034125794581L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_USERNAME = "username";
	public static final String PROP_REAL_NAME = "realName";
	public static final String PROP_STATUS = "status";
	public static final String PROP_AGENT_ID = "agentId";
	public static final String PROP_ID = "id";
	public static final String PROP_MERCHANT_NO = "merchantNo";
	public static final String PROP_QUOTA = "quota";
	public static final String PROP_WARN_TYPE = "warnType";
	public static final String PROP_CURRENT_QUOTA = "currentQuota";
	public static final String PROP_QUOTA_STATUS = "quotaStatus";
	public static final String PROP_NAME = "name";
	public static final String PROP_EXCHANGE = "exchange";
	//endregion
	
	
	//region properties
	/** 账号  */
	private String username;
	/** 姓名 */
	private String realName;
	/** 状态 */
	private String status;
	/** 代理id */
	private Integer agentId;
	/** 商户信息id */
	private Integer id;
	/** 商户号 */
	private String merchantNo;
	/** 总额度 */
	private Double quota;
	/** 警告类型 */
	private String warnType;
	/** 当前额度 */
	private Double currentQuota;
	/** 状态 */
	private String quotaStatus;
	/** 货币名称 */
	private String name;
	/** 货币汇率 */
	private Double exchange;
	//endregion

	
	//region constuctors
	public VAgentQuota(){
	}

	public VAgentQuota(Integer id){
		this.id = id;
	}
	//endregion


	//region getters and setters
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String value) {
		this.username = value;
	}
	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String value) {
		this.realName = value;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer value) {
		this.id = value;
	}
	public String getMerchantNo() {
		return this.merchantNo;
	}

	public void setMerchantNo(String value) {
		this.merchantNo = value;
	}
	public Double getQuota() {
		return this.quota;
	}

	public void setQuota(Double value) {
		this.quota = value;
	}
	public String getWarnType() {
		return this.warnType;
	}

	public void setWarnType(String value) {
		this.warnType = value;
	}
	public Double getCurrentQuota() {
		return this.currentQuota;
	}

	public void setCurrentQuota(Double value) {
		this.currentQuota = value;
	}
	public String getQuotaStatus() {
		return this.quotaStatus;
	}

	public void setQuotaStatus(String value) {
		this.quotaStatus = value;
	}
	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}
	public Double getExchange() {
		return this.exchange;
	}

	public void setExchange(Double value) {
		this.exchange = value;
	}
	//endregion

	//region your codes 2
	/**
	 * 充值金额
	 */
	private Double amount;

	@Nonpersistent
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	//endregion your codes 2

}