package com.ruoyi.fangyuanapi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 产品对象 db_product
 *
 * @author fangyuan
 * @date 2020-09-07
 */
@ApiModel
public class DbProduct extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long productId;

    /** 产品名称 */
    @Excel(name = "产品名称")
    @ApiModelProperty(value = "产品名称")
    private String productName;

    /** 按钮集合 */
    @Excel(name = "按钮集合")
    @ApiModelProperty(value = "按钮集合")
    private String buttonList;

    /** 是否定制 */
    @Excel(name = "是否定制")
    @ApiModelProperty(value = "是否定制")
    private Integer isCustom;

    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    public Long getProductId()
    {
        return productId;
    }
    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getProductName()
    {
        return productName;
    }
    public void setButtonList(String buttonList)
    {
        this.buttonList = buttonList;
    }

    public String getButtonList()
    {
        return buttonList;
    }
    public void setIsCustom(Integer isCustom)
    {
        this.isCustom = isCustom;
    }

    public Integer getIsCustom()
    {
        return isCustom;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("productId", getProductId())
                .append("productName", getProductName())
                .append("buttonList", getButtonList())
                .append("isCustom", getIsCustom())
                .append("createTime", getCreateTime())
                .toString();
    }
}