package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 设备对象 db_equipment
 * 
 * @author zheng
 * @date 2020-09-24
 */
@ApiModel
public class DbEquipment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long equipmentId;

    /** 心跳名称 */
    @Excel(name = "心跳名称")
    @ApiModelProperty(value = "心跳名称")
    private String heartbeatText;

    /** 使用期限（何时到期） */
    @Excel(name = "使用期限", readConverterExp = "何=时到期")
    @ApiModelProperty(value = "使用期限")
    private Date allottedTime;

    /** 是否取消服务 */
    @Excel(name = "是否取消服务")
    @ApiModelProperty(value = "是否取消服务")
    private Integer isPause;

    /** 取消说明 */
    @Excel(name = "取消说明")
    @ApiModelProperty(value = "取消说明")
    private String pauseState;

    /** 是否发生故障 */
    @Excel(name = "是否发生故障")
    @ApiModelProperty(value = "是否发生故障")
    private Integer isFault;

    /** 设备模板id */
    @Excel(name = "设备模板id")
    @ApiModelProperty(value = "设备模板id")
    private Long equipmentTemplateId;

    /** 设备号（两位） */
    @Excel(name = "设备号", readConverterExp = "两=位")
    @ApiModelProperty(value = "设备号")
    private Integer equipmentNo;

    public void setEquipmentId(Long equipmentId) 
    {
        this.equipmentId = equipmentId;
    }

    public Long getEquipmentId() 
    {
        return equipmentId;
    }
    public void setHeartbeatText(String heartbeatText) 
    {
        this.heartbeatText = heartbeatText;
    }

    public String getHeartbeatText() 
    {
        return heartbeatText;
    }
    public void setAllottedTime(Date allottedTime) 
    {
        this.allottedTime = allottedTime;
    }

    public Date getAllottedTime() 
    {
        return allottedTime;
    }
    public void setIsPause(Integer isPause) 
    {
        this.isPause = isPause;
    }

    public Integer getIsPause() 
    {
        return isPause;
    }
    public void setPauseState(String pauseState) 
    {
        this.pauseState = pauseState;
    }

    public String getPauseState() 
    {
        return pauseState;
    }
    public void setIsFault(Integer isFault) 
    {
        this.isFault = isFault;
    }

    public Integer getIsFault() 
    {
        return isFault;
    }
    public void setEquipmentTemplateId(Long equipmentTemplateId) 
    {
        this.equipmentTemplateId = equipmentTemplateId;
    }

    public Long getEquipmentTemplateId() 
    {
        return equipmentTemplateId;
    }
    public void setEquipmentNo(Integer equipmentNo) 
    {
        this.equipmentNo = equipmentNo;
    }

    public Integer getEquipmentNo() 
    {
        return equipmentNo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("equipmentId", getEquipmentId())
            .append("heartbeatText", getHeartbeatText())
            .append("allottedTime", getAllottedTime())
            .append("isPause", getIsPause())
            .append("pauseState", getPauseState())
            .append("isFault", getIsFault())
            .append("createTime", getCreateTime())
            .append("equipmentTemplateId", getEquipmentTemplateId())
            .append("equipmentNo", getEquipmentNo())
            .toString();
    }
}
