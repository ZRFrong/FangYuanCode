package com.ruoyi.fangyuanapi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 黄历对象
 * @author ZHAOSIAOSI
 * @version 2.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.fangyuanapi.dto.AlmanacDto.java
 * @Description
 * @createTime 2021年04月23日 13:45:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel
public class AlmanacDto {

    @ApiModelProperty(name = "tapuList",value = "忌做的事")
    private String tapuList;

    @ApiModelProperty(name = "towardlyList",value = "宜做的事")
    private String towardlyList;

}
