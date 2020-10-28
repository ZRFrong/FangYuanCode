package com.ruoyi.system.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * db_category对象 db_category
 * 
 * @author zheng
 * @date 2020-09-30
 */
@ApiModel
@ToString
@Data
public class DbCategory
{
    private static final long serialVersionUID = 1L;

    /** 商品id */
    private Long id;

    /** 商品标题 */
    @ApiModelProperty(value = "商品标题")
    private String title;

    /** 商品基本价格 */
    @ApiModelProperty(value = "商品基本价格")
    private Long price;

    /** 设备名字 */
    @ApiModelProperty(value = "设备名字")
    private String cname;

    @ApiModelProperty(value = "设备缩略图")
    private String images_url;


    private List<DbSpecParam> specParams;





}
