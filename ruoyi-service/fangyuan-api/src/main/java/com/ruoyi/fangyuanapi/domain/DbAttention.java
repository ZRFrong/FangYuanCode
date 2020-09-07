package com.ruoyi.fangyuanapi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 关注和被关注对象 db_attention
 *
 * @author fangyuan
 * @date 2020-09-07
 */
@ApiModel
public class DbAttention extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 关注和被关注表ID */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    @ApiModelProperty(value = "用户ID")
    private Long userId;

    /** 被关注人的ID */
    @Excel(name = "被关注人的ID")
    @ApiModelProperty(value = "被关注人的ID")
    private Long replyAttentionUserId;

    /** 关注时间 */
    @Excel(name = "关注时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "关注时间")
    private Date attentionTime;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setReplyAttentionUserId(Long replyAttentionUserId)
    {
        this.replyAttentionUserId = replyAttentionUserId;
    }

    public Long getReplyAttentionUserId()
    {
        return replyAttentionUserId;
    }
    public void setAttentionTime(Date attentionTime)
    {
        this.attentionTime = attentionTime;
    }

    public Date getAttentionTime()
    {
        return attentionTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("userId", getUserId())
                .append("replyAttentionUserId", getReplyAttentionUserId())
                .append("attentionTime", getAttentionTime())
                .toString();
    }
}