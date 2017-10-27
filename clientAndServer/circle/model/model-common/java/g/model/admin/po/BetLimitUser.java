package g.model.admin.po;

import org.soul.commons.bean.IEntity;


/**
 * 用户与投注限额关系表实体
 *
 * @author tom
 * @time 2016-4-20 11:39:00
 */
//region your codes 1
public class BetLimitUser implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -5204822442850590576L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_SCHEMA_CODE = "schemaCode";
	public static final String PROP_SYS_USER_ID = "sysUserId";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Integer id;
	/** 投注限额方案 */
	private String schemaCode;
	/** 用户ID（代理） */
	private Integer sysUserId;
	//endregion

	
	//region constuctors
	public BetLimitUser(){
	}

	public BetLimitUser(Integer id){
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