package g.service.chesscard.engine.listener;

/**
 * Created by Double on 2016/10/8.
 */
public class BetEvent {

    private Integer id;
    private Integer deskId;


    //------------@see NbBet---------------
    /**
     * 赛事ID
     */
    private long matchId;

    /**
     * 投注类型
     */
    private String type;

    /**
     * 投注项
     */
    private String item;

    /**
     * 投注额
     */
    private int gold;

    //------------@see NbBet---------------


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeskId() {
        return deskId;
    }

    public void setDeskId(Integer deskId) {
        this.deskId = deskId;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
}
