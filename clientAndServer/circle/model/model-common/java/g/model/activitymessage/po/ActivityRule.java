package g.model.activitymessage.po;

import org.soul.commons.bean.IEntity;

/**
 * 活动规则表实体
 *
 * @author black
 * @time 2016-9-5 15:03:25
 */
//region your codes 1
public class ActivityRule implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -1011507219688205495L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_ACTIVITY_MESSAGE_ID = "activityMessageId";
	public static final String PROP_LIMIT_NUMBER = "limitNumber";
	public static final String PROP_EFFECTIVE_TIME = "effectiveTime";
	public static final String PROP_IS_DEMAND_FIRST = "isDemandFirst";
	public static final String PROP_IS_DESIGNATED_GAME = "isDesignatedGame";
	public static final String PROP_GAME_TYPE = "gameType";
	public static final String PROP_IS_EXCLUSIVE = "isExclusive";
	public static final String PROP_EXCLUSIVE_ACTIVITY = "exclusiveActivity";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Integer id;
	/** 活动信息id */
	private Integer activityMessageId;
	/** 限制次数,无限制次数为0 */
	private Integer limitNumber;
	/** 有效时间 */
	private String effectiveTime;
	/** 是否要求首充 */
	private Boolean isDemandFirst;
	/** 是否指定游戏 */
	private Boolean isDesignatedGame;
	/** 参与游戏 */
	private String gameType;
	/** 是否设置不同享 */
	private Boolean isExclusive;
	/** 不同享设置 */
	private String exclusiveActivity;
	//endregion

	
	//region constuctors
	public ActivityRule(){
	}

	public ActivityRule(Integer id){
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
	public Integer getLimitNumber() {
		return this.limitNumber;
	}

	public void setLimitNumber(Integer value) {
		this.limitNumber = value;
	}
	public String getEffectiveTime() {
		return this.effectiveTime;
	}

	public void setEffectiveTime(String value) {
		this.effectiveTime = value;
	}
	public Boolean getIsDemandFirst() {
		return this.isDemandFirst;
	}

	public void setIsDemandFirst(Boolean value) {
		this.isDemandFirst = value;
	}
	public Boolean getIsDesignatedGame() {
		return this.isDesignatedGame;
	}

	public void setIsDesignatedGame(Boolean value) {
		this.isDesignatedGame = value;
	}
	public String getGameType() {
		return this.gameType;
	}

	public void setGameType(String value) {
		this.gameType = value;
	}
	public Boolean getIsExclusive() {
		return this.isExclusive;
	}

	public void setIsExclusive(Boolean value) {
		this.isExclusive = value;
	}
	public String getExclusiveActivity() {
		return this.exclusiveActivity;
	}

	public void setExclusiveActivity(String value) {
		this.exclusiveActivity = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}