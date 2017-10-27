package g.service.chesscard.engine.listener;

import g.service.engine.listener.IDeskListener;
import g.service.engine.manager.IDeskScheduleManager;
import g.service.engine.schedule.IDeskSchedule;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.spring.utils.SpringTool;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by longer on 2016/12/21.
 * <p>
 * 抽象的桌子监听器
 */
public abstract class AbstractDeskListener implements IDeskListener {

    private static Log log = LogFactory.getLog(AbstractDeskListener.class);

    @Autowired
    protected IDeskScheduleManager deskScheduleManager;

    private String scheduleBeanName;

    @Override
    public void onNew(DeskEvent deskEvent) {
        log.info("产生事件:桌子事件:{0},{1}", deskEvent.getDeskId(), "新建");
        if (deskScheduleManager.isContain(deskEvent.getDeskId())) {
            return;
        }
        //waring: prototype
        IDeskSchedule deskSchedule = (IDeskSchedule) SpringTool.getBean(scheduleBeanName);
        deskSchedule.setId(deskEvent.getDeskId());
        deskSchedule.start();
        deskScheduleManager.putSchedule(deskSchedule);
    }

    @Override
    public void onDestroy(DeskEvent deskEvent) {
        log.info("产生事件:桌子事件:{0},{1}", deskEvent.getDeskId(), "销毁");
        IDeskSchedule deskSchedule = deskScheduleManager.getSchedule(deskEvent.getDeskId());
        if (deskSchedule != null) {
            deskSchedule.stop();
        }
    }

    @Override
    public void onSeat(DeskPlayerEvent deskEvent) {
        log.info("产生事件:桌子事件:{0},{1}", deskEvent.getDeskId(), "落座");
    }

    @Override
    public void onLeave(DeskPlayerEvent deskEvent) {
        log.info("产生事件:桌子事件:{0},{1}", deskEvent.getDeskId(), "离座");
    }


    public void setScheduleBeanName(String scheduleBeanName) {
        this.scheduleBeanName = scheduleBeanName;
    }
}
