package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 轮播图对象 db_banner
 * 
 * @author zheng
 * @date 2020-11-12
 */
@ApiModel
public class DbBanner
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 轮播图 */
    @Excel(name = "轮播图")
    @ApiModelProperty(value = "轮播图")
    private String bannerImage;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /** 跳转页 */
    @Excel(name = "跳转页")
    @ApiModelProperty(value = "跳转页")
    private String bannerUrl;

    /** 是否封禁 0：否 1：是 */
    @Excel(name = "是否封禁 0：否 1：是")
    @ApiModelProperty(value = "是否封禁 0：否 1：是")
    private Integer isBanned;

    /** 轮播图类型 */
    @Excel(name = "轮播图类型")
    @ApiModelProperty(value = "轮播图类型")
    private Integer bannerTpye;
    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setBannerImage(String bannerImage) 
    {
        this.bannerImage = bannerImage;
    }

    public String getBannerImage() 
    {
        return bannerImage;
    }
    public void setBannerUrl(String bannerUrl) 
    {
        this.bannerUrl = bannerUrl;
    }

    public String getBannerUrl() 
    {
        return bannerUrl;
    }
    public void setIsBanned(Integer isBanned) 
    {
        this.isBanned = isBanned;
    }

    public Integer getIsBanned() 
    {
        return isBanned;
    }
    public void setBannerTpye(Integer bannerTpye) 
    {
        this.bannerTpye = bannerTpye;
    }

    public Integer getBannerTpye() 
    {
        return bannerTpye;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("bannerImage", getBannerImage())
            .append("bannerUrl", getBannerUrl())
            .append("createTime", getCreateTime())
            .append("isBanned", getIsBanned())
            .append("bannerTpye", getBannerTpye())
            .toString();
    }
}
