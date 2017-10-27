package g.model.api.param;

/**
 * 玩家盈亏参数
 * Created by black on 2017/6/1.
 */
public class ProfitAndLossParams extends PageParams {

    /**
     * 搜索 开始时间
     */
    private Integer startTime;
    /**
     * 搜索 结束时间
     */
    private Integer endTime;

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }
}
