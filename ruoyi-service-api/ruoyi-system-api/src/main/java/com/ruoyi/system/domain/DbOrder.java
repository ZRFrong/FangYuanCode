package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * db_order对象 db_order
 * 
 * @author zheng
 * @date 2020-09-30
 */
@ApiModel
public class DbOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单id */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /** 订单号 */
    @Excel(name = "订单号")
    @ApiModelProperty(value = "订单号")
    private Long order;

    /** 是否支付成功：0：否 1：是 */
    @Excel(name = "是否支付成功：0：否 1：是")
    @ApiModelProperty(value = "是否支付成功：0：否 1：是")
    private Integer isSusses;

    /** 价格 */
    @Excel(name = "价格")
    @ApiModelProperty(value = "价格")
    private Long price;

    /** 购买的商品合集 */
    @Excel(name = "购买的商品合集")
    @ApiModelProperty(value = "购买的商品合集")
    private String categoryList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setOrder(Long order) 
    {
        this.order = order;
    }

    public Long getOrder() 
    {
        return order;
    }
    public void setIsSusses(Integer isSusses) 
    {
        this.isSusses = isSusses;
    }

    public Integer getIsSusses() 
    {
        return isSusses;
    }
    public void setPrice(Long price)
    {
        this.price = price;
    }

    public Long getPrice()
    {
        return price;
    }
    public void setCategoryList(String categoryList) 
    {
        this.categoryList = categoryList;
    }

    public String getCategoryList() 
    {
        return categoryList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("order", getOrder())
            .append("createTime", getCreateTime())
            .append("isSusses", getIsSusses())
            .append("price", getPrice())
            .append("categoryList", getCategoryList())
            .toString();
    }
}
