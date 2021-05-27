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
    /**
     * 操作时间
//     */
    private Long operationTime;

    /**
     * 操作类型
     */
    private Integer operationType;

    /**
     * 操作名称
     */
    private String operationText;

    /**
     * 头像
     */
    private String avatar;
}
