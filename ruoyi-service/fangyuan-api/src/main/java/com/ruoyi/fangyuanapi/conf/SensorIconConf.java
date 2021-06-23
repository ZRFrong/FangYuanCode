package com.ruoyi.fangyuanapi.conf;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZHAO
 * @version 1.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.fangyuanapi.conf.SensorIconConf.java
 * @Description 传感器 icon配置类
 * @createTime 2021年06月20日 14:01:00
 */
@Data
@Configuration
@RefreshScope
public class SensorIconConf {

    /**
     * 空气温度
     * */
    @Value("${com.fangyuan.sensorIcon.temperatureAirImg}")
    private String temperatureAirImg;

    /**
     * 空气湿度
     * */
    @Value("${com.fangyuan.sensorIcon.humidityAirImg}")
    private String humidityAirImg;

    /**
     * 土壤湿度
     * */
    @Value("${com.fangyuan.sensorIcon.humiditySoilImg}")
    private String humiditySoilImg;

    /**
     *  土壤温度
     * */
    @Value("${com.fangyuan.sensorIcon.temperatureSoilImg}")
    private String temperatureSoilImg;

    /**
     * 氮
     * */
    @Value("${com.fangyuan.sensorIcon.nitrogenImg}")
    private String nitrogenImg;

    /**
     * 磷
     * */
    @Value("${com.fangyuan.sensorIcon.phosphorusImg}")
    private String phosphorusImg;

    /**
     * 钾
     * */
    @Value("${com.fangyuan.sensorIcon.potassiumImg}")
    private String potassiumImg;

    /**
     * 电导率
     * */
    @Value("${com.fangyuan.sensorIcon.conductivityImg}")
    private String conductivityImg;

    /**
     * 光照强度
     * */
    @Value("${com.fangyuan.sensorIcon.lightImg}")
    private String lightImg;

    /**
     * 二氧化碳
     * */
    @Value("${com.fangyuan.sensorIcon.co2Img}")
    private String co2Img;

    /**
     * 酸碱值
     * */
    @Value("${com.fangyuan.sensorIcon.phImg}")
    private String phImg;
}
