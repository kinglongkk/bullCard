package g.model.playerstatistics.po;

import org.soul.commons.bean.IEntity;

import java.util.Date;

/**
 * Created by lenovo on 2017/3/24.
 * 奖池报表代理商用户
 */
public class JackpotPlayer implements IEntity<Long> {

    private static final long serialVersionUID = -6564002363940957889L;

    public static final String PROP_ID = "id";
    public static final String PROP_DATE_TIME = "datetime";
    public static final String PROP_AGENT_NAME="agentname";
    public static final String PROP_PLAYER_NAME="playername";
    public static final String PROP_GNAME_NAME="gamename";
    public static final String PROP_GAME_MODEL_NAME = "gamemodelname";
    public static final String PROP_PROFIT_AMOUNT = "profitamount";
    public static final String PROP_SYS_USER_ID = "sysuserid";
    public static final String PROP_GAME_ID="gameid";
    public static final String PROP_GAME_MODEL_ID="gamemodelid";
    public static final String PROP_BET_TIME = "betTime";


    private Long id;
    private Date datetime;
    private String agentname;
    private String playername;
    private String gamename;
    private String gamemodelname;
    private Double profitamount;
    private Integer sysuserid;
    private Integer gameid;
    private Integer gamemodelid;
    private Date betTime;


    public JackpotPlayer(){
    }

    public JackpotPlayer(Long id){
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getAgentname() {
        return agentname;
    }

    public void setAgentname(String agentname) {
        this.agentname = agentname;
    }

    public String getPlayername() {
        return playername;
    }

    public void setPlayername(String playername) {
        this.playername = playername;
    }

    public String getGamename() {
        return gamename;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    public String getGamemodelname() {
        return gamemodelname;
    }

    public void setGamemodelname(String gamemodelname) {
        this.gamemodelname = gamemodelname;
    }

    public Double getProfitamount() {
        return profitamount;
    }

    public void setProfitamount(Double profitamount) {
        this.profitamount = profitamount;
    }

    public Integer getSysuserid() {
        return sysuserid;
    }

    public void setSysuserid(Integer sysuserid) {
        this.sysuserid = sysuserid;
    }

    public Integer getGameid() {
        return gameid;
    }

    public void setGameid(Integer gameid) {
        this.gameid = gameid;
    }

    public Integer getGamemodelid() {
        return gamemodelid;
    }

    public void setGamemodelid(Integer gamemodelid) {
        this.gamemodelid = gamemodelid;
    }

    public Date getBetTime() {
        return betTime;
    }

    public void setBetTime(Date betTime) {
        this.betTime = betTime;
    }
}
