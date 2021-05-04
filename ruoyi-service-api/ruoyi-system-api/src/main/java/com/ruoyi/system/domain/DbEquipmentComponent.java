package com.ruoyi.system.domain;

import lombok.*;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 版本加功能对象 db_equipment_component
 * 
 * @author zheng
 * @date 2021-04-08
 */
@ApiModel
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DbEquipmentComponent
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 分类id */
    @Excel(name = "分类id")
    @ApiModelProperty(value = "分类id")
    private Long classificationId;

    /** 设备版本 */
    @Excel(name = "设备版本")
    @ApiModelProperty(value = "设备版本")
    private String equipmentVersion;

    /** 功能标识（ids） */
    @Excel(name = "功能标识", readConverterExp = "i=ds")
    @ApiModelProperty(value = "功能标识")
    private String functionLogo;

    /** 说明备注 */
    @Excel(name = "说明备注")
    @ApiModelProperty(value = "说明备注")
    private String noteText;

    /** 设备心跳 */
    @Excel(name = "设备心跳")
    @ApiModelProperty(value = "设备心跳")
    private String heartbeatText;

    /** 设备心跳唯一编码 */
    @Excel(name = "设备心跳唯一编码")
    @ApiModelProperty(value = "设备心跳唯一编码")
    private String equipmentNo;

    /** 设备名称 */
    @Excel(name = "设备名称")
    @ApiModelProperty(value = "设备名称")
    private String equipmentName;

    /** 创建时间 */
    @Excel(name = "创建时间")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**  设备id */
    @Excel(name = "设备id")
    @ApiModelProperty(value = "所属设备id")
    private Long equipmentId;

    /**  设备功能指令集 */
    @Excel(name = "设备功能指令集")
    @ApiModelProperty(value = "设备功能指令集")
    private String spList;

    public String getEquipmentNoString()
    {
        if (equipmentNo!=null){
            if (Integer.valueOf(equipmentNo) < 10){
                return "0"+equipmentNo;
            }
            else {

                return equipmentNo+"";
            }
        }
        return equipmentNo+"";

    }
}
