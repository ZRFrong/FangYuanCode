package com.ruoyi.system.domain.monitor;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 视频监控设备对象 db_monitor
 * 
 * @author zheng
 * @date 2021-05-25
 */
@ApiModel
@Data
public class DbMonitor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增ID */
    private Long id;

    /** 大棚设备ID */
    @Excel(name = "大棚设备ID")
    @ApiModelProperty(value = "大棚设备ID")
    private Long equipmentId;

    /** 大棚设备-心跳名称 */
    @Excel(name = "大棚设备心跳名称")
    @ApiModelProperty(value = "大棚设备心跳名称")
    private String equipmentHeartName;

    /** 设备名称 */
    @Excel(name = "设备名称")
    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    /** 设备注册码 */
    private String deviceRegisterCode;

    /** 设备序列号 */
    @Excel(name = "设备序列号")
    @ApiModelProperty(value = "设备序列号")
    private String deviceSerial;

    /** 设备种类(-1-未知设备，0-IPC，1-NVR，2-VMS) */
    @Excel(name = "设备种类(-1-未知设备，0-IPC，1-NVR，2-VMS)")
    @ApiModelProperty(value = "设备种类(-1-未知设备，0-IPC，1-NVR，2-VMS)")
    private Integer deviceType;

    /** 设备型号 */
    @Excel(name = "设备型号")
    @ApiModelProperty(value = "设备型号")
    private String deviceModel;

    /** 设备抓图地址 */
    @Excel(name = "设备抓图地址")
    @ApiModelProperty(value = "设备抓图地址")
    private String deviceCameraImage;

    /** 设备安装位置 */
    @Excel(name = "设备安装位置")
    @ApiModelProperty(value = "设备安装位置")
    private String deviceLocation;

    /** 设备安装日期 */
    @Excel(name = "设备安装日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "设备安装日期")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date equipmentProductionDate;

    /** 备注 */
    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String deviceDesc;

    /** 设备通道 */
    @Excel(name = "设备通道")
    @ApiModelProperty(value = "设备通道")
    private String deviceChannel;
}
