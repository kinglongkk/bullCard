package g.service.admin.agent.quota.statistics;


import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 代理额度消耗 1月/次
 * 每个月的第二天 一点四十分
 * Created by black on 2016/12/15.1
 */
public class QuotaSumJob extends QuartzJobBean {

    @Autowired
    private IAgentQuotaStatisticsService statisticsService;

    private Log log = LogFactory.getLog(QuotaSumJob.class);

    public void run() throws Exception{

        executeInternal(null);
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        boolean message = statisticsService.consumeAgentQuota();
        log.info("代理额度消耗" + message);
        boolean isSuccess = statisticsService.truncateDate();
        log.info("清理代理额度消耗表数据" + isSuccess);
    }
}
