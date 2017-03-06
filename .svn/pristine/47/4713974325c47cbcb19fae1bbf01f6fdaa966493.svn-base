package com.sncfc.spider.admin.factory;

import com.sncfc.spider.admin.pojo.ScheduleJob;
import com.sncfc.spider.admin.service.IMissionService;
import com.sncfc.spider.admin.utils.SpringContextHelper;
import com.sncfc.spider.dubbo.SpiderMissionService;
import org.quartz.*;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by 123 on 2016/9/29.
 */
@Component(value = "quartzJobFactory")
public class QuartzJobFactory implements Job {

    private static String JOB_DATA_NAME = "scheduleJob";


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("任务成功运行");
        IMissionService missionService = (IMissionService)SpringContextHelper.getBean("missionService");
        SpiderMissionService spiderMissionService = (SpiderMissionService)SpringContextHelper.getBean("spiderMissionService");
        ScheduleJob scheduleJob = (ScheduleJob)jobExecutionContext.getMergedJobDataMap().get(JOB_DATA_NAME);
        Map map = missionService.queryMissionById(scheduleJob.getJobId());
        spiderMissionService.startMissionByType(map.get("TYPE").toString());
        System.out.println("任务名称 = [" + scheduleJob.getJobName() + "]");
    }
    /**
     * 添加多任务
     * @param jobList
     * @param schedulerFactoryBean
     */
    public static void addJobs(List<ScheduleJob> jobList, SchedulerFactoryBean schedulerFactoryBean) throws Exception{
        for (ScheduleJob job : jobList) {
            addJob(job,schedulerFactoryBean);
        }
    }
    /**
     * 添加任务
     * @param job
     * @param schedulerFactoryBean
     */
    public static void addJob(ScheduleJob job,SchedulerFactoryBean schedulerFactoryBean) throws Exception{
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
            //获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            //不存在，创建一个
            if (null == trigger) {
                JobDetail jobDetail = JobBuilder.newJob(QuartzJobFactory.class).withIdentity(job.getJobName(), job.getJobGroup()).build();
                jobDetail.getJobDataMap().put(JOB_DATA_NAME, job);
                //表达式调度构建器
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
                //按新的cronExpression表达式构建一个新的trigger
                trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
                scheduler.scheduleJob(jobDetail, trigger);
            } else {
                // Trigger已存在，那么更新相应的定时设置
                //表达式调度构建器
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
                //按新的cronExpression表达式重新构建trigger
                trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
                //按新的trigger重新设置job执行
                scheduler.rescheduleJob(triggerKey, trigger);
            }
    }
    /**
     * 删除任务
     */
    public static void removeJob(String name,String groupName,SchedulerFactoryBean schedulerFactoryBean) throws Exception{
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(name, groupName);
        scheduler.pauseTrigger(triggerKey);// 停止触发器
        scheduler.unscheduleJob(triggerKey);// 移除触发器
    }
    public static ScheduleJob transMapToJob(Map map){
        ScheduleJob job = new ScheduleJob();
        job.setJobId(map.get("MISSION_ID").toString());
        job.setJobName(map.get("JOB_NAME").toString());
        job.setJobGroup(map.get("JOB_GROUP").toString());
        job.setJobStatus(map.get("JOB_STATUS").toString());
        job.setCronExpression(map.get("CRON_EXPRESSION").toString());
        return job;
    }
}
