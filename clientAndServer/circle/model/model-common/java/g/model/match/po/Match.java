package g.model.match.po;

import org.soul.commons.support.Nonpersistent;
import java.util.Date;
import java.util.List;

import org.soul.commons.bean.IEntity;
import org.soul.model.common.Sortable;


/**
 * 比赛表实体
 *
 * @author lenovo
 * @time 2016-9-30 16:51:10
 */
//region your codes 1
public class Match implements IEntity<Long> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 533695413005600923L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_GAME_ID = "gameId";
	public static final String PROP_GAME_MODEL_ID = "gameModelId";
	public static final String PROP_GAME_ROOM_ID = "gameRoomId";
	public static final String PROP_IS_DEFAULT_DEALER = "isDefaultDealer";
	public static final String PROP_DEALER_USER_ID = "dealerUserId";
	public static final String PROP_RESULT = "result";
	public static final String PROP_CODE = "code";
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
	public static final String PROP_SETTLE_TYPE = "settleType";
	public static final String PROP_SETTLE_STATUS = "settleStatus";
	public static final String PROP_IS_SETTLING = "isSettling";
	public static final String PROP_IS_RESULTED = "isResulted";
	public static final String PROP_IS_CANCEL = "isCancel";
	public static final String PROP_REST_TIME = "restTime";
	public static final String PROP_BET_TIME = "betTime";
	public static final String PROP_RESULT_TIME = "resultTime";
	public static final String PROP_MATCHRESULT= "matchResult";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Long id;
	/** 游戏ID */
	private Integer gameId;
	/** 游戏玩法id */
	private Integer gameModelId;
	/** 游戏房间ID  */
	private Integer gameRoomId;
	/** 庄家ID */
	private Integer dealerUserId;
	/**  */
	private String result;
	/** 代码 */
	private String code;
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
	/** 结算类型 [10=手动,20=自动] */
	private String settleType;
	/** 结算状态 [10=未结算,20=已结算] */
	private String settleStatus;
	/** 是否结算中 */
	private Boolean isSettling;
	/** 是否采集过结果数据 */
	private Boolean isResulted;
	/** 是否取消比赛 */
	private Boolean isCancel;
	//endregion

	
	//region constuctors
	public Match(){
	}

	public Match(Long id){
		this.id = id;
	}
	//endregion


	//region getters and setters
	public Long getId() {
		return this.id;
	}

	public void setId(Long value) {
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
	public Integer getGameRoomId() {
		return this.gameRoomId;
	}

	public void setGameRoomId(Integer value) {
		this.gameRoomId = value;
	}

	public Integer getDealerUserId() {
		return this.dealerUserId;
	}

	public void setDealerUserId(Integer value) {
		this.dealerUserId = value;
	}
	public String getResult() {
		return this.result;
	}

	public void setResult(String value) {
		this.result = value;
	}
	public String getCode() {
		return this.code;
	}

	public void setCode(String value) {
		this.code = value;
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
	public String getSettleType() {
		return this.settleType;
	}

	public void setSettleType(String value) {
		this.settleType = value;
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

	/** 子表  */
	private List<MatchResult> matchResult;

	@Nonpersistent
	public List<MatchResult> getMatchResult() {
		return matchResult;
	}

	public void setMatchResult(List<MatchResult> matchResult) {
		this.matchResult = matchResult;
	}

	//endregion

	//region your codes 2

	/**
	 * 是否在进行中
	 * @return
	 */
	@Nonpersistent
	public Boolean getIsRunning(){
		if (endTime == null) {
			return false;
		}
		long now = new Date().getTime();
		if (beginTime.getTime() <= now && endTime.getTime() > now){
			return true;
		}
		return false;
	}

	//下一局的开始时间
	private Date beginTimeNext;

	/**
	 * 设置下一局的开始时间
	 * @param beginTimeNext
	 */
	public void setBeginTimeNext(Date beginTimeNext) {
		this.beginTimeNext = beginTimeNext;
	}

	/**
	 * 获取下一局的开始时间
	 * @return
	 */
	@Nonpersistent
	public Date getBeginTimeNext() {
		return beginTimeNext;
	}

    //endregion your codes 2

}