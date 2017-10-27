package g.service.chesscard.engine.task;

import g.model.match.po.Match;

/**
 * Created by Double on 2016/9/30.
 */
public interface IMatchTask<T> extends Runnable {

    /**
     * 获取游ID
     *
     * @return
     */
    T getId();

    /**
     * 设置游戏ID
     *
     * @param id
     */
    void setId(T id);

    /**
     * 游戏开始
     */
    void start();

    /**
     * 游戏结束
     */
    void stop();

    /**
     * 游戏结算
     */
    void settle();

    /**
     * 游戏是否进行中
     */
    boolean isRunning();

    boolean isSettled();

    /**
     * 获取当前赛事对象
     *
     * @return
     */
    Match getMatch();
}
