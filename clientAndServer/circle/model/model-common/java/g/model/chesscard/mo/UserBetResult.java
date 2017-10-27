package g.model.chesscard.mo;

/**
 * Created by Jason on 2016/10/14.
 */
public class UserBetResult {
    /** 用户ID */
    private Integer userId;
    /** 座位号 -1的时候是表示庄家  系统庄的时候用户ID为null */
    private Integer seatNo;
    /** 实际加减金币 */
    private Long coin;
    /** 输赢金币  用于做动画效果 */
    private Long incCoin;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(Integer seatNo) {
        this.seatNo = seatNo;
    }

    public Long getCoin() {
        return coin;
    }

    public void setCoin(Long coin) {
        this.coin = coin;
    }

    public Long getIncCoin() {
        return incCoin;
    }

    public void setIncCoin(Long incCoin) {
        this.incCoin = incCoin;
    }
}
