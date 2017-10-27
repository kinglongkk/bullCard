package g.service.admin.agent.quota.playersummery;

import g.service.playerstatistics.IPlayerSummeryService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 玩家数据统计
 */
public class PlayerSummeryJob extends QuartzJobBean {

    @Autowired
    private IPlayerSummeryService playerSummeryService;

    private Log log = LogFactory.getLog(PlayerSummeryJob.class);

    public void run() throws Exception{
        executeInternal(null);
    }
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("玩家数据统计开始:");
       boolean bool =  playerSummeryService.quartzPlayerSummery();
        log.info("玩家数据统计结束:"+bool);
    }
}
