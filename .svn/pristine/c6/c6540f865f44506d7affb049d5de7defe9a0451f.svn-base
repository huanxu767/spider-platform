package com.sncfc.spider.admin.dao;

import java.util.List;
import java.util.Map;


/**
 * 任务管理
 *
 * @author xuhuan
 */
public interface IMissionDao {
    /**
     * 查询任务sequence
     *
     * @return
     */
    Long queryMissionId();

    /**
     * 添加任务
     *
     * @param params
     * @return
     */
    int addMission(Map<String, String> params);

    /**
     * 添加任务明细
     *
     * @param params
     * @return
     */
    int addMissionDetail(Map<String, String> params);

    /**
     * 查询任务类型列表
     *
     * @param missionTypePcode code父ID
     * @return
     */
    List<Map<String, String>> queryCode(String missionTypePcode);

    /**
     * 递归调用code表
     *
     * @param rootId
     * @return
     */
    List queryRecursionCode(String rootId);

    /**
     * 查询任务明细
     *
     * @param type
     * @return
     */
    Map queryMission(String type);

    /**
     * 修改任务状态
     *
     * @param type
     * @param i
     * @return
     */
    int updateMissionStatus(String type, int i);

    /**
     * 新增JOB任务
     *
     * @param map
     * @return
     */
    int addMissionJob(Map map);

    /**
     * 查询JOB
     *
     * @param mission_id
     * @return
     */
    Map queryMissionJob(String mission_id);

    /**
     * 查询任务JOB
     *
     * @param i 0 禁用 1启用
     * @return
     */
    List queryJobs(int i);

    void test(String string);

    Map testget();

    void hmUpdate();

    List<Map<String, Object>> hmQueryAllMission();

    int updateMission(String missionId, String params, String js);

    int excute(String sql);

    List query(String sql);

    /**
     * 根据任务编号修改任务状态
     * @param mission_id
     * @param i
     * @return
     */
    int updateMissionStatusById(String mission_id, int i);
    /**
     * 根据任务编号修改JOB状态
     * @param mission_id
     * @param i
     * @return
     */
    int updateMissionJobStatus(String mission_id, int i);

    /**
     * 根据任务编号查询任务明细
     * @param id
     * @return
     */
    Map queryMissionById(String id);

    /**
     * 修改任务主表
     * @param params
     */
    int updateMission(Map<String, String> params);

    /**
     * 修改任务明细表
     * @param params
     */
    int updateMissionDetail(Map<String, String> params);

    /**
     * 修改任务定时器表
     * @param params
     */
    int updateMissionJob(Map<String, String> params);

    /**
     * 删除明细表
     * @param mission_id
     */
    int deleteMissionJob(String mission_id);

    Long testCount();

    void getUp(int start, int end);

}
