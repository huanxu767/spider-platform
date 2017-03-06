package com.sncfc.spider.fileparse.controller;

import com.sncfc.spider.fileparse.service.IFileProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Controller
@RequestMapping("/mission")
public class SpiderMissionController {

    @Autowired
    private IFileProcessService fileProcessService;

    @ResponseBody
    @RequestMapping(value = "test")
    public String test(String type) throws IOException {
//        List<String> list = new ArrayList();
//        list.add("100005$@$$2016-8-24 12:38:59$@$$张文$@$$13026892***$@$$2,500.00$@$$32");
//        list.add("100005$@$$2016-8-24 12:38:59$@$$张文$@$$13026892***$@$$2,500.00$@$$32");
//        list.add("100005$@$$2016-8-24 12:38:59$@$$张文$@$$13026892***$@$$2,500.00$@$$32");
//        list.add("100005$@$$2016-8-24 12:38:59$@$$张文$@$$13026892***$@$$2,500.00$@$$32");
//        list.add("100005$@$$2016-8-24 12:38:59$@$$张文$@$$13026892***$@$$2,500.00$@$$32");
//        list.add("100005$@$$2016-8-24 12:38:59$@$$张文$@$$13026892***$@$$2,500.00$@$$32");
//        list.add("100005$@$$2016-8-24 12:38:59$@$$张文$@$$13026892***$@$$2,500.00$@$$32");
//        list.add("100005$@$$2016-8-24 12:38:59$@$$张文$@$$13026892***$@$$2,500.00$@$$32");
//        fileProcessService.addBlackOrGrayList(list);
        File file = new File("/home/weblogic/java/remoteLogs/work05/spiderfile.log");
        File dest = new File("/home/weblogic/java/analysisedLogs/tmp/spiderfilexxxx.log");
        Path path = Files.copy(file.toPath(), dest.toPath());
        return path.toString();
    }


}
