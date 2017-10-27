package g.web.player.model;

/**
 * Created by longer on 7/8/16.
 */
public class BetParam {

    private long matchId;

    private Double singleAmount;

    private String[] betInfos;

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public Double getSingleAmount() {
        return singleAmount;
    }

    public void setSingleAmount(Double singleAmount) {
        this.singleAmount = singleAmount;
    }

    public String[] getBetInfos() {
        return betInfos;
    }

    public void setBetInfos(String[] betInfos) {
        this.betInfos = betInfos;
    }
}
