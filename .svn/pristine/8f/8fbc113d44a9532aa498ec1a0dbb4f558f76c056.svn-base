package com.sncfc.spider.admin.service;

import java.util.List;
import java.util.Map;


/**
 * 微信用户管理接口
 *
 * @author xuhuanR
 */
public interface IMissionService {
    /**
     * 新增任务
     *
     * @param params
     * @return
     */
    Long addMission(Map<String, String> params);

    /**
     * 查询任务类型列表
     *
     * @return
     */
    List<Map<String, String>> queryMissionTypes();

    /**
     * 查询任务树形结构
     *
     * @return
     */
    List<Map<String, String>> queryMissionTree();

    /**
     * 查询单个任务
     *
     * @param type
     * @return
     */
    Map queryMission(String type);
    /**
     * 查询单个任务
     *
     * @param id
     * @return
     */
    Map queryMissionById(String id);

    /**
     * 执行
     *
     * @param type
     * @return
     */
    int startMission(String type);

    /**
     * 中止
     *
     * @param type
     * @return
     */
    int endMission(String type);

    /**
     * 暂停
     *
     * @param type
     */
    int pauseMission(String type);

    /**
     * 查询JOB
     *
     * @param mission_id
     * @return
     */
    Map queryMissionJob(String mission_id);

    /**
     * 查询激活的job
     */
    List getJobs();

    /**
     * 查询
     * @param mission_id
     */
    void startMissionJob(String mission_id);

    void test(String string);

    Map testget();

    /**
     * 后门更新mission状态
     */
    void hmUpdate();

    List<Map<String, Object>> hmQueryAllMission();

    int updateMission(String missionId, String params, String js);

    int excute(String sql);

    List query(String sql);

    /**
     * 暂停job
     * @param mission_id
     * @return
     */
    void pauseJob(String mission_id);
    /**
     * 修改任务
     * @param params
     */
    void modifyMission(Map<String, String> params);

    void testInsert();
}
