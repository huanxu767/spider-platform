package com.sncfc.spider.dubbo;


import java.util.List;
import java.util.Map;

public interface SpiderFileProcessService {

	/**
	 * 查询文件
	 * @param fileName
	 */
	List<Map> getFiles(String fileName);

	/**
	 * 解析日志
	 * @param params
	 */
	void analysisLog(Map params);
}
