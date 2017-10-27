package g.model.ai.po;

import org.soul.commons.bean.IEntity;
import org.soul.model.common.Sortable;


/**
 * ai用户实体
 *
 * @author LENOVO
 * @time 2016-12-20 15:58:34
 */
//region your codes 1
public class AiUser implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -409273681129586700L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_USER_ID = "userId";
	public static final String PROP_AI_ROOM_ID = "aiRoomId";
	//endregion
	
	
	//region properties
	/**  */
	private Integer id;
	/**  */
	private Integer userId;
	/**  */
	private Integer aiRoomId;
	//endregion

	
	//region constuctors
	public AiUser(){
	}

	public AiUser(Integer id){
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
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer value) {
		this.userId = value;
	}
	public Integer getAiRoomId() {
		return this.aiRoomId;
	}

	public void setAiRoomId(Integer value) {
		this.aiRoomId = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}