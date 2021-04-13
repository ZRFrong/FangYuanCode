package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 产品批次对象 db_product_batch
 * 
 * @author zheng
 * @date 2021-04-08
 */
@ApiModel
public class DbProductBatch extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 产品批次 */
    @Excel(name = "产品批次")
    @ApiModelProperty(value = "产品批次")
    private String productBatch;

    /** 服务地区 */
    @Excel(name = "服务地区")
    @ApiModelProperty(value = "服务地区")
    private String serviceArea;

    /** 备注信息 */
    @Excel(name = "备注信息")
    @ApiModelProperty(value = "备注信息")
    private String noteText;

    /** 备用字段 */
    @Excel(name = "备用字段")
    @ApiModelProperty(value = "备用字段")
    private String standbyOne;

    /** 备用字段2 */
    @Excel(name = "备用字段2")
    @ApiModelProperty(value = "备用字段2")
    private String standbyTwo;

    /** 设备ids */
    @Excel(name = "设备ids")
    @ApiModelProperty(value = "设备ids")
    private String equipmentComponentIds;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProductBatch(String productBatch) 
    {
        this.productBatch = productBatch;
    }

    public String getProductBatch() 
    {
        return productBatch;
    }
    public void setServiceArea(String serviceArea) 
    {
        this.serviceArea = serviceArea;
    }

    public String getServiceArea() 
    {
        return serviceArea;
    }
    public void setNoteText(String noteText) 
    {
        this.noteText = noteText;
    }

    public String getNoteText() 
    {
        return noteText;
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
    public void setEquipmentComponentIds(String equipmentComponentIds) 
    {
        this.equipmentComponentIds = equipmentComponentIds;
    }

    public String getEquipmentComponentIds() 
    {
        return equipmentComponentIds;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("productBatch", getProductBatch())
            .append("createTime", getCreateTime())
            .append("serviceArea", getServiceArea())
            .append("noteText", getNoteText())
            .append("standbyOne", getStandbyOne())
            .append("standbyTwo", getStandbyTwo())
            .append("equipmentComponentIds", getEquipmentComponentIds())
            .toString();
    }
}
