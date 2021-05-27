package com.ruoyi.system.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * app版本更新对象 db_app_version
 * 
 * @author zheng
 * @date 2020-10-28
 */
@ApiModel
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DbAppVersion
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 版本号 */
    @Excel(name = "版本号")
    @ApiModelProperty(value = "版本号")
    private Integer appVersion;

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

    /** 更新人 */
    @Excel(name = "更新人")
    @ApiModelProperty(value = "更新人")
    private String updateBy;

    /** md5 */
    @Excel(name = "md5")
    @ApiModelProperty(value = "md5")
    private String md5;

    /** 创建时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
