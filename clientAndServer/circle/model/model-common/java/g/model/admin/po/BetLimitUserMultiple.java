package g.model.admin.po;

import org.soul.commons.bean.IEntity;


/**
 * 用户与综合投注限额关系表实体
 *
 * @author tom
 * @time 2016-4-20 11:46:13
 */
//region your codes 1
public class BetLimitUserMultiple implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 2057395396956069179L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_SCHEMA_CODE = "schemaCode";
	public static final String PROP_SYS_USER_ID = "sysUserId";
	//endregion
	
	
	//region properties
	/**  */
	private Integer id;
	/** 用户与综合投注关系表 */
	private String schemaCode;
	/** 用户ID */
	private Integer sysUserId;
	//endregion

	
	//region constuctors
	public BetLimitUserMultiple(){
	}

	public BetLimitUserMultiple(Integer id){
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
	public Integer getSysUserId() {
		return this.sysUserId;
	}

	public void setSysUserId(Integer value) {
		this.sysUserId = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}