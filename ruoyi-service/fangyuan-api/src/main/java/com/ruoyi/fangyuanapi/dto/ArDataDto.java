package com.ruoyi.fangyuanapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author Mr.ZHAO
 * @version 1.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.fangyuanapi.dto.ArSensor.java
 * @Description
 * @createTime 2021年06月04日 17:39:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ArDataDto {

    private List<Map<String,Object>> sensor;

    private List<ArOperateDto> operateDto;

    private String landName;

    private String videoUrl;

}
