package com.ruoyi.fangyuantcp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备状态对象 db_tcp_type
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
public class DbTcpType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long tcpTypeId;

    /** 温度 */
    @Excel(name = "温度")
    private String temperature;

    /** 湿度 */
    @Excel(name = "湿度")
    private String humidity;

    /** 光照 */
    @Excel(name = "光照")
    private String light;

    /** 心跳名称 */
    @Excel(name = "心跳名称")
    private String heartName;

    /** 二氧化碳 */
    @Excel(name = "二氧化碳")
    private String co2;

    /** 是否开启自动控制 */
    @Excel(name = "是否开启自动控制")
    private Integer idAuto;

    /** 自动控制温度区间（json） */
    @Excel(name = "自动控制温度区间", readConverterExp = "j=son")
    private String autocontrolType;

    public void setTcpTypeId(Long tcpTypeId) 
    {
        this.tcpTypeId = tcpTypeId;
    }

    public Long getTcpTypeId() 
    {
        return tcpTypeId;
    }
    public void setTemperature(String temperature) 
    {
        this.temperature = temperature;
    }

    public String getTemperature() 
    {
        return temperature;
    }
    public void setHumidity(String humidity) 
    {
        this.humidity = humidity;
    }

    public String getHumidity() 
    {
        return humidity;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("tcpTypeId", getTcpTypeId())
            .append("temperature", getTemperature())
            .append("humidity", getHumidity())
            .append("light", getLight())
            .append("heartName", getHeartName())
            .append("co2", getCo2())
            .append("idAuto", getIdAuto())
            .append("autocontrolType", getAutocontrolType())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
