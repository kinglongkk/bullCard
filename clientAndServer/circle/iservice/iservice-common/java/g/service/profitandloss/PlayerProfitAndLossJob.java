package g.service.profitandloss;

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
 * 玩家盈亏统计调度 每天 一点十五分
 * Created by black on 2017/5/31.
 */
public class PlayerProfitAndLossJob extends QuartzJobBean {

    @Autowired
    private IPlayerProfitAnsLossService profitAnsLossService;

    private Log log = LogFactory.getLog(PlayerProfitAndLossJob.class);

    public void run() throws Exception {

        executeInternal(null);
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        Map map = new HashMap();
        String endString =  DateTool.formatDate(new Date(), DateTool.FMT_HYPHEN_DAY) + " 00:00:00";
        Timestamp endTime = Timestamp.valueOf(endString);
        map.put("endTime", endTime);
        boolean message = profitAnsLossService.playerProfitAndLoss(map);
        log.info("玩家盈亏消耗调度器" + message);
    }
}
