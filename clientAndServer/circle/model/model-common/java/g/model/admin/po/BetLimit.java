package g.model.admin.po;

import org.soul.commons.bean.IEntity;
import org.soul.model.common.Sortable;


/**
 * 投注限额实体
 *
 * @author tom
 * @tableAuthor Tom
 * @time 2016-4-20 11:35:40
 */
//region your codes 1
public class BetLimit implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 8559664902361187528L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_BET_TYPE = "betType";
	public static final String PROP_RTYPE = "rtype";
	public static final String PROP_BET_MIN = "betMin";
	public static final String PROP_BET_MAX = "betMax";
	public static final String PROP_ITEM_MAX = "itemMax";
	public static final String PROP_COLUMN_GROUP_CODE = "columnGroupCode";
	public static final String PROP_SCHEMA_CODE = "schemaCode";
	public static final String PROP_ORDER_NUM = "orderNum";
	public static final String PROP_CREATE_USER = "createUser";
	public static final String PROP_CREATE_TIME = "createTime";
	public static final String PROP_UPDATE_USER = "updateUser";
	public static final String PROP_UPDATE_TIME = "updateTime";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Integer id;
	/** 玩法 */
	private String betType;
	/** 是否滚球 */
	private String rtype;
	/** 单注投注最低 */
	private Integer betMin;
	/** 单注投注最高 */
	private Integer betMax;
	/** 单项限额 */
	private Integer itemMax;
	/** 列分组 */
	private String columnGroupCode;
	/** 方案分组 */
	private String schemaCode;
	/** 排序 */
	private Integer orderNum;
	/** 创建用户 */
	private Integer createUser;
	/** 创建时间 */
	private java.util.Date createTime;
	/** 更新用户 */
	private Integer updateUser;
	/** 更新时间 */
	private java.util.Date updateTime;
	//endregion

	
	//region constuctors
	public BetLimit(){
	}

	public BetLimit(Integer id){
		this.id = id;
	}
	//endregion


	//region getters and setters
	@Sortable
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer value) {
		this.id = value;
	}
	public String getBetType() {
		return this.betType;
	}

	public void setBetType(String value) {
		this.betType = value;
	}
	public String getRtype() {
		return this.rtype;
	}

	public void setRtype(String value) {
		this.rtype = value;
	}
	public Integer getBetMin() {
		return this.betMin;
	}

	public void setBetMin(Integer value) {
		this.betMin = value;
	}
	public Integer getBetMax() {
		return this.betMax;
	}

	public void setBetMax(Integer value) {
		this.betMax = value;
	}
	public Integer getItemMax() {
		return this.itemMax;
	}

	public void setItemMax(Integer value) {
		this.itemMax = value;
	}
	public String getColumnGroupCode() {
		return this.columnGroupCode;
	}

	public void setColumnGroupCode(String value) {
		this.columnGroupCode = value;
	}
	public String getSchemaCode() {
		return this.schemaCode;
	}

	public void setSchemaCode(String value) {
		this.schemaCode = value;
	}
	@Sortable
	public Integer getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(Integer value) {
		this.orderNum = value;
	}
	public Integer getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(Integer value) {
		this.createUser = value;
	}
	@Sortable
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	public Integer getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(Integer value) {
		this.updateUser = value;
	}
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}