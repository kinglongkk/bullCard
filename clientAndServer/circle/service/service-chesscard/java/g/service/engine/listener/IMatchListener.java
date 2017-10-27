package g.service.engine.listener;

import g.service.chesscard.engine.listener.MatchEvent;

/**
 * Created by Double on 2016/9/30.
 */
public interface IMatchListener {

    /**
     * 新赛事
     *
     * @param matchEvent
     */
    void onStart(MatchEvent matchEvent);

    /**
     * 赛事结束
     *
     * @param matchEvent
     */
    void onStop(MatchEvent matchEvent);

    /**
     * 赛事结算完毕
     *
     * @param matchEvent
     */
    void onSettle(MatchEvent matchEvent);

    /**
     * 赛事真正完结
     *
     * @param matchEvent
     */
    void onFinish(MatchEvent matchEvent);

}
