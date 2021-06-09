package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 设备状态对象 db_tcp_type
 *
 * @author zheng
 * @date 2020-09-28
 */
@ApiModel
@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DbTcpType
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long tcpTypeId;

    /** 土壤温度 */
    @Excel(name = "土壤温度")
    @ApiModelProperty(value = "土壤温度")
    private String temperatureSoil;

    /** 土壤湿度 */
    @Excel(name = "土壤湿度")
    @ApiModelProperty(value = "土壤湿度")
    private String humiditySoil;

    /** 光照 */
    @Excel(name = "光照")
    @ApiModelProperty(value = "光照")
    private String light;

    /** 心跳名称加设备号 */
    @Excel(name = "心跳名称加设备号")
    @ApiModelProperty(value = "心跳名称加设备号")
    private String heartName;

    /** 二氧化碳 */
    @Excel(name = "二氧化碳")
    @ApiModelProperty(value = "二氧化碳")
    private String co2;

    /** 是否开启自动控制 */
    @Excel(name = "是否开启自动控制")
    @ApiModelProperty(value = "是否开启自动控制")
    private Integer idAuto;

    /** 自动开风口温度（json） */
    @Excel(name = "自动开风口温度", readConverterExp = "j=son")
    @ApiModelProperty(value = "自动开风口温度")
    private String autocontrolType;

    /** 自动开风口温度（json） */
    @Excel(name = "自动关风口温度", readConverterExp = "j=son")
    @ApiModelProperty(value = "自动关风口温度")
    private String autocontrolTypeEnd;

    /** 空气温度 */
    @Excel(name = "空气温度")
    @ApiModelProperty(value = "空气温度")
    private String temperatureAir;

    /** 空气湿度 */
    @Excel(name = "空气湿度")
    @ApiModelProperty(value = "空气湿度")
    private String humidityAir;

    /** 电导率 */
    @Excel(name = "电导率")
    @ApiModelProperty(value = "电导率")
    private String conductivity;

    /** ph */
    @Excel(name = "ph")
    @ApiModelProperty(value = "ph")
    private String ph;

    /** 氮 */
    @Excel(name = "氮")
    @ApiModelProperty(value = "氮")
    private String nitrogen;

    /** 磷 */
    @Excel(name = "磷")
    @ApiModelProperty(value = "磷")
    private String phosphorus;

    /** 钾 */
    @Excel(name = "钾")
    @ApiModelProperty(value = "钾")
    private String potassium;

    /** 是否显示（掉线的话不显示） */
    @Excel(name = "是否显示", readConverterExp = "掉=线的话不显示")
    @ApiModelProperty(value = "是否显示")
    private Integer isShow;


    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


}