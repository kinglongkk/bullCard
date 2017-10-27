package g.model.playerstatistics.po;


import org.soul.commons.bean.IEntity;
import org.soul.commons.support.Nonpersistent;
import org.soul.model.common.Sortable;

import java.util.Date;


/**
 * 玩家数据统计表实体
 *
 * @author lenovo
 * @time 2017-1-5 14:06:09
 */
//region your codes 1
public class PlayerDataStatistics implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -6564006963940957855L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_SYS_USER_ID = "sysUserId";
	public static final String PROP_USERNAME = "username";
	public static final String PROP_NICKNAME = "nickname";
	public static final String PROP_OWNER_USERNAME = "ownerUsername";
	public static final String PROP_OWNER_NICKNAME = "ownerNickname";
	public static final String PROP_GAME_ID = "gameId";
	public static final String PROP_GAME_NAME = "gameName";
	public static final String PROP_GAME_MODEL_ID = "gameModelId";
	public static final String PROP_GAME_MODEL_NAME = "gameModelName";
	public static final String PROP_ROOM_ID = "roomId";
	public static final String PROP_ROOM_NAME = "roomName";
	public static final String PROP_BET_NO = "betNo";
	public static final String PROP_BET_DATE = "betDate";
	public static final String PROP_SINGLE_AMOUNT = "singleAmount";
	public static final String PROP_EFFECTIVE_AMOUNT = "effectiveAmount";
	public static final String PROP_PROFIT_AMOUNT = "profitAmount";
	public static final String PROP_WIN_AMOUNT = "winAmount";
	public static final String PROP_STATUS = "status";
	public static final String PROP_RESULT = "result";
	public static final String PROP_BET_TIME = "betTime";
	public static final String PROP_CONFIRM_TIME = "confirmTime";
	public static final String PROP_SETTLE_TIME = "settleTime";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Integer id;
	/** 用户id */
	private Integer sysUserId;
	/** 代理用户名 */
	private String ownerUsername;
	/** 代理昵称 */
	private String ownerNickname;
	/** 所属游戏 */
	private Integer gameId;
	/** 游戏名称 */
	private String gameName;
	/** 所属游戏玩法 */
	private Integer gameModelId;
	/** 玩法名称 */
	private String gameModelName;
	/** 房间ID */
	private Integer roomId;
	/** 房间名称 */
	private String roomName;
	/** 注单号 */
	private String betNo;
	/** 游戏日期 */
	private java.util.Date betDate;
	/** 下单金额(交易量) */
	private Double singleAmount;
	/** 有效投注额 */
	private Double effectiveAmount;
	/** 盈亏结果金额(派彩) */
	private Double profitAmount;
	/** 实际获利金额 */
	private Double winAmount;
	/** 状态:[10=未确认,20=已确认(有效),30=取消] */
	private String status;
	/** 结果:[10=赢,20=输,30=平,40=赢半,50=输半] */
	private String result;
	/** 注单时间 */
	private java.util.Date betTime;
	/** 确认时间 */
	private java.util.Date confirmTime;
	/** 结算时间 */
	private java.util.Date settleTime;
	//endregion

	
	//region constuctors
	public PlayerDataStatistics(){
	}

	public PlayerDataStatistics(Integer id){
		this.id = id;
	}
	//endregion


	//region getters and setters

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(Integer sysUserId) {
		this.sysUserId = sysUserId;
	}

	public String getOwnerUsername() {
		return ownerUsername;
	}

	public void setOwnerUsername(String ownerUsername) {
		this.ownerUsername = ownerUsername;
	}

	public String getOwnerNickname() {
		return ownerNickname;
	}

	public void setOwnerNickname(String ownerNickname) {
		this.ownerNickname = ownerNickname;
	}

	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public Integer getGameModelId() {
		return gameModelId;
	}

	public void setGameModelId(Integer gameModelId) {
		this.gameModelId = gameModelId;
	}

	public String getGameModelName() {
		return gameModelName;
	}

	public void setGameModelName(String gameModelName) {
		this.gameModelName = gameModelName;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getBetNo() {
		return betNo;
	}

	public void setBetNo(String betNo) {
		this.betNo = betNo;
	}

	public Date getBetDate() {
		return betDate;
	}

	public void setBetDate(Date betDate) {
		this.betDate = betDate;
	}

	public Double getSingleAmount() {
		return singleAmount;
	}

	public void setSingleAmount(Double singleAmount) {
		this.singleAmount = singleAmount;
	}

	public Double getEffectiveAmount() {
		return effectiveAmount;
	}

	public void setEffectiveAmount(Double effectiveAmount) {
		this.effectiveAmount = effectiveAmount;
	}

	public Double getProfitAmount() {
		return profitAmount;
	}

	public void setProfitAmount(Double profitAmount) {
		this.profitAmount = profitAmount;
	}

	public Double getWinAmount() {
		return winAmount;
	}

	public void setWinAmount(Double winAmount) {
		this.winAmount = winAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Date getBetTime() {
		return betTime;
	}

	public void setBetTime(Date betTime) {
		this.betTime = betTime;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	@Sortable
	public Date getSettleTime() {
		return settleTime;
	}

	public void setSettleTime(Date settleTime) {
		this.settleTime = settleTime;
	}

	//endregion

	//region your codes 2
	/** 用户名 */
	private String username;
	/** 昵称 */
	private String nickname;

	@Nonpersistent
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	@Nonpersistent
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	//endregion your codes 2

}