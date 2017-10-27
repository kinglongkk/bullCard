package g.model.avatarurl.po;

import org.soul.commons.bean.IEntity;


/**
 * 用户头像实体
 *
 * @author black
 * @time 2017-4-21 11:23:25
 */
//region your codes 1
public class UserAvatarUrl implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = 3130375505504791917L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_AVATAR_URL = "avatarUrl";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Integer id;
	/** 头像 */
	private String avatarUrl;
	//endregion

	
	//region constuctors
	public UserAvatarUrl(){
	}

	public UserAvatarUrl(Integer id){
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
	public String getAvatarUrl() {
		return this.avatarUrl;
	}

	public void setAvatarUrl(String value) {
		this.avatarUrl = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}