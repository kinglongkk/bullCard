package g.service.engine.manager;

import g.service.engine.schedule.IDeskSchedule;

/**
 * Created by Double on 2016/9/29.
 */
public interface IDeskScheduleManager<T> {
    /**
     * 调度器的大小
     *
     * @return
     */
    int size();

    /**
     * 新建调度器
     *
     * @param schedule
     */
    void putSchedule(IDeskSchedule<T> schedule);

    /**
     * 获取调度器
     *
     * @param scheduleId
     * @return
     */
    IDeskSchedule<T> getSchedule(T scheduleId);

    /**
     * 移除调度器
     *
     * @param scheduleId
     */
    void removeSchedule(T scheduleId);

    /**
     * 是否包含调度ID
     *
     * @param scheduleId
     * @return
     */
    boolean isContain(T scheduleId);
}
