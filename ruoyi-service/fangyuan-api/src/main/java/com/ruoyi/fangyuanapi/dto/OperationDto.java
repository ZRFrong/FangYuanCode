package com.ruoyi.fangyuanapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ZHAO
 * @version 2.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.fangyuanapi.dto.OperationDto.java
 * @Description 操作记录
 * @createTime 2021年05月02日 23:58:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperationDto {
    private Long operationTime;
    private Integer operationType;
    private String operationText;
    private String avatar;
}
