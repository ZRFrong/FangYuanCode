package com.ruoyi.system.domain;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel
@Data
public class DbOperationVo {
    public DbOperationVo() {
    }

    public DbOperationVo(String heartName, String facility, String operationText, String isTrue, Date createTime) {
        this.heartName = heartName;
        this.facility = facility;
        this.operationText = operationText;
        this.isTrue = isTrue;
        this.createTime = createTime;
    }

    /** 心跳名称 */
    @ApiModelProperty(value = "设备绑定心跳名称")
    private String heartName;

    /** 设备号 */
    @ApiModelProperty(value = "设备唯一编号")
    private String facility;


    /** 操作指令 */
    @ApiModelProperty(value = "操作指令")
    private String operationText;

    /** 是否成功 */
    @ApiModelProperty(value = "是否成功")
    private String isTrue;

    /*创建时间*/
    @ApiModelProperty(value = "创建时间")
    private Date createTime;



}
