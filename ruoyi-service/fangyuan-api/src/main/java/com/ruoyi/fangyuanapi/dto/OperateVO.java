package com.ruoyi.fangyuanapi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Mr.ZHAO
 * @version 2.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.fangyuanapi.dto.OperateVO.java
 * @Description 操作设备接口接收对象
 * @createTime 2021年05月06日 13:21:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("操作设备接口接收对象")
public class OperateVO {

    @ApiModelProperty(name = "landId",value = "大棚id" ,required = true)
    private Long landId;

    @ApiModelProperty(name = "operationText",value = "指令" ,required = true)
    private String operationText;

    @ApiModelProperty(name = "operationType",value = "操作类型 123" ,required = true)
    private String operationType;

    @ApiModelProperty(name = "handleName",value = "操作 上还是下 开始还是暂停" ,required = true)
    private String handleName;

    @ApiModelProperty(name = "operationId",value = "功能id" ,required = true)
    private  Long operationId;

    @ApiModelProperty(name = "startTime",value = "开启时间" ,required = false)
    private Long startTime;

    @ApiModelProperty(name = "stopTime",value = "停止时间" ,required = false)
    private Long stopTime;

    @ApiModelProperty(name = "flag",value = "补光灯定时：0：设置定时 1：关闭定时" ,required = false)
    private Integer flag;

    @ApiModelProperty(name = "percentage",value = "卷帘卷膜进度条数值" ,required = false)
    private Integer percentage;
}
