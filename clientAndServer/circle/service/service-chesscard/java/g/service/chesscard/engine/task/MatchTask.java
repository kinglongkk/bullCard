package g.service.chesscard.engine.task;

import g.model.bet.BetSettleStatus;
import g.model.match.vo.MatchVo;
import g.service.chesscard.engine.bull100.message.Bull100BetMessage;
import g.service.chesscard.engine.model.Desk;
import g.model.match.po.Match;
import g.service.engine.listener.IMatchListener;
import g.service.chesscard.engine.listener.MatchEvent;
import g.service.engine.manager.IDeskManager;
import g.service.engine.schedule.IDeskSchedule;
import g.service.match.MatchService;
import g.service.webSocket.context.WsSessionManager;
import org.soul.commons.lang.ArrayTool;
import org.soul.commons.lang.DateTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.spring.utils.SpringTool;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Double on 2016/9/30.
 */
public class MatchTask implements IMatchTask<Long> {

    protected Log log = LogFactory.getLog(getClass());

    protected Long id;

    protected Integer deskId;

    protected IDeskSchedule deskSchedule;

    protected volatile MatchStatus status = MatchStatus.INIT;

    protected Match match;

    @Autowired
    protected MatchService matchService;

    private IMatchListener matchListener;

    @Autowired
    private IDeskManager deskManager;

    @Autowired
    private WsSessionManager wsSessionManager;

    public MatchTask() {
    }

    protected void initMatch() {
        Desk desk = this.deskManager.byId(deskId);
        Integer gameId = desk.getGameId();
        Integer modelId = desk.getGameModelId();
        Integer roomId = desk.getGameRoomId();
        Integer dealerUserId = desk.getDealerUserId();

        //生成新的match
        Match match = new Match();
        match.setGameId(gameId);
        match.setGameRoomId(roomId);
        match.setGameModelId(modelId);
        match.setDealerUserId(dealerUserId);
        match.setSettleStatus(BetSettleStatus.UN_SETTLE.getCode());

        Date now = new Date();
        match.setBeginTime(now);
        match.setEndTime(DateTool.addSeconds(now, matchStatusSleepMap.get(MatchStatus.RUNNING)));
        match.setBeginTimeNext(DateTool.addSeconds(match.getEndTime(),matchStatusSleepMap.get(MatchStatus.SETTLED)));
        this.match = match;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public void start() {
        log.info("桌号:{0},赛事[启动]开始.", deskId);
        try {
            initMatch();
            MatchVo matchVo = new MatchVo();
            matchVo.setResult(match);
            matchService.insert(matchVo);
            setId(match.getId());
            setStatus(MatchStatus.RUNNING);
            fireStartEvent(match);
            synchronized (this) {
                this.notifyAll();
            }
            log.info("桌号:{0},赛事[启动]结束,赛事ID:{1},当前状态:{2}.", deskId, getId(), getStatus());
        } catch (Exception e) {
            log.info("桌号:{0},赛事[启动]异常.", deskId);
            log.error(e);
        }
    }

    /**
     * 触发开始事件
     *
     * @param match
     */
    private void fireStartEvent(Match match) {
        MatchEvent matchEvent = new MatchEvent(deskId, getId());
        matchEvent.setMatch(match);
        matchListener.onStart(matchEvent);
    }

    @Override
    public void stop() {
        try {
            if (status != MatchStatus.RUNNING) {
                throw new IllegalStateException("状态不对:赛事结束动作只能在[RUNNING]下时,才能进行");
            }
            log.info("桌号:{0},赛事[停止]开始,赛事ID:{1},当前状态:{2}.", deskId, getId(), getStatus());
            setStatus(MatchStatus.STOP);
            matchListener.onStop(new MatchEvent(deskId, getId()));
            settle();
            setStatus(MatchStatus.SETTLED);
        } catch (Exception e) {
            log.error(e);
        }


    }

    @Override
    public void settle() {
        try {
            Desk desk = deskManager.byId(deskId);
            if(desk.clearLastSecondBets()){
                Bull100BetMessage message = (Bull100BetMessage) SpringTool.getBean("betMessage");
                message.setDeskId(deskId);
                message.handleMessage();//结算前把前一秒的投注下发
                wsSessionManager.sendNetBeanGroup(deskId, message.objMessage().getMsg());
            }
            log.info("桌号:{0},赛事[结算]开始,赛事ID:{1},当前状态:{2}.", deskId, getId(), getStatus());
            MatchEvent matchEvent = new MatchEvent(deskId, getId());
            matchEvent.setMatchId(match.getId());
            matchListener.onSettle(matchEvent);


            log.info("桌号:{0},赛事[结算]结束,赛事ID:{1},当前状态:{2}.", deskId, getId(), getStatus());

            matchListener.onFinish(new MatchEvent(deskId, getId()));
        } catch (Exception e) {
            log.error(e);
        }
    }

    @Override
    public boolean isRunning() {
        if (status == MatchStatus.INIT || status == MatchStatus.RUNNING) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isSettled() {
        return status == MatchStatus.INIT
                || status == MatchStatus.SETTLED
                || status == MatchStatus.FINISHED;
    }

    @Override
    public Match getMatch() {
        return match;
    }

    public void setDeskId(Integer deskId) {
        this.deskId = deskId;
    }

    /**
     * 重新生成一个
     */
    public void newMatch() {
        try {
            deskSchedule.newMatch();
        } catch (Exception e) {
            log.error(e,"桌号:{0},创建新赛事异常,原赛事ID:{1},当前状态:{2}.", deskId, getId(), getStatus());
        } finally {
            setStatus(MatchStatus.FINISHED);
        }
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                if (status == MatchStatus.FINISHED) {
                    break;
                }
                switch (status) {
                    case INIT:
                        start();
                        sleep(status);
                        break;
                    case RUNNING:
                        stop();
                        sleep(status);
                        break;
                    case SETTLED:
                        newMatch();
                        sleep(status);
                        break;
                }
            }
        } catch (Exception e) {
            log.error(e,"桌号:{0},赛事[异常]退出,赛事ID:{1},当前状态:{2}.", deskId, getId(), getStatus());
        }
    }

    private void sleep(MatchStatus status) throws Exception {
        Integer sleep = this.matchStatusSleepMap.get(status);
        if (sleep == null) {
            throw new Exception("赛事各环节-间隔时间-未设置正确.");
        }
        TimeUnit.SECONDS.sleep(sleep);

    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setDeskSchedule(IDeskSchedule deskSchedule) {
        this.deskSchedule = deskSchedule;
    }

    protected Integer getDeskId() {
        return deskId;
    }

    public void setMatchListener(IMatchListener matchListener) {
        this.matchListener = matchListener;
    }

    //赛事各状态之间的时间间隔
    private Map<MatchStatus, Integer> matchStatusSleepMap;

    public void setMatchStatusSleeps(MatchStatusSleep[] matchStatusSleeps) {
        if (ArrayTool.isEmpty(matchStatusSleeps)) {
            return;
        }
        matchStatusSleepMap = new HashMap(matchStatusSleeps.length);
        for (int i = 0; i < matchStatusSleeps.length; i++) {
            MatchStatusSleep matchStatusSleep = matchStatusSleeps[i];
            matchStatusSleepMap.put(matchStatusSleep.getMatchStatus(), matchStatusSleep.getDelay());
        }
    }
}
