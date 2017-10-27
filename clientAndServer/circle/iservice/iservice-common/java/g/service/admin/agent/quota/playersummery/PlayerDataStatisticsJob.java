package g.service.admin.agent.quota.playersummery;

import g.service.playerstatistics.IPlayerDataStatisticsService;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 玩家盈利数据统计明细
 */
public class PlayerDataStatisticsJob extends QuartzJobBean {

    @Autowired
    private IPlayerDataStatisticsService playerDataStatisticsService;

    private Log log = LogFactory.getLog(PlayerDataStatisticsJob.class);
    public void run() throws Exception{
        executeInternal(null);
    }
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("玩家数据盈亏统计明细开始:");
        boolean bool =  playerDataStatisticsService.quartzPlayerDataStatistics();
        log.info("玩家数据盈亏统计明细结束:"+bool);
    }
}
