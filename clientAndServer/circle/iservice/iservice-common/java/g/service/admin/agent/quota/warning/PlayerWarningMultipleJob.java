package g.service.admin.agent.quota.warning;

import g.service.warning.IPlayerWarningMultipleService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created by lenovo on 2017/3/2.
 */
public class PlayerWarningMultipleJob extends QuartzJobBean {

    @Autowired
    private IPlayerWarningMultipleService playerWarningMultipleService;

    private Log log = LogFactory.getLog(PlayerWarningMultipleJob.class);

    public void run() throws Exception{
        executeInternal(null);
    }
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("预警赢得金币和投注金额倍数比开始:");
        boolean bool =  playerWarningMultipleService.quartzWarningMultiple();
        log.info("预警赢得金币和投注金额倍数比结束:"+bool);
    }
}
