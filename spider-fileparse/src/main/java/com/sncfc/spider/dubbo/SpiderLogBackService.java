package com.sncfc.spider.dubbo;

/**
 * 关闭 重启logback
 */
public interface SpiderLogBackService {

	/**
	 *释放对filelog控制
	 */
	public void releaseControlLog();
	/**
	 *启动logback
	 */
	public void startLog();
}
