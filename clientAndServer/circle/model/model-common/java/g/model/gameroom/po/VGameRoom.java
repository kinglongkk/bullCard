package g.model.gameroom.po;

import org.soul.commons.bean.IEntity;
import org.soul.model.common.Sortable;


/**
 * 实体
 *
 * @author lenovo
 * @time 2017-3-3 10:13:29
 */
//region your codes 1
public class VGameRoom implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 3948601029261910167L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_GAME_ID = "gameId";
	public static final String PROP_GAME_MODEL_ID = "gameModelId";
	public static final String PROP_NAME = "name";
	public static final String PROP_STATUS = "status";
	public static final String PROP_DESCRIPTION = "description";
	public static final String PROP_MAX_LIMIT_PLAYER_NUMBER = "maxLimitPlayerNumber";
	public static final String PROP_MIN_LIMIT_PLAYER_BLANCE = "minLimitPlayerBlance";
	public static final String PROP_PER_DESK_SEAT_COUNT = "perDeskSeatCount";
	public static final String PROP_IS_BUILD_IN = "isBuildIn";
	public static final String PROP_JACKPOT_SUM = "jackpotSum";
	public static final String PROP_MAX_LIMIT_GAME_LOSE = "maxLimitGameLose";
	public static final String PROP_MIN_JACKPOT_LIMIT = "minJackpotLimit";
	public static final String PROP_MAX_JACKPOT_LIMIT = "maxJackpotLimit";
	public static final String PROP_GAMENAME = "gamename";
	public static final String PROP_MODELNAME = "modelname";
	public static final String PROP_BET_CHIP = "betChip";
	public static final String PROP_DEALER_BLANCE = "dealerBlance";
	public static final String PROP_DEALER_BLANCE_TIP = "dealerBlanceTip";
	public static final String PROP_DEALER_BLANCE_QUIT = "dealerBlanceQuit";
	public static final String PROP_BET_TIMES = "betTimes";
	//endregion
	
	
	//region properties
	/**  */
	private Integer id;
	/**  */
	private Integer gameId;
	/**  */
	private Integer gameModelId;
	/**  */
	private String name;
	/**  */
	private String status;
	/**  */
	private String description;
	/**  */
	private Integer maxLimitPlayerNumber;
	/**  */
	private Integer minLimitPlayerBlance;
	/**  */
	private Integer perDeskSeatCount;
	/**  */
	private Boolean isBuildIn;
	/**  */
	private Long jackpotSum;
	/**  */
	private Long maxLimitGameLose;
	/**  */
	private Long minJackpotLimit;
	/**  */
	private Long maxJackpotLimit;
	/**  */
	private String gamename;
	/**  */
	private String modelname;
	/**  */
	private String betChip;
	/**  */
	private Integer dealerBlance;
	/**  */
	private Integer dealerBlanceTip;
	/**  */
	private Integer dealerBlanceQuit;
	/**  */
	private Integer betTimes;
	//endregion

	
	//region constuctors
	public VGameRoom(){
	}

	public VGameRoom(Integer id){
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
	public Integer getGameId() {
		return this.gameId;
	}

	public void setGameId(Integer value) {
		this.gameId = value;
	}
	public Integer getGameModelId() {
		return this.gameModelId;
	}

	public void setGameModelId(Integer value) {
		this.gameModelId = value;
	}
	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String value) {
		this.status = value;
	}
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String value) {
		this.description = value;
	}
	public Integer getMaxLimitPlayerNumber() {
		return this.maxLimitPlayerNumber;
	}

	public void setMaxLimitPlayerNumber(Integer value) {
		this.maxLimitPlayerNumber = value;
	}
	public Integer getMinLimitPlayerBlance() {
		return this.minLimitPlayerBlance;
	}

	public void setMinLimitPlayerBlance(Integer value) {
		this.minLimitPlayerBlance = value;
	}
	public Integer getPerDeskSeatCount() {
		return this.perDeskSeatCount;
	}

	public void setPerDeskSeatCount(Integer value) {
		this.perDeskSeatCount = value;
	}
	public Boolean getIsBuildIn() {
		return this.isBuildIn;
	}

	public void setIsBuildIn(Boolean value) {
		this.isBuildIn = value;
	}
	public Long getJackpotSum() {
		return this.jackpotSum;
	}

	public void setJackpotSum(Long value) {
		this.jackpotSum = value;
	}
	public Long getMaxLimitGameLose() {
		return this.maxLimitGameLose;
	}

	public void setMaxLimitGameLose(Long value) {
		this.maxLimitGameLose = value;
	}
	public Long getMinJackpotLimit() {
		return this.minJackpotLimit;
	}

	public void setMinJackpotLimit(Long value) {
		this.minJackpotLimit = value;
	}
	public Long getMaxJackpotLimit() {
		return this.maxJackpotLimit;
	}

	public void setMaxJackpotLimit(Long value) {
		this.maxJackpotLimit = value;
	}
	public String getGamename() {
		return this.gamename;
	}

	public void setGamename(String value) {
		this.gamename = value;
	}
	public String getModelname() {
		return this.modelname;
	}

	public void setModelname(String value) {
		this.modelname = value;
	}
	public String getBetChip() {
		return this.betChip;
	}

	public void setBetChip(String value) {
		this.betChip = value;
	}
	public Integer getDealerBlance() {
		return this.dealerBlance;
	}

	public void setDealerBlance(Integer value) {
		this.dealerBlance = value;
	}
	public Integer getDealerBlanceTip() {
		return this.dealerBlanceTip;
	}

	public void setDealerBlanceTip(Integer value) {
		this.dealerBlanceTip = value;
	}
	public Integer getDealerBlanceQuit() {
		return this.dealerBlanceQuit;
	}

	public void setDealerBlanceQuit(Integer value) {
		this.dealerBlanceQuit = value;
	}
	public Integer getBetTimes() {
		return this.betTimes;
	}

	public void setBetTimes(Integer value) {
		this.betTimes = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}