package g.model.match.po;

import java.io.Serializable;

/**
 * @author: tom
 * @date: 16-6-3
 */
public class MatchBetCount implements Serializable {

    // 赛事ID
    private Long matchId;

    /** 比赛类型:[FT=足球...] */
    private String ballType;

    // 赛事阶段
    private String phase;

    // 已结算注单
    private Integer closeBet;

    // 未结算注单
    private Integer unCloseBet;

    // 总额
    private Double totalMoney;

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public Integer getCloseBet() {
        return closeBet;
    }

    public void setCloseBet(Integer closeBet) {
        this.closeBet = closeBet;
    }

    public Integer getUnCloseBet() {
        return unCloseBet;
    }

    public void setUnCloseBet(Integer unCloseBet) {
        this.unCloseBet = unCloseBet;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getBallType() {
        return ballType;
    }

    public void setBallType(String ballType) {
        this.ballType = ballType;
    }
}
