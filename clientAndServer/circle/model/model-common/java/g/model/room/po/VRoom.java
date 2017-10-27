package g.model.room.po;

import org.soul.commons.bean.IEntity;
import org.soul.commons.support.Nonpersistent;


/**
 * 实体
 *
 * @author LENOVO
 * @time 2017-3-11 11:10:36
 */
//region your codes 1
public class VRoom implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 2136591993453323019L;
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
	public static final String PROP_JACKPOT = "jackpot";
	public static final String PROP_JACKPOT_OVERFLOW = "jackpotOverflow";
	public static final String PROP_GAME_CODE = "gameCode";
	public static final String PROP_GAME_NAME = "gameName";
	public static final String PROP_GAME_ICON = "gameIcon";
	public static final String PROP_GAME_STATUS = "gameStatus";
	public static final String PROP_GAME_MODEL_CODE = "gameModelCode";
	public static final String PROP_GAME_MODEL_NAME = "gameModelName";
	public static final String PROP_GAME_MODEL_STATUS = "gameModelStatus";
	public static final String PROP_GAME_MODEL_ICON = "gameModelIcon";
	public static final String PROP_MAX_LIMIT_PLAYER_BLANCE = "maxLimitPlayerBlance";
	//endregion
	
	
	//region properties
	/** 房间id */
	private Integer id;
	/** 游戏id */
	private Integer gameId;
	/** 玩法id */
	private Integer gameModelId;
	/** 房间名称 */
	private String name;
	/** 房间状态 */
	private String status;
	/** 房间描述 */
	private String description;
	/** 房间最大容纳玩家数 */
	private Integer maxLimitPlayerNumber;
	/** 房间最小进房下限 */
	private Integer minLimitPlayerBlance;
	/** 房间当前玩家数 */
	private Integer perDeskSeatCount;
	/** 房间变量奖池金额 */
	private Long jackpot;
	/** 房间奖池溢出金额 */
	private Long jackpotOverflow;
	/** 游戏code */
	private String gameCode;
	/** 游戏名称 */
	private String gameName;
	/** 游戏图标 */
	private String gameIcon;
	/** 游戏状态 */
	private String gameStatus;
	/** 玩法code */
	private String gameModelCode;
	/** 玩法名称 */
	private String gameModelName;
	/** 玩法状态 */
	private String gameModelStatus;
	/** 玩法图标 */
	private String gameModelIcon;
	/** 房间最大限入金额 */
	private Long maxLimitPlayerBlance;
	//endregion

	
	//region constuctors
	public VRoom(){
	}

	public VRoom(Integer id){
		this.id = id;
	}
	//endregion


	//region getters and setters
	@org.soul.model.common.Sortable
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
	public Long getJackpot() {
		return this.jackpot;
	}

	public void setJackpot(Long value) {
		this.jackpot = value;
	}
	public Long getJackpotOverflow() {
		return this.jackpotOverflow;
	}

	public void setJackpotOverflow(Long value) {
		this.jackpotOverflow = value;
	}
	public String getGameCode() {
		return this.gameCode;
	}

	public void setGameCode(String value) {
		this.gameCode = value;
	}
	public String getGameName() {
		return this.gameName;
	}

	public void setGameName(String value) {
		this.gameName = value;
	}
	public String getGameIcon() {
		return this.gameIcon;
	}

	public void setGameIcon(String value) {
		this.gameIcon = value;
	}
	public String getGameStatus() {
		return this.gameStatus;
	}

	public void setGameStatus(String value) {
		this.gameStatus = value;
	}
	public String getGameModelCode() {
		return this.gameModelCode;
	}

	public void setGameModelCode(String value) {
		this.gameModelCode = value;
	}
	public String getGameModelName() {
		return this.gameModelName;
	}

	public void setGameModelName(String value) {
		this.gameModelName = value;
	}
	public String getGameModelStatus() {
		return this.gameModelStatus;
	}

	public void setGameModelStatus(String value) {
		this.gameModelStatus = value;
	}
	public String getGameModelIcon() {
		return this.gameModelIcon;
	}

	public void setGameModelIcon(String value) {
		this.gameModelIcon = value;
	}
	public Long getMaxLimitPlayerBlance() {
		return this.maxLimitPlayerBlance;
	}

	public void setMaxLimitPlayerBlance(Long value) {
		this.maxLimitPlayerBlance = value;
	}
	//endregion

	//region your codes 2
	public static final String PROP_CURRENT_PLAYER_COUNT = "currentPlayerCount";
	public static final String PROP_CURRENT_AI_COUNT = "currentAiCount";
	private Integer currentPlayerCount = 0;
	private Integer currentAiCount = 0;

	@Nonpersistent
	public Integer getCurrentAiCount() {
		return currentAiCount;
	}

	public void setCurrentAiCount(Integer currentAiCount) {
		this.currentAiCount = currentAiCount;
	}

	@Nonpersistent
    public Integer getCurrentPlayerCount() {
        return currentPlayerCount;
    }

	public void setCurrentPlayerCount(Integer currentPlayerCount) {
		this.currentPlayerCount = currentPlayerCount;
	}
	//endregion your codes 2

}