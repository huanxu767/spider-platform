package com.sncfc.spider.dubbo;



public interface SpiderMissionService {
	
	/**
	 * 执行任务
	 * @param type
	 */
	public void startMissionByType(String type);

    /**
	 * 暂停任务
	 * @param type
	 * @return
	 */
	public boolean pause(String type);
	
}
