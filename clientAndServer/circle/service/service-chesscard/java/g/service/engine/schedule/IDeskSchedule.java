package g.service.engine.schedule;

import g.service.chesscard.engine.task.IMatchTask;

/**
 * Created by tony on 2016/10/18.
 */
public interface IDeskSchedule<T> extends ISchedule<T> {

    /**
     * 调度器
     *
     * @param id
     */
    void setId(Integer id);

    /**
     * 新开赛事
     */
    void newMatch();

    /**
     * 获取游戏赛事
     *
     * @return
     */
    IMatchTask<Long> getMatchTask2();


}
