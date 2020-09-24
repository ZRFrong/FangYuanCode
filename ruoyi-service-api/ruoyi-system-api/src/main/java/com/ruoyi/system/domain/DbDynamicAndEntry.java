package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 动态和词条中间对象 db_dynamic_and_entry
 *
 * @author fangyuan
 * @date 2020-09-07
 */
@ApiModel
public class DbDynamicAndEntry extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 动态和词条中间表ID */
    private Long id;

    /** 词条id */
    @Excel(name = "词条id")
    @ApiModelProperty(value = "词条id")
    private Long entryId;

    /** 动态id */
    @Excel(name = "动态id")
    @ApiModelProperty(value = "动态id")
    private Long dynamicId;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setEntryId(Long entryId)
    {
        this.entryId = entryId;
    }

    public Long getEntryId()
    {
        return entryId;
    }
    public void setDynamicId(Long dynamicId)
    {
        this.dynamicId = dynamicId;
    }

    public Long getDynamicId()
    {
        return dynamicId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("entryId", getEntryId())
                .append("dynamicId", getDynamicId())
                .toString();
    }
}