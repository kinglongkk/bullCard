package g.web.player.model;

import org.soul.model.error.ErrorMessage;

import java.util.List;

/**
 * Created by longer on 4/26/16.
 */
public class BetPreRs extends ErrorMessage {

    //可赢金额
    private Double canWin;

    private Double singleAmount;

    private long matchId;

    /**
     * 投注项列表
     */
    private List<BetInfo> betInfos;

    public Double getSingleAmount() {
        return singleAmount;
    }

    public void setSingleAmount(Double singleAmount) {
        this.singleAmount = singleAmount;
    }

    public List<BetInfo> getBetInfos() {
        return betInfos;
    }

    public void setBetInfos(List<BetInfo> betInfos) {
        this.betInfos = betInfos;
    }

    public Double getCanWin() {
        return canWin;
    }

    public void setCanWin(Double canWin) {
        this.canWin = canWin;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }
}
