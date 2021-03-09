package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 db_function_and_gateway
 * 
 * @author zheng
 * @date 2021-03-02
 */
@ApiModel
public class DbFunctionAndGateway extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 设备与功能中间表 */
    private Long id;

    /** 大棚id */
    @Excel(name = "大棚id")
    @ApiModelProperty(value = "大棚id")
    private Long equipmentId;

    /** 功能项id */
    @Excel(name = "功能项id")
    @ApiModelProperty(value = "功能项id")
    private Long functionId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setEquipmentId(Long equipmentId) 
    {
        this.equipmentId = equipmentId;
    }

    public Long getEquipmentId() 
    {
        return equipmentId;
    }
    public void setFunctionId(Long functionId) 
    {
        this.functionId = functionId;
    }

    public Long getFunctionId() 
    {
        return functionId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("equipmentId", getEquipmentId())
            .append("functionId", getFunctionId())
            .toString();
    }
}
