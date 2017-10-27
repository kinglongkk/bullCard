package g.model.admin.po;

import org.soul.commons.bean.IEntity;


/**
 * 用户组与综合投注限额关系表实体
 *
 * @author tom
 * @time 2016-4-20 11:41:52
 */
//region your codes 1
public class BetLimitUserGroup implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 5710043476086843512L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_SCHEMA_CODE = "schemaCode";
	public static final String PROP_SYS_USER_GROUP_ID = "sysUserGroupId";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Integer id;
	/** 投注限额方案 */
	private String schemaCode;
	/** 玩家分组ID */
	private Integer sysUserGroupId;
	//endregion

	
	//region constuctors
	public BetLimitUserGroup(){
	}

	public BetLimitUserGroup(Integer id){
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
	public String getSchemaCode() {
		return this.schemaCode;
	}

	public void setSchemaCode(String value) {
		this.schemaCode = value;
	}
	public Integer getSysUserGroupId() {
		return this.sysUserGroupId;
	}

	public void setSysUserGroupId(Integer value) {
		this.sysUserGroupId = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}