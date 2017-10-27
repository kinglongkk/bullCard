package g.model.admin.agent.gameview.po;

import org.soul.commons.bean.IEntity;


/**
 * 实体
 *
 * @author black
 * @time 2016-12-19 11:01:49
 */
//region your codes 1
public class VAgentGame implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -5302260737887431502L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_NAME = "name";
	public static final String PROP_STATUS = "status";
	public static final String PROP_TYPE = "type";
	public static final String PROP_FIRST_TYPE = "firstType";
	public static final String PROP_IS_DELETED = "isDeleted";
	public static final String PROP_GAME_LOGO = "gameLogo";
	public static final String PROP_GAME_LINK = "gameLink";
	public static final String PROP_AGENT_ID = "agentId";
	//endregion
	
	
	//region properties
	/** 游戏id  */
	private Integer id;
	/** 游戏名称 */
	private String name;
	/** 游戏状态 */
	private String status;
	/** 游戏二级类型 */
	private String type;
	/** 游戏一级类型 */
	private String firstType;
	/** 是否删除 */
	private Boolean isDeleted;
	/** 游戏logo */
	private String gameLogo;
	/** 游戏链接 */
	private String gameLink;
	/** 代理id */
	private Integer agentId;
	//endregion

	
	//region constuctors
	public VAgentGame(){
	}

	public VAgentGame(Integer id){
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
	public String getType() {
		return this.type;
	}

	public void setType(String value) {
		this.type = value;
	}
	public String getFirstType() {
		return this.firstType;
	}

	public void setFirstType(String value) {
		this.firstType = value;
	}
	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Boolean value) {
		this.isDeleted = value;
	}
	public String getGameLogo() {
		return this.gameLogo;
	}

	public void setGameLogo(String value) {
		this.gameLogo = value;
	}
	public String getGameLink() {
		return this.gameLink;
	}

	public void setGameLink(String value) {
		this.gameLink = value;
	}
	public Integer getAgentId() {
		return this.agentId;
	}

	public void setAgentId(Integer value) {
		this.agentId = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}