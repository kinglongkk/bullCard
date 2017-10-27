package g.model.admin.gamebull100room.po;

import org.soul.commons.bean.IEntity;


/**
 * 实体
 *
 * @author lenovo
 * @time 2016-12-24 15:43:30
 */
//region your codes 1
public class VRoomBull100 implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 3337052090109905988L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_GAME_ID = "gameId";
	public static final String PROP_GAME_MODEL_ID = "gameModelId";
	public static final String PROP_NAME = "name";
	public static final String PROP_STATUS = "status";
	public static final String PROP_MAX_LIMIT_PLAYER_NUMBER = "maxLimitPlayerNumber";
	public static final String PROP_MIN_LIMIT_PLAYER_BLANCE = "minLimitPlayerBlance";
	public static final String PROP_MIN_LIMIT_DEALER_BLANCE = "dealerBlance";
	public static final String PROP_CURRENT_PLAYER_COUNT = "currentPlayerCount";
	public static final String PROP_PER_DESK_SEAT_COUNT = "perDeskSeatCount";
	public static final String PROP_GAME_CODE = "gameCode";
	public static final String PROP_GAME_NAME = "gameName";
	public static final String PROP_GAME_ICON = "gameIcon";
	public static final String PROP_GAME_STATUS = "gameStatus";
	public static final String PROP_GAME_MODEL_CODE = "gameModelCode";
	public static final String PROP_GAME_MODEL_NAME = "gameModelName";
	public static final String PROP_GAME_MODEL_STATUS = "gameModelStatus";
	public static final String PROP_GAME_MODEL_ICON = "gameModelIcon";
	public static final String PROP_DEALER_BLANCE_TIP = "dealerBlanceTip";
	public static final String PROP_DEALER_BLANCE_QUIT = "dealerBlanceQuit";
	public static final String PROP_BET_CHIP = "betChip";
	public static final String PROP_BET_TIMES = "betTimes";
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
	/** 房间可容纳最大玩家数 */
	private Integer maxLimitPlayerNumber;
	/** 最小进房下限 */
	private Integer minLimitPlayerBlance;
	/** 上庄金额 */
	private Integer dealerBlance;
	/** 房间当前玩家数 */
	private Integer currentPlayerCount;
	/** 每桌最大座位数 */
	private Integer perDeskSeatCount;
	/** 游戏代码 */
	private String gameCode;
	/** 游戏名称 */
	private String gameName;
	/** 游戏图标 */
	private String gameIcon;
	/** 游戏状态 */
	private String gameStatus;
	/** 玩法代码 */
	private String gameModelCode;
	/** 玩法名称 */
	private String gameModelName;
	/** 玩法状态 */
	private String gameModelStatus;
	/** 玩法图标 */
	private String gameModelIcon;
	/** 庄家提醒金额 */
	private Integer dealerBlanceTip;
	/** 庄家下庄金额 */
	private Integer dealerBlanceQuit;
	/** 投注筹码 */
	private String betChip;
	/** 最大投注次数 */
	private Integer betTimes;
	//endregion

	
	//region constuctors
	public VRoomBull100(){
	}

	public VRoomBull100(Integer id){
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

    public Integer getDealerBlance() {
        return dealerBlance;
    }

    public void setDealerBlance(Integer dealerBlance) {
        this.dealerBlance = dealerBlance;
    }

    public Integer getCurrentPlayerCount() {
		return this.currentPlayerCount;
	}

	public void setCurrentPlayerCount(Integer value) {
		this.currentPlayerCount = value;
	}
	public Integer getPerDeskSeatCount() {
		return this.perDeskSeatCount;
	}

	public void setPerDeskSeatCount(Integer value) {
		this.perDeskSeatCount = value;
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

	public Integer getDealerBlanceTip() {
		return dealerBlanceTip;
	}

	public void setDealerBlanceTip(Integer dealerBlanceTip) {
		this.dealerBlanceTip = dealerBlanceTip;
	}

	public Integer getDealerBlanceQuit() {
		return dealerBlanceQuit;
	}

	public void setDealerBlanceQuit(Integer dealerBlanceQuit) {
		this.dealerBlanceQuit = dealerBlanceQuit;
	}

	public String getBetChip() {
		return betChip;
	}

	public void setBetChip(String betChip) {
		this.betChip = betChip;
	}

	public Integer getBetTimes() {
		return betTimes;
	}

	public void setBetTimes(Integer betTimes) {
		this.betTimes = betTimes;
	}

//endregion

	//region your codes 2

	//endregion your codes 2

}