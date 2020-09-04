package com.ruoyi.fangyuanapi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 土地对象 db_land
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
public class DbLand extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long landId;

    /** 地区（省市县三级逗号分隔） */
    @Excel(name = "地区", readConverterExp = "省=市县三级逗号分隔")
    private String region;

    /** 经度 */
    @Excel(name = "经度")
    private String longitude;

    /** 纬度 */
    @Excel(name = "纬度")
    private String latitude;

    /** 产品类别 */
    @Excel(name = "产品类别")
    private String productCategory;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 备注信息 */
    @Excel(name = "备注信息")
    private String noteText;

    /** 关联用户id */
    @Excel(name = "关联用户id")
    private Long dbUserId;

    /** 别称自定义 */
    @Excel(name = "别称自定义")
    private String nickName;

    public void setLandId(Long landId) 
    {
        this.landId = landId;
    }

    public Long getLandId() 
    {
        return landId;
    }
    public void setRegion(String region) 
    {
        this.region = region;
    }

    public String getRegion() 
    {
        return region;
    }
    public void setLongitude(String longitude) 
    {
        this.longitude = longitude;
    }

    public String getLongitude() 
    {
        return longitude;
    }
    public void setLatitude(String latitude) 
    {
        this.latitude = latitude;
    }

    public String getLatitude() 
    {
        return latitude;
    }
    public void setProductCategory(String productCategory) 
    {
        this.productCategory = productCategory;
    }

    public String getProductCategory() 
    {
        return productCategory;
    }
    public void setProductName(String productName) 
    {
        this.productName = productName;
    }

    public String getProductName() 
    {
        return productName;
    }
    public void setNoteText(String noteText) 
    {
        this.noteText = noteText;
    }

    public String getNoteText() 
    {
        return noteText;
    }
    public void setDbUserId(Long dbUserId) 
    {
        this.dbUserId = dbUserId;
    }

    public Long getDbUserId() 
    {
        return dbUserId;
    }
    public void setNickName(String nickName) 
    {
        this.nickName = nickName;
    }

    public String getNickName() 
    {
        return nickName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("landId", getLandId())
            .append("region", getRegion())
            .append("longitude", getLongitude())
            .append("latitude", getLatitude())
            .append("productCategory", getProductCategory())
            .append("productName", getProductName())
            .append("noteText", getNoteText())
            .append("createTime", getCreateTime())
            .append("dbUserId", getDbUserId())
            .append("nickName", getNickName())
            .toString();
    }
}
