package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 词条对象 db_entry
 *
 * @author fangyuan
 * @date 2020-09-07
 */
@ApiModel
public class DbEntry extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 词条ID */
    private Long id;

    /** 热度排行 */
    @Excel(name = "热度排行")
    @ApiModelProperty(value = "热度排行")
    private Long heatNumber;

    /** 是否封禁 0：否 1：是 */
    @Excel(name = "是否封禁 0：否 1：是")
    @ApiModelProperty(value = "是否封禁 0：否 1：是")
    private Integer isBanned;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    /** 词条名称 */
    @Excel(name = "词条名称")
    @ApiModelProperty(value = "词条名称")
    private String entryName;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setHeatNumber(Long heatNumber)
    {
        this.heatNumber = heatNumber;
    }

    public Long getHeatNumber()
    {
        return heatNumber;
    }
    public void setIsBanned(Integer isBanned)
    {
        this.isBanned = isBanned;
    }

    public Integer getIsBanned()
    {
        return isBanned;
    }
    public void setCreatedTime(Date createdTime)
    {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime()
    {
        return createdTime;
    }
    public void setEntryName(String entryName)
    {
        this.entryName = entryName;
    }

    public String getEntryName()
    {
        return entryName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("heatNumber", getHeatNumber())
                .append("isBanned", getIsBanned())
                .append("createdTime", getCreatedTime())
                .append("entryName", getEntryName())
                .toString();
    }
}