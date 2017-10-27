package g.model.common.userbankcard.po;


import org.soul.commons.bean.IEntity;
import org.soul.commons.support.Nonpersistent;


/**
 * 用户银行卡表实体
 *
 * @author eagle
 * @tableAuthor simon
 * @time 2016-2-25 10:31:56
 */
//region your codes 1
public class UserBankcard implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -2587548984041152444L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_USER_ID = "userId";
	public static final String PROP_BANKCARD_MASTER_NAME = "bankcardMasterName";
	public static final String PROP_BANKCARD_NUMBER = "bankcardNumber";
	public static final String PROP_CREATE_TIME = "createTime";
	public static final String PROP_USE_COUNT = "useCount";
	public static final String PROP_USE_STAUTS = "useStauts";
	public static final String PROP_IS_DEFAULT = "isDefault";
	public static final String PROP_BANK_NAME = "bankName";
	public static final String PROP_BANK_DEPOSIT = "bankDeposit";
	public static final String PROP_CUSTOM_BANK_NAME = "customBankName";
	public static final String PROP_BANK_TYPE = "bankType";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Integer id;
	/** 外键：sys_user表主键 */
	private Integer userId;
	/** 卡主名 */
	private String bankcardMasterName;
	/** 银行卡号 */
	private String bankcardNumber;
	/** 创建时间 */
	private java.util.Date createTime;
	/** 使用次数 */
	private Integer useCount;
	/** 使用状态 0:未使用 1：使用过 */
	private Boolean useStauts;
	/** 0非默认 1：默认 */
	private Boolean isDefault;
	/** 银行卡名称 */
	private String bankName;
	/** 开户行 */
	private String bankDeposit;
	/** 没有匹配到银行自行输入的银行信息 */
	private String customBankName;
	/** 帐号类型 (wechat:微信,alipay:支付宝,bank:银行卡) */
	private String bankType;
	//endregion

	
	//region constuctors
	public UserBankcard(){
	}

	public UserBankcard(Integer id){
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
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer value) {
		this.userId = value;
	}
	public String getBankcardMasterName() {
		return this.bankcardMasterName;
	}

	public void setBankcardMasterName(String value) {
		this.bankcardMasterName = value;
	}
	public String getBankcardNumber() {
		return this.bankcardNumber;
	}

	public void setBankcardNumber(String value) {
		this.bankcardNumber = value;
	}
	@org.soul.model.common.Sortable
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	public Integer getUseCount() {
		return this.useCount;
	}

	public void setUseCount(Integer value) {
		this.useCount = value;
	}
	public Boolean getUseStauts() {
		return this.useStauts;
	}

	public void setUseStauts(Boolean value) {
		this.useStauts = value;
	}
	@org.soul.model.common.Sortable
	public Boolean getIsDefault() {
		return this.isDefault;
	}

	public void setIsDefault(Boolean value) {
		this.isDefault = value;
	}
	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String value) {
		this.bankName = value;
	}
	public String getBankDeposit() {
		return this.bankDeposit;
	}

	public void setBankDeposit(String value) {
		this.bankDeposit = value;
	}
	public String getCustomBankName() {
		return this.customBankName;
	}

	public void setCustomBankName(String value) {
		this.customBankName = value;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	//endregion

	//region your codes 2
	private String bankTypeDesc;
	private String bankNameDesc;

	@Nonpersistent
	public String getBankTypeDesc() {
		return bankTypeDesc;
	}

	public void setBankTypeDesc(String bankTypeDesc) {
		this.bankTypeDesc = bankTypeDesc;
	}

	@Nonpersistent
	public String getBankNameDesc() {
		return bankNameDesc;
	}

	public void setBankNameDesc(String bankNameDesc) {
		this.bankNameDesc = bankNameDesc;
	}

	//endregion your codes 2

}