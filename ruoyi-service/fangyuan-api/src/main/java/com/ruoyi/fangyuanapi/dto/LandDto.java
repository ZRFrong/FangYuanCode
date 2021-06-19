package com.ruoyi.fangyuanapi.dto;

import com.ruoyi.common.core.domain.SensorDeviceDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
* @Description:    java类作用描述
* @Author:          ZhaoHuiSheng
* @CreateDate:    2021/4/13 9:52
* @Version:         1.0
* @Sign:            天下风云出我辈，一入代码岁月催。
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("智控详情LandDto")
public class LandDto {

    @ApiModelProperty("大棚名称")
    private String landName;

    @ApiModelProperty("大棚id")
    private Long landId;

//    @ApiModelProperty("土壤温度")
//    private String temperatureSoil;
//
//    @ApiModelProperty("土壤湿度")
//    private String humiditySoil;
//
//    @ApiModelProperty("光照")
//    private String light;
//
//    @ApiModelProperty("二氧化碳")
//    private String co2;
//
//    @ApiModelProperty("空气温度")
//    private String temperatureAir;
//
//    @ApiModelProperty("空气湿度")
//    private String humidityAir;
//
//    @ApiModelProperty("电导率")
//    private String conductivity;
//
//    @ApiModelProperty("ph")
//    private String ph;
//
//    @ApiModelProperty("氮")
//    private String nitrogen;
//
//    @ApiModelProperty("磷")
//    private String phosphorus;
//
//    @ApiModelProperty("钾")
//    private String potassium;

    @ApiModelProperty("是否显示")
    private String isShow;

    @ApiModelProperty("大棚所属功能")
    private List<FunctionDto> functionList;

    @ApiModelProperty("是否该大棚的管理员")
    private Long isAdmin;

    @ApiModelProperty("传感数据")
    private List<SensorDeviceDto> sensor;

}
