package com.ruoyi.common.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ZHAO
 * @version 2.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.fangyuanapi.dto.SensorDeviceDto.java
 * @Description
 * @createTime 2021年05月31日 10:34:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class SensorDeviceDto implements Serializable {

    /**
     * 设备名称
     */
    public String deviceName;
    /**
     * 数值
     */
    public String value;
    /**
     * 单位
     */
    public String unit;
    /**
     * 图标
     */
    public String icon;
}
