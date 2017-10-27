package g.service.chesscard.engine.model;

/**
 * 上庄信息
 * Created by Jason on 2016/11/3.
 */
public class GameDealer {
    private Integer deskId;
    private Integer userId;
    private Long dealerCoin;

    public Integer getDeskId() {
        return deskId;
    }

    public void setDeskId(Integer deskId) {
        this.deskId = deskId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getDealerCoin() {
        return dealerCoin;
    }

    public void setDealerCoin(Long dealerCoin) {
        this.dealerCoin = dealerCoin;
    }
}
