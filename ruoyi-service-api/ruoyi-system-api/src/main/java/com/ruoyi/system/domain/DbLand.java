package com.ruoyi.system.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.TreeEntity;

import java.util.Date;

/**
 * 土地对象 db_land
 *
 * @author zheng
 * @date 2020-09-25
 */
@Data
public class DbLand
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long landId;

    /** 地区（省市县三级逗号分隔） */
    @Excel(name = "地区", readConverterExp = "省=市县三级逗号分隔")
    @ApiModelProperty(value = "地区",required = true)
    private String region;

    /** 经度 */
    @Excel(name = "经度")
    @ApiModelProperty(value = "经度",required = true)
    private String longitude;

    /** 纬度 */
    @Excel(name = "纬度")
    @ApiModelProperty(value = "纬度",required = true)
    private String latitude;

    /** 产品类别 */
    @Excel(name = "产品类别")
    @ApiModelProperty(value = "产品类别",required = true)
    private String productCategory;

    /** 产品名称 */
    @Excel(name = "产品名称")
    @ApiModelProperty(value = "产品名称",required = true)
    private String productName;

    /** 备注信息 */
    @Excel(name = "备注信息")
    @ApiModelProperty(value = "备注信息",required = false)
    private String noteText;

    /** 关联用户id */
    @Excel(name = "关联用户id")
    private Long dbUserId;

    /** 详细地址 */
    @Excel(name = "详细地址")
    @ApiModelProperty(value = "详细地址",required = true)
    private String address;

    /** 创建时间 */
    @Excel(name = "创建时间")
    private Date createTime;

    /** 修改时间 */
    @Excel(name = "修改时间")
    private Date updateTime;

    /** 大棚面积 */
    @Excel(name = "大棚面积")
    @ApiModelProperty(value = "大棚面积",required = true)
    private String area;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /** 别称自定义 */
    @Excel(name = "别称自定义")
    @ApiModelProperty(value = "别称自定义",required = true)
    private String nickName;

    /** 地区id */
    @Excel(name = "地区id")
    private Long siteId;

    /** 设备集 */
    @Excel(name = "设备集")
    private String equipmentIds;

    /** 不知道 */
    @Excel(name = "设备集")
    private String parentName;

    @Override
    public String toString() {
        return "DbLand{" +
                "landId=" + landId +
                ", region='" + region + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", productCategory='" + productCategory + '\'' +
                ", productName='" + productName + '\'' +
                ", noteText='" + noteText + '\'' +
                ", dbUserId=" + dbUserId +
                ", address='" + address + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", area=" + area +
                ", nickName='" + nickName + '\'' +
                ", siteId=" + siteId +
                ", equipmentIds='" + equipmentIds + '\'' +
                '}';
    }

}