package g.model.playerstatistics.po;

import org.soul.commons.bean.IEntity;

import java.util.Date;

/**
 * Created by lenovo on 2017/3/23.
 * 奖池报表实体对象
 */
public class JackpotMatch implements IEntity<Long> {

    private static final long serialVersionUID = -6564006963947957889L;
    public static final String PROP_ID = "id";
    public static final String PROP_BEGIN_TIME = "beginTime";
    public static final String PROP_END_TIME="endTime";
    public static final String PROP_PROFIT_AMOUNT="profitAmount";
    public static final String PROP_PUMPINGAMOUNT = "pumpingamount";
    public static final String PROP_GAME_ROOM_ID="gameRoomId";
    public static final String PROP_GAMEROOMNAME = "gameroomname";
    public static final String PROP_JACKPOT="jackpot";
    public static final String PROP_JACKPOT_OVERFLOW="jackpotOverflow";


    private Long id;
    private Date beginTime;
    private Date endTime;
    private Double profitAmount;
    private Double pumpingamount;
    private Integer gameRoomId;
    private String gameroomname;
    private Double jackpot;
    private Double jackpotOverflow;

    public JackpotMatch(){
    }

    public JackpotMatch(Long id){
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Double getProfitAmount() {
        return profitAmount;
    }

    public void setProfitAmount(Double profitAmount) {
        this.profitAmount = profitAmount;
    }

    public Double getPumpingamount() {
        return pumpingamount;
    }

    public void setPumpingamount(Double pumpingamount) {
        this.pumpingamount = pumpingamount;
    }

    public Integer getGameRoomId() {
        return gameRoomId;
    }

    public void setGameRoomId(Integer gameRoomId) {
        this.gameRoomId = gameRoomId;
    }

    public String getGameroomname() {
        return gameroomname;
    }

    public void setGameroomname(String gameroomname) {
        this.gameroomname = gameroomname;
    }

    public Double getJackpot() {
        return jackpot;
    }

    public void setJackpot(Double jackpot) {
        this.jackpot = jackpot;
    }

    public Double getJackpotOverflow() {
        return jackpotOverflow;
    }

    public void setJackpotOverflow(Double jackpotOverflow) {
        this.jackpotOverflow = jackpotOverflow;
    }
}
