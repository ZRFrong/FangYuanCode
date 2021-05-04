package com.ruoyi.fangyuanapi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author ZHAOXIAOSI
 * @version 2.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.fangyuanapi.dto.EquipMENT.java
 * @Description
 * @createTime 2021年04月25日 10:07:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "功能表传输对象")
public class FunctionDto {


    @ApiModelProperty("功能对应操作集")
    private Map<String,Object> res;

    @ApiModelProperty("是否在线")
    private String isOnline;
}
