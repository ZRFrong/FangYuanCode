package com.ruoyi.fangyuanapi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 转发对象 db_forward
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
public class DbForward extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 转发表 */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 被转发的动态ID */
    @Excel(name = "被转发的动态ID")
    private Long replyForwardDynamicId;

    /** 转发时的评论 */
    @Excel(name = "转发时的评论")
    private String forwardComment;

    /** 转发时间 */
    @Excel(name = "转发时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date forwardTime;

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
    public void setReplyForwardDynamicId(Long replyForwardDynamicId) 
    {
        this.replyForwardDynamicId = replyForwardDynamicId;
    }

    public Long getReplyForwardDynamicId() 
    {
        return replyForwardDynamicId;
    }
    public void setForwardComment(String forwardComment) 
    {
        this.forwardComment = forwardComment;
    }

    public String getForwardComment() 
    {
        return forwardComment;
    }
    public void setForwardTime(Date forwardTime) 
    {
        this.forwardTime = forwardTime;
    }

    public Date getForwardTime() 
    {
        return forwardTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("replyForwardDynamicId", getReplyForwardDynamicId())
            .append("forwardComment", getForwardComment())
            .append("forwardTime", getForwardTime())
            .toString();
    }
}
