package g.service.chesscard.engine.listener;

/**
 * Created by Double on 2016/9/30.
 */
public class DeskEvent {

    /**
     * 桌子ID
     */
    private Integer deskId;

    public DeskEvent(Integer deskId) {
        this.deskId = deskId;
    }


    public Integer getDeskId() {
        return deskId;
    }

    public void setDeskId(Integer deskId) {
        this.deskId = deskId;
    }

}
