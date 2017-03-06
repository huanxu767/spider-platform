package com.sncfc.spider.fileparse.utils;

import java.util.HashMap;
import java.util.Map;

public class CacheStatic {
	/**
	 * 存储解析方法
	 */
	private static Map<String,Integer> STATIC_ANALYSISE = new HashMap<String,Integer>();
	/**
	 * 新增任解析方法
	 * @param type
	 */
	public static void addAnalysiseCode(String type){
		STATIC_ANALYSISE.put(type,1);
	}
	
	/**
	 * 结束任务
	 * @param type
	 */
	public static void stopAnalysiseCode(String type){
		STATIC_ANALYSISE.remove(type);
	}
	
	/**
	 * 是否有
	 * @param type
	 */
	public static boolean has(String type){
		return STATIC_ANALYSISE.containsKey(type);
	}
}

