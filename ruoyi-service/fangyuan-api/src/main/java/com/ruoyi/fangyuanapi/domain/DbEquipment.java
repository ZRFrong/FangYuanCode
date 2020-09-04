package com.ruoyi.fangyuanapi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 设备对象 db_equipment
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
public class DbEquipment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long equipmentId;

    /** 所属用户id */
    @Excel(name = "所属用户id")
    private Long dbUserId;

    /** 心跳名称 */
    @Excel(name = "心跳名称")
    private String heartbeatText;

    /** 操作人id(后台用户id) */
    @Excel(name = "操作人id(后台用户id)")
    private Long operateBy;

    /** 使用期限（合适到期） */
    @Excel(name = "使用期限", readConverterExp = "合=适到期")
    private Date allottedTime;

    /** 是否取消服务 */
    @Excel(name = "是否取消服务")
    private Integer isPause;

    /** 取消说明 */
    @Excel(name = "取消说明")
    private String pauseState;

    /** 是否发生故障 */
    @Excel(name = "是否发生故障")
    private Integer isFault;

    public void setEquipmentId(Long equipmentId) 
    {
        this.equipmentId = equipmentId;
    }

    public Long getEquipmentId() 
    {
        return equipmentId;
    }
    public void setDbUserId(Long dbUserId) 
    {
        this.dbUserId = dbUserId;
    }

    public Long getDbUserId() 
    {
        return dbUserId;
    }
    public void setHeartbeatText(String heartbeatText) 
    {
        this.heartbeatText = heartbeatText;
    }

    public String getHeartbeatText() 
    {
        return heartbeatText;
    }
    public void setOperateBy(Long operateBy) 
    {
        this.operateBy = operateBy;
    }

    public Long getOperateBy() 
    {
        return operateBy;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("equipmentId", getEquipmentId())
            .append("dbUserId", getDbUserId())
            .append("heartbeatText", getHeartbeatText())
            .append("operateBy", getOperateBy())
            .append("allottedTime", getAllottedTime())
            .append("isPause", getIsPause())
            .append("pauseState", getPauseState())
            .append("isFault", getIsFault())
            .append("createTime", getCreateTime())
            .toString();
    }
}
