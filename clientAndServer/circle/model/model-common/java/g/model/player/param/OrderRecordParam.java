package g.model.player.param;

import java.util.Date;

/**
 * 查询下单记录接口的参数对象
 *
 * Created by jerry on 16-04-05.
 */
public class OrderRecordParam extends WalletParam {

    private static final long serialVersionUID = 3392830837556149616L;


    /**
     * 查询时间段的起始值
     */
    private Date startTime;
    /**
     * 查询时间段的终止值
     */
    private Date endTime;
    /**
     * 查询的起始id
     */
    private Long startId;

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

    public Long getStartId() {
        return startId;
    }

    public void setStartId(Long startId) {
        this.startId = startId;
    }
}
