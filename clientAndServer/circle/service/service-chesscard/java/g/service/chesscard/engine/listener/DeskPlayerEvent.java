package g.service.chesscard.engine.listener;

/**
 * Created by Double on 2016/9/30.
 * 桌子玩家事件
 */
public class DeskPlayerEvent extends DeskEvent {

    /**
     * 玩家ID
     */
    private Integer playerId;


    public DeskPlayerEvent(Integer deskId) {
        super(deskId);
    }

    public DeskPlayerEvent(Integer deskId, Integer playerId) {
        super(deskId);
        this.playerId = playerId;
    }


    public DeskPlayerEvent(DeskEvent deskEvent, Integer playerId) {
        super(deskEvent.getDeskId());
        this.playerId = playerId;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }
}
