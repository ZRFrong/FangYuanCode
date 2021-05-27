package com.ruoyi.fangyuanapi.conf;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author ZHAOXIAOSI
 * @version 1.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.fangyuanapi.conf.WeatherConf.java
 * @Description
 * @createTime 2021年04月23日 13:20:00
 */

@Data
@Configuration
@RefreshScope
public class WeatherConf {
    /**
     * 天气接口域名
     * */
    @Value("${com.fangyuan.weather.host}")
    private String host;

    /**
     * gps 天气接口地址url
     * */
    @Value("${com.fangyuan.weather.gpsPath}")
    private String weatherApiGpsUrl;

    /***
     * 以地区获取url
     */
    @Value("${com.fangyuan.weather.areaPath}")
    private String weatherApiAreaUrl;

    /**
     * 天气接口密钥
     * */
    @Value("${com.fangyuan.weather.appcode}")
    private String appCode;

    /**
     * 指数解析
     * */
    @Value("#{${com.fangyuan.weather.almanac}}")
    private Map<String,String> almanac;
}
