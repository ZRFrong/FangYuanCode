package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 db_gateway_and_greenhouse
 * 
 * @author zheng
 * @date 2021-03-02
 */
@ApiModel
public class DbGatewayAndGreenhouse extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 设备与大棚中间表 */
    private Long id;

    /** 大棚id */
    @Excel(name = "大棚id")
    @ApiModelProperty(value = "大棚id")
    private Long landId;

    /** 设备id */
    @Excel(name = "设备id")
    @ApiModelProperty(value = "设备id")
    private Long equipmentId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setLandId(Long landId) 
    {
        this.landId = landId;
    }

    public Long getLandId() 
    {
        return landId;
    }
    public void setEquipmentId(Long equipmentId) 
    {
        this.equipmentId = equipmentId;
    }

    public Long getEquipmentId() 
    {
        return equipmentId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("landId", getLandId())
            .append("equipmentId", getEquipmentId())
            .toString();
    }
}
