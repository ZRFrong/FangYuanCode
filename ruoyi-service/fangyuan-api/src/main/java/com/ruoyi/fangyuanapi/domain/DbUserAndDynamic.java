package com.ruoyi.fangyuanapi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 前台用户和动态中间对象 db_user_and_dynamic
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
public class DbUserAndDynamic extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户和动态中间表ID */
    private Long id;

    /** 动态ID */
    @Excel(name = "动态ID")
    private Long dynamicId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setDynamicId(Long dynamicId) 
    {
        this.dynamicId = dynamicId;
    }

    public Long getDynamicId() 
    {
        return dynamicId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("dynamicId", getDynamicId())
            .append("userId", getUserId())
            .toString();
    }
}
