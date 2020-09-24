package com.ruoyi.fangyuantcp.domain;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel
@Data
public class DbOperationVo {

    /** 心跳名称 */
    @ApiModelProperty(value = "设备绑定心跳名称")
    private String heartName;

    /** 设备号 */
    @ApiModelProperty(value = "设备唯一编号（心跳名称）")
    private String facility;


    /** 操作指令 */
    @ApiModelProperty(value = "操作指令")
    private String operationText;

    /** 是否成功 */
    @ApiModelProperty(value = "操作指令")
    private String isTrue;

    /*创建时间*/
    @ApiModelProperty(value = "创建时间")
    private Date createTime;



}
