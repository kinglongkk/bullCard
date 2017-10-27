package g.model.match.po;

import org.soul.commons.bean.IEntity;
import org.soul.commons.support.Nonpersistent;

import java.util.List;


/**
 * 比赛结果表实体
 *
 * @author longer
 * @time May 17, 2016 4:38:24 PM
 */
//region your codes 1
public class MatchResult implements IEntity<Long> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 2137655773514932862L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_MATCH_ID = "matchId";
	public static final String PROP_ITEM_TYPE = "itemType";
	public static final String PROP_RESULT = "result";
	public static final String PROP_TYPE = "type";
	public static final String PROP_HOST = "host";
	public static final String PROP_CLIENT = "client";
	public static final String PROP_PROVIDER = "provider";
	public static final String PROP_CREATE_TIME = "createTime";
	public static final String PROP_MANUAL_TIME = "manualTime";
	public static final String PROP_MANUAL_USER_ID = "manualUserId";
	public static final String PROP_OUTCOME = "outcome";
	public static final String PROP_SYS_USER_ID = "sysUserId";
	public static final String PROP_CONSEQUENCE = "consequence";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Long id;
	/** match表id */
	private Long matchId;
	/** 结果类型 */
	private String itemType;
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
	/** 创建时间 */
	private java.util.Date createTime;
	/** 手工录入时间 */
	private java.util.Date manualTime;
	/** 手工录入用户id */
	private Integer manualUserId;
	/** 与庄家相比输赢情况 */
	private String outcome;
	/** 玩家id */
	private Integer sysUserId;
	/** 赢牌位置 */
	private String consequence;
	/** 庄家点数 */
	private Integer hostPoint;
	/** 玩家点数 */
	private Integer clientPoint;

	private Match match;

	private List<MatchResult> matchResult;
	//endregion

	
	//region constuctors
	public MatchResult(){
	}

	public MatchResult(Long id){
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
	public Long getMatchId() {
		return this.matchId;
	}

	public void setMatchId(Long value) {
		this.matchId = value;
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
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
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
	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}
	public Integer getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(Integer sysUserId) {
		this.sysUserId = sysUserId;
	}
	public String getConsequence() {
		return consequence;
	}

	public void setConsequence(String consequence) {
		this.consequence = consequence;
	}
	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	//endregion

	//region your codes 2
    //FIXME: Double to Jason 实体类添加字段时,需要添加到 region your codes 的区块内,且如果这些字段不在表字段内,需要添加注解(不进行持久化)
	@Nonpersistent
    public Integer getHostPoint() {
		return hostPoint;
	}

	public void setHostPoint(Integer hostPoint) {
		this.hostPoint = hostPoint;
	}

	@Nonpersistent
	public Integer getClientPoint() {
		return clientPoint;
	}

	public void setClientPoint(Integer clientPoint) {
		this.clientPoint = clientPoint;
	}


	@Nonpersistent
	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}
	@Nonpersistent
	public List<MatchResult> getMatchResult() {
		return matchResult;
	}

	public void setMatchResult(List<MatchResult> matchResult) {
		this.matchResult = matchResult;
	}
	//endregion your codes 2

}