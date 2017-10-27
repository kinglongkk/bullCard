package g.model.player.po;

import org.soul.commons.support.Nonpersistent;

import org.soul.commons.bean.IEntity;
import org.soul.model.common.Sortable;


/**
 * 实体
 *
 * @author LENOVO
 * @time 2017-2-22 17:04:23
 */
//region your codes 1
public class UserPlayer implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -385825073767488587L;
	public static final String PROP_COIN = "coin";
	public static final String PROP_USABLE_COIN = "usableCoin";
	public static final String PROP_SESSION_ID = "sessionId";
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_PLAYER_GROUP_ID = "playerGroupId";
	public static final String PROP_WALLET_BALANCE = "walletBalance";
	public static final String PROP_RECHARGE_COUNT = "rechargeCount";
	public static final String PROP_RECHARGE_TOTAL = "rechargeTotal";
	public static final String PROP_RECHARGE_MAX_AMOUNT = "rechargeMaxAmount";
	public static final String PROP_WITHDRAW_COUNT = "withdrawCount";
	public static final String PROP_WITHDRAW_TOTAL = "withdrawTotal";
	public static final String PROP_FREEZING_FUNDS_BALANCE = "freezingFundsBalance";
	public static final String PROP_INVITATION_CODE = "invitationCode";
	public static final String PROP_IS_AI = "isAi";
	//endregion
	
	
	//region properties
	/** 主键，跟sys_user的ID一样 */
	private Integer id;
	/** 玩家分组ID */
	private Integer playerGroupId;
	/** 游戏余额 */
	private Double walletBalance;
	/** 累计充值次数 */
	private Integer rechargeCount;
	/** 累计充值总额 */
	private Double rechargeTotal;
	/** 最大充值金额 */
	private Double rechargeMaxAmount;
	/** 累计提现次数 */
	private Integer withdrawCount;
	/** 累计提现总额 */
	private Double withdrawTotal;
	/** 冻结资金余额(玩家预转账之后未确认转账的资金) */
	private Double freezingFundsBalance;
	/** 邀请码 */
	private String invitationCode;
	/** 是否是AI用户 */
	private Boolean isAi;
	//endregion

	
	//region constuctors
	public UserPlayer(){
	}

	public UserPlayer(Integer id){
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
	public Integer getPlayerGroupId() {
		return this.playerGroupId;
	}

	public void setPlayerGroupId(Integer value) {
		this.playerGroupId = value;
	}
	public Double getWalletBalance() {
		return this.walletBalance;
	}

	public void setWalletBalance(Double value) {
		this.walletBalance = value;
	}
	public Integer getRechargeCount() {
		return this.rechargeCount;
	}

	public void setRechargeCount(Integer value) {
		this.rechargeCount = value;
	}
	public Double getRechargeTotal() {
		return this.rechargeTotal;
	}

	public void setRechargeTotal(Double value) {
		this.rechargeTotal = value;
	}
	public Double getRechargeMaxAmount() {
		return this.rechargeMaxAmount;
	}

	public void setRechargeMaxAmount(Double value) {
		this.rechargeMaxAmount = value;
	}
	public Integer getWithdrawCount() {
		return this.withdrawCount;
	}

	public void setWithdrawCount(Integer value) {
		this.withdrawCount = value;
	}
	public Double getWithdrawTotal() {
		return this.withdrawTotal;
	}

	public void setWithdrawTotal(Double value) {
		this.withdrawTotal = value;
	}
	public Double getFreezingFundsBalance() {
		return this.freezingFundsBalance;
	}

	public void setFreezingFundsBalance(Double value) {
		this.freezingFundsBalance = value;
	}
	public String getInvitationCode() {
		return this.invitationCode;
	}

	public void setInvitationCode(String value) {
		this.invitationCode = value;
	}
	public Boolean getIsAi() {
		return this.isAi;
	}

	public void setIsAi(Boolean value) {
		this.isAi = value;
	}
	//endregion

	//region your codes 2


	/** 账号 */
	private String username;
	/** 头像地址 */
	private String avatarUrl;

    private String nickname;

    @Nonpersistent
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

	@Nonpersistent
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Nonpersistent
	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	private Long coin;

	/** 获取用户金币 */
	@Nonpersistent
	public Long getCoin(){
		Double balance = getWalletBalance();
		if (coin == null) {
			return balance.longValue();
		}
		return coin;
	}

	public void setCoin(Long coin){
		this.coin = coin;
	}

	private String sessionId;

	@Nonpersistent
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	//endregion your codes 2

}