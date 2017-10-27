package g.service.admin.agent.quota.statistics;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.soul.commons.lang.DateTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 代理旗下玩家额度消耗 每天 一点三十分
 * Created by black on 2016/12/14.
 */
public class QuotaStatisticsJob extends QuartzJobBean {

    @Autowired
    private IAgentQuotaStatisticsService statisticsService;

    private Log log = LogFactory.getLog(QuotaStatisticsJob.class);

    public void run() throws Exception{

        executeInternal(null);
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        String endString =  DateTool.formatDate(new Date(), DateTool.FMT_HYPHEN_DAY) + " 00:00:00";
        Timestamp endTime = Timestamp.valueOf(endString);
        Map map = new HashMap();
        map.put("endTime", endTime);
        boolean message = statisticsService.consumePlayerQuota(map);
        log.info("代理旗下玩家额度消耗" + message);
    }

}
