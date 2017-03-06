package com.sncfc.spider.dubbo.impl;

import java.util.HashMap;
import java.util.Map;

import com.sncfc.spider.worker.propertie.ConfigProperties;
import com.sncfc.spider.worker.utils.HttpManager;
import com.sncfc.spider.worker.utils.ScriptCacheStatic;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sncfc.spider.dubbo.SpiderWorkerService;
import com.sncfc.spider.worker.utils.SpiderUtils;

@Service("spiderWorkerService")
public class SpiderWorkerServiceImpl implements SpiderWorkerService {

    Logger logger = LoggerFactory.getLogger(SpiderWorkerServiceImpl.class);

    Logger spiderLog = LoggerFactory.getLogger("spiderLog");
    @Autowired
    private ConfigProperties configProperties;

    @Override
    public void addSpiderScript(String type, String js) {
        ScriptCacheStatic.addScript(type, js);
    }

    @Override
    public void removeSpiderScript(String type) {
        ScriptCacheStatic.removeScript(type);
    }

    @Override
    public Map<String, Object> spiderByUrl(String url, String type, String fyType) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String html = "";
        try {
            if ("100001".equals(fyType)) {
                //类型1
                html = SpiderUtils.crawler(url, ScriptCacheStatic.getScript(type), type, configProperties.getProxyUrl(), configProperties.getProxyPort(), configProperties.isProxyFlag());
            }else if ("100002".equals(fyType)) {
                //类型2
                html = HttpManager.requestByPostMethod(url, ScriptCacheStatic.getScript(type), type, configProperties.getProxyUrl(), configProperties.getProxyPort(), configProperties.isProxyFlag());
            }else if ("100032".equals(fyType)) {
                //类型3
                html = HttpManager.requestRMW(url, ScriptCacheStatic.getScript(type), type, configProperties.getProxyUrl(), configProperties.getProxyPort(), configProperties.isProxyFlag());
            }else if ("1000346".equals(fyType)) {
                //百度类型
                html = HttpManager.requestGet(url, ScriptCacheStatic.getScript(type), type, configProperties.getProxyUrl(), configProperties.getProxyPort(), configProperties.isProxyFlag());
            }
            returnMap.put("resultFlag", true);
            if (!StringUtils.isEmpty(html)) {
                spiderLog.info(html);
            }
        } catch (Exception e) {
            returnMap.put("resultCode", false);
            logger.error("错误：url:" + url + ",", e);
        }
        return returnMap;
    }
}
