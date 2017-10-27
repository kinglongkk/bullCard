package g.service.engine.listener;

import g.service.chesscard.engine.listener.SeatEvent;

/**
 * Created by tony on 2016/10/14.
 */
public interface ISeatListener {
    /**
     * 入座
     *
     * @param seatEvent
     */
    void onSeat(SeatEvent seatEvent);

}
