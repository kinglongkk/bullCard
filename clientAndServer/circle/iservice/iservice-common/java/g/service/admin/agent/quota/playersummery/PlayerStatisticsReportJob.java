package g.service.admin.agent.quota.playersummery;

import g.service.playerstatistics.IPlayerDataStatisticsService;
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
 * 代理盈亏 每天 二点十五分
 * Created by black on 2017/6/19.
 */
public class PlayerStatisticsReportJob extends QuartzJobBean {

    @Autowired
    private IPlayerDataStatisticsService statisticsService;

    private Log log = LogFactory.getLog(PlayerStatisticsReportJob.class);

    public void run() throws Exception{

        executeInternal(null);
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        String endString =  DateTool.formatDate(new Date(), DateTool.FMT_HYPHEN_DAY) + " 00:00:00";
        Timestamp endTime = Timestamp.valueOf(endString);
        Map map = new HashMap();
        map.put("endTime", endTime);
        boolean message = statisticsService.dataStatisticsReport(map);
        log.info("代理盈亏" + message);
    }

}
