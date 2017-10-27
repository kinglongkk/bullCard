package g.model.gameroom.po;


import org.soul.commons.bean.IEntity;


/**
 * 游戏房间表实体
 *
 * @author black
 * @time 2017-4-7 14:49:28
 */
//region your codes 1
public class GameRoom implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -7350269608299913279L;
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
	public static final String PROP_JACKPOT = "jackpot";
	public static final String PROP_JACKPOT_OVERFLOW = "jackpotOverflow";
	public static final String PROP_MAX_LIMIT_PLAYER_BLANCE = "maxLimitPlayerBlance";
	public static final String PROP_SEND_MSG_CONDITION = "sendMsgCondition";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Integer id;
	/** 所属游戏 */
	private Integer gameId;
	/** 所属游戏玩法 */
	private Integer gameModelId;
	/** 名称 */
	private String name;
	/** [10:启用,20:禁用,30:维护] */
	private String status;
	/** 描述 */
	private String description;
	/** 最大容纳玩家数 */
	private Integer maxLimitPlayerNumber;
	/** 最小进房下限 */
	private Integer minLimitPlayerBlance;
	/** 每桌最大座位数 */
	private Integer perDeskSeatCount;
	/** 是否内置 */
	private Boolean isBuildIn;
	/** 奖池实际金额 */
	private Long jackpot;
	/** 奖池溢出金额 */
	private Long jackpotOverflow;
	/** 房间最大限入金额 */
	private Long maxLimitPlayerBlance;
	/** 产生消息满足大于最高可输金额的比例条件 */
	private Double sendMsgCondition;
	//endregion

	
	//region constuctors
	public GameRoom(){
	}

	public GameRoom(Integer id){
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
	public Long getMaxLimitPlayerBlance() {
		return this.maxLimitPlayerBlance;
	}

	public void setMaxLimitPlayerBlance(Long value) {
		this.maxLimitPlayerBlance = value;
	}
	public Double getSendMsgCondition() {
		return this.sendMsgCondition;
	}

	public void setSendMsgCondition(Double value) {
		this.sendMsgCondition = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}