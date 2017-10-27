package g.service.chesscard.engine.model;

/**
 * 游戏桌子座位实体
 *
 * @author Jason
 * @time 2016-8-25 14:17:12
 */
public class GameDeskSeat {
    /**
     * 桌子
     */
    private Integer gameDeskId;

    /** 座位编号 */
    /**
     * 用户所在座位号 1-6   -1是庄家  -2是吃瓜群众
     */
    private Integer seatNo;

    /**
     * 玩家ID
     */
    private Integer userId;

    public Integer getGameDeskId() {
        return gameDeskId;
    }

    public void setGameDeskId(Integer gameDeskId) {
        this.gameDeskId = gameDeskId;
    }

    public Integer getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(Integer seatNo) {
        this.seatNo = seatNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}