package com.ruoyi.fangyuanapi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ZHAOXIAOSI
 * @version 1.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.fangyuanapi.dto.WeatherDto.java
 * @Description
 * @createTime 2021年04月23日 13:33:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel
public class WeatherDto {

    @ApiModelProperty(name = "temperature",value = "当前天气温度")
    private String temperature;

    @ApiModelProperty(name = "weatherPic",value = "天气图片")
    private String weatherPic;

    @ApiModelProperty(name = "weather",value = "天气状态 阴或者晴")
    private String weather;

    @ApiModelProperty(name = "temperatureMax",value = "三小时内得最大温度")
    private String temperatureMax;

    @ApiModelProperty(name = "temperature",value = "三小时内得最小温度")
    private String temperatureMin;

    @ApiModelProperty(name = "almanacDto",value = "黄历对象")
    private AlmanacDto almanacDto;

}
