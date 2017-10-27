package g.model.payaccount.po;

import org.soul.commons.bean.IEntity;
import org.soul.model.common.Sortable;


/**
 * 银行表实体
 *
 * @author mark
 * @tableAuthor simon
 * @time 2016-7-13 19:21:26
 */
//region your codes 1
public class Bank implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -3352395105681181214L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_BANK_NAME = "bankName";
	public static final String PROP_BANK_ICON = "bankIcon";
	public static final String PROP_BANK_DISTRICT = "bankDistrict";
	public static final String PROP_TYPE = "type";
	public static final String PROP_BANK_SHORT_NAME = "bankShortName";
	public static final String PROP_BANK_ICON_SIMPLIFY = "bankIconSimplify";
	public static final String PROP_BANK_SHORT_NAME2 = "bankShortName2";
	public static final String PROP_IS_USE = "isUse";
	public static final String PROP_ORDER_NUM = "orderNum";
	public static final String PROP_PAY_TYPE = "payType";
	public static final String PROP_WEBSITE = "website";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Integer id;
	/** 字典code */
	private String bankName;
	/** 完整logo */
	private String bankIcon;
	/** 银行所属区域 */
	private String bankDistrict;
	/** 类型（1银行,2第三方,3在线支付） */
	private String type;
	/** 支付接口一级简称 */
	private String bankShortName;
	/** 支付接口图标简图 */
	private String bankIconSimplify;
	/** 支付接口二级简称 */
	private String bankShortName2;
	/** 是否启用 */
	private Boolean isUse;
	/** 排序 */
	private Integer orderNum;
	/** 线上支付类型1:银行直连，2微信支付，3支付宝支付 */
	private String payType;
	/** 网上银行官网 */
	private String website;
	//endregion

	
	//region constuctors
	public Bank(){
	}

	public Bank(Integer id){
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
	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String value) {
		this.bankName = value;
	}
	public String getBankIcon() {
		return this.bankIcon;
	}

	public void setBankIcon(String value) {
		this.bankIcon = value;
	}
	public String getBankDistrict() {
		return this.bankDistrict;
	}

	public void setBankDistrict(String value) {
		this.bankDistrict = value;
	}
	public String getType() {
		return this.type;
	}

	public void setType(String value) {
		this.type = value;
	}
	public String getBankShortName() {
		return this.bankShortName;
	}

	public void setBankShortName(String value) {
		this.bankShortName = value;
	}
	public String getBankIconSimplify() {
		return this.bankIconSimplify;
	}

	public void setBankIconSimplify(String value) {
		this.bankIconSimplify = value;
	}
	public String getBankShortName2() {
		return this.bankShortName2;
	}

	public void setBankShortName2(String value) {
		this.bankShortName2 = value;
	}
	public Boolean getIsUse() {
		return this.isUse;
	}

	public void setIsUse(Boolean value) {
		this.isUse = value;
	}

	@Sortable
	public Integer getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(Integer value) {
		this.orderNum = value;
	}
	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String value) {
		this.payType = value;
	}
	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String value) {
		this.website = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}