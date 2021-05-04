package com.ruoyi.fangyuanapi.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ZHAO
 * @version 1.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.fangyuanapi.dto.EquipmentAdminDto.java
 * @Description TODO
 * @createTime 2021年04月06日 11:02:00
 * @sign 天下风云出我辈，一入代码岁月催！
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel
public class EquipmentAdminDto {
    //用户id
    private Long userId;
    //用户头像
    private String avatar;
    //是否为超级管理员
    private Long isSuperAdmin;
}
