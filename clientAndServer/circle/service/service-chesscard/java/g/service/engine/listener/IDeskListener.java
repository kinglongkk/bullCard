package g.service.engine.listener;

import g.service.chesscard.engine.listener.DeskEvent;
import g.service.chesscard.engine.listener.DeskPlayerEvent;

/**
 * Created by Double on 2016/9/30.
 */
public interface IDeskListener {

    /**
     * 新开桌子
     *
     * @param deskEvent
     */
    void onNew(DeskEvent deskEvent);

    /**
     * 销毁桌子
     *
     * @param deskEvent
     */
    void onDestroy(DeskEvent deskEvent);

    /**
     * 进入桌子
     *
     * @param deskEvent
     */
    void onSeat(DeskPlayerEvent deskEvent);

    /**
     * 退出桌子
     *
     * @param deskEvent
     */
    void onLeave(DeskPlayerEvent deskEvent);

}
