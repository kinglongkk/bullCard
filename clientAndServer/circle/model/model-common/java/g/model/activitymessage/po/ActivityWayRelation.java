package g.model.activitymessage.po;

import org.soul.commons.bean.IEntity;

/**
 * 活动优惠方式表实体
 *
 * @author black
 * @time 2016-9-5 15:04:06
 */
//region your codes 1
public class ActivityWayRelation implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -7660473921828538111L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ACTIVITY_MESSAGE_ID = "activityMessageId";
	public static final String PROP_PREFERENTIAL_FORM = "preferentialForm";
	public static final String PROP_PREFERENTIAL_VALUE = "preferentialValue";
	public static final String PROP_PREFERENTIAL_AUDIT = "preferentialAudit";
	public static final String PROP_ORDER_COLUMN = "orderColumn";
	public static final String PROP_ACTIVITY_RULE_ID = "activityRuleId";
	public static final String PROP_ID = "id";
	public static final String PROP_IS_ARTICLE = "isArticle";
	public static final String PROP_ARTICLE = "article";
	public static final String PROP_TRIGGER_VALUE = "triggerValue";
	public static final String PROP_PREFERENTIAL_RATIO = "preferentialRatio";
	//endregion
	
	
	//region properties
	/** 活动id */
	private Integer activityMessageId;
	/** 优惠形式 */
	private String preferentialForm;
	/** 满足优惠值 */
	private Double preferentialValue;
	/** 优惠稽核倍数 */
	private Double preferentialAudit;
	/** 顺序 */
	private Integer orderColumn;
	/** 活动规则关联id */
	private Integer activityRuleId;
	/** 主键 */
	private Integer id;
	/** 是否为实物 */
	private Boolean isArticle;
	/** 实物 */
	private String article;
	/** 优惠触发值 */
	private Double triggerValue;
	/** 优惠比例 */
	private Double preferentialRatio;
	//endregion


	//region constuctors
	public ActivityWayRelation(){
	}

	public ActivityWayRelation(Integer id){
		this.id = id;
	}
	//endregion


	//region getters and setters
	public Integer getActivityMessageId() {
		return this.activityMessageId;
	}

	public void setActivityMessageId(Integer value) {
		this.activityMessageId = value;
	}
	public String getPreferentialForm() {
		return this.preferentialForm;
	}

	public void setPreferentialForm(String value) {
		this.preferentialForm = value;
	}
	public Double getPreferentialValue() {
		return this.preferentialValue;
	}

	public void setPreferentialValue(Double value) {
		this.preferentialValue = value;
	}
	public Double getPreferentialAudit() {
		return this.preferentialAudit;
	}

	public void setPreferentialAudit(Double value) {
		this.preferentialAudit = value;
	}
	public Integer getOrderColumn() {
		return this.orderColumn;
	}

	public void setOrderColumn(Integer value) {
		this.orderColumn = value;
	}
	public Integer getActivityRuleId() {
		return this.activityRuleId;
	}

	public void setActivityRuleId(Integer value) {
		this.activityRuleId = value;
	}
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer value) {
		this.id = value;
	}
	public Boolean getIsArticle() {
		return this.isArticle;
	}

	public void setIsArticle(Boolean value) {
		this.isArticle = value;
	}
	public String getArticle() {
		return this.article;
	}

	public void setArticle(String value) {
		this.article = value;
	}
	public Double getTriggerValue() {
		return this.triggerValue;
	}

	public void setTriggerValue(Double value) {
		this.triggerValue = value;
	}
	public Double getPreferentialRatio() {
		return this.preferentialRatio;
	}

	public void setPreferentialRatio(Double value) {
		this.preferentialRatio = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}