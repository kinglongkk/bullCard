package g.model.bet.po;

import org.soul.commons.bean.IEntity;
import org.soul.model.common.Sortable;


/**
 * 实体
 *
 * @author mark
 * @time 2016-7-12 16:23:04
 */
//region your codes 1
public class VBetDetail implements IEntity<Long> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 7997778021004506928L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_MATCH_ID = "matchId";
	public static final String PROP_BET_ID = "betId";
	public static final String PROP_BET_TEAM = "betTeam";
	public static final String PROP_POINT = "point";
	public static final String PROP_BET_TYPE = "betType";
	public static final String PROP_BET_TEAM_TYPE = "betTeamType";
	public static final String PROP_IOR_FIELD = "iorField";
	public static final String PROP_RATIO = "ratio";
	public static final String PROP_HOST_SCORE = "hostScore";
	public static final String PROP_CLIENT_SCORE = "clientScore";
	public static final String PROP_CODE = "code";
	public static final String PROP_GAME_ID = "gameId";
	public static final String PROP_GAME_NAME = "gameName";
    public static final String PROP_GAME_MODEL_ID = "gameModelId";
	public static final String PROP_GAME_MODEL_NAME = "gameModelName";
    public static final String PROP_GAME_ROOM_ID = "gameRoomId";
	public static final String PROP_ROOM_NAME = "roomName";
	public static final String PROP_GAME_TYPE = "gameType";
	public static final String PROP_BALL_TYPE = "ballType";
	public static final String PROP_LEAGUE_CODE = "leagueCode";
	public static final String PROP_HOST_CODE = "hostCode";
	public static final String PROP_CLIENT_CODE = "clientCode";
	public static final String PROP_BEGIN_TIME = "beginTime";
	public static final String PROP_END_TIME = "endTime";
	public static final String PROP_COVER_TIME = "coverTime";
	public static final String PROP_CREATE_TIME = "createTime";
	public static final String PROP_GATHER_TIME = "gatherTime";
	public static final String PROP_RESULT_GATHER_TIME = "resultGatherTime";
	public static final String PROP_UPDATE_TIME = "updateTime";
	public static final String PROP_SETTLE_STATUS = "settleStatus";
	public static final String PROP_IS_SETTLING = "isSettling";
	public static final String PROP_IS_RESULTED = "isResulted";
	public static final String PROP_IS_CANCEL = "isCancel";
	public static final String PROP_GNUM_H = "gnumH";
	public static final String PROP_GNUM_C = "gnumC";
	public static final String PROP_STRONG = "strong";
	public static final String PROP_HAS_RUNNING_BALL = "hasRunningBall";
	public static final String PROP_SYS_USER_ID = "sysUserId";
	public static final String PROP_SYS_USERNAME = "username";
	public static final String PROP_OWNER_ID = "ownerId";
	public static final String PROP_OWNER_USERNAME = "ownerUsername";
	public static final String PROP_RTYPE = "rtype";
	public static final String PROP_BET_NO = "betNo";
	public static final String PROP_ORDER_TYPE = "orderType";
	public static final String PROP_SINGLE_AMOUNT = "singleAmount";
	public static final String PROP_PROFIT_AMOUNT = "profitAmount";
	public static final String PROP_EFFECTIVE_AMOUNT = "effectiveAmount";
	public static final String PROP_CAN_WIN = "canWin";
	public static final String PROP_STATUS = "status";
	public static final String PROP_RESULT = "result";
	public static final String PROP_IS_DELETED = "isDeleted";
	public static final String PROP_BET_TIME = "betTime";
	public static final String PROP_CONFIRM_TIME = "confirmTime";
	public static final String PROP_SETTLE_TIME = "settleTime";
	public static final String PROP_PHASE = "phase";
	public static final String PROP_BET_RESULT = "betResult";
	//endregion
	
	
	//region properties
	/**
	 * bet_detail表数据
	 */
	/** 主键 */
	private Long id;
	/** 赛事ID */
	private Long matchId;
	/** 注单id */
	private Long betId;
	/** 投注队伍代码 */
	private String betTeam;
	/** 赔率 */
	private Double point;
	/** 投注类型 */
	private String betType;
	/** 投注队类型(H=主队,C=客队,N=和局) */
	private String betTeamType;
	/** 采集赔率字段 */
	private String iorField;
	/** 盘口 */
	private String ratio;
	/** 主队比分(投注时) */
	private Integer hostScore;
	/** 客户比分(投注时) */
	private Integer clientScore;

	/**
	 * match表数据
	 */
	/** 代码 */
	private String code;
	/** 游戏id */
	private Integer gameId;
	/** 游戏名称 */
	private String gameName;
    /** 玩法id */
    private Integer gameModelId;
	/** 玩法名称 */
	private String gameModelName;
    /** 房间id */
    private Integer gameRoomId;
	/** 房间名称 */
	private String roomName;
	/** 游戏类型:[SPORT=体育...] */
	private String gameType;
	/** 比赛类型:[FT=足球...] */
	private String ballType;
	/** 联赛代码 */
	private Integer leagueCode;
	/** 主队代码 */
	private Integer hostCode;
	/** 客队代码 */
	private Integer clientCode;
	/** 比赛开始时间 */
	private java.util.Date beginTime;
	/** 比赛结束时间 */
	private java.util.Date endTime;
	/** 封盘时间 */
	private java.util.Date coverTime;
	/** 入库时间 */
	private java.util.Date createTime;
	/** 采集时间 */
	private java.util.Date gatherTime;
	/** 结果采集时间 */
	private java.util.Date resultGatherTime;
	/** 更新时间 */
	private java.util.Date updateTime;
	/** 结算状态 [10=未结算,20=已结算] */
	private String settleStatus;
	/** 是否结算中 */
	private Boolean isSettling;
	/** 是否采集过结果数据 */
	private Boolean isResulted;
	/** 是否取消比赛 */
	private Boolean isCancel;
	/** 外部:gnum_h */
	private Integer gnumH;
	/** 外部:gnum_c */
	private Integer gnumC;
	/** 外部:strong['H','C'] */
	private String strong;
	/** 外部:包含滚球否 */
	private Boolean hasRunningBall;

	/**
	 * bet表数据
	 */
	/** 会员ID */
	private Integer sysUserId;
	/** 玩家账号 */
	private String username;
	/** 所属上级id */
	private Integer ownerId;
	/** 所属上级用户名 */
	private String ownerUsername;
	/** 大类:['rb','today','early'] */
	private String rtype;
	/** 注单号 */
	private String betNo;
	/** 订单类别 */
	private String orderType;
	/** 下单金额(交易量) */
	private Double singleAmount;
	/** 盈亏结果金额(派彩) */
	private Double profitAmount;
	/** 有效交易量 */
	private Double effectiveAmount;
	/** 可赢额 */
	private Double canWin;
	/** 状态:[10=未确认,20=已确认(有效),30=取消 ] */
	private String status;
	/** 结果:[10=赢,20=输,30=平,40=赢半,50=输半] */
	private String result;
	/** 是否删除[1:yes,0:false] */
	private Boolean isDeleted;
	/** 注单时间 */
	private java.util.Date betTime;
	/** 确认时间 */
	private java.util.Date confirmTime;
	/** 结算时间 */
	private java.util.Date settleTime;
	/** 投注阶段 */
	private Integer phase;
	/** 结果:[10=赢,20=输,30=平,40=赢半,50=输半] */
	private String betResult;

	//endregion

	
	//region constuctors
	public VBetDetail(){
	}

	public VBetDetail(Long id){
		this.id = id;
	}
	//endregion

	@Sortable
	//region getters and setters
	public Long getId() {
		return this.id;
	}

	public void setId(Long value) {
		this.id = value;
	}
	public Long getMatchId() {
		return this.matchId;
	}

	public void setMatchId(Long value) {
		this.matchId = value;
	}
	public Long getBetId() {
		return this.betId;
	}

	public void setBetId(Long value) {
		this.betId = value;
	}
	public String getBetTeam() {
		return this.betTeam;
	}

	public void setBetTeam(String value) {
		this.betTeam = value;
	}
	public Double getPoint() {
		return this.point;
	}

	public void setPoint(Double value) {
		this.point = value;
	}
	public String getBetType() {
		return this.betType;
	}

	public void setBetType(String value) {
		this.betType = value;
	}
	public String getBetTeamType() {
		return this.betTeamType;
	}

	public void setBetTeamType(String value) {
		this.betTeamType = value;
	}
	public String getIorField() {
		return this.iorField;
	}

	public void setIorField(String value) {
		this.iorField = value;
	}
	public String getRatio() {
		return this.ratio;
	}

	public void setRatio(String value) {
		this.ratio = value;
	}
	public Integer getHostScore() {
		return this.hostScore;
	}

	public void setHostScore(Integer value) {
		this.hostScore = value;
	}
	public Integer getClientScore() {
		return this.clientScore;
	}

	public void setClientScore(Integer value) {
		this.clientScore = value;
	}
	public String getCode() {
		return this.code;
	}

	public void setCode(String value) {
		this.code = value;
	}
	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
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
    public Integer getGameRoomId() {
        return gameRoomId;
    }

    public void setGameRoomId(Integer gameRoomId) {
        this.gameRoomId = gameRoomId;
    }
    public String getGameType() {
		return this.gameType;
	}

	public void setGameType(String value) {
		this.gameType = value;
	}
	public String getBallType() {
		return this.ballType;
	}

	public void setBallType(String value) {
		this.ballType = value;
	}
	public Integer getLeagueCode() {
		return this.leagueCode;
	}

	public void setLeagueCode(Integer value) {
		this.leagueCode = value;
	}
	public Integer getHostCode() {
		return this.hostCode;
	}

	public void setHostCode(Integer value) {
		this.hostCode = value;
	}
	public Integer getClientCode() {
		return this.clientCode;
	}

	public void setClientCode(Integer value) {
		this.clientCode = value;
	}
	public java.util.Date getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(java.util.Date value) {
		this.beginTime = value;
	}
	public java.util.Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(java.util.Date value) {
		this.endTime = value;
	}
	public java.util.Date getCoverTime() {
		return this.coverTime;
	}

	public void setCoverTime(java.util.Date value) {
		this.coverTime = value;
	}
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	public java.util.Date getGatherTime() {
		return this.gatherTime;
	}

	public void setGatherTime(java.util.Date value) {
		this.gatherTime = value;
	}
	public java.util.Date getResultGatherTime() {
		return this.resultGatherTime;
	}

	public void setResultGatherTime(java.util.Date value) {
		this.resultGatherTime = value;
	}
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	public String getSettleStatus() {
		return this.settleStatus;
	}

	public void setSettleStatus(String value) {
		this.settleStatus = value;
	}
	public Boolean getIsSettling() {
		return this.isSettling;
	}

	public void setIsSettling(Boolean value) {
		this.isSettling = value;
	}
	public Boolean getIsResulted() {
		return this.isResulted;
	}

	public void setIsResulted(Boolean value) {
		this.isResulted = value;
	}
	public Boolean getIsCancel() {
		return this.isCancel;
	}

	public void setIsCancel(Boolean value) {
		this.isCancel = value;
	}
	public Integer getGnumH() {
		return this.gnumH;
	}

	public void setGnumH(Integer value) {
		this.gnumH = value;
	}
	public Integer getGnumC() {
		return this.gnumC;
	}

	public void setGnumC(Integer value) {
		this.gnumC = value;
	}
	public String getStrong() {
		return this.strong;
	}

	public void setStrong(String value) {
		this.strong = value;
	}
	public Boolean getHasRunningBall() {
		return this.hasRunningBall;
	}

	public void setHasRunningBall(Boolean value) {
		this.hasRunningBall = value;
	}
	public Integer getSysUserId() {
		return this.sysUserId;
	}

	public void setSysUserId(Integer value) {
		this.sysUserId = value;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public String getRtype() {
		return this.rtype;
	}

	public void setRtype(String value) {
		this.rtype = value;
	}
	public String getBetNo() {
		return this.betNo;
	}

	public void setBetNo(String value) {
		this.betNo = value;
	}
	public String getOrderType() {
		return this.orderType;
	}

	public void setOrderType(String value) {
		this.orderType = value;
	}
	public Double getSingleAmount() {
		return this.singleAmount;
	}

	public void setSingleAmount(Double value) {
		this.singleAmount = value;
	}
	public Double getProfitAmount() {
		return this.profitAmount;
	}

	public void setProfitAmount(Double value) {
		this.profitAmount = value;
	}
	public Double getEffectiveAmount() {
		return this.effectiveAmount;
	}

	public void setEffectiveAmount(Double value) {
		this.effectiveAmount = value;
	}
	public Double getCanWin() {
		return this.canWin;
	}

	public void setCanWin(Double value) {
		this.canWin = value;
	}
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String value) {
		this.status = value;
	}
	public String getResult() {
		return this.result;
	}

	public void setResult(String value) {
		this.result = value;
	}
	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Boolean value) {
		this.isDeleted = value;
	}
	public java.util.Date getBetTime() {
		return this.betTime;
	}

	public void setBetTime(java.util.Date value) {
		this.betTime = value;
	}
	public java.util.Date getConfirmTime() {
		return this.confirmTime;
	}

	public void setConfirmTime(java.util.Date value) {
		this.confirmTime = value;
	}
	public java.util.Date getSettleTime() {
		return this.settleTime;
	}

	public void setSettleTime(java.util.Date value) {
		this.settleTime = value;
	}
	public Integer getPhase() {
		return this.phase;
	}

	public void setPhase(Integer value) {
		this.phase = value;
	}
	public String getBetResult() {
		return this.betResult;
	}

	public void setBetResult(String value) {
		this.betResult = value;
	}
	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}
	public String getOwnerUsername() {
		return ownerUsername;
	}

	public void setOwnerUsername(String ownerUsername) {
		this.ownerUsername = ownerUsername;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}