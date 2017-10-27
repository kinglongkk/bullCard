package g.model.playerstatistics.po;

import org.soul.commons.bean.IEntity;
import org.soul.model.common.Sortable;


/**
 * 代理商每日实时统计表实体
 *
 * @author lenovo
 * @time 2017-1-21 9:43:02
 */
//region your codes 1
public class AgentDateActual implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -6157022612712769864L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_USERNAME = "username";
	public static final String PROP_NICKNAME = "nickname";
	public static final String PROP_STATISTICS_DATETIME = "statisticsDatetime";
	public static final String PROP_PROFIT_AMOUNT = "profitAmount";
	public static final String PROP_EFFECTIVE_AMOUNT = "effectiveAmount";
	public static final String PROP_IS_DELETE = "isDelete";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Integer id;
	/** 代理商用户名 */
	private String username;
	/** 昵称 */
	private String nickname;
	/** 统计时间 */
	private java.util.Date statisticsDatetime;
	/** 盈亏结果金额 */
	private Double profitAmount;
	/** 有效交易量 */
	private Double effectiveAmount;
	/** 是否删除 */
	private Boolean isDelete;
	//endregion

	
	//region constuctors
	public AgentDateActual(){
	}

	public AgentDateActual(Integer id){
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
	public java.util.Date getStatisticsDatetime() {
		return this.statisticsDatetime;
	}

	public void setStatisticsDatetime(java.util.Date value) {
		this.statisticsDatetime = value;
	}
	public Double getProfitAmount() {
		return this.profitAmount;
	}

	public void setProfitAmount(Double value) {
		this.profitAmount = value;
	}
	public Double getEffectiveAmount() {
		return this.effectiveAmount;
	}

	public void setEffectiveAmount(Double value) {
		this.effectiveAmount = value;
	}
	public Boolean getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(Boolean value) {
		this.isDelete = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}