package g.model.gameroom.po;

import org.soul.commons.bean.IEntity;
import org.soul.model.common.Sortable;


/**
 * Ai与玩家配比设置实体
 *
 * @author lenovo
 * @time 2017-2-15 14:28:52
 */
//region your codes 1
public class PlayerAiRatioControl implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 3738329954244576982L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_AI_PLAYER_CONTROL_ID = "aiPlayerControlId";
	public static final String PROP_PLAYER_PROPORTION_MIN = "playerProportionMin";
	public static final String PROP_PLAYER_PROPORTION_MAX = "playerProportionMax";
	public static final String PROP_AI_PROPORTION_MIN = "aiProportionMin";
	public static final String PROP_AI_PROPORTION_MAX = "aiProportionMax";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Integer id;
	/** Ai设置ID */
	private Integer aiPlayerControlId;
	/** 玩家最低占比 */
	private Integer playerProportionMin;
	/** 玩家最高占比 */
	private Integer playerProportionMax;
	/** AI最低占比 */
	private Integer aiProportionMin;
	/** AI最高占比 */
	private Integer aiProportionMax;
	//endregion

	
	//region constuctors
	public PlayerAiRatioControl(){
	}

	public PlayerAiRatioControl(Integer id){
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
	public Integer getAiPlayerControlId() {
		return this.aiPlayerControlId;
	}

	public void setAiPlayerControlId(Integer value) {
		this.aiPlayerControlId = value;
	}
	public Integer getPlayerProportionMin() {
		return this.playerProportionMin;
	}

	public void setPlayerProportionMin(Integer value) {
		this.playerProportionMin = value;
	}
	@Sortable
	public Integer getPlayerProportionMax() {
		return this.playerProportionMax;
	}

	public void setPlayerProportionMax(Integer value) {
		this.playerProportionMax = value;
	}
	public Integer getAiProportionMin() {
		return this.aiProportionMin;
	}

	public void setAiProportionMin(Integer value) {
		this.aiProportionMin = value;
	}
	public Integer getAiProportionMax() {
		return this.aiProportionMax;
	}

	public void setAiProportionMax(Integer value) {
		this.aiProportionMax = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}