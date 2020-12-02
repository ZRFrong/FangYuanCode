package com.ruoyi.system.domain;

import com.ruoyi.common.utils.DateUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 报警信息对象 db_abnormal_info
 * 
 * @author zheng
 * @date 2020-12-02
 */
@ApiModel
public class DbAbnormalInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 异常类型 */
    @Excel(name = "异常类型")
    @ApiModelProperty(value = "异常类型")
    private String faultType;

    /** 报警时间 */
    @Excel(name = "报警时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "报警时间")
    private Date alarmTime;

    @Override
    public String toString() {
        return "DbAbnormalInfo{" +
                "id=" + id +
                ", faultType='" + faultType + '\'' +
                ", alarmTime=" + alarmTime +
                ", dbEquipmentId=" + dbEquipmentId +
                ", isDispose=" + isDispose +
                ", alarmExplain='" + alarmExplain + '\'' +
                ", objectType='" + objectType + '\'' +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getDbEquipmentId() {
        return dbEquipmentId;
    }

    public void setDbEquipmentId(Long dbEquipmentId) {
        this.dbEquipmentId = dbEquipmentId;
    }

    /** 报警时间 */
    @ApiModelProperty(value = "报警时间")
    private Long dbEquipmentId;

    /** 是否处理 */
    @Excel(name = "是否处理")
    @ApiModelProperty(value = "是否处理")
    private Integer isDispose;

    /** 报警说明 */
    @Excel(name = "报警说明")
    @ApiModelProperty(value = "报警说明")
    private String alarmExplain;

    /** 故障类型 */
    @Excel(name = "故障类型")
    @ApiModelProperty(value = "故障类型")
    private String objectType;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setFaultType(String faultType) 
    {
        this.faultType = faultType;
    }

    public String getFaultType() 
    {
        return faultType;
    }
    public void setAlarmTime(Date alarmTime) 
    {
        this.alarmTime = alarmTime;
    }

    public Date getAlarmTime() 
    {
        return alarmTime;
    }
    public void setIsDispose(Integer isDispose) 
    {
        this.isDispose = isDispose;
    }

    public Integer getIsDispose() 
    {
        return isDispose;
    }
    public void setAlarmExplain(String alarmExplain) 
    {
        this.alarmExplain = alarmExplain;
    }

    public String getAlarmExplain() 
    {
        return alarmExplain;
    }
    public void setObjectType(String objectType) 
    {
        this.objectType = objectType;
    }

    public String getObjectType() 
    {
        return objectType;
    }

}
