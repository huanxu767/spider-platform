package com.sncfc.spider.admin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.sncfc.spider.admin.factory.QuartzJobFactory;
import com.sncfc.spider.admin.pojo.ScheduleJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sncfc.spider.admin.pojo.BaseResultBean;
import com.sncfc.spider.admin.service.IMissionService;
import com.sncfc.spider.dubbo.SpiderMissionService;



@Controller
@RequestMapping("/spider")
public class SpiderBrainController extends BaseActionController{
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
	@Autowired
	private SpiderMissionService spiderMissionService;
	//广播调用
	@Autowired
	private SpiderMissionService spiderBroadcastService;
	
	@Autowired
	private IMissionService missionService;
	/**
	 * 开始任务
	 * @return
	 */
    @RequestMapping(value = "startMission" )
    public void startMission(HttpServletResponse response,String type){
		Map mission = missionService.queryMission(type);
		BaseResultBean resultBean = new BaseResultBean();
		if(mission == null){
			resultBean.setSuccess(false);
			resultBean.setErrorMsg("该任务尚未创建");
		}else if("1".equals(mission.get("STATUS"))){
			resultBean.setSuccess(false);
			resultBean.setErrorMsg("该任务已经执行");
		}else {
            if("1".equals(mission.get("IS_INTERVAL").toString())){
                //定时任务
                //根据ID 查询定时任务表
                Map jobMap = missionService.queryMissionJob(mission.get("MISSION_ID").toString());
                jobMap.put("JOB_STATUS","1");
                ScheduleJob job = QuartzJobFactory.transMapToJob(jobMap);
                try {
                    QuartzJobFactory.addJob(job,schedulerFactoryBean);
                    //修改状态
                    missionService.startMissionJob(mission.get("MISSION_ID").toString());
                    resultBean.setSuccess(true);
                } catch (Exception e) {
                    resultBean.setSuccess(false);
                    resultBean.setErrorMsg("定时器启动失败");
                    logger.error("定时器启动失败：",e);
                }
            }else{
                //非定时任务
                resultBean.setSuccess(true);
                spiderMissionService.startMissionByType(type);
            }
		}
		responseWithJson(response, resultBean);
    }	
    
    /**
	 * 暂停任务
	 * @return
	 */
    @RequestMapping(value = "pauseMission" )
    public void pauseMission(HttpServletResponse response,String type){
		BaseResultBean resultBean = new BaseResultBean();
		Map mission = missionService.queryMission(type);
		if(mission == null){
			resultBean.setSuccess(false);
			resultBean.setErrorMsg("该任务尚未创建");
		}else if("1".equals(mission.get("STATUS"))){
			//执行中
            //是否是定时任务
            if("1".equals(mission.get("IS_INTERVAL").toString())){
                Map jobMap = missionService.queryMissionJob(mission.get("MISSION_ID").toString());
                try {
                    QuartzJobFactory.removeJob(jobMap.get("JOB_NAME").toString(),jobMap.get("JOB_GROUP").toString(),schedulerFactoryBean);
                    missionService.pauseJob(mission.get("MISSION_ID").toString());
                    resultBean.setSuccess(true);
                } catch (Exception e) {
                    resultBean.setSuccess(false);
                    resultBean.setErrorMsg("定时器关闭失败");
                    logger.error("定时器关闭失败：",e);
                }
            }else{
                resultBean.setSuccess(true);
                missionService.pauseMission(type);
                spiderBroadcastService.pause(type);
            }
		}else {
//			非执行中
			missionService.pauseMission(type);
			resultBean.setSuccess(true);
			missionService.startMission(type);
		}
		responseWithJson(response, resultBean);
    }	
    
    
	/**
	 * 停止任务
	 * @return
	 */
    @RequestMapping(value = "stopMission" )
    public void stopMission(HttpServletResponse response,String type){
		BaseResultBean resultBean = new BaseResultBean();
		Map mission = missionService.queryMission(type);
		if(mission == null){
			resultBean.setSuccess(false);
			resultBean.setErrorMsg("该任务尚未创建");
		}else if("1".equals(mission.get("STATUS"))){
			//执行中
			resultBean.setSuccess(true);
			missionService.endMission(type);
			spiderBroadcastService.pause(type);
		}else {
//			非执行中
			missionService.endMission(type);
			resultBean.setSuccess(true);
			missionService.startMission(type);
		}
		responseWithJson(response, resultBean);
    }

    /**
     * 测试定时器
     *
     * @return
     */
    @RequestMapping(value = "interval")
    public void interval() throws Exception {
    }
    @RequestMapping(value = "addTest")
    public void addTest() throws Exception {
        ScheduleJob nJob = new ScheduleJob();
        nJob.setJobId("10005");
        nJob.setJobName("data_import3" );
        nJob.setJobGroup("spider");
        nJob.setJobStatus("1");
        nJob.setCronExpression("0/5 * * * * ?");
        nJob.setDesc("数据导入任务");
        QuartzJobFactory.addJob(nJob,schedulerFactoryBean);

    }
        @RequestMapping(value = "pauseTest")
    public void pauseTest() throws Exception {
        try {
//            String triggerName = "data_import3";
            String triggerName = "baidu";
            String triggerGroupName = "spider";
            QuartzJobFactory.removeJob(triggerName,triggerGroupName,schedulerFactoryBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
