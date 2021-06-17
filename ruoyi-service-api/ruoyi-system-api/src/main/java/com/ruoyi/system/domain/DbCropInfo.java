package com.ruoyi.system.domain;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 db_crop_info
 * 
 * @author zheng
 * @date 2021-06-08
 */
@ApiModel
@Data
public class DbCropInfo
{
    private static final long serialVersionUID = 1L;

    /** 作物信息表id */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    @ApiModelProperty(value = "名称")
    private String name;

    /** 作物生长周期 */
    @Excel(name = "作物生长周期")
    @ApiModelProperty(value = "作物生长周期")
    private String growstate;

    /** 大棚id */
    @Excel(name = "大棚id")
    @ApiModelProperty(value = "大棚id")
    private Long tagid;

    /** $column.columnComment */
    @Excel(name = "大棚id")
    @ApiModelProperty(value = "大棚id")
    private String x;

    /** $column.columnComment */
    @Excel(name = "大棚id")
    @ApiModelProperty(value = "大棚id")
    private String y;

    /** 宽 */
    @Excel(name = "宽")
    @ApiModelProperty(value = "宽")
    private Long width;

    /** 高 */
    @Excel(name = "高")
    @ApiModelProperty(value = "高")
    private Long height;

    /** 牌子图片 */
    @Excel(name = "牌子图片")
    @ApiModelProperty(value = "牌子图片")
    private String imgUrl;




    public static void main(String [] args){
        String s = "生长期80天\n剩余时间3天";
        System.out.println(JSON.toJSONString(s));
    }
}
