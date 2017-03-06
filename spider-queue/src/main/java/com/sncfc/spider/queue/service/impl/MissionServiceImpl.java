package com.sncfc.spider.queue.service.impl;


import java.util.Map;

import javax.annotation.Resource;

import com.sncfc.spider.queue.pojo.Constants;
import org.springframework.stereotype.Service;

import com.sncfc.spider.queue.dao.IMissionDao;
import com.sncfc.spider.queue.service.IMissionService;


@Service(value = "missionService")
public class MissionServiceImpl implements IMissionService{

	@Resource
	private IMissionDao missionDao;
	
	@Override
	public Map queryMission(String type) {
		return missionDao.queryMission(type);
	}

	@Override
	public void completeMission(String type) {
        //判断是否是定时任务
        Map missionMap = missionDao.queryMission(type);
        if("0".equals(missionMap.get("IS_INTERVAL").toString())){
            //不是定时任务
            missionDao.updateMissionStatus(type,Integer.parseInt(Constants.MISSION_COMPLETE));
        }
	}

	@Override
	public void pauseMission(String type, int index, int pageCounts){
		Map missionMap = missionDao.queryMission(type);
		missionDao.updateMissionStatus(type, Integer.parseInt(Constants.MISSION_PAUSE));
        String missionId = missionMap.get("ID").toString();
        //非定时任务
        Map pauseMap = missionDao.queryPauseMission(missionId);
        if(pauseMap == null){
            missionDao.savePauseMission(missionId,index,pageCounts);
        }else{
            missionDao.updatePauseMission(missionId,index,pageCounts);
        }
	}

	@Override
	public Map queryPauseMission(String type) {
		Map missionMap = missionDao.queryMission(type);
		String missionId = missionMap.get("ID").toString();
		return missionDao.queryPauseMission(missionId);
	}

	@Override
	public void startMission(String type) {
		missionDao.updateMissionStatus(type, Integer.parseInt(Constants.MISSION_DONING));
	}
}
