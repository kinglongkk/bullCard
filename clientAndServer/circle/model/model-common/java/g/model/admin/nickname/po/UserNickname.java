package g.model.admin.nickname.po;

import org.soul.commons.bean.IEntity;


/**
 * 昵称实体
 *
 * @author black
 * @time 2017-2-8 15:41:50
 */
//region your codes 1
public class UserNickname implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -8425625879727657420L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_NICKNAME = "nickname";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Integer id;
	/** 昵称 */
	private String nickname;
	//endregion

	
	//region constuctors
	public UserNickname(){
	}

	public UserNickname(Integer id){
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
	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String value) {
		this.nickname = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}