package com.ruoyi.fangyuanapi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 提醒对象 db_remind
 *
 * @author fangyuan
 * @date 2020-09-07
 */
@ApiModel
public class DbRemind extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 提醒表id */
    private Long id;

    /** 提醒类型 */
    @Excel(name = "提醒类型")
    @ApiModelProperty(value = "提醒类型")
    private Integer remind;

    /** 是否完成：0：否 1： 是 */
    @Excel(name = "是否完成：0：否 1： 是")
    @ApiModelProperty(value = "是否完成：0：否 1： 是")
    private Integer isSuccess;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    /** 用户id */
    @Excel(name = "用户id")
    @ApiModelProperty(value = "用户id")
    private Long userId;

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
    public void setRemind(Integer remind)
    {
        this.remind = remind;
    }

    public Integer getRemind()
    {
        return remind;
    }
    public void setIsSuccess(Integer isSuccess)
    {
        this.isSuccess = isSuccess;
    }

    public Integer getIsSuccess()
    {
        return isSuccess;
    }
    public void setCreatedTime(Date createdTime)
    {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime()
    {
        return createdTime;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
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
                .append("remind", getRemind())
                .append("isSuccess", getIsSuccess())
                .append("createdTime", getCreatedTime())
                .append("userId", getUserId())
                .append("dynamicId", getDynamicId())
                .toString();
    }
}