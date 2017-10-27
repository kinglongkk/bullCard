package g.model.admin.agent.game.po;

import org.soul.commons.bean.IEntity;


/**
 * 代理游戏实体
 *
 * @author black
 * @time 2016-12-5 12:01:04
 */
public class UserAgentGame implements IEntity<Integer> {

	private static final long serialVersionUID = 5178350588364742637L;

	public static final String PROP_ID = "id";
	public static final String PROP_GAME_ID = "gameId";
	public static final String PROP_GAME_LOGO = "gameLogo";
	public static final String PROP_GAME_LINK = "gameLink";
	public static final String PROP_AGENT_ID = "agentId";

	/** 主键 */
	private Integer id;
	/** 游戏id */
	private Integer gameId;
	/** 游戏logo */
	private String gameLogo;
	/** 游戏返回链接 */
	private String gameLink;
	/** 代理商id */
	private Integer agentId;

	public UserAgentGame(){
	}

	public UserAgentGame(Integer id){
		this.id = id;
	}


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
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
}