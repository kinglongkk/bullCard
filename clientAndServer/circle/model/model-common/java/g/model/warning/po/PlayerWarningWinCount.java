package g.model.warning.po;

import javassist.runtime.Desc;
import org.soul.commons.bean.IEntity;
import org.soul.model.common.Sortable;

import java.lang.reflect.Array;
import java.util.List;


/**
 * 说明预警异常用户赢得连续派彩次数实体
 *
 * @author lenovo
 * @time 2017-2-27 15:00:30
 */
//region your codes 1
public class PlayerWarningWinCount implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -9000387835863626421L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_PLAYER_ID = "playerId";
	public static final String PROP_DATE = "date";
	public static final String PROP_USERNAME = "username";
	public static final String PROP_NICKNAME = "nickname";
	public static final String PROP_REGISTER_DATE = "registerDate";
	public static final String PROP_WIN_AMOUNT = "winAmount";
	public static final String PROP_BET_AMOUNT = "betAmount";
	public static final String PROP_START_TIME = "startTime";
	public static final String PROP_END_TIME = "endTime";
	public static final String PROP_NUM = "num";
	public static final String PROP_MATCH_ID = "matchId";
	public static final String PROP_STATUS = "status";
	public static final String PROP_IS_DELETE = "isDelete";
	public static final String PROP_CREATE_TIME = "createTime";
	public static final String PROP_AGENT_USERNAME="agentUsername";
	public static final String PROP_AGENT_ID="agentId";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Integer id;
	/** 玩家ID */
	private Integer playerId;
	/** 日期 */
	private java.util.Date date;
	/** 用户名 */
	private String username;
	/** 昵称 */
	private String nickname;
	/** 注册日期 */
	private java.util.Date registerDate;
	/** 赢得金额 */
	private Double winAmount;
	/** 投注金额 */
	private Double betAmount;
	/** 开始时间 */
	private java.util.Date startTime;
	/** 结束时间 */
	private java.util.Date endTime;
	/** 连赢次数 */
	private Integer num;
	/** 赛事ID； */
	private Object matchId;
	/** 1:黄色2:红色； */
	private String status;
	/** 是否删除； */
	private Boolean isDelete;
	/** 创建时间； */
	private java.util.Date createTime;
	//endregion
	/**代理商用户名**/
	private String agentUsername;
	/**代理商ID**/
	private Integer agentId;

	
	//region constuctors
	public PlayerWarningWinCount(){
	}

	public PlayerWarningWinCount(Integer id){
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
	public Integer getPlayerId() {
		return this.playerId;
	}

	public void setPlayerId(Integer value) {
		this.playerId = value;
	}
	public java.util.Date getDate() {
		return this.date;
	}

	public void setDate(java.util.Date value) {
		this.date = value;
	}
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String value) {
		this.username = value;
	}
	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String value) {
		this.nickname = value;
	}
	public java.util.Date getRegisterDate() {
		return this.registerDate;
	}

	public void setRegisterDate(java.util.Date value) {
		this.registerDate = value;
	}
	public Double getWinAmount() {
		return this.winAmount;
	}

	public void setWinAmount(Double value) {
		this.winAmount = value;
	}
	public Double getBetAmount() {
		return this.betAmount;
	}

	public void setBetAmount(Double value) {
		this.betAmount = value;
	}
	@Sortable
	public java.util.Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(java.util.Date value) {
		this.startTime = value;
	}
	@Sortable
	public java.util.Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(java.util.Date value) {
		this.endTime = value;
	}
	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer value) {
		this.num = value;
	}

	public Object getMatchId() {
		return matchId;
	}

	public void setMatchId(Object matchId) {
		this.matchId = matchId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String value) {
		this.status = value;
	}
	public Boolean getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(Boolean value) {
		this.isDelete = value;
	}
	@Sortable
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2


	public Boolean getDelete() {
		return isDelete;
	}

	public void setDelete(Boolean delete) {
		isDelete = delete;
	}

	public String getAgentUsername() {
		return agentUsername;
	}

	public void setAgentUsername(String agentUsername) {
		this.agentUsername = agentUsername;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
}