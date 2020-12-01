package com.ruoyi.system.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 状态记录对象 db_state_records
 *
 * @author 正
 * @date 2020-09-23
 */
@ApiModel
public class DbStateRecords
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long stateRecordsId;

    /** 心跳名称+设备号 */
    @Excel(name = "心跳名称+设备号")
    @ApiModelProperty(value = "心跳名称+设备号")
    private String codeOnly;

    @ApiModelProperty(value = "状态值")
    private DbTcpType type;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public DbTcpType getType() {
        return type;
    }

    public void setType(DbTcpType type) {
        this.type = type;
    }

    /** 时间 */

    @Excel(name = "时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "时间")
    private Date demandTime;

    /** 状态表typejson字串 */
    @Excel(name = "状态表typejson字串")
    @ApiModelProperty(value = "状态表typejson字串")
    private String stateJson;

    public void setStateRecordsId(Long stateRecordsId)
    {
        this.stateRecordsId = stateRecordsId;
    }

    public Long getStateRecordsId()
    {
        return stateRecordsId;
    }
    public void setCodeOnly(String codeOnly)
    {
        this.codeOnly = codeOnly;
    }

    public String getCodeOnly()
    {
        return codeOnly;
    }
    public void setDemandTime(Date demandTime)
    {
        this.demandTime = demandTime;
    }

    public Date getDemandTime()
    {
        return demandTime;
    }
    public void setStateJson(String stateJson)
    {
        this.stateJson = stateJson;
    }

    public String getStateJson()
    {
        return stateJson;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("stateRecordsId", getStateRecordsId())
                .append("codeOnly", getCodeOnly())
                .append("demandTime", getDemandTime())
                .append("stateJson", getStateJson())
                .toString();
    }


}

