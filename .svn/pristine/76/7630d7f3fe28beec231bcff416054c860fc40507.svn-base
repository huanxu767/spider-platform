package com.sncfc.spider.queue.service;

import java.util.Map;

public interface IMissionService {

	/**
	 * 查询单个任务
	 * @param type
	 * @return
	 */
	Map queryMission(String type);
	/**
	 * 任务结束
	 * @param type
	 */
	void completeMission(String type);
	/**
	 * 暂停任务
	 * @param type
	 */
	void pauseMission(String type, int index,int pageCounts);
	/**
	 * 查询暂停任务
	 * @param type
	 * @return
	 */
	Map queryPauseMission(String type);
	/**
	 * 任务结束
	 * @param type
	 */
	void startMission(String type);
}
