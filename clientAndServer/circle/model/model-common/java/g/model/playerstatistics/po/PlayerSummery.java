package g.model.playerstatistics.po;

import org.soul.commons.bean.IEntity;
import org.soul.model.common.Sortable;


/**
 * 玩家数据统计表实体
 *
 * @author lenovo
 * @time 2017-1-5 17:36:23
 */
//region your codes 1
public class PlayerSummery implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -2861514160185446979L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_USERNAME = "username";
	public static final String PROP_NICKNAME = "nickname";
	public static final String PROP_DATE = "date";
	public static final String PROP_PLAYER_SUMMERY = "playerSummery";
	public static final String PROP_ACTIVE_PLAYER_QTY = "activePlayerQty";
	public static final String PROP_NEW_PLAYER_QTY = "newPlayerQty";
	//endregion
	
	
	//region properties
	/** 主键 */
	private Integer id;
	/** 代理商用户名 */
	private String username;
	/** 昵称 */
	private String nickname;
	/** 日期 */
	private java.util.Date date;
	/** 总拥有玩家数 */
	private Integer playerSummery;
	/** 活跃玩家数 */
	private Integer activePlayerQty;
	/** 新增玩家数 */
	private Integer newPlayerQty;
	//endregion

	
	//region constuctors
	public PlayerSummery(){
	}

	public PlayerSummery(Integer id){
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
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String value) {
		this.username = value;
	}
	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String value) {
		this.nickname = value;
	}
	@org.soul.model.common.Sortable
	public java.util.Date getDate() {
		return this.date;
	}

	public void setDate(java.util.Date value) {
		this.date = value;
	}
	@Sortable
	public Integer getPlayerSummery() {
		return this.playerSummery;
	}

	public void setPlayerSummery(Integer value) {
		this.playerSummery = value;
	}
	public Integer getActivePlayerQty() {
		return this.activePlayerQty;
	}

	public void setActivePlayerQty(Integer value) {
		this.activePlayerQty = value;
	}
	public Integer getNewPlayerQty() {
		return this.newPlayerQty;
	}

	public void setNewPlayerQty(Integer value) {
		this.newPlayerQty = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}