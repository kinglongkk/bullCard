package g.model.warning.po;

import org.soul.commons.bean.IEntity;
import org.soul.model.common.Sortable;


/**
 * 实体
 *
 * @author lenovo
 * @time 2017-2-28 11:03:58
 */
//region your codes 1
public class VWarningPlayerDetail implements IEntity<Long> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -6164157438832230046L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_SYS_USER_ID = "sysUserId";
	public static final String PROP_MATCH_ID = "matchId";
	public static final String PROP_BET_TIME = "betTime";
	public static final String PROP_USERNAME = "username";
	public static final String PROP_GAMENAME = "gamename";
	public static final String PROP_GAMEMODELNAME = "gamemodelname";
	public static final String PROP_GAMEROOMNAME = "gameroomname";
	public static final String PROP_BET_NO = "betNo";
	public static final String PROP_SINGLE_AMOUNT = "singleAmount";
	public static final String PROP_PROFIT_AMOUNT = "profitAmount";
	public static final String PROP_EFFECTIVE_AMOUNT = "effectiveAmount";
	public static final String PROP_SETTLE_STATUS = "settleStatus";
	public static final String PROP_PUMPINGAMOUNT = "pumpingamount";
	//endregion
	
	
	//region properties
	/**  */
	private Long id;
	/**  */
	private Integer sysUserId;
	/**  */
	private Long matchId;
	/**  */
	private java.util.Date betTime;
	/**  */
	private String username;
	/**  */
	private String gamename;
	/**  */
	private String gamemodelname;
	/**  */
	private String gameroomname;
	/**  */
	private String betNo;
	/**  */
	private Double singleAmount;
	/**  */
	private Double profitAmount;
	/**  */
	private Double effectiveAmount;
	/**  */
	private String settleStatus;
   /**抽水金额**/
	private Double pumpingamount;
	//endregion

	
	//region constuctors
	public VWarningPlayerDetail(){
	}

	public VWarningPlayerDetail(Long id){
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
	public Integer getSysUserId() {
		return this.sysUserId;
	}

	public void setSysUserId(Integer value) {
		this.sysUserId = value;
	}
	public Long getMatchId() {
		return this.matchId;
	}

	public void setMatchId(Long value) {
		this.matchId = value;
	}
	public java.util.Date getBetTime() {
		return this.betTime;
	}

	public void setBetTime(java.util.Date value) {
		this.betTime = value;
	}
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String value) {
		this.username = value;
	}
	public String getGamename() {
		return this.gamename;
	}

	public void setGamename(String value) {
		this.gamename = value;
	}
	public String getGamemodelname() {
		return this.gamemodelname;
	}

	public void setGamemodelname(String value) {
		this.gamemodelname = value;
	}
	public String getGameroomname() {
		return this.gameroomname;
	}

	public void setGameroomname(String value) {
		this.gameroomname = value;
	}
	public String getBetNo() {
		return this.betNo;
	}

	public void setBetNo(String value) {
		this.betNo = value;
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
	public String getSettleStatus() {
		return this.settleStatus;
	}

	public void setSettleStatus(String value) {
		this.settleStatus = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2


	public Double getPumpingamount() {
		return pumpingamount;
	}

	public void setPumpingamount(Double pumpingamount) {
		this.pumpingamount = pumpingamount;
	}
}