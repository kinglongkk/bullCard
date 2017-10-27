package g.model.report.po;

import org.soul.commons.bean.IEntity;
import org.soul.commons.support.Nonpersistent;
import org.soul.model.common.Sortable;


/**
 * 实体
 *
 * @author tom
 * @time 2016-8-1 15:34:20
 */
//region your codes 1
public class ReportCqssc implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 2464474563300973394L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_MATCH_ID = "matchId";
	public static final String PROP_MATCH_CODE = "matchCode";
	public static final String PROP_USER_ID = "userId";
	public static final String PROP_USERNAME = "username";
	public static final String PROP_SINGLE_AMOUNT = "singleAmount";
	public static final String PROP_PROFIT_AMOUNT = "profitAmount";
	public static final String PROP_EFFECTIVE_AMOUNT = "effectiveAmount";
	public static final String PROP_RAKEBACK_AMOUNT = "rakebackAmount";
	public static final String PROP_IS_DELETED = "isDeleted";
	public static final String PROP_BEGIN_TIME = "beginTime";
	public static final String PROP_CREATE_TIME = "createTime";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Integer id;
	/** 比赛ID */
	private Long matchId;
	/** 比赛编号 */
	private String matchCode;
	/** 用户ID */
	private Integer userId;
	/** 用户名 */
	private String username;
	/** 下单金额(交易量) */
	private Double singleAmount;
	/** 盈亏结果金额(派彩) */
	private Double profitAmount;
	/** 有效交易量 */
	private Double effectiveAmount;
	/** 返水金额 */
	private Double rakebackAmount;
	/** 是否删除[1:yes,0:false] */
	private Boolean isDeleted;
	/** 比赛开始时间 */
	private java.util.Date beginTime;
	/** 创建时间 */
	private java.util.Date createTime;
	//endregion

	
	//region constuctors
	public ReportCqssc(){
	}

	public ReportCqssc(Integer id){
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
	public Long getMatchId() {
		return this.matchId;
	}

	public void setMatchId(Long value) {
		this.matchId = value;
	}
	@Sortable
	public String getMatchCode() {
		return this.matchCode;
	}

	public void setMatchCode(String value) {
		this.matchCode = value;
	}
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer value) {
		this.userId = value;
	}
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String value) {
		this.username = value;
	}
	public Double getSingleAmount() {
		return this.singleAmount;
	}

	public void setSingleAmount(Double value) {
		this.singleAmount = value;
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
	public Double getRakebackAmount() {
		return this.rakebackAmount;
	}

	public void setRakebackAmount(Double value) {
		this.rakebackAmount = value;
	}
	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Boolean value) {
		this.isDeleted = value;
	}
	public java.util.Date getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(java.util.Date value) {
		this.beginTime = value;
	}
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	//endregion

	//region your codes 2
	/** 今日总场次 */
	private Integer totalNumber;

	/** 用户身份 */
	private String userType;

	@Nonpersistent
	public Integer getTotalNumber(){ return totalNumber; }

	public void setTotalNumber(Integer value){ this.totalNumber = value; }

	@Nonpersistent
	public String getUserType() { return userType; }

	public void setUserType(String value) { this.userType = value; }


	//endregion your codes 2

}