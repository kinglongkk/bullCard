package g.model.gameroom.po;

import org.soul.commons.bean.IEntity;


/**
 * Ai玩家设置实体
 *
 * @author LENOVO
 * @time 2017-3-1 15:37:42
 */
//region your codes 1
public class PlayerAiControl implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -3163892931501697587L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_ROOM_ID = "roomId";
	public static final String PROP_ROOM_NAME = "roomName";
	public static final String PROP_BATCH_ID = "batchId";
	public static final String PROP_STATUS = "status";
	public static final String PROP_AI_QTY = "aiQty";
	public static final String PROP_CONTROL_MODE = "controlMode";
	public static final String PROP_BRING_GOLD_MIN = "bringGoldMin";
	public static final String PROP_BRING_GOLD_MAX = "bringGoldMax";
	public static final String PROP_INTERVAL_MIN_TIME = "intervalMinTime";
	public static final String PROP_INTERVAL_MAX_TIME = "intervalMaxTime";
	public static final String PROP_LEVEL_MIN_TIME = "leaveMinTime";
	public static final String PROP_LEVEL_MAX_TIME = "leaveMaxTime";
	public static final String PROP_REST_MIN_GAMES = "restMinGames";
	public static final String PROP_REST_MAX_GAMES = "restMaxGames";
	public static final String PROP_ROOM_MAX_QTY = "roomMaxQty";
	public static final String PROP_CREATE_USER = "createUser";
	public static final String PROP_CREATE_TIME = "createTime";
	public static final String PROP_UPDATE_USER = "updateUser";
	public static final String PROP_UPDATE_TIME = "updateTime";
	public static final String PROP_BEGIN_CONTROL_TIME = "beginControlTime";
	public static final String PROP_CONTROL_CYCLE = "controlCycle";
	public static final String PROP_BET_COUNT = "betCount";
	public static final String PROP_CHIP_RATES = "chipRates";
	public static final String PROP_GAME_MODEL = "gameModel";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Integer id;
	/** 房间ID */
	private Integer roomId;
	/** 房间名 */
	private String roomName;
	/** 批次标志 */
	private String batchId;
	/** 状态（10：生效 20：已失效 30：未生效） */
	private String status;
	/** AI数目 */
	private Integer aiQty;
	/** 调控方式（1：进入；2：退出） */
	private String controlMode;
	/** 最少携带金币数 */
	private Integer bringGoldMin;
	/** 最多携带金币数 */
	private Integer bringGoldMax;
	/** 进入最小时间间隔 */
	private Integer intervalMinTime;
	/** 进入最大时间间隔 */
	private Integer intervalMaxTime;
	/** 离开最小时间间隔 */
	private Integer leaveMinTime;
	/** 离开最大时间间隔 */
	private Integer leaveMaxTime;
	/** 最少游戏休息局数 */
	private Integer restMinGames;
	/** 最大游戏休息局数 */
	private Integer restMaxGames;
	/** 游戏人数最高值 */
	private Integer roomMaxQty;
	/** 创建人 */
	private String createUser;
	/** 创建时间 */
	private java.util.Date createTime;
	/** 更新者 */
	private String updateUser;
	/** 更新时间 */
	private java.util.Date updateTime;
	/** 开始操作时间 */
	private java.util.Date beginControlTime;
	/** 控制周期 分钟数 */
	private Integer controlCycle;
	/** 平均每场比赛投注次数 */
	private Integer betCount;
	/** 投注筹码概率范围,逗号隔开 */
	private String chipRates;
	/** 游戏玩法 */
	private Integer gameModel;
	//endregion

	
	//region constuctors
	public PlayerAiControl(){
	}

	public PlayerAiControl(Integer id){
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
	public Integer getRoomId() {
		return this.roomId;
	}

	public void setRoomId(Integer value) {
		this.roomId = value;
	}
	public String getRoomName() {
		return this.roomName;
	}

	public void setRoomName(String value) {
		this.roomName = value;
	}
	public String getBatchId() {
		return this.batchId;
	}

	public void setBatchId(String value) {
		this.batchId = value;
	}
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String value) {
		this.status = value;
	}
	public Integer getAiQty() {
		return this.aiQty;
	}

	public void setAiQty(Integer value) {
		this.aiQty = value;
	}
	public String getControlMode() {
		return this.controlMode;
	}

	public void setControlMode(String value) {
		this.controlMode = value;
	}
	public Integer getBringGoldMin() {
		return this.bringGoldMin;
	}

	public void setBringGoldMin(Integer value) {
		this.bringGoldMin = value;
	}
	public Integer getBringGoldMax() {
		return this.bringGoldMax;
	}

	public void setBringGoldMax(Integer value) {
		this.bringGoldMax = value;
	}
	public Integer getIntervalMinTime() {
		return this.intervalMinTime;
	}

	public void setIntervalMinTime(Integer value) {
		this.intervalMinTime = value;
	}
	public Integer getIntervalMaxTime() {
		return this.intervalMaxTime;
	}

	public void setIntervalMaxTime(Integer value) {
		this.intervalMaxTime = value;
	}
	public Integer getLeaveMinTime() {
		return this.leaveMinTime;
	}

	public void setLeaveMinTime(Integer value) {
		this.leaveMinTime = value;
	}
	public Integer getLeaveMaxTime() {
		return this.leaveMaxTime;
	}

	public void setLeaveMaxTime(Integer value) {
		this.leaveMaxTime = value;
	}
	public Integer getRestMinGames() {
		return this.restMinGames;
	}

	public void setRestMinGames(Integer value) {
		this.restMinGames = value;
	}
	public Integer getRestMaxGames() {
		return this.restMaxGames;
	}

	public void setRestMaxGames(Integer value) {
		this.restMaxGames = value;
	}
	public Integer getRoomMaxQty() {
		return this.roomMaxQty;
	}

	public void setRoomMaxQty(Integer value) {
		this.roomMaxQty = value;
	}
	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String value) {
		this.createUser = value;
	}
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String value) {
		this.updateUser = value;
	}
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	public java.util.Date getBeginControlTime() {
		return this.beginControlTime;
	}

	public void setBeginControlTime(java.util.Date value) {
		this.beginControlTime = value;
	}
	public Integer getControlCycle() {
		return this.controlCycle;
	}

	public void setControlCycle(Integer value) {
		this.controlCycle = value;
	}
	public Integer getBetCount() {
		return this.betCount;
	}

	public void setBetCount(Integer value) {
		this.betCount = value;
	}
	public String getChipRates() {
		return this.chipRates;
	}

	public void setChipRates(String value) {
		this.chipRates = value;
	}
	public Integer getGameModel() {
		return this.gameModel;
	}

	public void setGameModel(Integer value) {
		this.gameModel = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}