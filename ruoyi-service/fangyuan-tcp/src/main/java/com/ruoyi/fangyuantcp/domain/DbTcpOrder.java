package com.ruoyi.fangyuantcp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 操作记录对象 db_tcp_order
 *
 * @author fangyuan
 * @date 2020-09-07
 */
@ApiModel
public class DbTcpOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long tcpOrderId;

    /** 心跳名称唯一标识 */
    @Excel(name = "心跳名称唯一标识")
    @ApiModelProperty(value = "心跳名称唯一标识")
    private String heartName;

    /** 返回的代码 */
    @Excel(name = "返回的代码")
    @ApiModelProperty(value = "返回的代码")
    private String code;

    /** 发送内容 */
    @Excel(name = "发送内容")
    @ApiModelProperty(value = "发送内容")
    private String text;

    /** 是否返回 */
    @Excel(name = "是否返回")
    @ApiModelProperty(value = "是否返回")
    private Integer results;

    /** 用时单位毫秒 */
    @Excel(name = "用时单位毫秒")
    @ApiModelProperty(value = "用时单位毫秒")
    private Long whenTime;

    public void setTcpOrderId(Long tcpOrderId)
    {
        this.tcpOrderId = tcpOrderId;
    }

    public Long getTcpOrderId()
    {
        return tcpOrderId;
    }
    public void setHeartName(String heartName)
    {
        this.heartName = heartName;
    }

    public String getHeartName()
    {
        return heartName;
    }
    public void setCode(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }
    public void setText(String text)
    {
        this.text = text;
    }

    public String getText()
    {
        return text;
    }
    public void setResults(Integer results)
    {
        this.results = results;
    }

    public Integer getResults()
    {
        return results;
    }
    public void setWhenTime(Long whenTime)
    {
        this.whenTime = whenTime;
    }

    public Long getWhenTime()
    {
        return whenTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("tcpOrderId", getTcpOrderId())
                .append("heartName", getHeartName())
                .append("code", getCode())
                .append("text", getText())
                .append("results", getResults())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("whenTime", getWhenTime())
                .toString();
    }
}