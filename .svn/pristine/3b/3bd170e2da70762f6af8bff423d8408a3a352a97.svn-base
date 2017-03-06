package com.sncfc.spider.admin.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by 123 on 2016/9/30.
 */
@RestController
@RequestMapping("/test")
public class RestFulController {


    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public String test(@PathVariable("id") Integer id) {
        return id + "";
    }

}
