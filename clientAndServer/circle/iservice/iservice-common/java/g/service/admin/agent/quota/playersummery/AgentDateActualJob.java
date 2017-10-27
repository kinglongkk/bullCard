package g.service.admin.agent.quota.playersummery;


import g.service.playerstatistics.IAgentDateActualService;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 首页盈亏实时定时统计
 */
public class AgentDateActualJob extends QuartzJobBean {

    private Log log = LogFactory.getLog(AgentDateActualJob.class);

    @Autowired
    private IAgentDateActualService agentDateActualService;

    public void run() throws Exception{
        executeInternal(null);
    }
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        log.info("盈亏实时统计开始：");
        boolean bool = agentDateActualService.quartzAgentDateActual();
        log.info("盈亏实时统计结束："+bool);
    }
}
