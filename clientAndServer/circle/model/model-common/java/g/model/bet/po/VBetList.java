package g.model.bet.po;

import g.model.bet.BetItem;
import g.model.bet.BetTypeEnum;
import g.model.bet.IBetItem;
import org.soul.commons.bean.IEntity;
import org.soul.commons.support.Nonpersistent;


/**
 * 注单实体
 *
 * @author longer
 * @time Jul 8, 2016 5:13:20 PM
 */
//region your codes 1
public class VBetList implements IEntity<Long> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 3788251261260661115L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_RTYPE = "rtype";
	public static final String PROP_BET_NO = "betNo";
	public static final String PROP_ORDER_TYPE = "orderType";
	public static final String PROP_SINGLE_AMOUNT = "singleAmount";
	public static final String PROP_PROFIT_AMOUNT = "profitAmount";
	public static final String PROP_EFFECTIVE_AMOUNT = "effectiveAmount";
	public static final String PROP_CAN_WIN = "canWin";
	public static final String PROP_STATUS = "status";
	public static final String PROP_SETTLE_STATUS = "settleStatus";
	public static final String PROP_RESULT = "result";
	public static final String PROP_STRONG = "strong";
	public static final String PROP_BET_TIME = "betTime";
	public static final String PROP_BALL_TYPE = "ballType";
	public static final String PROP_PHASE = "phase";
	public static final String PROP_BET_TEAM = "betTeam";
	public static final String PROP_POINT = "point";
	public static final String PROP_BET_TYPE = "betType";
	public static final String PROP_BET_TEAM_TYPE = "betTeamType";
	public static final String PROP_IOR_FIELD = "iorField";
	public static final String PROP_RATIO = "ratio";
	public static final String PROP_HOST_SCORE = "hostScore";
	public static final String PROP_CLIENT_SCORE = "clientScore";
	public static final String PROP_MATCH_ID = "matchId";
	public static final String PROP_MATCH_CODE = "matchCode";
	public static final String PROP_GAME_TYPE = "gameType";
	public static final String PROP_BEGIN_TIME = "beginTime";
	public static final String PROP_LEAGUE_CODE = "leagueCode";
	public static final String PROP_HOST_CODE = "hostCode";
	public static final String PROP_CLIENT_CODE = "clientCode";
	public static final String PROP_USERNAME = "username";
	public static final String PROP_OWNER_ID = "ownerId";
	public static final String PROP_AGENT_USERNAME = "agentUsername";
	//endregion
	
	
	//region properties
	/**  */
	private Long id;
	/**  */
	private String rtype;
	/**  */
	private String betNo;
	/**  */
	private String orderType;
	/**  */
	private Double singleAmount;
	/**  */
	private Double profitAmount;
	/**  */
	private Double effectiveAmount;
	/**  */
	private Double canWin;
	/**  */
	private String status;
	/**  */
	private String settleStatus;
	/**  */
	private String result;
	/**  */
	private String strong;
	/**  */
	private java.util.Date betTime;
	/**  */
	private String ballType;
	/**  */
	private Integer phase;
	/**  */
	private String betTeam;
	/**  */
	private Double point;
	/**  */
	private String betType;
	/**  */
	private String betTeamType;
	/**  */
	private String iorField;
	/**  */
	private String ratio;
	/**  */
	private Integer hostScore;
	/**  */
	private Integer clientScore;
	/**  */
	private Long matchId;
	/**  */
	private String matchCode;
	/**  */
	private String gameType;
	/**  */
	private java.util.Date beginTime;
	/**  */
	private Integer leagueCode;
	/**  */
	private Integer hostCode;
	/**  */
	private Integer clientCode;
	/**  */
	private String username;
	/**  */
	private Integer ownerId;
	/**  */
	private String agentUsername;
	//endregion

	
	//region constuctors
	public VBetList(){
	}

	public VBetList(Long id){
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
	public String getRtype() {
		return this.rtype;
	}

	public void setRtype(String value) {
		this.rtype = value;
	}
	@org.soul.model.common.Sortable
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
	public String getSettleStatus() {
		return this.settleStatus;
	}

	public void setSettleStatus(String value) {
		this.settleStatus = value;
	}
	public String getResult() {
		return this.result;
	}

	public void setResult(String value) {
		this.result = value;
	}
	public String getStrong() {
		return this.strong;
	}

	public void setStrong(String value) {
		this.strong = value;
	}
	@org.soul.model.common.Sortable
	public java.util.Date getBetTime() {
		return this.betTime;
	}

	public void setBetTime(java.util.Date value) {
		this.betTime = value;
	}
	public String getBallType() {
		return this.ballType;
	}

	public void setBallType(String value) {
		this.ballType = value;
	}
	public Integer getPhase() {
		return this.phase;
	}

	public void setPhase(Integer value) {
		this.phase = value;
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
	public Long getMatchId() {
		return this.matchId;
	}

	public void setMatchId(Long value) {
		this.matchId = value;
	}
	public String getMatchCode() {
		return this.matchCode;
	}

	public void setMatchCode(String value) {
		this.matchCode = value;
	}
	public String getGameType() {
		return this.gameType;
	}

	public void setGameType(String value) {
		this.gameType = value;
	}
	public java.util.Date getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(java.util.Date value) {
		this.beginTime = value;
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
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String value) {
		this.username = value;
	}
	public Integer getOwnerId() {
		return this.ownerId;
	}

	public void setOwnerId(Integer value) {
		this.ownerId = value;
	}

	public String getAgentUsername() {
		return agentUsername;
	}

	public void setAgentUsername(String agentUsername) {
		this.agentUsername = agentUsername;
	}

	//endregion

	//region your codes 2
	private String desc;
	@Nonpersistent
	public String getDesc() {
		IBetItem betItem = BetItem.fromString(BetTypeEnum.enumOf(betType),this.iorField);
		if (betItem != null){
			return betItem.toDesc();
		}
		return "";
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	//endregion your codes 2

}