package com.ruoyi;

import com.ruoyi.fangyuantcp.timing.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.ruoyi.system.annotation.EnableRyFeignClients;

import tk.mybatis.spring.annotation.MapperScan;

/**
 * 启动程序
 *
 * @author ruoyi
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@EnableRyFeignClients
@MapperScan("com.ruoyi.*.mapper")
@Log4j2
public class RuoYiFangYuanTcp {
    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        /*
         *开启心跳定时查询
         * */
        SpringApplication.run(RuoYiFangYuanTcp.class, args);
        log.info("开启心跳定时查询");
        TaskHeartbeat taskHeartbeat = new TaskHeartbeat();
        taskHeartbeat.HeartbeatRun();
        /*
         * 开始定时装态查询
         * */
        log.info("开始定时装态查询");
        TaskTcpType taskTcpType = new TaskTcpType();
        taskTcpType.HeartbeatRun();

        /*
         * 开启通风查询
         * */
        log.info("开启通风查询");
        TaskTongFeng taskTongFeng = new TaskTongFeng();
        taskTongFeng.HeartbeatRun();
        /*
         *开启 远程，本地检测
         * */
        log.info("开启远程，本地检测");
        TaskOnline taskOnline = new TaskOnline();
        taskOnline.HeartbeatRun();
/*
* 开启传感数据定时存储
* */
        log.info("装态定时存储");
        TaskTcpOrder taskTcpOrder = new TaskTcpOrder();
        taskTcpOrder.HeartbeatRun();

    }
}