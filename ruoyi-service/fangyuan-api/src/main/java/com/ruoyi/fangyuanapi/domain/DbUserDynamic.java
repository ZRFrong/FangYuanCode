package com.ruoyi.fangyuanapi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 动态对象 db_user_dynamic
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
public class DbUserDynamic extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 动态表ID */
    private Long id;

    /** 是否有视频：0：有 1：没有 */
    @Excel(name = "是否有视频：0：有 1：没有")
    private Integer isHaveVoide;

    /** 资源：json形式（图片地址，视频，图片最多6张） */
    @Excel(name = "资源：json形式", readConverterExp = "图=片地址，视频，图片最多6张")
    private String resource;

    /** 内容 */
    @Excel(name = "内容")
    private String content;

    /** 词条 id */
    @Excel(name = "词条 id")
    private Long relId;

    /** 定位信息 */
    @Excel(name = "定位信息")
    private String orientation;

    /** 权限：0：所有人可见 1：关注的人可见 2：仅自己可见 3：陌生人可见 */
    @Excel(name = "权限：0：所有人可见 1：关注的人可见 2：仅自己可见 3：陌生人可见")
    private Integer permission;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 是否封禁：0：否 1：是 */
    @Excel(name = "是否封禁：0：否 1：是")
    private Integer isBanned;

    /** 转发数量 */
    @Excel(name = "转发数量")
    private Long forwardNum;

    /** 评论数量 */
    @Excel(name = "评论数量")
    private Long commentNum;

    /** 点赞数量 */
    @Excel(name = "点赞数量")
    private Long likeNum;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setIsHaveVoide(Integer isHaveVoide) 
    {
        this.isHaveVoide = isHaveVoide;
    }

    public Integer getIsHaveVoide() 
    {
        return isHaveVoide;
    }
    public void setResource(String resource) 
    {
        this.resource = resource;
    }

    public String getResource() 
    {
        return resource;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setRelId(Long relId) 
    {
        this.relId = relId;
    }

    public Long getRelId() 
    {
        return relId;
    }
    public void setOrientation(String orientation) 
    {
        this.orientation = orientation;
    }

    public String getOrientation() 
    {
        return orientation;
    }
    public void setPermission(Integer permission) 
    {
        this.permission = permission;
    }

    public Integer getPermission() 
    {
        return permission;
    }
    public void setCreatedTime(Date createdTime) 
    {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime() 
    {
        return createdTime;
    }
    public void setIsBanned(Integer isBanned) 
    {
        this.isBanned = isBanned;
    }

    public Integer getIsBanned() 
    {
        return isBanned;
    }
    public void setForwardNum(Long forwardNum) 
    {
        this.forwardNum = forwardNum;
    }

    public Long getForwardNum() 
    {
        return forwardNum;
    }
    public void setCommentNum(Long commentNum) 
    {
        this.commentNum = commentNum;
    }

    public Long getCommentNum() 
    {
        return commentNum;
    }
    public void setLikeNum(Long likeNum) 
    {
        this.likeNum = likeNum;
    }

    public Long getLikeNum() 
    {
        return likeNum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("isHaveVoide", getIsHaveVoide())
            .append("resource", getResource())
            .append("content", getContent())
            .append("relId", getRelId())
            .append("orientation", getOrientation())
            .append("permission", getPermission())
            .append("createdTime", getCreatedTime())
            .append("isBanned", getIsBanned())
            .append("forwardNum", getForwardNum())
            .append("commentNum", getCommentNum())
            .append("likeNum", getLikeNum())
            .toString();
    }
}
