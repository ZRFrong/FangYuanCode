package com.ruoyi;

import com.ruoyi.fangyuantcp.timing.TaskHeartbeat;
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
public class RuoYiFangYuanTcp
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        /*
         *开启心跳定时查询
         * */
        SpringApplication.run(RuoYiFangYuanTcp.class, args);
        log.info("开启心跳定时查询");
        TaskHeartbeat taskHeartbeat = new TaskHeartbeat();
        taskHeartbeat.HeartbeatRun();

    }
}