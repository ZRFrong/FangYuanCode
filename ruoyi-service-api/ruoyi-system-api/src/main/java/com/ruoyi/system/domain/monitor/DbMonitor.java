package com.ruoyi.system.domain.monitor;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.json.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 视频监控设备对象 db_monitor
 *
 * @author zheng
 * @date 2021-05-25
 */
@ApiModel
@Data
@Accessors(chain = true)
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


    /** 设备种类(0:录像机  1:视频摄像头 2:通道  ) */
    @Excel(name = "设备种类(0:录像机  1:视频摄像头 2:通道  )")
    @ApiModelProperty(value = "设备种类(0:录像机  1:视频摄像头 2:通道  )")
    private Integer deviceType;

    /** 通道摄像头父Id(录像机ID) */
    @Excel(name = "通道摄像头父Id(录像机ID)")
    @ApiModelProperty(value = "通道摄像头父Id(录像机ID)")
    private Long channelParentId;

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

    /** 通道添加状态(0:添加-已在表单添加 1:未添加-指只是数据同步过来未在表单添加) */
    @Excel(name = "通道添加状态")
    @ApiModelProperty(value = "通道添加状态 (0:添加-已在表单添加 1:未添加-指只是数据同步过来未在表单添加)")
    private Byte channelStatus;

    /** 设备视频流地址 */
    @ApiModelProperty(value = "设备视频流地址")
    private JSONObject deviceVideoUrls;

    /** 通道树地址 */
    @ApiModelProperty(value = "通道树地址")
    private Long[] channelIds;


}
