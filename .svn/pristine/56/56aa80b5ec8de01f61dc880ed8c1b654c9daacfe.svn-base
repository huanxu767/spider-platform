package com.sncfc.spider.worker.propertie;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by xuhuan on 2016/8/22.
 */
@Component("configProperties")
public class ConfigProperties {
    @Value("${proxy.url}")
    private String proxyUrl;

    @Value("${proxy.port}")
    private int proxyPort;

    @Value("${proxy.flag}")
    private boolean proxyFlag;

    public String getProxyUrl() {
        return proxyUrl;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public boolean isProxyFlag() {
        return proxyFlag;
    }
}
