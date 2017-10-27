package g.service.chesscard.engine.schedule;

import g.service.chesscard.engine.EngineConst;
import g.service.chesscard.engine.core.ChessCardEngine;
import org.soul.commons.lang.DateTool;

import java.util.Date;

/**
 * Created by Double on 2016/9/30.
 * 桌子时间点
 */
public class DeskTimePoint {

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date stopTime;


    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    /**
     * 下一个时间点
     * 开始: 上一场结束时间 + ${结束到开始的偏移量}
     * 结束: 本场开始时间   + ${开始到结束的偏移量}
     *
     * @return
     */
    public DeskTimePoint next() {
        DeskTimePoint timePoint = new DeskTimePoint();
        timePoint.setStartTime(
                DateTool.addSeconds(
                        this.getStopTime(),
                        EngineConst.MATCH_STOP_START_TIME_OFFSET
                ));
        timePoint.setStopTime(
                DateTool.addSeconds(
                        this.getStartTime(),
                        EngineConst.MATCH_START_STOP_TIME_OFFSET
                ));
        return timePoint;
    }

    /**
     * 创建时间点
     *
     * @param index
     * @return
     */
    public static DeskTimePoint create(int index) {
        DeskTimePoint point = new DeskTimePoint();
        point.setStartTime(DateTool.addSeconds(ChessCardEngine.START_TIME,
                EngineConst.EACH_DESK_TIME_POINT_OFFSET * index));
        point.setStopTime(DateTool.addSeconds(
                point.getStartTime()
                , EngineConst.MATCH_START_STOP_TIME_OFFSET));

        return point;
    }
}
