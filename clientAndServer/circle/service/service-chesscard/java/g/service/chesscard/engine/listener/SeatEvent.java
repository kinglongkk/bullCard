package g.service.chesscard.engine.listener;

/**
 * Created by tony on 2016/10/14.
 */
public class SeatEvent {

    private Integer userId;
    private Integer deskId;

    public SeatEvent(Integer userId, Integer deskId) {
        this.userId = userId;
        this.deskId = deskId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDeskId() {
        return deskId;
    }

    public void setDeskId(Integer deskId) {
        this.deskId = deskId;
    }
}
