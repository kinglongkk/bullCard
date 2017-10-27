package g.admin.agent.quota.transaction.form;

import org.soul.web.support.IForm;

import java.util.Date;


/**
 * 额度日志查询表单验证对象
 *
 * @author black
 * @time 2016-12-3 10:47:44
 */
public class AgentQuotaTransactionSearchForm implements IForm {

    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;

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