package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备状态对象 db_tcp_type
 *
 * @author zheng
 * @date 2020-09-28
 */
@ApiModel
public class DbTcpType extends BaseEntity
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

    @Override
    public String toString() {
        return "DbTcpType{" +
                "tcpTypeId=" + tcpTypeId +
                ", temperatureSoil='" + temperatureSoil + '\'' +
                ", humiditySoil='" + humiditySoil + '\'' +
                ", light='" + light + '\'' +
                ", heartName='" + heartName + '\'' +
                ", co2='" + co2 + '\'' +
                ", idAuto=" + idAuto +
                ", autocontrolType='" + autocontrolType + '\'' +
                ", autocontrolTypeEnd='" + autocontrolTypeEnd + '\'' +
                ", temperatureAir='" + temperatureAir + '\'' +
                ", humidityAir='" + humidityAir + '\'' +
                ", isShow=" + isShow +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAutocontrolTypeEnd() {
        return autocontrolTypeEnd;
    }

    public void setAutocontrolTypeEnd(String autocontrolTypeEnd) {
        this.autocontrolTypeEnd = autocontrolTypeEnd;
    }

    /** 是否显示（掉线的话不显示） */
    @Excel(name = "是否显示", readConverterExp = "掉=线的话不显示")
    @ApiModelProperty(value = "是否显示")
    private Integer isShow;

    public void setTcpTypeId(Long tcpTypeId)
    {
        this.tcpTypeId = tcpTypeId;
    }

    public Long getTcpTypeId()
    {
        return tcpTypeId;
    }
    public void setTemperatureSoil(String temperatureSoil)
    {
        this.temperatureSoil = temperatureSoil;
    }

    public String getTemperatureSoil()
    {
        return temperatureSoil;
    }
    public void setHumiditySoil(String humiditySoil)
    {
        this.humiditySoil = humiditySoil;
    }

    public String getHumiditySoil()
    {
        return humiditySoil;
    }
    public void setLight(String light)
    {
        this.light = light;
    }

    public String getLight()
    {
        return light;
    }
    public void setHeartName(String heartName)
    {
        this.heartName = heartName;
    }

    public String getHeartName()
    {
        return heartName;
    }
    public void setCo2(String co2)
    {
        this.co2 = co2;
    }

    public String getCo2()
    {
        return co2;
    }
    public void setIdAuto(Integer idAuto)
    {
        this.idAuto = idAuto;
    }

    public Integer getIdAuto()
    {
        return idAuto;
    }
    public void setAutocontrolType(String autocontrolType)
    {
        this.autocontrolType = autocontrolType;
    }

    public String getAutocontrolType()
    {
        return autocontrolType;
    }
    public void setTemperatureAir(String temperatureAir)
    {
        this.temperatureAir = temperatureAir;
    }

    public String getTemperatureAir()
    {
        return temperatureAir;
    }
    public void setHumidityAir(String humidityAir)
    {
        this.humidityAir = humidityAir;
    }

    public String getHumidityAir()
    {
        return humidityAir;
    }
    public void setIsShow(Integer isShow)
    {
        this.isShow = isShow;
    }

    public Integer getIsShow()
    {
        return isShow;
    }

}