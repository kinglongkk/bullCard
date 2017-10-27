package g.service.chesscard.engine.schedule;

import g.model.enums.GameModelCodeEnum;
import g.service.chesscard.engine.factory.IModelFactory;
import g.service.chesscard.engine.manager.DeskManager;
import g.service.chesscard.engine.model.Desk;
import g.service.chesscard.engine.model.Room;
import g.service.chesscard.engine.task.IMatchTask;
import g.service.chesscard.engine.task.MatchStatusSleep;
import g.service.chesscard.engine.task.MatchTask;
import g.service.engine.listener.IMatchListener;
import g.service.engine.manager.IDeskManager;
import g.service.engine.manager.IRoomManager;
import g.service.engine.schedule.IDeskSchedule;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.spring.utils.SpringTool;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by Double on 2016/9/30.
 * 桌子调度器
 */
public class DeskSchedule implements IDeskSchedule<Integer> {

    private static Log log = LogFactory.getLog(DeskSchedule.class);

    private Integer deskId;

    protected IMatchTask<Long> matchTask;

    private ScheduledFuture scheduledFuture;

    private ScheduledExecutorService scheduledExecutorService;

    @Autowired
    private IDeskManager deskManager;

    @Autowired
    private IModelFactory modelFactory;

    public DeskSchedule() {
    }

    @Override
    public void start() {
        log.info("桌号:{0},调度器[启动].", deskId);
        scheduledExecutorService = createExecutorService();
        newMatch();
    }

    @Override
    public void pause() {

    }

    /**
     * 创建线程
     *
     * @return
     */
    private ScheduledExecutorService createExecutorService() {
        return Executors.newSingleThreadScheduledExecutor(new DeskThreadFactory(deskId));
    }

    /**
     * 新开赛事
     */
    @Override
    public void newMatch() {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }
        log.info("桌号:{0},调度器[新建]赛事.", deskId);
        this.matchTask = schedule(deskId);
    }

    private IMatchTask schedule(Integer deskId) {
        IMatchListener matchListener = modelFactory.getMatchListener(deskId);
        MatchTask matchTask = (MatchTask) SpringTool.getBean("matchTask");
        matchTask.setDeskId(deskId);
        matchTask.setDeskSchedule(this);
        matchTask.setMatchListener(matchListener);

        //设置时间间隔
        Desk desk = deskManager.byId(deskId);
        Room room = desk.getRoom();
        GameModelCodeEnum modelCodeEnum = GameModelCodeEnum.enumOf(room.getModelCode());
        if (modelCodeEnum == GameModelCodeEnum.BET) {
            matchTask.setMatchStatusSleeps(MatchStatusSleep.BULL_100_FLOW);
        } else if (modelCodeEnum == GameModelCodeEnum.GRAB) {
            matchTask.setMatchStatusSleeps(MatchStatusSleep.BULL_BAO_FLOW);
        }

        scheduledFuture = scheduledExecutorService.schedule(matchTask, 0, TimeUnit.SECONDS);
        return matchTask;
    }

    @Override
    public void stop() {
        log.info("桌号:{0},调度器[停止].", deskId);
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
        }
    }


    @Override
    public IMatchTask getMatchTask2() {
        if (matchTask != null && matchTask.getId() == null) {
            try {
                synchronized (matchTask) {
                    matchTask.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return matchTask;
    }

    @Override
    public Integer getId() {
        return deskId;
    }

    @Override
    public void setId(Integer deskId) {
        this.deskId = deskId;
    }
}
