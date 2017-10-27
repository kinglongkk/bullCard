package g.model.match.po;

import org.soul.commons.support.Nonpersistent;
import java.util.Date;

import org.soul.commons.bean.IEntity;
import org.soul.model.common.Sortable;


/**
 * 实体
 *
 * @author longer
 * @time Jul 7, 2016 8:40:51 PM
 */
//region your codes 1
public class VMatchResult implements IEntity<Long> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -3796527974471087250L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_GAME_MODEL_ID = "gameModelId";
	public static final String PROP_GAME_ROOM_ID = "gameRoomId";
	public static final String PROP_CODE = "code";
	public static final String PROP_BALL_TYPE = "ballType";
	public static final String PROP_LEAGUE_CODE = "leagueCode";
	public static final String PROP_HOST_CODE = "hostCode";
	public static final String PROP_CLIENT_CODE = "clientCode";
	public static final String PROP_BEGIN_TIME = "beginTime";
	public static final String PROP_END_TIME = "endTime";
	public static final String PROP_COVER_TIME = "coverTime";
	public static final String PROP_CREATE_TIME = "createTime";
	public static final String PROP_GATHER_TIME = "gatherTime";
	public static final String PROP_UPDATE_TIME = "updateTime";
	public static final String PROP_IS_SETTLING = "isSettling";
	public static final String PROP_SETTLE_TYPE = "settleType";
	public static final String PROP_SETTLE_STATUS = "settleStatus";
	public static final String PROP_IS_RESULTED = "isResulted";
	public static final String PROP_GAME_TYPE = "gameType";
	public static final String PROP_RESULT_GATHER_TIME = "resultGatherTime";
	public static final String PROP_IS_CANCEL = "isCancel";
	public static final String PROP_RESULT_ID = "resultId";
	public static final String PROP_ITEM_TYPE = "itemType";
	public static final String PROP_OUTCOME = "outcome";
	public static final String PRROP_CONSEQUENCE = "consequence";
	public static final String PROP_RESULT = "result";
	public static final String PROP_TYPE = "type";
	public static final String PROP_HOST = "host";
	public static final String PROP_CLIENT = "client";
	public static final String PROP_PROVIDER = "provider";
	public static final String PROP_MANUAL_TIME = "manualTime";
	public static final String PROP_MANUAL_USER_ID = "manualUserId";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Long id;
	/** 游戏类型id */
	private Integer gameModelId;
	/** 游戏房间id */
	private Integer gameRoomId;
	/** 代码 */
	private String code;
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
	/** 更新时间 */
	private java.util.Date updateTime;
	/** 是否结算中 */
	private Boolean isSettling;
	/** 结算类型 [10=手动,20=自动] */
	private String settleType;
	/** 结算状态 [10=未结算,20=已结算] */
	private String settleStatus;
	/** 是否采集过结果数据 */
	private Boolean isResulted;
	/** 游戏类型:[SPORT=体育...] */
	private String gameType;
	/** 结果采集时间 */
	private java.util.Date resultGatherTime;
	/** 是否取消比赛 */
	private Boolean isCancel;
	/** 结果id */
	private Long resultId;
	/** 结果类型 */
	private String itemType;
	/** 与庄家相比输赢 */
	private String outcome;
	/** 牌所在的位置 */
	private String consequence;
	/** 采集数据 */
	private String result;
	/** 采集数据 */
	private String type;
	/** 采集数据 */
	private String host;
	/** 采集数据 */
	private String client;
	/** 数据提供方式:1:手工,2:采集 */
	private String provider;
	/** 手工录入时间 */
	private java.util.Date manualTime;
	/** 手工录入用户id */
	private Integer manualUserId;
	//endregion

	
	//region constuctors
	public VMatchResult(){
	}

	public VMatchResult(Long id){
		this.id = id;
	}
	//endregion


	//region getters and setters
	@org.soul.model.common.Sortable
	public Long getId() {
		return this.id;
	}

	public void setId(Long value) {
		this.id = value;
	}
	@org.soul.model.common.Sortable
	public String getCode() {
		return this.code;
	}

	public void setCode(String value) {
		this.code = value;
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
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	public Boolean getIsSettling() {
		return this.isSettling;
	}

	public void setIsSettling(Boolean value) {
		this.isSettling = value;
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
	public Boolean getIsResulted() {
		return this.isResulted;
	}

	public void setIsResulted(Boolean value) {
		this.isResulted = value;
	}
	public String getGameType() {
		return this.gameType;
	}

	public void setGameType(String value) {
		this.gameType = value;
	}
	public java.util.Date getResultGatherTime() {
		return this.resultGatherTime;
	}

	public void setResultGatherTime(java.util.Date value) {
		this.resultGatherTime = value;
	}
	public Boolean getIsCancel() {
		return this.isCancel;
	}

	public void setIsCancel(Boolean value) {
		this.isCancel = value;
	}
	public Long getResultId() {
		return this.resultId;
	}

	public void setResultId(Long value) {
		this.resultId = value;
	}
	public String getItemType() {
		return this.itemType;
	}

	public void setItemType(String value) {
		this.itemType = value;
	}
	public String getResult() {
		return this.result;
	}

	public void setResult(String value) {
		this.result = value;
	}
	public String getType() {
		return this.type;
	}

	public void setType(String value) {
		this.type = value;
	}
	public String getHost() {
		return this.host;
	}

	public void setHost(String value) {
		this.host = value;
	}
	public String getClient() {
		return this.client;
	}

	public void setClient(String value) {
		this.client = value;
	}
	public String getProvider() {
		return this.provider;
	}

	public void setProvider(String value) {
		this.provider = value;
	}
	public java.util.Date getManualTime() {
		return this.manualTime;
	}

	public void setManualTime(java.util.Date value) {
		this.manualTime = value;
	}
	public Integer getManualUserId() {
		return this.manualUserId;
	}

	public void setManualUserId(Integer value) {
		this.manualUserId = value;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getGameModelId() {
		return gameModelId;
	}

	public void setGameModelId(Integer gameModelId) {
		this.gameModelId = gameModelId;
	}

	public Integer getGameRoomId() {
		return gameRoomId;
	}

	public void setGameRoomId(Integer gameRoomId) {
		this.gameRoomId = gameRoomId;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public String getConsequence() {
		return consequence;
	}

	public void setConsequence(String consequence) {
		this.consequence = consequence;
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

	//endregion your codes 2

}