package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 db_function
 * 
 * @author zheng
 * @date 2021-03-02
 */
@ApiModel
public class DbFunction extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 功能表 */
    private Long id;

    /** 功能名称 */
    @Excel(name = "功能名称")
    @ApiModelProperty(value = "功能名称")
    private String functionName;

    /** 指令 */
    @Excel(name = "指令")
    @ApiModelProperty(value = "指令")
    private String commandText;

    /** 功能类型：例：卷帘包含卷帘北卷帘南 0为父级 */
    @Excel(name = "功能类型：例：卷帘包含卷帘北卷帘南 0为父级")
    @ApiModelProperty(value = "功能类型：例：卷帘包含卷帘北卷帘南 0为父级")
    private Long functionTypeId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setFunctionName(String functionName) 
    {
        this.functionName = functionName;
    }

    public String getFunctionName() 
    {
        return functionName;
    }
    public void setCommandText(String commandText) 
    {
        this.commandText = commandText;
    }

    public String getCommandText() 
    {
        return commandText;
    }
    public void setFunctionTypeId(Long functionTypeId) 
    {
        this.functionTypeId = functionTypeId;
    }

    public Long getFunctionTypeId() 
    {
        return functionTypeId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("functionName", getFunctionName())
            .append("commandText", getCommandText())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("functionTypeId", getFunctionTypeId())
            .toString();
    }
}
