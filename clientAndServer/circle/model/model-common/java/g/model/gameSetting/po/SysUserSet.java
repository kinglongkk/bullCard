package g.model.gameSetting.po;

import org.soul.commons.bean.IEntity;
import org.soul.model.common.Sortable;


/**
 * 实体
 *
 * @author lenovo
 * @time 2016-11-2 17:11:53
 */
//region your codes 1
public class SysUserSet implements IEntity<Integer> {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -830231795010147097L;
	//endregion your codes 3

	//region property name constants
	public static final String PROP_ID = "id";
	public static final String PROP_PLAYER_ID = "playerId";
	public static final String PROP_SOUND = "sound";
	public static final String PROP_MUSIC = "music";
	public static final String PROP_GAMETYPE = "gametype";
	//endregion
	
	
	//region properties
	/** --主键 */
	private Integer id;
	/** --玩家id */
	private Integer playerId;
	/** --音效设置开关 */
	private Boolean sound;
	/** --音乐设置开关 */
	private Boolean music;
	/** --游戏类型 */
	private String gametype;
	//endregion

	
	//region constuctors
	public SysUserSet(){
	}

	public SysUserSet(Integer id){
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
	public Integer getPlayerId() {
		return this.playerId;
	}

	public void setPlayerId(Integer value) {
		this.playerId = value;
	}
	public Boolean getSound() {
		return this.sound;
	}

	public void setSound(Boolean value) {
		this.sound = value;
	}
	public Boolean getMusic() {
		return this.music;
	}

	public void setMusic(Boolean value) {
		this.music = value;
	}
	public String getGametype() {
		return this.gametype;
	}

	public void setGametype(String value) {
		this.gametype = value;
	}
	//endregion

	//region your codes 2

	//endregion your codes 2

}