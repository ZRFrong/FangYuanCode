package com.ruoyi.system.controller;


import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.feign.RemoteApiService;
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

    private RemoteApiService remoteApiService = SpringUtils.getBean(RemoteApiService.class);
    private RemoteTcpService remoteTcpService = SpringUtils.getBean(RemoteTcpService.class);


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

    /*
    * 更新redis中的设备列表
    * */
    @GetMapping("syncEquipmentList")
    public R syncEquipmentList() {
        return remoteApiService.syncEquipmentList();
    }






}
