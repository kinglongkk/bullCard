package g.service.admin.agent.quota.warning;

import g.service.warning.IPlayerWarningMultipleService;
import g.service.warning.IPlayerWarningWinCountService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created by lenovo on 2017/3/2.
 */
public class PlayerWarningWinCountJob extends QuartzJobBean {

    @Autowired
    private IPlayerWarningWinCountService playerWarningWinCountService;

    private Log log = LogFactory.getLog(PlayerWarningWinCountJob.class);

    public void run() throws Exception{
        executeInternal(null);
    }
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("预警连赢派彩开始:");
        boolean bool =  playerWarningWinCountService.quartzWarningWinCount();
        log.info("预警连赢派彩结束:"+bool);
    }
}
