package com.ruoyi.system.domain;

/*
 * 天气预报返回对象
 * */

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class WeatherVo {


    private String weather_code;

    //aqi明细数据
    @ApiModelProperty(value = "aqi明细数据")
    private AqiDetail aqiDetail;

    //风向名称
    @ApiModelProperty(value = "风向名称")
    private String wind_direction;

    //采集时间
    @ApiModelProperty(value = "采集时间")
    private String temperature_time;

    //风力
    @ApiModelProperty(value = "风力")
    private String wind_power;

    //空气质量指数，越小越好
    @ApiModelProperty(value = "空气质量指数，越小越好")
    private String aqi;

    //空气湿度
    @ApiModelProperty(value = "空气湿度")
    private String sd;

    //天气图片标识
    @ApiModelProperty(value = "天气图片标识")
    private String weather_pic;

    //天气文字标识
    @ApiModelProperty(value = "天气文字标识")
    private String weather;

    //当前气温
    @ApiModelProperty(value = "当前气温")
    private String temperature;


    @Data
    @ApiModel
    public class AqiDetail {


//城市代码
        @ApiModelProperty(value = "城市代码")
        private String num;




        //一氧化碳1小时平均
        @ApiModelProperty(value = "一氧化碳1小时平均")
        private String co;



        //二氧化硫1小时平均
        @ApiModelProperty(value = "风向名称")
        private String so2;

//        城市名字
        @ApiModelProperty(value = "城市名字")
        private String area;


        //臭氧1小时平均
        @ApiModelProperty(value = "臭氧1小时平均")
        private String o3;
        //二氧化氮1小时平均
        @ApiModelProperty(value = "二氧化氮1小时平均")
        private String no2;

        //空气质量指数，越小越好
        @ApiModelProperty(value = "空气质量指数，越小越好")
        private String aqi;

        //空气质量指数类别，有“优、良、轻度污染、中度污染、重度污染、严重污染”6类
        @ApiModelProperty(value = "空气质量指数类别")
        private String quality;

        //颗粒物（粒径小于等于10μm）1小时平均
        @ApiModelProperty(value = "颗粒物（粒径小于等于10μm）1小时平均")
        private String pm10;

        //颗粒物（粒径小于等于2.5μm）1小时平均
        @ApiModelProperty(value = "颗粒物（粒径小于等于2.5μm）1小时平均")
        private String pm2_5;

        //臭氧8小时平均
        @ApiModelProperty(value = "臭氧8小时平均")
        private String o3_8h;

        @ApiModelProperty(value = "颗粒物(PM2.5)")
        private String primary_pollutant;


    }

}
