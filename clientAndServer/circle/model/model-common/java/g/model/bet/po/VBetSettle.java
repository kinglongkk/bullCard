package g.model.bet.po;

import org.soul.commons.bean.IEntity;
import org.soul.commons.support.Nonpersistent;
import org.soul.model.common.Sortable;


/**
 * 注单结算实体
 *
 * @author longer
 * @tableAuthor Longer
 * @time May 20, 2016 2:57:03 PM
 */
//region your codes 1
public class VBetSettle implements IEntity<Long>,IBetSettle {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 4491961832969462459L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_SYS_USER_ID = "sysUserId";
	public static final String PROP_RTYPE = "rtype";
	public static final String PROP_BET_NO = "betNo";
	public static final String PROP_ORDER_TYPE = "orderType";
	public static final String PROP_SINGLE_AMOUNT = "singleAmount";
	public static final String PROP_PROFIT_AMOUNT = "profitAmount";
	public static final String PROP_EFFECTIVE_AMOUNT = "effectiveAmount";
	public static final String PROP_CAN_WIN = "canWin";
	public static final String PROP_STATUS = "status";
	public static final String PROP_SETTLE_STATUS = "settleStatus";
	public static final String PROP_BET_TIME = "betTime";
	public static final String PROP_CONFIRM_TIME = "confirmTime";
	public static final String PROP_BET_DETAIL_ID = "betDetailId";
	public static final String PROP_MATCH_ID = "matchId";
	public static final String PROP_BET_ID = "betId";
	public static final String PROP_BALL_TYPE = "ballType";
	public static final String PROP_BET_TEAM = "betTeam";
	public static final String PROP_POINT = "point";
	public static final String PROP_BET_TYPE = "betType";
	public static final String PROP_BET_TEAM_TYPE = "betTeamType";
	public static final String PROP_IOR_FIELD = "iorField";
	public static final String PROP_RATIO = "ratio";
	public static final String PROP_HOST_SCORE = "hostScore";
	public static final String PROP_CLIENT_SCORE = "clientScore";
	public static final String PROP_HOST_CODE = "hostCode";
	public static final String PROP_CLIENT_CODE = "clientCode";
	public static final String PROP_STRONG = "strong";
	//endregion


	//region properties
	/**  */
	private Long id;
	/**  */
	private Integer sysUserId;
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
	private java.util.Date betTime;
	/**  */
	private java.util.Date confirmTime;
	/**  */
	private Long betDetailId;
	/**  */
	private Long matchId;
	/**  */
	private Long betId;
	/**  */
	private String ballType;
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
	private Integer hostCode;
	/**  */
	private Integer clientCode;
	/**  */
	private String strong;
	//endregion


	//region constuctors
	public VBetSettle(){
	}

	public VBetSettle(Long id){
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
	public Integer getSysUserId() {
		return this.sysUserId;
	}

	public void setSysUserId(Integer value) {
		this.sysUserId = value;
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
	public String getSettleStatus() {
		return this.settleStatus;
	}

	public void setSettleStatus(String value) {
		this.settleStatus = value;
	}
	public java.util.Date getBetTime() {
		return this.betTime;
	}

	public void setBetTime(java.util.Date value) {
		this.betTime = value;
	}
	@org.soul.model.common.Sortable
	public java.util.Date getConfirmTime() {
		return this.confirmTime;
	}

	public void setConfirmTime(java.util.Date value) {
		this.confirmTime = value;
	}
	@Sortable
	public Long getBetDetailId() {
		return this.betDetailId;
	}

	public void setBetDetailId(Long value) {
		this.betDetailId = value;
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
	public String getBallType() {
		return this.ballType;
	}

	public void setBallType(String value) {
		this.ballType = value;
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
	public String getStrong() {
		return this.strong;
	}

	public void setStrong(String value) {
		this.strong = value;
	}

	public Integer getHostScore() {
		return hostScore;
	}

	public void setHostScore(Integer hostScore) {
		this.hostScore = hostScore;
	}

	public Integer getClientScore() {
		return clientScore;
	}

	public void setClientScore(Integer clientScore) {
		this.clientScore = clientScore;
	}
	//endregion

	//region your codes 2
	private Integer count;
	private Double incBalance;
	private Double incCoin;
	private Integer dealerUserId;
	private Double incBankerCoin;
	private Double waterPoint;
	private String result;


	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Nonpersistent
	public Double getIncBalance() {
		return incBalance;
	}

	@Override
	public void setIncBalance(Double incBalance) {
		this.incBalance = incBalance;
	}

	@Nonpersistent
	public Double getIncCoin() {
		return incCoin;
	}

	@Override
	public void setIncCoin(Double incCoin) {
		this.incCoin = incCoin;
	}

	@Nonpersistent
	public Integer getDealerUserId() {
		return dealerUserId;
	}

	@Override
	public void setDealerUserId(Integer dealerUserId) {
		this.dealerUserId = dealerUserId;
	}

	@Nonpersistent
	public Double getIncBankerCoin() {
		return incBankerCoin;
	}

	@Override
	public void setIncBankerCoin(Double incBankerCoin) {
		this.incBankerCoin = incBankerCoin;
	}

	@Override
	public Long getWaterAmount() {
		return null;
	}

	@Override
	public void setWaterAmount(Long waterAmount) {

	}

	@Nonpersistent
	public double getWaterPoint() {
		return waterPoint;
	}

	@Override
	public void setWaterPoint(double waterPoint) {
	    this.waterPoint = waterPoint;
	}

	@Nonpersistent
	public String getResult() {
		return result;
	}

	@Override
	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public boolean isSave() {
		return false;
	}
	//endregion your codes 2

}