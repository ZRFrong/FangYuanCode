package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * app版本更新对象 db_app_version
 * 
 * @author zheng
 * @date 2020-10-28
 */
@ApiModel
public class DbAppVersion extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 版本号 */
    @Excel(name = "版本号")
    @ApiModelProperty(value = "版本号")
    private String appVersion;

    /** 特性说明 */
    @Excel(name = "特性说明")
    @ApiModelProperty(value = "特性说明")
    private String updateState;

    /** 是否强制更新 */
    @Excel(name = "是否强制更新")
    @ApiModelProperty(value = "是否强制更新")
    private Integer isConstraint;

    /** 下载链接 */
    @Excel(name = "下载链接")
    @ApiModelProperty(value = "下载链接")
    private String downloadUrl;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setAppVersion(String appVersion) 
    {
        this.appVersion = appVersion;
    }

    public String getAppVersion() 
    {
        return appVersion;
    }
    public void setUpdateState(String updateState) 
    {
        this.updateState = updateState;
    }

    public String getUpdateState() 
    {
        return updateState;
    }
    public void setIsConstraint(Integer isConstraint) 
    {
        this.isConstraint = isConstraint;
    }

    public Integer getIsConstraint() 
    {
        return isConstraint;
    }
    public void setDownloadUrl(String downloadUrl) 
    {
        this.downloadUrl = downloadUrl;
    }

    public String getDownloadUrl() 
    {
        return downloadUrl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("appVersion", getAppVersion())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .append("updateState", getUpdateState())
            .append("isConstraint", getIsConstraint())
            .append("downloadUrl", getDownloadUrl())
            .toString();
    }
}
