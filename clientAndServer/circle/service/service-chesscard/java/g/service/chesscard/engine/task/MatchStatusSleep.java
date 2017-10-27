package g.service.chesscard.engine.task;

/**
 * Created by longer on 2017/1/21.
 * 赛事状态 延迟对应表
 */
public class MatchStatusSleep {

    //押宝-执行时间延迟-流程
    public static MatchStatusSleep[] BULL_BAO_FLOW = new MatchStatusSleep[]{
            new MatchStatusSleep(MatchStatus.RUNNING, 17),
            new MatchStatusSleep(MatchStatus.SETTLED, 11),
            new MatchStatusSleep(MatchStatus.FINISHED, 0),


    };

    //百人-执行时间延迟-流程
    public static MatchStatusSleep[] BULL_100_FLOW = new MatchStatusSleep[]{
            new MatchStatusSleep(MatchStatus.RUNNING, 15),
            new MatchStatusSleep(MatchStatus.SETTLED, 15),
            new MatchStatusSleep(MatchStatus.FINISHED, 0),
    };

    private MatchStatus matchStatus;

    /**
     * 延时(单位：s)
     */
    private int delay;

    public MatchStatusSleep(MatchStatus matchStatus, int delay) {
        this.matchStatus = matchStatus;
        this.delay = delay;
    }

    public MatchStatus getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(MatchStatus matchStatus) {
        this.matchStatus = matchStatus;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
