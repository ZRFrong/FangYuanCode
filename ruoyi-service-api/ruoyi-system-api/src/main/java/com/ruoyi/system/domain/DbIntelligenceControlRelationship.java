package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 产品，功能，产品批次，id ，用户id  ，场所关系中间对象 db_intelligence_control_relationship
 * 
 * @author zheng
 * @date 2021-04-08
 */
@ApiModel
public class DbIntelligenceControlRelationship extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    @ApiModelProperty(value = "用户id")
    private Long dbUserId;

    /** 设备组件ids */
    @Excel(name = "设备组件ids")
    @ApiModelProperty(value = "设备组件ids")
    private String dbEquipmentComponentIds;

    /** 产品id */
    @Excel(name = "产品id")
    @ApiModelProperty(value = "产品id")
    private Long dbEquipmentIds;

    /** 产品批次id */
    @Excel(name = "产品批次id")
    @ApiModelProperty(value = "产品批次id")
    private Long dbProductBathId;

    /** 功能集 */
    @Excel(name = "功能集")
    @ApiModelProperty(value = "功能集")
    private String dbFunctionDisplay;

    /** 网关id */
    @Excel(name = "网关id")
    @ApiModelProperty(value = "网关id")
    private Long dbEquipmentTempId;

    /** 备用字段 */
    @Excel(name = "备用字段")
    @ApiModelProperty(value = "备用字段")
    private String standbyOne;

    /** 备用字段2 */
    @Excel(name = "备用字段2")
    @ApiModelProperty(value = "备用字段2")
    private String standbyTwo;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setDbUserId(Long dbUserId) 
    {
        this.dbUserId = dbUserId;
    }

    public Long getDbUserId() 
    {
        return dbUserId;
    }
    public void setDbEquipmentComponentIds(String dbEquipmentComponentIds) 
    {
        this.dbEquipmentComponentIds = dbEquipmentComponentIds;
    }

    public String getDbEquipmentComponentIds() 
    {
        return dbEquipmentComponentIds;
    }
    public void setDbEquipmentIds(Long dbEquipmentIds) 
    {
        this.dbEquipmentIds = dbEquipmentIds;
    }

    public Long getDbEquipmentIds() 
    {
        return dbEquipmentIds;
    }
    public void setDbProductBathId(Long dbProductBathId) 
    {
        this.dbProductBathId = dbProductBathId;
    }

    public Long getDbProductBathId() 
    {
        return dbProductBathId;
    }
    public void setDbFunctionDisplay(String dbFunctionDisplay) 
    {
        this.dbFunctionDisplay = dbFunctionDisplay;
    }

    public String getDbFunctionDisplay() 
    {
        return dbFunctionDisplay;
    }
    public void setDbEquipmentTempId(Long dbEquipmentTempId) 
    {
        this.dbEquipmentTempId = dbEquipmentTempId;
    }

    public Long getDbEquipmentTempId() 
    {
        return dbEquipmentTempId;
    }
    public void setStandbyOne(String standbyOne) 
    {
        this.standbyOne = standbyOne;
    }

    public String getStandbyOne() 
    {
        return standbyOne;
    }
    public void setStandbyTwo(String standbyTwo) 
    {
        this.standbyTwo = standbyTwo;
    }

    public String getStandbyTwo() 
    {
        return standbyTwo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("dbUserId", getDbUserId())
            .append("dbEquipmentComponentIds", getDbEquipmentComponentIds())
            .append("dbEquipmentIds", getDbEquipmentIds())
            .append("dbProductBathId", getDbProductBathId())
            .append("dbFunctionDisplay", getDbFunctionDisplay())
            .append("dbEquipmentTempId", getDbEquipmentTempId())
            .append("standbyOne", getStandbyOne())
            .append("standbyTwo", getStandbyTwo())
            .toString();
    }
}
