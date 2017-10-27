package g.model.admin.gameroomconfig.po;


import org.soul.commons.bean.IEntity;
import org.soul.commons.support.Nonpersistent;

import java.util.Date;


/**
 * 游戏房间配置信息表实体
 *
 * @author LENOVO
 * @time 2017-3-14 19:41:31
 */
//region your codes 1
public class GameRoomConfigBull100 implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -8119023806059394954L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_ROOM_ID = "roomId";
	public static final String PROP_BET_CHIP = "betChip";
	public static final String PROP_DEALER_BLANCE = "dealerBlance";
	public static final String PROP_DEALER_BLANCE_TIP = "dealerBlanceTip";
	public static final String PROP_DEALER_BLANCE_QUIT = "dealerBlanceQuit";
	public static final String PROP_BET_TIMES = "betTimes";
	public static final String PROP_JACKPOT_SUM = "jackpotSum";
	public static final String PROP_MAX_LIMIT_GAME_LOSE = "maxLimitGameLose";
	public static final String PROP_MIN_JACKPOT_LIMIT = "minJackpotLimit";
	public static final String PROP_MAX_JACKPOT_LIMIT = "maxJackpotLimit";
	public static final String PROP_MAX_JACKPOT_AMATCH = "maxJackpotAmatch";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Integer id;
	/** 房间编号 */
	private Integer roomId;
	/** 投注筹码 */
	private String betChip;
	/** 上庄金额 */
	private Integer dealerBlance;
	/** 庄家提醒金额 */
	private Integer dealerBlanceTip;
	/** 庄家下庄金额 */
	private Integer dealerBlanceQuit;
	/** 最大投注次数 */
	private Integer betTimes;
	/** 奖池金额 */
	private Long jackpotSum;
	/** 单局最高可输金额 */
	private Long maxLimitGameLose;
	/** 奖池最低下限金额 */
	private Long minJackpotLimit;
	/** 奖池最高积累金额 */
	private Long maxJackpotLimit;
	/** 单场比赛最高金额,超出部分溢出 */
	private Long maxJackpotAmatch;
	//endregion

	
	//region constuctors
	public GameRoomConfigBull100(){
	}

	public GameRoomConfigBull100(Integer id){
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
	@org.soul.model.common.Sortable
	public Integer getRoomId() {
		return this.roomId;
	}

	public void setRoomId(Integer value) {
		this.roomId = value;
	}
	@org.soul.model.common.Sortable
	public String getBetChip() {
		return this.betChip;
	}

	public void setBetChip(String value) {
		this.betChip = value;
	}
	@org.soul.model.common.Sortable
	public Integer getDealerBlance() {
		return this.dealerBlance;
	}

	public void setDealerBlance(Integer value) {
		this.dealerBlance = value;
	}
	@org.soul.model.common.Sortable
	public Integer getDealerBlanceTip() {
		return this.dealerBlanceTip;
	}

	public void setDealerBlanceTip(Integer value) {
		this.dealerBlanceTip = value;
	}
	@org.soul.model.common.Sortable
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
	public Long getMaxJackpotAmatch() {
		return this.maxJackpotAmatch;
	}

	public void setMaxJackpotAmatch(Long value) {
		this.maxJackpotAmatch = value;
	}
	//endregion

	//region your codes 2
	/**
	 * 游戏名称
	 */
	private String gameName;
	/**
	 * 玩法名称
	 */
	private String gameModelName;
	/**
	 * 房间名称
	 */
	private String gameRoomName;

	@Nonpersistent
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	@Nonpersistent
	public String getGameModelName() {
		return gameModelName;
	}
	public void setGameModelName(String gameModelName) {
		this.gameModelName = gameModelName;
	}

	@Nonpersistent
	public String getGameRoomName() {
		return gameRoomName;
	}
	public void setGameRoomName(String gameRoomName) {
		this.gameRoomName = gameRoomName;
	}

	/**
	 * 奖池实际金额
	 */
	private Long jackpot;
	/**
	 * 奖池溢出金额
	 */
	private Long jackpotOverflow;
	/**
	 * 赛事截止时间
	 */
	private Date confirmTime;

	@Nonpersistent
	public Long getJackpot() {
		return jackpot;
	}
	public void setJackpot(Long jackpot) {
		this.jackpot = jackpot;
	}

	@Nonpersistent
	public Long getJackpotOverflow() {
		return jackpotOverflow;
	}
	public void setJackpotOverflow(Long jackpotOverflow) {
		this.jackpotOverflow = jackpotOverflow;
	}

	@Nonpersistent
	public Date getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	//endregion your codes 2

}