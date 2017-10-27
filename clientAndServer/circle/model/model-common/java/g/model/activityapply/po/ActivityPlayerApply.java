package g.model.activityapply.po;

import org.soul.commons.bean.IEntity;
import org.soul.commons.support.Nonpersistent;

import java.util.Date;


/**
 * 活动申请玩家表实体
 *
 * @author black
 * @time 2016-9-18 19:10:57
 */
//region your codes 1
public class ActivityPlayerApply implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -6101850354492281049L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_ACTIVITY_MESSAGE_ID = "activityMessageId";
	public static final String PROP_USER_ID = "userId";
	public static final String PROP_USER_NAME = "userName";
	public static final String PROP_REGISTER_TIME = "registerTime";
	public static final String PROP_APPLY_TIME = "applyTime";
	public static final String PROP_PLAYER_RECHARGE_ID = "playerRechargeId";
	public static final String PROP_BALANCE_START_TIME = "balanceStartTime";
	public static final String PROP_BALANCE_END_TIME = "balanceEndTime";
	public static final String PROP_PREFERENTIAL_VALUE = "preferentialValue";
	public static final String PROP_ARTICLE = "article";
	public static final String PROP_IS_REALIZE = "isRealize";
	public static final String PROP_IS_EFFECTIVE = "isEffective";
	public static final String PROP_RELATION_PLAYER_ID = "relationPlayerId";
	public static final String PROP_ACTIVITY_CLASSIFY_KEY = "activityClassifyKey";
	public static final String PROP_STARTTIME = "starttime";
	public static final String PROP_ENDTIME = "endtime";
	public static final String PROP_IS_DELETED = "isDeleted";
	public static final String PROP_ACTIVITY_TYPE_CODE = "activityTypeCode";
	public static final String PROP_ACTIVITY_WAY_RELATION_ID = "activityWayRelationId";


	//endregion


	//region properties
	/** 主键 */
	private Integer id;
	/** 活动消息id */
	private Integer activityMessageId;
	/** 玩家账户id */
	private Integer userId;
	/** 玩家姓名 */
	private String userName;
	/** 注册时间 */
	private java.util.Date registerTime;
	/** 申请时间 */
	private java.util.Date applyTime;
	/** 存款id */
	private Integer playerRechargeId;
	/** 活动结算开始时间 */
	private java.util.Date balanceStartTime;
	/** 活动结算结束时间 */
	private java.util.Date balanceEndTime;
	/** 优惠 */
	private Double preferentialValue;
	/** 实物 */
	private String article;
	/** 是否兑换 */
	private Boolean isRealize;
	/** 是否有效 */
	private Boolean isEffective;
	/** 相关用户id */
	private Integer relationPlayerId;
	/** 活动类型 */
	private String activityClassifyKey;
	/** 开始时间 */
	private java.util.Date starttime;
	/** 结束时间 */
	private java.util.Date endtime;
	/** 是否删除 */
	private boolean isDeleted;
	/** 游戏活动代码 */
	private String activityTypeCode;
	/** 优惠关id */
	private Integer activityWayRelationId;

	//endregion

	//region constuctors
	public ActivityPlayerApply(){
	}

	public ActivityPlayerApply(Integer id){
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
	public Integer getActivityMessageId() {
		return this.activityMessageId;
	}

	public void setActivityMessageId(Integer value) {
		this.activityMessageId = value;
	}
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer value) {
		this.userId = value;
	}
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String value) {
		this.userName = value;
	}
	public java.util.Date getRegisterTime() {
		return this.registerTime;
	}

	public void setRegisterTime(java.util.Date value) {
		this.registerTime = value;
	}
	public java.util.Date getApplyTime() {
		return this.applyTime;
	}

	public void setApplyTime(java.util.Date value) {
		this.applyTime = value;
	}
	public Integer getPlayerRechargeId() {
		return this.playerRechargeId;
	}

	public void setPlayerRechargeId(Integer value) {
		this.playerRechargeId = value;
	}
	public java.util.Date getBalanceStartTime() {
		return this.balanceStartTime;
	}

	public void setBalanceStartTime(java.util.Date value) {
		this.balanceStartTime = value;
	}
	public java.util.Date getBalanceEndTime() {
		return this.balanceEndTime;
	}

	public void setBalanceEndTime(java.util.Date value) {
		this.balanceEndTime = value;
	}
	public Double getPreferentialValue() {
		return this.preferentialValue;
	}

	public void setPreferentialValue(Double value) {
		this.preferentialValue = value;
	}
	public String getArticle() {
		return this.article;
	}

	public void setArticle(String value) {
		this.article = value;
	}
	public Boolean getIsRealize() {
		return this.isRealize;
	}

	public void setIsRealize(Boolean value) {
		this.isRealize = value;
	}
	public Boolean getIsEffective() {
		return this.isEffective;
	}

	public void setIsEffective(Boolean value) {
		this.isEffective = value;
	}
	public Integer getRelationPlayerId() {
		return relationPlayerId;
	}

	public void setRelationPlayerId(Integer relationPlayerId) {
		this.relationPlayerId = relationPlayerId;
	}

	public String getActivityClassifyKey() {
		return activityClassifyKey;
	}

	public void setActivityClassifyKey(String activityClassifyKey) {
		this.activityClassifyKey = activityClassifyKey;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean deleted) {
		isDeleted = deleted;
	}

	public String getActivityTypeCode() {
		return activityTypeCode;
	}

	public void setActivityTypeCode(String activityTypeCode) {
		this.activityTypeCode = activityTypeCode;
	}

	public Integer getActivityWayRelationId() {
		return activityWayRelationId;
	}

	public void setActivityWayRelationId(Integer activityWayRelationId) {
		this.activityWayRelationId = activityWayRelationId;
	}

	//endregion

	//region your codes 2

	/**
	 * 总消费
	 */
	private float totalConsume;
	/**
	 * 总返金
	 */
	private float totalCashBack;
	/**
	 * 参与人数
	 */
	private Integer joinNumber;
	/**
	 * 参与人数比例
	 */
	private String joinScale;
	/**
	 * 存款金额
	 */
	private Double rechargeAmount;
	/**
	 * 优惠形式
	 */
	private String preferentialForm;


	@Nonpersistent
	public float getTotalConsume() {
		return totalConsume;
	}

	public void setTotalConsume(float totalConsume) {
		this.totalConsume = totalConsume;
	}

	@Nonpersistent
	public float getTotalCashBack() {
		return totalCashBack;
	}

	public void setTotalCashBack(float totalCashBack) {
		this.totalCashBack = totalCashBack;
	}

	@Nonpersistent
	public Integer getJoinNumber() {
		return joinNumber;
	}

	public void setJoinNumber(Integer joinNumber) {
		this.joinNumber = joinNumber;
	}

	@Nonpersistent
	public String getJoinScale() {
		return joinScale;
	}

	public void setJoinScale(String joinScale) {
		this.joinScale = joinScale;
	}

	@Nonpersistent
	public Double getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(Double rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	@Nonpersistent
	public String getPreferentialForm() {
		return preferentialForm;
	}

	public void setPreferentialForm(String preferentialForm) {
		this.preferentialForm = preferentialForm;
	}

	//endregion your codes 2

}