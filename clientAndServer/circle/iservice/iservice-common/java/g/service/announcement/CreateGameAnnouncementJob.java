package g.service.announcement;

import g.model.announcement.po.GameAnnouncement;
import g.model.announcement.vo.GameAnnouncementListVo;
import g.model.enums.TimeUnitEnum;

import org.quartz.*;
import org.soul.commons.lang.DateTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Date;

/**
 * 游戏公告任务调度 创建任务
 * Created by black on 2016/12/24.
 */
public class CreateGameAnnouncementJob extends QuartzJobBean {

    private Log log = LogFactory.getLog(CreateGameAnnouncementJob.class);

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private IGameAnnouncementService announcementService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        GameAnnouncementListVo listVo = new GameAnnouncementListVo();
        listVo = announcementService.selectGameAnnouncement(listVo);
        //非空的情况下创建调度器
        if (listVo.getResult() != null && !listVo.getResult().isEmpty()) {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            for (GameAnnouncement announcement : listVo.getResult()) {
                //创建任务调度器
                createScheduleJob(scheduler, StartGameAnnouncementJob.class, announcement, "gameAnnouncement", true);
                //创建销毁任务调度器
                createScheduleJob(scheduler, DeleteGameAnnouncementJob.class, announcement, "gameAnnouncement", false);
            }
        }
    }

    /**
     * 创建任务调度器
     * @param scheduler Quartz Scheduler
     * @param clsJob 任务执行类
     * @param cls 实体类
     * @param name 标识
     * @param isCreateStart 是否为创建启动任务
     */
    protected void createScheduleJob(Scheduler scheduler, Class clsJob, GameAnnouncement cls, String name, boolean isCreateStart){

        String group = "group";
        if (isCreateStart) {
            name = "s_" + name + "_";
            group = "s_" + group + "_";
        } else {
            name = "d_" + name + "_";
            group = "d_" + group + "_";
        }
        TriggerKey triggerKey = TriggerKey.triggerKey(name + cls.getId(), group + cls.getId());
        try {
            Trigger trigger = scheduler.getTrigger(triggerKey);
            //判断是否存在
            if (trigger == null) {
                //创建所触发的任务调度器
                JobDetail jobDetail = JobBuilder.newJob(clsJob).withIdentity(name + cls.getId(),
                        group + cls.getId()).build();
                jobDetail.getJobDataMap().put("gameAnnouncement", cls);
                //表达式调度构建器
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronSchedule(cls, isCreateStart));
                //按新的表达式构建一个新的trigger
                trigger = TriggerBuilder.newTrigger().withIdentity(name + cls.getId(),
                        group + cls.getId()).withSchedule(scheduleBuilder).build();
                scheduler.scheduleJob(jobDetail, trigger);
            } else {
                //更新相应的定时设置
                ScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronSchedule(cls, isCreateStart));
                //按新的cronExpression表达式重新构建trigger
                trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
                //按新的trigger重新设置job执行
                scheduler.rescheduleJob(triggerKey, trigger);
            }
        }catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * 判断是否为静态任务
     * 创建任务调度器
     * @param announcement 游戏公告类
     * @param isCreate 创建任务(true)
     * @param isCreate 销毁任务(false)
     * @return String cronExpression
     */
    protected String cronSchedule(GameAnnouncement announcement, boolean isCreate) {

        if (isCreate) {
            if (announcement.getRepeat()) {
                return cronExpression(announcement.getRepeatTime(), announcement.getRepeatUnit());
            } else {
                return cronExpression(announcement.getValidityStartTime(), null);
            }
        } else {
            return cronExpression(announcement.getValidityStartTime(), announcement.getValidityEndTime());
        }
    }

    /**
     * 创建动态cronExpression表达式
     * @param repeatTime 重复间隔
     * @param repeatUnit 重复单位
     * @return String cronExpression
     */
    protected String cronExpression(Integer repeatTime, String repeatUnit){

        if (repeatTime != null && repeatUnit != null && !StringTool.isBlank(repeatUnit)) {
            if (repeatUnit.equals(TimeUnitEnum.DAY.getCode())) {
                return "0 0 0 0/" + repeatTime + " * ? *";
            } else if (repeatUnit.equals(TimeUnitEnum.HOUR.getCode())) {
                return "0 0 0/" + repeatTime + " * * ? *";
            } else if (repeatUnit.equals(TimeUnitEnum.MINUTE.getCode())) {
                return "0 0/" + repeatTime + " * * * ? *";
            } else if (repeatUnit.equals(TimeUnitEnum.SECOND.getCode())) {
                return "0/" + repeatTime + " * * * * ? *";
            }
        }
        return null;
    }

    /**
     * 创建静态cronExpression表达式
     * 开始任务：startDate
     * 销毁任务：startDate、endDate
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return String cronExpression
     */
    protected String cronExpression(Date startDate, Date endDate){

        if (startDate != null && endDate != null) {
            //判断是否为同一天，同一天则加一天
            if (DateTool.isSameDay(startDate, endDate)) {
                return DateTool.toCronExp(DateTool.addDays(endDate, 1));
            } else {
                return DateTool.toCronExp(endDate);
            }
        } else if (startDate != null && endDate == null) {
            return DateTool.toCronExp(startDate);
        }
        return null;
    }

}
