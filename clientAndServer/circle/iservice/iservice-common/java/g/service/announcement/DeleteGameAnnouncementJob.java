package g.service.announcement;

import g.model.announcement.po.GameAnnouncement;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * 游戏公告任务调度 销毁任务
 * Created by black on 2016/12/28.
 */
public class DeleteGameAnnouncementJob extends QuartzJobBean {

    private Log log = LogFactory.getLog(DeleteGameAnnouncementJob.class);

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            GameAnnouncement gameAnnouncement = (GameAnnouncement)context.getMergedJobDataMap().get("gameAnnouncement");
            JobKey jobKey = JobKey.jobKey("s_gameAnnouncement_" + gameAnnouncement.getId(), "s_group_" + gameAnnouncement.getId());
            scheduler.deleteJob(jobKey);
            log.info("销毁调度器" + gameAnnouncement.getId());
        } catch (Exception e) {
            log.error(e);
        }
    }
}
