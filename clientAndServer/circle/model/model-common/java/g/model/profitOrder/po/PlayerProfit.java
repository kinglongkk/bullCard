package g.model.profitOrder.po;

import org.soul.commons.bean.IEntity;
import org.soul.model.common.Sortable;


/**
 * 玩家盈利榜实体
 *
 * @author black
 * @time 2016-11-4 10:15:09
 */
//region your codes 1
public class PlayerProfit implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 1579916821416979342L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_USER_ID = "userId";
	public static final String PROP_PROFIT_AMOUNT = "profitAmount";
	public static final String PROP_GATHER_TIME = "gatherTime";
	public static final String PROP_GAME_ID = "gameId";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Integer id;
	/** 玩家id */
	private Integer userId;
	/** 盈利额 */
	private Double profitAmount;
	/** 采集时间 */
	private java.util.Date gatherTime;
	/** 游戏id */
	private Integer gameId;
	//endregion

	
	//region constuctors
	public PlayerProfit(){
	}

	public PlayerProfit(Integer id){
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
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer value) {
		this.userId = value;
	}
	public Double getProfitAmount() {
		return this.profitAmount;
	}

	public void setProfitAmount(Double value) {
		this.profitAmount = value;
	}
	public java.util.Date getGatherTime() {
		return this.gatherTime;
	}

	public void setGatherTime(java.util.Date value) {
		this.gatherTime = value;
	}
	public Integer getGameId() {
		return this.gameId;
	}

	public void setGameId(Integer value) {
		this.gameId = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}