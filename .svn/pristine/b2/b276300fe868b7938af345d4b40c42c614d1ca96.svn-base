package com.sncfc.spider.admin.dao.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sncfc.spider.admin.dao.IMissionDao;
import com.sncfc.spider.admin.pojo.ScheduleJob;
import com.sncfc.spider.admin.utils.BaseJdbcDAO;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import org.springframework.util.StringUtils;

@Repository(value = "missionDao")
public class MissionDaoImpl extends BaseJdbcDAO implements IMissionDao {
	/**
	 * 数据库别名
	 */
	private final static String DB_ALIAS= "cfc_rpt.";
	private final static Logger logger = Logger.getLogger(MissionDaoImpl.class);

	@Override
	public Long queryMissionId() {
		return this.getJdbcTemplate().queryForObject(
				"select "+DB_ALIAS+"seq_s_mission.nextval from dual",Long.class);
	}

	@Override
	public int addMission(Map<String, String> params) {
		String sql = "insert into "+DB_ALIAS+"s_mission(mission_id,mission_type,js,thread_num,parse_type,sleep_time,describe,create_time,is_interval) "
				+ "values(?,?,?,?,?,?,?,sysdate,?)";
		return update(
				sql,
				new Object[] { params.get("id"),params.get("type"), params.get("js"),
						params.get("threadNum"), params.get("parseType"),
						params.get("sleepTime"),params.get("describe") ,params.get("isInterval")});
	}

	@Override
	public int addMissionDetail(Map<String, String> params) {
		String sql = "insert into "+DB_ALIAS+"s_mission_detail(detail_id,mission_id,url_path,params,start_page,end_page) "
				+ "values("+DB_ALIAS+"seq_s_mission_detail.nextval,?,?,?,?,?)";
		return update(
				sql,
				new Object[] { params.get("id"), params.get("url"),
						params.get("pams"), params.get("startPage"),
						params.get("endPage") });
	}

	@Override
	public List queryCode(String missionTypePcode) {
		String sql = "select * from "+DB_ALIAS+"s_code where p_code = ? order by sn asc";
		return queryForList(sql, new Object[] { missionTypePcode });
	}
	
	@Override
	public List queryRecursionCode(String rootId) {
		String sql = "select code_id id,code_number code,p_code_number p_code,code_name name,sn,extra_one from " +
				""+DB_ALIAS+"s_code start with code_id = ? connect by prior code_id = p_code_number ";
		return queryForList(sql, new Object[] { rootId });
	}

	@Override
	public Map queryMission(String type) {
		String sql = " select sm.mission_id,sm.mission_type type,sm.js,sm.thread_num,sm.status,sm.sleep_time,sm.parse_type,sm.describe,sm.sleep_time,sm.is_interval,"
				+ " sd.url_path url,sd.params,sd.start_page,sd.end_page,"
				+ " (select code_name  from "+DB_ALIAS+"s_code sc where sc.code_number =  sm.mission_type) type_name,"
				+ " (select code_name  from "+DB_ALIAS+"s_code sc where sc.code_number =  sm.parse_type)parse_name,"
				+ " to_char(sm.create_time,'yyyy-mm-dd hh24:mi:ss') create_time,"
				+ " to_char(sm.finish_time,'yyyy-mm-dd hh24:mi:ss') finish_time,sj.job_name,sj.job_group,sj.cron_expression,sj.job_status"
				+ " from "+DB_ALIAS+"s_mission sm"
				+ " left join "+DB_ALIAS+"s_mission_detail sd on sd.mission_id = sm.mission_id"
                + " left join "+DB_ALIAS+"s_mission_job sj on sj.mission_id = sm.mission_id"
				+ " where sm.mission_type = ?";
		return queryForMap(sql, new Object[] { type });
	}

    @Override
    public int updateMissionStatusById(String mission_id, int i) {
        String sql = " update "+DB_ALIAS+"s_mission set status = ? where mission_id = ?";
        return update(sql, new Object[] { i,mission_id});
    }

    @Override
    public int updateMissionJobStatus(String mission_id, int i) {
        String sql = " update "+DB_ALIAS+"s_mission_job set job_status = ? where mission_id = ?";
        return update(sql, new Object[] { i,mission_id});
    }

    @Override
    public Map queryMissionById(String id) {
        String sql = " select sm.mission_id,sm.mission_type type,sm.js,sm.thread_num,sm.status,sm.sleep_time,sm.parse_type,sm.describe,sm.sleep_time,sm.is_interval,"
                + " sd.url_path url,sd.params,sd.start_page,sd.end_page,"
                + " (select code_name  from "+DB_ALIAS+"s_code sc where sc.code_number =  sm.mission_type) type_name,"
                + " (select code_name  from "+DB_ALIAS+"s_code sc where sc.code_number =  sm.parse_type)parse_name,"
                + " to_char(sm.create_time,'yyyy-mm-dd hh24:mi:ss') create_time,"
                + " to_char(sm.finish_time,'yyyy-mm-dd hh24:mi:ss') finish_time"
                + " from "+DB_ALIAS+"s_mission sm"
                + " left join "+DB_ALIAS+"s_mission_detail sd on sd.mission_id = sm.mission_id"
                + " where sm.mission_id = ?";
        return queryForMap(sql, new Object[] { id });
    }

    @Override
    public int updateMission(Map<String, String> params) {

        String sql = "update "+DB_ALIAS+"s_mission set js = ?,thread_num = ?,parse_type = ?,sleep_time = ?,describe=?,is_interval=? where mission_type = ?";
        return update(
                sql,
                new Object[] { params.get("js"),params.get("threadNum"),params.get("parseType"),
                        params.get("sleepTime"),params.get("describe"),params.get("isInterval"),params.get("type")});
    }

    @Override
    public int updateMissionDetail(Map<String, String> params) {
        String sql = "update "+DB_ALIAS+"s_mission_detail set url_path = ?,params = ? where mission_id = ?";
        return update(sql,new Object[] { params.get("url"),params.get("pams"),params.get("id") });
    }

    @Override
    public int updateMissionJob(Map<String, String> params) {
        String sql = "update "+DB_ALIAS+"s_mission_job set cron_expression = ? where mission_id = ?";
        return update(sql,new Object[] {params.get("cronExpression"),params.get("id")});
    }

    @Override
    public int deleteMissionJob(String mission_id) {
        String sql = "delete "+DB_ALIAS+"s_mission_job where mission_id = ?";
        return update(sql,new Object[] {mission_id});
    }

    @Override
    public Long testCount() {
        String sql = "SELECT COUNT(*) COUNTS  FROM S_TEMP2 S ";
        return  queryForObject(sql,Long.class);
    }

    @Override
    public void getUp(int start, int end) {
        String sql = "update s_mission_detail set params = params || (" +
                " select wm_concat(company_code) " +
                " from (" +
                " SELECT rownum rn,company_code  FROM( " +
                " select s.company_code from  S_TEMP2 S " +
                " ) where rownum <= ? ) where rn > ? )" +
                "where mission_id = '100050' ";
        System.out.println(start + " " + end + " SQL:"+ sql);
        update(sql,new Object[]{end,start});
    }

    @Override
	public int updateMissionStatus(String type, int status) {
		String sql = " update "+DB_ALIAS+"s_mission set status = ? where mission_type = ?";
		return update(sql, new Object[] { status,type});
	}

    @Override
    public int addMissionJob(Map params) {
        String sql = "insert into "+DB_ALIAS+"s_mission_job(mission_id,job_name,job_group,cron_expression,job_status) "
                + "values(?,?,?,?,0)";
        return update(
                sql,
                new Object[] { params.get("id"), params.get("mTypeName"),
                        params.get("jobGroup"), params.get("cronExpression")});
    }

    @Override
    public Map queryMissionJob(String missionId) {
        String sql = " select * from "+DB_ALIAS+"s_mission_job where mission_id = ?";
        return queryForMap(sql, new Object[]{missionId});
    }

    @Override
    public List queryJobs(int i) {
        String sql = "select * from "+DB_ALIAS+"s_mission_job where job_status = ?";
        return this.getJdbcTemplate().queryForList(sql, i);
    }

    @Override
	public void test(String string) {
		String sql = " insert into "+DB_ALIAS+"s_test values(2,?) ";
		update(sql, new Object[] { string});
	}
	
	public Map testget() {
		String sql = " select * from "+DB_ALIAS+"s_test where id = 1 ";
		return queryForMap(sql);
	}

	@Override
	public void hmUpdate() {
		String sql = " update "+DB_ALIAS+"s_mission set status = 0 ";
		update(sql);
	}
	@Override
	public List<Map<String,Object>> hmQueryAllMission() {
		String sql = " select mission_id,mission_type from "+DB_ALIAS+"s_mission";
		return queryForList(sql);
	}

	@Override
	public int updateMission(String missionId, String params,String js) {
		String sql = " update "+DB_ALIAS+"s_mission set js = ? where mission_id = ? ";
		update(sql,new Object[] { js,missionId});
		sql = " update "+DB_ALIAS+"s_mission_detail set params = ? where mission_id = ? ";
		return update(sql,new Object[] { params,missionId});
	}

    @Override
    public int excute(String sql) {
        return update(sql);
    }

    @Override
    public List query(String sql) {
        return queryForList(sql);
    }



    class JobMapper implements RowMapper{
        @Override
        public Object mapRow(ResultSet resultSet, int i) throws SQLException {
            ScheduleJob job = new ScheduleJob();
            job.setJobId(resultSet.getString("JOB_ID"));
            job.setJobName(resultSet.getString("JOB_NAME").toString());
            job.setJobGroup(resultSet.getString("JOB_GROUP").toString());
            job.setJobStatus(resultSet.getString("JOB_STATUS").toString());
            job.setCronExpression(resultSet.getString("CRON_EXPRESSION").toString());
            job.setDesc(resultSet.getString("JOB_DESC").toString());
            return job;
        }
    }
}
