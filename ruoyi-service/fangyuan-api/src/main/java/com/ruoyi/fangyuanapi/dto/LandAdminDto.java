package com.ruoyi.fangyuanapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author ZHAOXIAOSI
 * @version 1.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.fangyuanapi.dto.LandAdminDto.java
 * @createTime 2021年04月29日 09:52:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LandAdminDto {

    private Long landId;

    private String landName;

    private List<Map<String,String>> admins;

    private Long isSuperAdmin;

    private List<OperationDto> operation;

}
