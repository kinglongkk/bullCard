package g.web.player.model;

/**
 * 投注信息类
 * Created by jerry on 16-7-5.
 */
public class BetInfo {

    //投注项
    private String iorField;
    private String iorFieldDesc;

    //下单金额
    private Double singleAmount;

    //提示信息
    private String message;

    //玩法
    private String betType;

    //赔率
    private Double odds;

    //可赢
    private Double canWin;

    public String getIorField() {
        return iorField;
    }

    public void setIorField(String iorField) {
        this.iorField = iorField;
    }

    public Double getSingleAmount() {
        return singleAmount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSingleAmount(Double singleAmount) {
        this.singleAmount = singleAmount;
    }

    public String getBetType() {
        return betType;
    }

    public void setBetType(String betType) {
        this.betType = betType;
    }

    public Double getOdds() {
        return odds;
    }

    public void setOdds(Double odds) {
        this.odds = odds;
    }

    public Double getCanWin() {
        return canWin;
    }

    public void setCanWin(Double canWin) {
        this.canWin = canWin;
    }

    public String getIorFieldDesc() {
        return iorFieldDesc;
    }

    public void setIorFieldDesc(String iorFieldDesc) {
        this.iorFieldDesc = iorFieldDesc;
    }
}
