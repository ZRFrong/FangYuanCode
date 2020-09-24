package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 点赞对象 db_give_like
 *
 * @author fangyuan
 * @date 2020-09-07
 */
@ApiModel
public class DbGiveLike extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 点赞表ID */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    @ApiModelProperty(value = "用户ID")
    private Long userId;

    /** 评论ID */
    @Excel(name = "评论ID")
    @ApiModelProperty(value = "评论ID")
    private Long commentId;

    /** 动态ID */
    @Excel(name = "动态ID")
    @ApiModelProperty(value = "动态ID")
    private Long dynamicId;

    /** 点赞时间 */
    @Excel(name = "点赞时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "点赞时间")
    private Date giveLikeTime;

    /** 是否取消点赞 0: 否 1: 是 */
    @Excel(name = "是否取消点赞 0: 否 1: 是")
    @ApiModelProperty(value = "是否取消点赞 0: 否 1: 是")
    private Integer isCancel;

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
    public void setCommentId(Long commentId)
    {
        this.commentId = commentId;
    }

    public Long getCommentId()
    {
        return commentId;
    }
    public void setDynamicId(Long dynamicId)
    {
        this.dynamicId = dynamicId;
    }

    public Long getDynamicId()
    {
        return dynamicId;
    }
    public void setGiveLikeTime(Date giveLikeTime)
    {
        this.giveLikeTime = giveLikeTime;
    }

    public Date getGiveLikeTime()
    {
        return giveLikeTime;
    }
    public void setIsCancel(Integer isCancel)
    {
        this.isCancel = isCancel;
    }

    public Integer getIsCancel()
    {
        return isCancel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("userId", getUserId())
                .append("commentId", getCommentId())
                .append("dynamicId", getDynamicId())
                .append("giveLikeTime", getGiveLikeTime())
                .append("isCancel", getIsCancel())
                .toString();
    }
}