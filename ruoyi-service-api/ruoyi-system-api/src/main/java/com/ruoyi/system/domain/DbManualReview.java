package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 审核表对象 db_manual_review
 * 
 * @author ruoyi
 * @date 2020-09-22
 */
@ApiModel
@Data
public class DbManualReview extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 人工审核表id */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /** 用户发布的疑似动态id */
    @Excel(name = "用户发布的疑似动态id")
    @ApiModelProperty(value = "用户发布的疑似动态id")
    private Long dynamicId;

    /** 动态内容 */
    @Excel(name = "动态内容")
    @ApiModelProperty(value = "动态内容")
    private String dynamicContent;

    /** 动态资源 */
    @Excel(name = "动态资源")
    @ApiModelProperty(value = "动态资源")
    private String dynamicResource;

    /** 人工审核是否完成 0：否 1 ：是 */
    @Excel(name = "人工审核是否完成 0：否 1 ：是")
    @ApiModelProperty(value = "人工审核是否完成 0：否 1 ：是")
    private Integer isSussess;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date created;

    /** 审核人 */
    @Excel(name = "审核人")
    @ApiModelProperty(value = "审核人")
    private String reviewUser;

    /** 审核时间 */
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "审核时间")
    private Date reviewTime;

    /** 审核时间 */
    @Excel(name = "审核结果 0：通过 1：违规")
    @ApiModelProperty(value = "审核结果 0：通过 1：违规")
    private Integer isViolation;


}
