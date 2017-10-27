package g.model.bet.po;

import g.model.match.po.Match;
import org.soul.commons.bean.IEntity;
import org.soul.commons.support.Nonpersistent;


/**
 * 投注详情实体
 *
 * @author tom
 * @time 2016-5-10 15:13:30
 */
//region your codes 1
public class BetDetail implements IEntity<Long> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 8330713398519871222L;
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
	public static final String PROP_PLAYER_RESULT = "playerResult";
	//endregion
	
	
	//region properties
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

	private Match match;
	//endregion

	
	//region constuctors
	public BetDetail(){
	}

	public BetDetail(Long id){
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

	public Long getMatchId() {
		return matchId;
	}

	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}

	public Long getBetId() {
		return betId;
	}

	public void setBetId(Long betId) {
		this.betId = betId;
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
		return betTeamType;
	}

	public void setBetTeamType(String betTeamType) {
		this.betTeamType = betTeamType;
	}

	public String getIorField() {
		return iorField;
	}

	public void setIorField(String iorField) {
		this.iorField = iorField;
	}

	public Integer getClientScore() {
		return clientScore;
	}

	public void setClientScore(Integer clientScore) {
		this.clientScore = clientScore;
	}

	public Integer getHostScore() {
		return hostScore;
	}

	public void setHostScore(Integer hostScore) {
		this.hostScore = hostScore;
	}

	public String getRatio() {
		return ratio;
	}

	public void setRatio(String ratio) {
		this.ratio = ratio;
	}


	//endregion

	//region your codes 2
	@Nonpersistent
	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}
	//endregion your codes 2

}