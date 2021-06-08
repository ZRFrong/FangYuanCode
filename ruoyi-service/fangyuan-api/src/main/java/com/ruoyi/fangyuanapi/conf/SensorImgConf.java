package com.ruoyi.fangyuanapi.conf;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZHAOXIAOSI
 * @version 1.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.fangyuanapi.conf.SennerImgConf.java
 * @Description AR平台各个温度对应的曲线图
 * @createTime 2021年06月04日 19:39:00
 */
@Data
@Configuration
@RefreshScope
public class SensorImgConf {

    /**
     * 空气温度
     * */
    @Value("${com.fangyuan.sensorImg.temperatureAirImg}")
    private String temperatureAirImg;

    /**
     * 空气湿度
     * */
    @Value("${com.fangyuan.sensorImg.humidityAirImg}")
    private String humidityAirImg;

    /**
     * 土壤湿度
     * */
    @Value("${com.fangyuan.sensorImg.humiditySoilImg}")
    private String humiditySoilImg;

    /**
     *  土壤温度
     * */
    @Value("${com.fangyuan.sensorImg.temperatureSoilImg}")
    private String temperatureSoilImg;

    /**
     * 氮
     * */
    @Value("${com.fangyuan.sensorImg.nitrogenImg}")
    private String nitrogenImg;

    /**
     * 磷
     * */
    @Value("${com.fangyuan.sensorImg.phosphorusImg}")
    private String phosphorusImg;

    /**
     * 钾
     * */
    @Value("${com.fangyuan.sensorImg.potassiumImg}")
    private String potassiumImg;

    /**
     * 电导率
     * */
    @Value("${com.fangyuan.sensorImg.conductivityImg}")
    private String conductivityImg;

    /**
     * 光照强度
     * */
    @Value("${com.fangyuan.sensorImg.lightImg}")
    private String lightImg;

    /**
     * 二氧化碳
     * */
    @Value("${com.fangyuan.sensorImg.co2Img}")
    private String co2Img;

    /**
     * 酸碱值
     * */
    @Value("${com.fangyuan.sensorImg.phImg}")
    private String phImg;
}
