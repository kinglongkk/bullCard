package g.service.engine.schedule;

/**
 * Created by Double on 2016/9/30.
 * 调度器
 */
public interface ISchedule<T> {

    /**
     * 启动调度器
     */
    void start();

    /**
     * 暂停调度器
     */
    void pause();

    /**
     * 结束调度器
     */
    void stop();

    /**
     * 获取调度ID
     *
     * @return
     */
    T getId();

}
