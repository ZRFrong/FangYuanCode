package com.ruoyi.system.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 【农业资讯】对象 db_information
 * 
 * @author zheng
 * @date 2021-04-08
 */
@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DbInformation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 资讯id */
    private Long id;

    /** 封面 */
    @Excel(name = "封面")
    @ApiModelProperty(value = "封面")
    private String cover;

    /** 标题 */
    @Excel(name = "标题")
    @ApiModelProperty(value = "标题")
    private String title;

    /** 是否删除 */
    @Excel(name = "是否删除")
    @ApiModelProperty(value = "是否删除")
    private Integer isDel;

    /** 内容：阿宏公众号文章链接 */
    @Excel(name = "内容：阿宏公众号文章链接")
    @ApiModelProperty(value = "内容：阿宏公众号文章链接")
    private String content;

    /** 阅读数 */
    @Excel(name = "阅读数")
    @ApiModelProperty(value = "阅读数")
    private Long readNum;

    /** 资讯类型 */
    @Excel(name = "资讯类型")
    @ApiModelProperty(value = "资讯类型")
    private Integer informationType;

    /** 资讯类型 */
    @Excel(name = "创建时间")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
