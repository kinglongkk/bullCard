package g.service.chesscard.engine.schedule;

import g.service.chesscard.engine.listener.DeskEvent;
import g.service.engine.manager.IDeskScheduleManager;
import g.service.engine.schedule.IDeskSchedule;
import g.service.engine.schedule.ISchedule;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Double on 2016/9/29.
 */
public class DeskScheduleManager implements IDeskScheduleManager<Integer> {

    private static Log log = LogFactory.getLog(DeskScheduleManager.class);

    private Map<Integer, IDeskSchedule<Integer>> deskSchedules = new HashMap();


    @Override
    public int size() {
        return deskSchedules.size();
    }

    @Override
    public void putSchedule(IDeskSchedule<Integer> schedule) {
        deskSchedules.put(schedule.getId(), schedule);
    }

    @Override
    public IDeskSchedule getSchedule(Integer scheduleId) {
        if (deskSchedules.containsKey(scheduleId)) {
            return deskSchedules.get(scheduleId);
        } else {
            log.warn("获取调度器失败：{0}", scheduleId);
        }
        return null;
    }

    @Override
    public void removeSchedule(Integer scheduleId) {
        if (deskSchedules.containsKey(scheduleId)) {
            ISchedule deskSchedule = deskSchedules.remove(scheduleId);
            deskSchedule.stop();
        }
    }

    @Override
    public boolean isContain(Integer scheduleId) {
        return deskSchedules.containsKey(scheduleId);
    }

    public void onChange(DeskEvent deskEvent) {


    }

}
