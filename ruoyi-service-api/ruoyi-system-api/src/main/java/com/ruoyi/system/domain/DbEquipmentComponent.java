package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 版本加功能对象 db_equipment_component
 * 
 * @author zheng
 * @date 2021-04-08
 */
@ApiModel
public class DbEquipmentComponent extends BaseEntity
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

    /** 备用字段 */
    @Excel(name = "备用字段")
    @ApiModelProperty(value = "备用字段")
    private String standbyOne;

    /** 备用字段2 */
    @Excel(name = "备用字段2")
    @ApiModelProperty(value = "备用字段2")
    private String standbyTwo;

    /** 设备名称 */
    @Excel(name = "设备名称")
    @ApiModelProperty(value = "设备名称")
    private String equipmentName;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setClassificationId(Long classificationId) 
    {
        this.classificationId = classificationId;
    }

    public Long getClassificationId() 
    {
        return classificationId;
    }
    public void setEquipmentVersion(String equipmentVersion) 
    {
        this.equipmentVersion = equipmentVersion;
    }

    public String getEquipmentVersion() 
    {
        return equipmentVersion;
    }
    public void setFunctionLogo(String functionLogo) 
    {
        this.functionLogo = functionLogo;
    }

    public String getFunctionLogo() 
    {
        return functionLogo;
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
    public void setEquipmentName(String equipmentName) 
    {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentName() 
    {
        return equipmentName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("classificationId", getClassificationId())
            .append("equipmentVersion", getEquipmentVersion())
            .append("functionLogo", getFunctionLogo())
            .append("noteText", getNoteText())
            .append("createTime", getCreateTime())
            .append("standbyOne", getStandbyOne())
            .append("standbyTwo", getStandbyTwo())
            .append("equipmentName", getEquipmentName())
            .toString();
    }
}
