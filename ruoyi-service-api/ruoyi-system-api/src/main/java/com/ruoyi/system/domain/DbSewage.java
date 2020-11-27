package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 水肥对象 db_sewage
 * 
 * @author zheng
 * @date 2020-11-23
 */
@ApiModel
public class DbSewage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 心跳名称 */
    @Excel(name = "心跳名称")
    @ApiModelProperty(value = "心跳名称")
    private String heartName;

    /** 设备号 */
    @Excel(name = "设备号")
    @ApiModelProperty(value = "设备号")
    private Integer equipmentNo;

    /** 绑定用户id */
    @Excel(name = "绑定用户id")
    @ApiModelProperty(value = "绑定用户id")
    private Long dbUserId;

    /** 是否过期 */
    @Excel(name = "是否过期")
    @ApiModelProperty(value = "是否过期")
    private Integer isOverdue;

    /** 过期时间 */
    @Excel(name = "过期时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "过期时间")
    private Date overdueTime;

    /** 是否掉线 */
    @Excel(name = "是否掉线")
    @ApiModelProperty(value = "是否掉线")
    private Integer isDrops;

    /** 设备id集合，分隔 */
    @Excel(name = "设备id集合，分隔")
    @ApiModelProperty(value = "设备id集合，分隔")
    private String equipmentIds;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setHeartName(String heartName) 
    {
        this.heartName = heartName;
    }

    public String getHeartName() 
    {
        return heartName;
    }
    public void setEquipmentNo(Integer equipmentNo) 
    {
        this.equipmentNo = equipmentNo;
    }

    public String getEquipmentNo()
    {
        if (equipmentNo<10){
        return "0"+equipmentNo;
        }else {
            return equipmentNo.toString();
        }
    }
    public void setDbUserId(Long dbUserId) 
    {
        this.dbUserId = dbUserId;
    }

    public Long getDbUserId() 
    {
        return dbUserId;
    }
    public void setIsOverdue(Integer isOverdue) 
    {
        this.isOverdue = isOverdue;
    }

    public Integer getIsOverdue() 
    {
        return isOverdue;
    }
    public void setOverdueTime(Date overdueTime) 
    {
        this.overdueTime = overdueTime;
    }

    public Date getOverdueTime() 
    {
        return overdueTime;
    }
    public void setIsDrops(Integer isDrops) 
    {
        this.isDrops = isDrops;
    }

    public Integer getIsDrops() 
    {
        return isDrops;
    }
    public void setEquipmentIds(String equipmentIds) 
    {
        this.equipmentIds = equipmentIds;
    }

    public String getEquipmentIds() 
    {
        return equipmentIds;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("heartName", getHeartName())
            .append("equipmentNo", getEquipmentNo())
            .append("dbUserId", getDbUserId())
            .append("createTime", getCreateTime())
            .append("isOverdue", getIsOverdue())
            .append("overdueTime", getOverdueTime())
            .append("isDrops", getIsDrops())
            .append("equipmentIds", getEquipmentIds())
            .toString();
    }
}
