package g.model.admin.po;

import org.soul.commons.bean.IEntity;
import org.soul.model.common.Sortable;


/**
 * 玩家分组综合过关单项限额实体
 *
 * @author tom
 * @time 2016-4-21 17:23:46
 */
//region your codes 1
public class VUserGroupBetLimitMultiple implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 7290961091131303934L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_BET_NUM = "betNum";
	public static final String PROP_ODDS_MIN = "oddsMin";
	public static final String PROP_ODDS_MAX = "oddsMax";
	public static final String PROP_BET_MIN = "betMin";
	public static final String PROP_BET_MAX = "betMax";
	public static final String PROP_SYS_USER_GROUP_ID = "sysUserGroupId";
	public static final String PROP_SCHEMA_CODE = "schemaCode";
	public static final String PROP_ORDER_NUM = "orderNum";
	public static final String PROP_GROUP_NAME = "groupName";
	public static final String PROP_CREATE_USER = "createUser";
	//endregion
	
	
	//region properties
	/**  */
	private Integer id;
	/** 串 */
	private Integer betNum;
	/** 赔率最小积 */
	private Integer oddsMin;
	/** 赔率最大积 */
	private Integer oddsMax;
	/** 单注最小额度 */
	private Integer betMin;
	/** 单注最大额度 */
	private Integer betMax;
	/** 玩家分组id */
	private Integer sysUserGroupId;
	/** 方案编码 */
	private String schemaCode;
	/** 排序 */
	private Integer orderNum;
	/** 分组名称 */
	private String groupName;
	/** 创建用户 */
	private Integer createUser;
	//endregion

	
	//region constuctors
	public VUserGroupBetLimitMultiple(){
	}

	public VUserGroupBetLimitMultiple(Integer id){
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
	public Integer getBetNum() {
		return this.betNum;
	}

	public void setBetNum(Integer value) {
		this.betNum = value;
	}
	public Integer getOddsMin() {
		return this.oddsMin;
	}

	public void setOddsMin(Integer value) {
		this.oddsMin = value;
	}
	public Integer getOddsMax() {
		return this.oddsMax;
	}

	public void setOddsMax(Integer value) {
		this.oddsMax = value;
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
	//endregion

	//region your codes 2

	//endregion your codes 2

}