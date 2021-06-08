package com.fangyuan.netty;

import com.ruoyi.system.annotation.EnableRyFeignClients;
import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Description: netty启动类
 * @Author zheng
 * @Date 2021/6/7 13:41
 * @Version 1.0
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@EnableRyFeignClients
@MapperScan("com.fangyuan.*.mapper")
@Log4j2
public class RuoYiFangYuanNetty {

    public static void main(String[] args) {
        SpringApplication.run(RuoYiFangYuanNetty.class, args);
    }
}
