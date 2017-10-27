package g.model.admin.agent.message.po;

import org.soul.commons.bean.IEntity;

/**
 * 代理商扩展表实体
 *
 * @author black
 * @time 2016-11-28 16:41:26
 */
//region your codes 1
public class UserAgent implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 5598188387300892443L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_AGENT_ID = "agentId";
	public static final String PROP_MERCHANT_NO = "merchantNo";
	public static final String PROP_MERCHANT_KEY = "merchantKey";
	public static final String PROP_MERCHANT_LOGO = "merchantLogo";
	public static final String PROP_MERCHANT_INDEX_LINK = "merchantIndexLink";
	public static final String PROP_MERCHANT_RECHARGE_LINK = "merchantRechargeLink";
	public static final String PROP_QUOTA = "quota";
	public static final String PROP_EXCHANGE_RATE_ID = "exchangeRateId";
	public static final String PROP_CURRENT_QUOTA = "currentQuota";
	public static final String PROP_WARN_TYPE = "warnType";
	public static final String PROP_QUOTA_STATUS = "quotaStatus";
	//endregion
	
	
	//region properties
	/** id */
	private Integer id;
	/** 用户id */
	private Integer agentId;
	/** 商户号 */
	private String merchantNo;
	/** 商户密匙 */
	private String merchantKey;
	/** 商户logo */
	private String merchantLogo;
	/** 商户主页链接 */
	private String merchantIndexLink;
	/** 商户充值链接 */
	private String merchantRechargeLink;
	/** 额度 */
	private Double quota;
	/** 汇率id */
	private Integer exchangeRateId;
	/** 当前额度 */
	private Double currentQuota;
	/** 警告类型 */
	private String warnType;
	/** 状态 */
	private String quotaStatus;
	//endregion

	
	//region constuctors
	public UserAgent(){
	}

	public UserAgent(Integer id){
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
	public String getMerchantNo() {
		return this.merchantNo;
	}

	public void setMerchantNo(String value) {
		this.merchantNo = value;
	}
	public String getMerchantKey() {
		return this.merchantKey;
	}

	public void setMerchantKey(String value) {
		this.merchantKey = value;
	}
	public String getMerchantLogo() {
		return this.merchantLogo;
	}

	public void setMerchantLogo(String value) {
		this.merchantLogo = value;
	}
	public String getMerchantIndexLink() {
		return this.merchantIndexLink;
	}

	public void setMerchantIndexLink(String value) {
		this.merchantIndexLink = value;
	}
	public String getMerchantRechargeLink() {
		return this.merchantRechargeLink;
	}

	public void setMerchantRechargeLink(String value) {
		this.merchantRechargeLink = value;
	}
	public Double getQuota() {
		return this.quota;
	}

	public void setQuota(Double value) {
		this.quota = value;
	}
	public Integer getExchangeRateId() {
		return this.exchangeRateId;
	}

	public void setExchangeRateId(Integer value) {
		this.exchangeRateId = value;
	}
	public Double getCurrentQuota() {
		return this.currentQuota;
	}

	public void setCurrentQuota(Double value) {
		this.currentQuota = value;
	}
	public String getWarnType() {
		return this.warnType;
	}

	public void setWarnType(String value) {
		this.warnType = value;
	}
	public String getQuotaStatus() {
		return this.quotaStatus;
	}

	public void setQuotaStatus(String value) {
		this.quotaStatus = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}