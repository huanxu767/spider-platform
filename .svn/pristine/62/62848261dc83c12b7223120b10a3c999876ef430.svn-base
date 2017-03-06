package com.sncfc.spider.queue.utils;

import java.util.HashMap;
import java.util.Map;

public class CacheStatic {
	/**
	 * 存储任务状态
	 */
	private static Map<String,Integer> STATIC_MISSION = new HashMap<String,Integer>();
	/**
	 * 新增任务
	 * @param type
	 */
	public static void addMission(String type){
		STATIC_MISSION.put(type,1);
	}
	
	/**
	 * 结束任务
	 * @param type
	 */
	public static void stopMission(String type){
		STATIC_MISSION.remove(type);

	}
	
	/**
	 * 是否有
	 * @param type
	 */
	public static boolean has(String type){
		return STATIC_MISSION.containsKey(type);
	}
	/**
	 * 是否暂停
	 * @param type
	 * @return true 暂停 ,false 不暂停
	 */
	public static boolean isPause(String type) {
		Integer val = STATIC_MISSION.get(type);
		return val == 1 ? false : true;
	}
	/**
	 * 暂停任务
	 * @param type
	 */
	public static void pauseMission(String type){
		STATIC_MISSION.put(type, 2);
	}
}

