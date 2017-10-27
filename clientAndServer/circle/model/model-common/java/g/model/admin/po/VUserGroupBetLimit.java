package g.model.admin.po;

import org.soul.commons.bean.IEntity;
import org.soul.model.common.Sortable;


/**
 * 玩家分组单注投注限额实体
 *
 * @author tom
 * @time 2016-4-21 17:20:59
 */
//region your codes 1
public class VUserGroupBetLimit implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -3379205334754898483L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_BET_TYPE = "betType";
	public static final String PROP_RTYPE = "rtype";
	public static final String PROP_BET_MIN = "betMin";
	public static final String PROP_BET_MAX = "betMax";
	public static final String PROP_ITEM_MAX = "itemMax";
	public static final String PROP_SYS_USER_GROUP_ID = "sysUserGroupId";
	public static final String PROP_SCHEMA_CODE = "schemaCode";
	public static final String PROP_ORDER_NUM = "orderNum";
	public static final String PROP_GROUP_NAME = "groupName";
	public static final String PROP_CREATE_USER = "createUser";
	public static final String PROP_COLUMN_GROUP_CODE = "columnGroupCode";
	//endregion
	
	
	//region properties
	/**  */
	private Integer id;
	/** 玩法 */
	private String betType;
	/** 滚球 */
	private String rtype;
	/** 单注最小投注限额 */
	private Integer betMin;
	/** 单注最大投注限额 */
	private Integer betMax;
	/** 单项最大投注限额 */
	private Integer itemMax;
	/** 分组ID */
	private Integer sysUserGroupId;
	/** 方案编码 */
	private String schemaCode;
	/** 排序 */
	private Integer orderNum;
	/** 分组名称 */
	private String groupName;
	/** 创建用户 */
	private Integer createUser;
	/** 列分组 */
	private String columnGroupCode;
	/** 组方案id */
	private Integer rid;
	//endregion

	
	//region constuctors
	public VUserGroupBetLimit(){
	}

	public VUserGroupBetLimit(Integer id){
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
	@Sortable
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
	public Integer getSysUserGroupId() {
		return this.sysUserGroupId;
	}

	public void setSysUserGroupId(Integer value) {
		this.sysUserGroupId = value;
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
	@Sortable
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public String getColumnGroupCode() {
		return columnGroupCode;
	}

	public void setColumnGroupCode(String columnGroupCode) {
		this.columnGroupCode = columnGroupCode;
	}

//endregion
	//region your codes 2

	//endregion your codes 2

}