package com.sncfc.spider.queue.dao;

import java.util.Map;


/**
 * 任务管理
 *
 * @author xuhuan
 */
public interface IMissionDao {

    /**
     * 查询任务明细
     *
     * @param id
     * @return
     */
    Map queryMission(String id);

    /**
     * 更新任务状态
     *
     * @param type
     * @param status
     */
    void updateMissionStatus(String type, int status);

    /**
     * 保存任务进度
     *
     * @param mission_id
     * @param index
     * @param pageCounts
     */
    void savePauseMission(String mission_id, int index, int pageCounts);

    /**
     * 查询已保存的
     *
     * @param missionId
     * @return
     */
    Map queryPauseMission(String missionId);

    /**
     * 更新暂停任务
     *
     * @param missionId
     * @param index
     * @param pageCounts
     */
    void updatePauseMission(String missionId, int index, int pageCounts);
}
