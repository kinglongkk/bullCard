package g.model.bet.po;

import org.soul.commons.bean.IEntity;
import org.soul.commons.support.Nonpersistent;

import java.util.Date;
import java.util.List;


/**
 * 注单实体
 *
 * @author longer
 * @time Apr 26, 2016 4:56:53 PM
 */
//region your codes 1
public class Bet implements IEntity<Long>,IBetSettle {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 7976017605760713655L;
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
	public static final String PROP_RESULT = "result";
	public static final String PROP_IS_DELETED = "isDeleted";
	public static final String PROP_STRONG = "strong";
	public static final String PROP_BET_TIME = "betTime";
	public static final String PROP_CONFIRM_TIME = "confirmTime";
	public static final String PROP_SETTLE_TIME = "settleTime";
	public static final String PROP_UPDATE_USER = "updateUser";
	public static final String PROP_UPDATE_TIME = "updateTime";
	public static final String PROP_DELETE_USER = "deleteUser";
	public static final String PROP_DELETE_TIME = "deleteTime";
	public static final String PROP_BALL_TYPE = "ballType";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Long id;
	/** 会员ID */
	private Integer sysUserId;
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
	/** 状态:[10=未结算,20=已结算,30=已重结] */
	private String settleStatus;
	/** 结果:[10=赢,20=输,30=平,40=赢半,50=输半] */
	private String result;
	/** 是否删除[1:yes,0:false] */
	private Boolean isDeleted;
	/** 强弱 */
	private String strong;
	/** 注单时间 */
	private java.util.Date betTime;
	/** 确认时间 */
	private java.util.Date confirmTime;
	/** 结算时间 */
	private java.util.Date settleTime;
	/** 变更者 */
	private Integer updateUser;
	/** 变更时间 */
	private java.util.Date updateTime;
	/** 删除者 */
	private Integer deleteUser;
	/** 删除时间 */
	private java.util.Date deleteTime;
	/** 游戏类型代码:[FT=足球] */
	private String ballType;
	/** 投注阶段 */
	private Integer phase;
	/** 抽水金额 */
	private Long waterAmount = 0L;

	/**
	 * 注单详情
	 */
	private List<BetDetail> betDetailList;
	//endregion

	//region constuctors
	public Bet(){
	}

	public Bet(Long id){
		this.id = id;
	}
	//endregion


	//region getters and setters

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(Integer sysUserId) {
		this.sysUserId = sysUserId;
	}

	public String getRtype() {
		return rtype;
	}

	public void setRtype(String rtype) {
		this.rtype = rtype;
	}

	public String getBetNo() {
		return betNo;
	}

	public void setBetNo(String betNo) {
		this.betNo = betNo;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Double getSingleAmount() {
		return singleAmount;
	}

	public void setSingleAmount(Double singleAmount) {
		this.singleAmount = singleAmount;
	}

	public Double getProfitAmount() {
		return profitAmount;
	}

	public void setProfitAmount(Double profitAmount) {
		this.profitAmount = profitAmount;
	}

	public Double getEffectiveAmount() {
		return effectiveAmount;
	}

	public void setEffectiveAmount(Double effectiveAmount) {
		this.effectiveAmount = effectiveAmount;
	}

	public Double getCanWin() {
		return canWin;
	}

	public void setCanWin(Double canWin) {
		this.canWin = canWin;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSettleStatus() {
		return settleStatus;
	}

	public void setSettleStatus(String settleStatus) {
		this.settleStatus = settleStatus;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getStrong() {
		return strong;
	}

	public void setStrong(String strong) {
		this.strong = strong;
	}

	public Date getBetTime() {
		return betTime;
	}

	public void setBetTime(Date betTime) {
		this.betTime = betTime;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public Date getSettleTime() {
		return settleTime;
	}

	public void setSettleTime(Date settleTime) {
		this.settleTime = settleTime;
	}

	public Integer getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getDeleteUser() {
		return deleteUser;
	}

	public void setDeleteUser(Integer deleteUser) {
		this.deleteUser = deleteUser;
	}

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public String getBallType() {
		return ballType;
	}

	public void setBallType(String ballType) {
		this.ballType = ballType;
	}

	public Integer getPhase() {
		return phase;
	}

	public void setPhase(Integer phase) {
		this.phase = phase;
	}

	public Long getWaterAmount() {
		return waterAmount;
	}

	public void setWaterAmount(Long waterAmount) {
		this.waterAmount = waterAmount;
	}

	@Nonpersistent
	public List<BetDetail> getBetDetailList() {
		return betDetailList;
	}

	public void setBetDetailList(List<BetDetail> betDetailList) {
		this.betDetailList = betDetailList;
	}

	//endregion

	//region your codes 2
	@Nonpersistent
	public String getBetType() {
		BetDetail betDetail = betDetailList.get(0);
		return betDetail.getBetType();
	}

	@Nonpersistent
	@Override
	public Long getMatchId() {
	    BetDetail betDetail = betDetailList.get(0);
		return betDetail.getMatchId();
	}

	@Nonpersistent
	@Override
	public Long getBetId() {
		BetDetail betDetail = betDetailList.get(0);
		return betDetail.getBetId();
	}

	@Nonpersistent
	@Override
	public Double getPoint() {
		BetDetail betDetail = betDetailList.get(0);
		return betDetail.getPoint();
	}

	@Nonpersistent
	@Override
	public String getIorField() {
		BetDetail betDetail = betDetailList.get(0);
		return betDetail.getIorField();
	}

	@Nonpersistent
	@Override
	public String getRatio() {
		BetDetail betDetail = betDetailList.get(0);
		return betDetail.getRatio();
	}

	@Nonpersistent
	@Override
	public Integer getHostScore() {
		BetDetail betDetail = betDetailList.get(0);
		return betDetail.getHostScore();
	}

	@Nonpersistent
	@Override
	public Integer getClientScore() {
		BetDetail betDetail = betDetailList.get(0);
		return betDetail.getClientScore();
	}

	@Nonpersistent
	@Override
	public void setPoint(Double point) {
		BetDetail betDetail = betDetailList.get(0);
		betDetail.setPoint(point);
	}

	private Double incBalance;
	private Double incCoin;
	private Integer dealerUserId;
	private Double incBankerCoin;
	private double waterPoint;

	/** 是否要保存,ai在系统庄时投注不保存 */
	private boolean isSave = false;

	public boolean isSave() {
		return isSave;
	}

	public void setSave(boolean save) {
		isSave = save;
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

	@Nonpersistent
	public double getWaterPoint() {
		return waterPoint;
	}

	@Override
	public void setWaterPoint(double waterPoint) {
		this.waterPoint = waterPoint;
	}

	//endregion your codes 2

}