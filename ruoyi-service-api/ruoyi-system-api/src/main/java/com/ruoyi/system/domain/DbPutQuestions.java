package com.ruoyi.system.domain;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 db_put_questions
 * 
 * @author zheng
 * @date 2021-01-18
 */
@ApiModel
@Data
@ToString
public class DbPutQuestions extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 提问表 */
    private Long id;

    /** 手机号 */
    @Excel(name = "手机号")
    @ApiModelProperty(value = "手机号")
    private String phone;

    /** 问题描述 */
    @Excel(name = "问题描述")
    @ApiModelProperty(value = "问题描述")
    private String questionsText;

    /** 描述地的位置 */
    @Excel(name = "描述地的位置")
    @ApiModelProperty(value = "描述地的位置")
    private String landText;

    /** 称呼 */
    @Excel(name = "称呼")
    @ApiModelProperty(value = "称呼")
    private String nickname;

    /** 是否对此问题进行解答 */
    @Excel(name = "是否对此问题进行解答")
    @ApiModelProperty(value = "是否对此问题进行解答")
    private Integer isAnswer;

    /** 是否删除：0 否 ： 1 是 */
    @Excel(name = "是否删除：0 否 ： 1 是")
    @ApiModelProperty(value = "是否删除：0 否 ： 1 是")
    private Integer isDel;

    /** 提问用户id */
    @Excel(name = "提问用户id")
    @ApiModelProperty(value = "提问用户id")
    private Long dbUserId;

    /** 提问用户id */
    @Excel(name = "问题类型id")
    @ApiModelProperty(value = "问题类型id")
    private Long problemTypeId;

    /** 提问用户id */
    @Excel(name = "问题图片")
    @ApiModelProperty(value = "问题图片")
    private String images;


}
