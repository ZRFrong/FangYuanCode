package com.ruoyi.fangyuanapi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 评论对象 db_comment
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
public class DbComment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 评论ID */
    private Long id;

    /** 评论者id /一级 */
    @Excel(name = "评论者id /一级")
    private Long observerId;

    /** 评论者名字 */
    @Excel(name = "评论者名字")
    private String observer;

    /** 评论对应的 动态ID */
    @Excel(name = "评论对应的 动态ID")
    private Long dynamicId;

    /** 评论内容 */
    @Excel(name = "评论内容")
    private String commentContent;

    /** 是否封禁 0：否 1： 是 */
    @Excel(name = "是否封禁 0：否 1： 是")
    private Integer isBanned;

    /** 主题类型： */
    @Excel(name = "主题类型：")
    private Integer themeType;

    /** 评论时间 */
    @Excel(name = "评论时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date commentTime;

    /** 父评论的id */
    @Excel(name = "父评论的id")
    private Long parentCommentId;

    /** 父评论的用户id /被评论的用户id */
    @Excel(name = "父评论的用户id /被评论的用户id")
    private Long parentCommentUserId;

    /** 点赞数 */
    @Excel(name = "点赞数")
    private Long praiseNum;

    /** 0：一级评论 2：二级评论 */
    @Excel(name = "0：一级评论 2：二级评论")
    private Integer commentLevel;

    /** 被回复的评论id */
    @Excel(name = "被回复的评论id")
    private Long replyCommentId;

    /** 被回复的评论用户id */
    @Excel(name = "被回复的评论用户id")
    private Long replyCommentUserId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setObserverId(Long observerId) 
    {
        this.observerId = observerId;
    }

    public Long getObserverId() 
    {
        return observerId;
    }
    public void setObserver(String observer) 
    {
        this.observer = observer;
    }

    public String getObserver() 
    {
        return observer;
    }
    public void setDynamicId(Long dynamicId) 
    {
        this.dynamicId = dynamicId;
    }

    public Long getDynamicId() 
    {
        return dynamicId;
    }
    public void setCommentContent(String commentContent) 
    {
        this.commentContent = commentContent;
    }

    public String getCommentContent() 
    {
        return commentContent;
    }
    public void setIsBanned(Integer isBanned) 
    {
        this.isBanned = isBanned;
    }

    public Integer getIsBanned() 
    {
        return isBanned;
    }
    public void setThemeType(Integer themeType) 
    {
        this.themeType = themeType;
    }

    public Integer getThemeType() 
    {
        return themeType;
    }
    public void setCommentTime(Date commentTime) 
    {
        this.commentTime = commentTime;
    }

    public Date getCommentTime() 
    {
        return commentTime;
    }
    public void setParentCommentId(Long parentCommentId) 
    {
        this.parentCommentId = parentCommentId;
    }

    public Long getParentCommentId() 
    {
        return parentCommentId;
    }
    public void setParentCommentUserId(Long parentCommentUserId) 
    {
        this.parentCommentUserId = parentCommentUserId;
    }

    public Long getParentCommentUserId() 
    {
        return parentCommentUserId;
    }
    public void setPraiseNum(Long praiseNum) 
    {
        this.praiseNum = praiseNum;
    }

    public Long getPraiseNum() 
    {
        return praiseNum;
    }
    public void setCommentLevel(Integer commentLevel) 
    {
        this.commentLevel = commentLevel;
    }

    public Integer getCommentLevel() 
    {
        return commentLevel;
    }
    public void setReplyCommentId(Long replyCommentId) 
    {
        this.replyCommentId = replyCommentId;
    }

    public Long getReplyCommentId() 
    {
        return replyCommentId;
    }
    public void setReplyCommentUserId(Long replyCommentUserId) 
    {
        this.replyCommentUserId = replyCommentUserId;
    }

    public Long getReplyCommentUserId() 
    {
        return replyCommentUserId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("observerId", getObserverId())
            .append("observer", getObserver())
            .append("dynamicId", getDynamicId())
            .append("commentContent", getCommentContent())
            .append("isBanned", getIsBanned())
            .append("themeType", getThemeType())
            .append("commentTime", getCommentTime())
            .append("parentCommentId", getParentCommentId())
            .append("parentCommentUserId", getParentCommentUserId())
            .append("praiseNum", getPraiseNum())
            .append("commentLevel", getCommentLevel())
            .append("replyCommentId", getReplyCommentId())
            .append("replyCommentUserId", getReplyCommentUserId())
            .toString();
    }
}
