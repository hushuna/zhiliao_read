package com.zhiliao.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: hsn
 * @description:
 * @date: 2019/10/8 11:14
 **/
@RestController
public class TestController {

    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "/test")
    public String test(){
        return "hello World!!!";
    }
}
