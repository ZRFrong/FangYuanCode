package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 db_greenhouse_and_function
 * 
 * @author zheng
 * @date 2021-03-02
 */
@ApiModel
public class DbGreenhouseAndFunction extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 设备与功能项中间表 */
    private Long id;

    /** 设备id */
    @Excel(name = "设备id")
    @ApiModelProperty(value = "设备id")
    private Long equimentId;

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
    public void setEquimentId(Long equimentId) 
    {
        this.equimentId = equimentId;
    }

    public Long getEquimentId() 
    {
        return equimentId;
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
            .append("equimentId", getEquimentId())
            .append("functionId", getFunctionId())
            .toString();
    }
}
