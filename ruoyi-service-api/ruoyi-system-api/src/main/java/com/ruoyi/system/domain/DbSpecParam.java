package com.ruoyi.system.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * db_spec_param对象 db_spec_param
 * 
 * @author zheng
 * @date 2020-09-30
 */
@ApiModel
@Data
public class DbSpecParam
{
    private static final long serialVersionUID = 1L;

    /** 商品参数id */
    private Long id;

    /** 商品id */
    @Excel(name = "商品id")
    @ApiModelProperty(value = "商品id")
    private Long cid;

    /** 参数名称 */
    @Excel(name = "参数名称")
    @ApiModelProperty(value = "参数名称")
    private String name;

    /** 参数数量 */
    @Excel(name = "参数数量")
    @ApiModelProperty(value = "参数数量")
    private Integer num;

    /** 组件单价 */
    @Excel(name = "组件单价")
    @ApiModelProperty(value = "组件单价")
    private Long price;

    /** 规格 */
    @Excel(name = "规格")
    @ApiModelProperty(value = "规格")
    private String spec;

    /** 规格 */
    @Excel(name = "图片")
    @ApiModelProperty(value = "图片")
    private String imagesUrl;

}
