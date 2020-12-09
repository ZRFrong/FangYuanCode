package com.ruoyi.system.domain;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel
public class DbOperationVo {
    public DbOperationVo() {
    }


    @Override
    public String toString() {
        return "DbOperationVo{" +
                "heartName='" + heartName + '\'' +
                ", facility='" + facility + '\'' +
                ", operationText='" + operationText + '\'' +
                ", isTrue='" + isTrue + '\'' +
                ", createTime=" + createTime +
                ", operationName='" + operationName + '\'' +
                ", operationId='" + operationId + '\'' +
                '}';
    }

    public String getHeartName() {
        return heartName;
    }

    public void setHeartName(String heartName) {
        this.heartName = heartName;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getOperationText() {
        return operationText;
    }

    public void setOperationText(String operationText) {
        this.operationText = operationText;
    }

    public String getIsTrue() {
        return isTrue;
    }

    public void setIsTrue(String isTrue) {
        this.isTrue = isTrue;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
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

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    /** 是否成功 */
    @ApiModelProperty(value = "是否成功")
    private String isTrue;

    /*创建时间*/
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /*操作指令名称*/
    @ApiModelProperty(value = "操作对象名称")
    private String operationName;

    /*操作指令名称*/
    @ApiModelProperty(value = "操作设备id")
    private String operationId;

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }
}
