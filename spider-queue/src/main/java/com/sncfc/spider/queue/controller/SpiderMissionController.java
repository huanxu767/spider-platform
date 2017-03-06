package com.sncfc.spider.queue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sncfc.spider.dubbo.SpiderWorkerService;
import com.sncfc.spider.queue.service.IMissionService;


@Controller
@RequestMapping("/mission")
public class SpiderMissionController {
    @Autowired
    SpiderWorkerService spiderWorkerService;
    @Autowired
    private IMissionService missionService;

    @ResponseBody
    @RequestMapping(value = "test")
    public String test(String type) {
//		Map map = missionService.queryMission(type);
        System.out.println("dsdsd");
        return "test";
    }


}
