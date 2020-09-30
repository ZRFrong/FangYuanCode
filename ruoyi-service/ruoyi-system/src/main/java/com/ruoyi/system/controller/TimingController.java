package com.ruoyi.system.controller;


import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.feign.RemoteTcpService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * 定时任务调用类
 * */
@RestController
@Api("timing")
@RequestMapping("timing")
public class TimingController {

    @Autowired
    private RemoteTcpService remoteTcpService;


    /*
     * 状态更新调用
     * */
    @GetMapping("startTiming")
    public R startTiming() {
        return remoteTcpService.strtTiming();
    }

    /*
     * 状态留跟定时调用
     * */
    @GetMapping("startSaveTiming")
    public R startSaveTiming() {
        return remoteTcpService.startSaveTiming();
    }


}
