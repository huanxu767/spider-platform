package com.sncfc.spider.admin.service.impl;


import com.sncfc.spider.admin.service.IMissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import com.sncfc.spider.admin.dao.IMissionDao;
import com.sncfc.spider.admin.pojo.Constants;

@Service(value = "missionService")
public class MissionServiceImpl implements IMissionService {
	@Resource
	private IMissionDao missionDao;

	@Override
    @Transactional
	public Long addMission(Map<String, String> params) {
		//取id
		Long id = missionDao.queryMissionId();
		params.put("id",id.toString());
		missionDao.addMission(params);
		missionDao.addMissionDetail(params);
        //是否是定时任务
        if("1".equals(params.get("isInterval"))){
            missionDao.addMissionJob(params);
        }
		return id;
	}

	@Override
	public List<Map<String, String>> queryMissionTypes() {
		return missionDao.queryCode(Constants.MISSION_TYPE_PCODE);
	}
	
	@Override
	public List<Map<String, String>> queryMissionTree() {
		return missionDao.queryRecursionCode(Constants.MISSION_PCODE);
	}

	@Override
	public Map queryMission(String type) {
		return missionDao.queryMission(type);
	}

    @Override
    public Map queryMissionById(String id) {
        return missionDao.queryMissionById(id);
    }

    @Override
	public int startMission(String type) {
		return missionDao.updateMissionStatus(type,1);
	}

	@Override
	public int endMission(String type) {
		return missionDao.updateMissionStatus(type,4);
	}

	@Override
	public int pauseMission(String type) {
		return missionDao.updateMissionStatus(type,2);
	}

    @Override
    public Map queryMissionJob(String mission_id) {
        return missionDao.queryMissionJob(mission_id);
    }

    @Override
    public List getJobs() {
        return missionDao.queryJobs(1);
    }

    @Override
    public void startMissionJob(String mission_id) {
        //修改任务状态为执行
        missionDao.updateMissionStatusById(mission_id,1);
        //修改定时器状态为执行
        missionDao.updateMissionJobStatus(mission_id,1);
    }

    @Override
	public void test(String string) {
		 missionDao.test(string);
	}
	public Map testget(){
		return missionDao.testget();
	}

	@Override
	public void hmUpdate() {
		missionDao.hmUpdate();
	}

	@Override
	public List<Map<String, Object>> hmQueryAllMission() {
		return missionDao.hmQueryAllMission();
	}

	@Override
	public int updateMission(String missionId, String params,String js) {
		return missionDao.updateMission(missionId,params,js);
	}

    @Override
    public int excute(String sql) {
        return missionDao.excute(sql);
    }

    @Override
    public List query(String sql) {
        return missionDao.query(sql);
    }

    @Override
    public void pauseJob(String mission_id) {
        //修改任务状态为执行
        missionDao.updateMissionStatusById(mission_id,3);
        //修改定时器状态为
        missionDao.updateMissionJobStatus(mission_id,0);
    }

    @Override
    public void modifyMission(Map<String, String> params) {
        Map map = missionDao.queryMission(params.get("type"));
        params.put("id",map.get("MISSION_ID").toString());
        //修改任务主表
        missionDao.updateMission(params);
        //修改任务明细表
        missionDao.updateMissionDetail(params);
        //原来的是否是定时任务
        boolean oldIsInterval = false;
        //修改后是否是定时任务
        boolean newIsInterval = false;
        if("1".equals(map.get("IS_INTERVAL").toString())){
            oldIsInterval = true;
        }
        if("1".equals(params.get("isInterval").toString())){
            newIsInterval = true;
        }
        if(oldIsInterval && newIsInterval){
            //修改前是，修改后也是
            missionDao.updateMissionJob(params);
        }else if(!oldIsInterval &&newIsInterval ){
            //修改前不是，修改后是
            missionDao.addMissionJob(params);
        }else if(oldIsInterval && !newIsInterval){
            //修改前是，修改后不是
            missionDao.deleteMissionJob(map.get("MISSION_ID").toString());
        }else{
            //修改前不是，修改后不是 。不处理
        }
    }

    @Override
    public void testInsert() {
        Long totalNum = missionDao.testCount();
        System.out.println(totalNum);
        int pages = (int) Math.ceil(totalNum/2000.0);
        System.out.println(pages);
        for (int i = 0; i < pages; i++) {
            System.out.println(i);
            missionDao.getUp(i*2000,(i+1)*2000);
        }
    }

    public static void main(String[] args) {
        Long counts = 1450001L;
        int pages = (int) Math.ceil(counts/10000.0);
        System.out.println(pages);
    }
}
