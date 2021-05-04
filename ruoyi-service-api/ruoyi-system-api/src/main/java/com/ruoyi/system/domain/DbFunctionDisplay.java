package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 页面功能显示对象 db_function_display
 * 
 * @author zheng
 * @date 2021-04-08
 */
@ApiModel
public class DbFunctionDisplay extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 功能名称 */
    @Excel(name = "功能名称")
    @ApiModelProperty(value = "功能名称")
    private String functionName;

    /** 功能来源app/小程序 */
    @Excel(name = "功能来源app/小程序")
    @ApiModelProperty(value = "功能来源app/小程序")
    private String functionSource;

    /** 备注说明 */
    @Excel(name = "备注说明")
    @ApiModelProperty(value = "备注说明")
    private String noteText;

    /** 功能标识 */
    @Excel(name = "功能标识")
    @ApiModelProperty(value = "功能标识")
    private String standbyOne;

    /** 备用2 */
    @Excel(name = "备用2")
    @ApiModelProperty(value = "备用2")
    private String standbyTwo;

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
    public void setFunctionSource(String functionSource) 
    {
        this.functionSource = functionSource;
    }

    public String getFunctionSource() 
    {
        return functionSource;
    }
    public void setNoteText(String noteText) 
    {
        this.noteText = noteText;
    }

    public String getNoteText() 
    {
        return noteText;
    }
    public void setStandbyOne(String standbyOne) 
    {
        this.standbyOne = standbyOne;
    }

    public String getStandbyOne() 
    {
        return standbyOne;
    }
    public void setStandbyTwo(String standbyTwo) 
    {
        this.standbyTwo = standbyTwo;
    }

    public String getStandbyTwo() 
    {
        return standbyTwo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("functionName", getFunctionName())
            .append("functionSource", getFunctionSource())
            .append("noteText", getNoteText())
            .append("createTime", getCreateTime())
            .append("standbyOne", getStandbyOne())
            .append("standbyTwo", getStandbyTwo())
            .toString();
    }
}
