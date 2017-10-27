package g.model.api.param;

import java.util.Date;


/**
 * 玩家游戏记录参数
 * Created by black on 2016/11/8.
 */
public class BetRecordParams extends PageParams {

    /**
     * 搜索 开始时间
     */
    private java.util.Date startTime;
    /**
     * 搜索 结束时间
     */
    private java.util.Date endTime;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
