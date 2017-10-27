package g.service.profitOrder;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.soul.commons.lang.DateTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by black on 2016/11/1.
 * 任务调度 盈利榜 每天 一点
 */
public class MatchProfitJob extends QuartzJobBean {

    @Autowired
    private IPlayerProfitService profitService;

    private Log log = LogFactory.getLog(MatchProfitJob.class);

    public void run() throws Exception{

        executeInternal(null);
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        Map map = new HashMap();
        //删除过期的盈利榜数据
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);
        Date lastWeek = calendar.getTime();
        Date oneDay = DateTool.parseDate(DateTool.formatDate(lastWeek, DateTool.FMT_HYPHEN_DAY) + " 00:00:00", DateTool.FMT_HYPHEN_DAY_CLN_SECOND);
        Date endDay = DateTool.addDays(oneDay, -1);
        map.put("endDay", endDay);
        boolean isSuccess = profitService.deleteExpiredData(map);
        log.info("删除过期的盈利榜数据" + isSuccess);
        //采集玩家盈利榜信息
        String endString =  DateTool.formatDate(new Date(), DateTool.FMT_HYPHEN_DAY) + " 00:00:00";
        Timestamp endTime = Timestamp.valueOf(endString);
        map.clear();
        map.put("endtime", endTime);
        boolean message = profitService.playerProfitOrder(map);
        log.info("玩家盈利榜任务调度器采集信息" + message);
    }

}
