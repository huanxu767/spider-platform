package com.sncfc.spider.dubbo.impl;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import com.sncfc.spider.dubbo.SpiderLogBackService;
import com.sncfc.spider.worker.utils.FileUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service("spiderLogBackService")
public class SpiderLogBackServiceImpl implements SpiderLogBackService {
    private static final String LOG_BACK_NAME = "logback.xml";

    @Override
    public void releaseControlLog() {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        System.out.println("releaseControlLog");
        lc.reset();
    }

    @Override
    public void startLog() {
        System.out.println("startLog");
        FileUtils fileUtils= new FileUtils();
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(lc);
        try {
            configurator.doConfigure(fileUtils.getPath() + LOG_BACK_NAME);
        } catch (JoranException e) {
            e.printStackTrace();
        }
    }
}
