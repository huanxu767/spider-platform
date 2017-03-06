package com.sncfc.spider.fileparse.propertie;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by xuhuan on 2016/8/22.
 */
@Component("configProperties")
public class ConfigProperties {
    @Value("#{setting[remotefile]}")
    private String fileHome;
    @Value("#{setting[analysisedLog]}")
    private String fileAnalysisedHome;

    public String getFileHome() {
        return fileHome;
    }

    public String getFileAnalysisedHome() {
        return fileAnalysisedHome;
    }
}
