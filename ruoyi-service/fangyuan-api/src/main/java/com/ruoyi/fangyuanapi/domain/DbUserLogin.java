package com.ruoyi.fangyuanapi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 登录日志对象 db_user_login
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
public class DbUserLogin extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 登录日志ID */
    private Long id;

    /** 登录人id */
    @Excel(name = "登录人id")
    private Long loginId;

    /** 登录渠道 0：小程序 1：APP */
    @Excel(name = "登录渠道 0：小程序 1：APP")
    private Integer loginFrom;

    /** 登录时间 */
    @Excel(name = "登录时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date loginTime;

    /** 登录ip */
    @Excel(name = "登录ip")
    private String loginIp;

    /** 登录地址 */
    @Excel(name = "登录地址")
    private String locationInfo;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setLoginId(Long loginId) 
    {
        this.loginId = loginId;
    }

    public Long getLoginId() 
    {
        return loginId;
    }
    public void setLoginFrom(Integer loginFrom) 
    {
        this.loginFrom = loginFrom;
    }

    public Integer getLoginFrom() 
    {
        return loginFrom;
    }
    public void setLoginTime(Date loginTime) 
    {
        this.loginTime = loginTime;
    }

    public Date getLoginTime() 
    {
        return loginTime;
    }
    public void setLoginIp(String loginIp) 
    {
        this.loginIp = loginIp;
    }

    public String getLoginIp() 
    {
        return loginIp;
    }
    public void setLocationInfo(String locationInfo) 
    {
        this.locationInfo = locationInfo;
    }

    public String getLocationInfo() 
    {
        return locationInfo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("loginId", getLoginId())
            .append("loginFrom", getLoginFrom())
            .append("loginTime", getLoginTime())
            .append("loginIp", getLoginIp())
            .append("locationInfo", getLocationInfo())
            .toString();
    }
}
