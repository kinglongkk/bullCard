package g.model.profitandloss.po;

import org.soul.commons.bean.IEntity;
import org.soul.commons.support.Nonpersistent;


/**
 * 玩家盈亏实体
 *
 * @author black
 * @time 2017-5-31 15:15:09
 */
//region your codes 1
public class PlayerProfitAndLoss implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 1579916821416979342L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_GATHER_TIME = "gatherTime";
	public static final String PROP_PLAYER_ID = "playerId";
	public static final String PROP_BET_FREQUENCY = "betFrequency";
	public static final String PROP_EFFECTIVE_AMOUNT = "effectiveAmount";
	public static final String PROP_PROFIT_AMOUNT = "profitAmount";
	public static final String PROP_WATER_AMOUNT = "waterAmount";
	//endregion

	//region properties
	/** 主键 */
	private Integer id;
	/** 采集时间 */
	private Long gatherTime;
	/** 玩家账号 */
	private Integer playerId;
	/** 投注次数 */
	private Integer betFrequency;
	/** 有效投注额 */
	private Long effectiveAmount;
	/** 盈亏额 */
	private Long profitAmount;
	/** 抽水额 */
	private Long waterAmount;
	//endregion


	//region constuctors
	public PlayerProfitAndLoss(){
	}

	public PlayerProfitAndLoss(Integer id){
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

	public Long getGatherTime() {
		return gatherTime;
	}

	public void setGatherTime(Long gatherTime) {
		this.gatherTime = gatherTime;
	}

	public Integer getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}

	public Integer getBetFrequency() {
		return betFrequency;
	}

	public void setBetFrequency(Integer betFrequency) {
		this.betFrequency = betFrequency;
	}

	public Long getEffectiveAmount() {
		return effectiveAmount;
	}

	public void setEffectiveAmount(Long effectiveAmount) {
		this.effectiveAmount = effectiveAmount;
	}

	public Long getProfitAmount() {
		return profitAmount;
	}

	public void setProfitAmount(Long profitAmount) {
		this.profitAmount = profitAmount;
	}

	public Long getWaterAmount() {
		return waterAmount;
	}

	public void setWaterAmount(Long waterAmount) {
		this.waterAmount = waterAmount;
	}

	//endregion

	//region your codes 2
	/**
	 * 搜索开始时间
	 */
	private Integer startTime;
	/**
	 * 搜索结束时间
	 */
	private Integer endTime;

	@Nonpersistent
	public Integer getStartTime() {
		return startTime;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	@Nonpersistent
	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}

	//endregion your codes 2

}