package com.sncfc.spider.dubbo;

import java.util.Map;

public interface SpiderWorkerService {
    /**
     * 增加脚本缓存--采用广播形式
     * @param type
     * @param js
     * @return
     */
    public void addSpiderScript(String type,String js);
    /**
     * 去除脚本缓存--采用广播形式
     * @param type
     * @return
     */
    public void removeSpiderScript(String type);
    /**
     * 根据单个URL爬取网页
     * @param url
     * @param type
     * @return
     */
    public Map<String,Object> spiderByUrl(String url,String type,String fyType);
}
