package com.sncfc.spider.admin.listener;

import com.sncfc.spider.admin.factory.QuartzJobFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.sncfc.spider.admin.service.IMissionService;
import com.sncfc.spider.admin.pojo.ScheduleJob;



/**
 * Created by 123 on 2016/9/30.
 */
@Service
public class StartupListener implements ApplicationListener<ContextRefreshedEvent>{

    @Autowired
    private IMissionService missionService;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    /**
     * 判断应用是否已经启动
     */
    private static boolean isRunning = false;
    static Logger logger = LoggerFactory.getLogger(StartupListener.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() != null) {
            logger.debug("初始化JOB");
            /**
             * 重复执行判断
             */
            if(isRunning){
                return;
            }
            isRunning = true;
            try {
                //这里获取任务信息数据
                List<ScheduleJob> jobList = new ArrayList();
                List<Map> list = missionService.getJobs();
                for (Map map : list) {
                    ScheduleJob job = QuartzJobFactory.transMapToJob(map);
                    jobList.add(job);
                }
                if(!jobList.isEmpty()){
                    QuartzJobFactory.addJobs(jobList, schedulerFactoryBean);
                }
            } catch (Exception e) {
                logger.error("StartupListener初始化JOB失败", e);
            }
        }
    }
}
