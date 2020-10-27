package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备和土地中间对象 db_land_equipment
 * 
 * @author zheng
 * @date 2020-09-30
 */
@ApiModel
public class DbLandEquipment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 土地id */
    @Excel(name = "土地id")
    @ApiModelProperty(value = "土地id")
    private Long dbLandId;

    /** 设备id */
    @Excel(name = "设备id")
    @ApiModelProperty(value = "设备id")
    private Long dbEquipmentId;

    /** 自定义操作集（默认为模板的操作集） */
    @Excel(name = "自定义操作集", readConverterExp = "默=认为模板的操作集")
    @ApiModelProperty(value = "自定义操作集")
    private String handleText;

    /** 设备名称自定义 */
    @Excel(name = "设备名称自定义")
    @ApiModelProperty(value = "设备名称自定义")
    private String equipmentName;

    /** 绑定用户id */
    @Excel(name = "绑定用户id")
    @ApiModelProperty(value = "绑定用户id")
    private Long dbUserId;

    public void setDbLandId(Long dbLandId) 
    {
        this.dbLandId = dbLandId;
    }

    public Long getDbLandId() 
    {
        return dbLandId;
    }
    public void setDbEquipmentId(Long dbEquipmentId) 
    {
        this.dbEquipmentId = dbEquipmentId;
    }

    public Long getDbEquipmentId() 
    {
        return dbEquipmentId;
    }
    public void setHandleText(String handleText) 
    {
        this.handleText = handleText;
    }

    public String getHandleText() 
    {
        return handleText;
    }
    public void setEquipmentName(String equipmentName) 
    {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentName() 
    {
        return equipmentName;
    }
    public void setDbUserId(Long dbUserId) 
    {
        this.dbUserId = dbUserId;
    }

    public Long getDbUserId() 
    {
        return dbUserId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("dbLandId", getDbLandId())
            .append("dbEquipmentId", getDbEquipmentId())
            .append("handleText", getHandleText())
            .append("equipmentName", getEquipmentName())
            .append("dbUserId", getDbUserId())
            .toString();
    }
}
