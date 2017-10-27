package g.model.agent.po;

import org.soul.commons.bean.IEntity;
import org.soul.model.common.Sortable;


/**
 * 实体
 *
 * @author orange
 * @time 2016-4-27 15:56:13
 */
//region your codes 1
public class VUserPlayerGroup implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -1927595912741551326L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_PLAYERNUM = "playernum";
	public static final String PROP_GROUP_NAME = "groupName";
	public static final String PROP_CREATE_USER = "createUser";
	public static final String PROP_CREATE_TIME = "createTime";
	public static final String PROP_UPDATE_USER = "updateUser";
	public static final String PROP_UPDATE_TIME = "updateTime";
	public static final String PROP_IS_DEFUALT = "isDefault";
	//endregion
	
	
	//region properties
	/** id */
	private Integer id;
	/** 玩家个数 */
	private Long playernum;
	/** 分组名称 */
	private String groupName;
	/** 创建人id */
	private Integer createUser;
	/** 创建时间 */
	private java.util.Date createTime;
	/** 编辑用户 */
	private Integer updateUser;
	/** 编辑时间 */
	private java.util.Date updateTime;
	/** 是否默认 */
	private Boolean isDefault;
	//endregion

	
	//region constuctors
	public VUserPlayerGroup(){
	}

	public VUserPlayerGroup(Integer id){
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
	public Long getPlayernum() {
		return this.playernum;
	}

	public void setPlayernum(Long value) {
		this.playernum = value;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String value) {
		this.groupName = value;
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

	public void setIsDefault(Boolean value) {
		this.isDefault = value;
	}

	public Boolean getIsDefault() {
		return this.isDefault;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}