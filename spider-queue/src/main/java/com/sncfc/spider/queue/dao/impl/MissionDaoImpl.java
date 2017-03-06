package com.sncfc.spider.queue.dao.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.sncfc.spider.queue.dao.IMissionDao;
import com.sncfc.spider.queue.utils.BaseJdbcDAO;

@Repository(value = "missionDao")
public class MissionDaoImpl extends BaseJdbcDAO implements IMissionDao {
	/**
	 * 数据库别名
	 */
	private final static String DB_ALIAS= "cfc_rpt.";
	private final static Logger logger = Logger.getLogger(MissionDaoImpl.class);

	@Override
	public Map queryMission(String type) {
		String sql = " select sm.mission_id id,sm.mission_type type,sm.js,sm.thread_num,sm.status,sm.sleep_time,sm.parse_type,sm.describe,sm.sleep_time,sm.is_interval,"
				+ " sd.url_path url,sd.params,sd.start_page,sd.end_page,"
				+ " (select code_name  from "+DB_ALIAS+"s_code sc where sc.code_number =  sm.mission_type) type_name,"
				+ " (select code_name  from "+DB_ALIAS+"s_code sc where sc.code_number =  sm.parse_type)parse_name,"
				+ " to_char(sm.create_time,'yyyy-mm-dd hh24:mi:ss') create_time,"
				+ " to_char(sm.finish_time,'yyyy-mm-dd hh24:mi:ss') finish_time"
				+ " from "+DB_ALIAS+"s_mission sm"
				+ " left join "+DB_ALIAS+"s_mission_detail sd on sd.mission_id = sm.mission_id"
				+ " where sm.mission_type = ?";
		return queryForMap(sql, new Object[] { type });
	}

	@Override
	public void updateMissionStatus(String type, int status) {
		String sql = " update "+DB_ALIAS+"s_mission set status = ?,finish_time = sysdate where mission_type = ? ";
		update(sql, new Object[] { status,type});
	}
	
	@Override
	public void savePauseMission(String mission_id, int index,int pageCounts) {
		String sql = " insert into "+DB_ALIAS+"s_mission_pause(pause_id,mission_id,current_page,page_counts,create_date) " +
				"values("+DB_ALIAS+"seq_s_mission_pause.nextval,?,?,?,sysdate)";
		update(sql, new Object[] { mission_id,index,pageCounts});
	}
	
	@Override
	public Map queryPauseMission(String missionId) {
		String sql = " select * from "+DB_ALIAS+"s_mission_pause where mission_id = ?";
		return queryForMap(sql, new Object[] { missionId });
	}

	@Override
	public void updatePauseMission(String missionId, int index, int pageCounts) {
		String sql = " update "+DB_ALIAS+"s_mission_pause set  current_page = current_page+?,page_counts = ? where mission_id = ?";
		update(sql, new Object[] { index,pageCounts,missionId});
	}
}
