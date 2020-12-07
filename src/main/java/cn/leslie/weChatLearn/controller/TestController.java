package cn.leslie.weChatLearn.controller;

import cn.leslie.weChatLearn.config.WeChatConfig;
import cn.leslie.weChatLearn.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private WeChatConfig weChatConfig;

    @RequestMapping("testWX")
    public String testWX(){
        System.out.println(weChatConfig.getAppId());
        return weChatConfig.getAppId();
    }

    @Autowired
    private VideoMapper videoMapper;

    @RequestMapping("testDB")
    public Object testDB(){

        return videoMapper.findAll();
    }



}
