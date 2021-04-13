package com.ruoyi.system.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DbEquipmentAdmin
{
    private static final long serialVersionUID = 1L;

    /** 设备管理员表 */
    private Long id;




    /** 是否删除：0 否 1是 */
    @Excel(name = "是否删除：0 否 1是")
    @ApiModelProperty(value = "是否删除：0 否 1是")
    private Integer isDel;

    /** 管理员对应的设备id */
    @Excel(name = "管理员对应的设备id")
    @ApiModelProperty(value = "管理员对应的设备id")
    private Long landId;

    /** 上级管理员id，0为顶级管理员  */
    @Excel(name = "用户id ")
    @ApiModelProperty(value = "用户id ")
    private Long userId;

    /** 此为保留字：留作对功能的权限控制 */
    @Excel(name = "此为保留字：留作对功能的权限控制")
    @ApiModelProperty(value = "此为保留字：留作对功能的权限控制")
    private Long functionIds;

    /** 是否为顶级管理员：0是 1否 */
    @Excel(name = "是否为顶级管理员：0是 1否")
    @ApiModelProperty(value = "是否为顶级管理员：0是 1否")
    private Integer isSuperAdmin;

    /** 用户头像 */
    @Excel(name = "用户头像")
    @ApiModelProperty(value = "用户头像")
    private String avatar;

    /** 用户头像 */
    @Excel(name = "创建时间")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /** 土地名字 */
    @Excel(name = "土地名字")
    @ApiModelProperty(value = "土地名字")
    private String landName;

    /** 设备id */
    @Excel(name = "设备id")
    @ApiModelProperty(value = "设备id")
    private String equipmentId;

}
