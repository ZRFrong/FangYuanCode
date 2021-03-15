package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 【请填写功能名称】对象 db_gateway
 * 
 * @author zheng
 * @date 2021-03-02
 */
@ApiModel
public class DbGateway extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 4g模块名称 */
    @Excel(name = "4g模块名称")
    @ApiModelProperty(value = "4g模块名称")
    private String heartbeatText;

    /** 使用期限 */
    @Excel(name = "使用期限", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "使用期限")
    private Date allottedTime;

    /** 是否在线 （0在线，1不在线） */
    @Excel(name = "是否在线 ", readConverterExp = "0=在线，1不在线")
    @ApiModelProperty(value = "是否在线 ")
    private Integer isOnline;

    /** 是否取消服务 */
    @Excel(name = "是否取消服务")
    @ApiModelProperty(value = "是否取消服务")
    private Integer isPause;

    /** 取消说明 */
    @Excel(name = "取消说明")
    @ApiModelProperty(value = "取消说明")
    private String pauseState;

    /** 是否发生故障（0正常，1故障） */
    @Excel(name = "是否发生故障", readConverterExp = "0=正常，1故障")
    @ApiModelProperty(value = "是否发生故障")
    private Integer isFault;

    /** 设备模板id */
    @Excel(name = "设备模板id")
    @ApiModelProperty(value = "设备模板id")
    private Integer equipmentNo;

    /** 二维码图片id */
    @Excel(name = "二维码图片id")
    @ApiModelProperty(value = "二维码图片id")
    private Long qrCodePicId;

    /** id数组： 多个，隔开 */
    @Excel(name = "id数组： 多个，隔开")
    @ApiModelProperty(value = "id数组： 多个，隔开")
    private String functionIds;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
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
    public void setIsOnline(Integer isOnline) 
    {
        this.isOnline = isOnline;
    }

    public Integer getIsOnline() 
    {
        return isOnline;
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
    public void setEquipmentNo(Integer equipmentNo) 
    {
        this.equipmentNo = equipmentNo;
    }

    public Integer getEquipmentNo() 
    {
        return equipmentNo;
    }
    public void setQrCodePicId(Long qrCodePicId) 
    {
        this.qrCodePicId = qrCodePicId;
    }

    public Long getQrCodePicId() 
    {
        return qrCodePicId;
    }
    public void setFunctionIds(String functionIds) 
    {
        this.functionIds = functionIds;
    }

    public String getFunctionIds() 
    {
        return functionIds;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("heartbeatText", getHeartbeatText())
            .append("allottedTime", getAllottedTime())
            .append("createTime", getCreateTime())
            .append("isOnline", getIsOnline())
            .append("isPause", getIsPause())
            .append("pauseState", getPauseState())
            .append("isFault", getIsFault())
            .append("equipmentNo", getEquipmentNo())
            .append("qrCodePicId", getQrCodePicId())
            .append("functionIds", getFunctionIds())
            .toString();
    }
}
