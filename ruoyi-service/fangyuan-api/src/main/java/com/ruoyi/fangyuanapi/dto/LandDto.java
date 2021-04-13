package com.ruoyi.fangyuanapi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ApiModelProperty("心跳名称")
    private String heartbeatText;

    @ApiModelProperty("设备id")
    private Long equipmentId;

    @ApiModelProperty("土壤温度")
    private String temperatureSoil;

    @ApiModelProperty("土壤湿度")
    private String humiditySoil;

    @ApiModelProperty("光照")
    private String light;

    @ApiModelProperty("二氧化碳")
    private String co2;

}
