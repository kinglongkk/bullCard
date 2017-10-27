package g.model.gamemodel.po;

import org.soul.commons.bean.IEntity;
import org.soul.model.common.Sortable;


/**
 * 游戏表实体
 *
 * @author lenovo
 * @time 2016-8-25 14:20:28
 */
//region your codes 1
public class GameModel implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -2333033127919279009L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_GAME_ID = "gameId";
	public static final String PROP_CODE = "code";
	public static final String PROP_NAME = "name";
	public static final String PROP_STATUS = "status";
	public static final String PROP_ICON = "icon";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Integer id;
	/** 所属游戏 */
	private Integer gameId;
	/** 代码 */
	private String code;
	/** 名称 */
	private String name;
	/** [10:启用,20:禁用,30:维护] */
	private String status;
	/** 图标路径 */
	private String icon;
	//endregion

	
	//region constuctors
	public GameModel(){
	}

	public GameModel(Integer id){
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
	public Integer getGameId() {
		return this.gameId;
	}

	public void setGameId(Integer value) {
		this.gameId = value;
	}
	public String getCode() {
		return this.code;
	}

	public void setCode(String value) {
		this.code = value;
	}
	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String value) {
		this.status = value;
	}
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String value) {
		this.icon = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}