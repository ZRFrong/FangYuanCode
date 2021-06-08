package com.ruoyi.system.domain;

import com.alibaba.fastjson.JSON;
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

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setGrowstate(String growstate) 
    {
        this.growstate = growstate;
    }

    public String getGrowstate() 
    {
        return growstate;
    }
    public void setTagid(Long tagid) 
    {
        this.tagid = tagid;
    }

    public Long getTagid() 
    {
        return tagid;
    }
    public void setX(String x) 
    {
        this.x = x;
    }

    public String getX() 
    {
        return x;
    }
    public void setY(String y) 
    {
        this.y = y;
    }

    public String getY() 
    {
        return y;
    }
    public void setWidth(Long width) 
    {
        this.width = width;
    }

    public Long getWidth() 
    {
        return width;
    }
    public void setHeight(Long height) 
    {
        this.height = height;
    }

    public Long getHeight() 
    {
        return height;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("growstate", getGrowstate())
            .append("tagid", getTagid())
            .append("x", getX())
            .append("y", getY())
            .append("width", getWidth())
            .append("height", getHeight())
            .toString();
    }

    public static void main(String [] args){
        String s = "生长期80天\n剩余时间3天";
        System.out.println(JSON.toJSONString(s));
    }
}
