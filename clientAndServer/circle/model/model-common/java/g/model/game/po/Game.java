package g.model.game.po;

import org.soul.commons.bean.IEntity;
import java.util.Date;


/**
 * 游戏表实体
 *
 * @author lenovo
 * @time 2016-8-25 14:17:12
 */
//region your codes 1
public class Game implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -8029552188744453769L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_CODE = "code";
	public static final String PROP_NAME = "name";
	public static final String PROP_STATUS = "status";
	public static final String PROP_ICON = "icon";
	public static final String PROP_TYPE = "type";
	public static final String PROP_FIRST_TYPE = "firstType";
	public static final String PROP_CREATE_USER = "createUser";
	public static final String PROP_CREATE_TIME = "createTime";
	public static final String PROP_UPDATE_USER = "updateUser";
	public static final String PROP_UPDATE_TIME = "updateTime";
	public static final String PROP_IS_DELETED = "isDeleted";

	//endregion
	
	
	//region properties
	/** 主键 */
	private Integer id;
	/** 代码 */
	private String code;
	/** 名称 */
	private String name;
	/** [10:启用,20:禁用,30:维护] */
	private String status;
	/** 图标路径 */
	private String icon;
	/** 类型:二级类型 */
	private String type;
	/** 类型:一级类型 */
	private String firstType;
	/** 创建时间 */
	private java.util.Date createTime;
	/** 创建人 */
	private String createUser;
	/** 是否删除 */
	private Boolean isDeleted;
	/** 更新人 */
	private String updateUser;
	/** 更新时间 */
	private java.util.Date updateTime;

	//endregion
	//region constuctors
	public Game(){
	}

	public Game(Integer id){
		this.id = id;
	}
	//endregion


	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@org.soul.model.common.Sortable
	public String getFirstType() {
		return firstType;
	}

	public void setFirstType(String firstType) {
		this.firstType = firstType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}