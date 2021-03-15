package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 db_user_record
 * 
 * @author zheng
 * @date 2021-03-04
 */
@ApiModel
public class DbUserRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户网络及操作状态记录表 */
    private Long id;

    /** 用户操作 */
    @Excel(name = "用户操作")
    @ApiModelProperty(value = "用户操作")
    private String operate;

    /** 网络状态 */
    @Excel(name = "网络状态")
    @ApiModelProperty(value = "网络状态")
    private String networkState;

    /** ip */
    @Excel(name = "ip")
    @ApiModelProperty(value = "ip")
    private String ip;

    /** 网络地址 */
    @Excel(name = "网络地址")
    @ApiModelProperty(value = "网络地址")
    private String ipAddress;

    /** 手机型号 */
    @Excel(name = "手机型号")
    @ApiModelProperty(value = "手机型号")
    private String phoneModel;

    /** 运营商 */
    @Excel(name = "运营商")
    @ApiModelProperty(value = "运营商")
    private String operator;

    /** 用户id */
    @Excel(name = "用户id")
    @ApiModelProperty(value = "用户id")
    private Long dbUserId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOperate(String operate) 
    {
        this.operate = operate;
    }

    public String getOperate() 
    {
        return operate;
    }
    public void setNetworkState(String networkState) 
    {
        this.networkState = networkState;
    }

    public String getNetworkState() 
    {
        return networkState;
    }
    public void setIp(String ip) 
    {
        this.ip = ip;
    }

    public String getIp() 
    {
        return ip;
    }
    public void setIpAddress(String ipAddress) 
    {
        this.ipAddress = ipAddress;
    }

    public String getIpAddress() 
    {
        return ipAddress;
    }
    public void setPhoneModel(String phoneModel) 
    {
        this.phoneModel = phoneModel;
    }

    public String getPhoneModel() 
    {
        return phoneModel;
    }
    public void setOperator(String operator) 
    {
        this.operator = operator;
    }

    public String getOperator() 
    {
        return operator;
    }
    public void setDbUserId(Long dbUserId) 
    {
        this.dbUserId = dbUserId;
    }

    public Long getDbUserId() 
    {
        return dbUserId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("operate", getOperate())
            .append("networkState", getNetworkState())
            .append("ip", getIp())
            .append("ipAddress", getIpAddress())
            .append("phoneModel", getPhoneModel())
            .append("operator", getOperator())
            .append("dbUserId", getDbUserId())
            .append("createTime", getCreateTime())
            .toString();
    }
}
