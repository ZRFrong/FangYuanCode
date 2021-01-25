package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 问题类型对象 db_problem_type
 * 
 * @author zheng
 * @date 2021-01-19
 */
@ApiModel
public class DbProblemType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 问题类型表 */
    private Long id;

    /** 问题类型· */
    @Excel(name = "问题类型·")
    @ApiModelProperty(value = "问题类型·")
    private String problemType;

    /** 是否删除/弃用: 0否 1是 */
    @Excel(name = "是否删除/弃用: 0否 1是")
    @ApiModelProperty(value = "是否删除/弃用: 0否 1是")
    private Integer isDel;

    /** 是否选择展示：0是 1否 */
    @Excel(name = "是否选择展示：0是 1否")
    @ApiModelProperty(value = "是否选择展示：0是 1否")
    private Integer isShow;

    /** 问题访问量统计 */
    @Excel(name = "问题访问量统计")
    @ApiModelProperty(value = "问题访问量统计")
    private Long clickNum;

    /** 保留字 */
    @Excel(name = "保留字")
    @ApiModelProperty(value = "保留字")
    private Integer type;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProblemType(String problemType) 
    {
        this.problemType = problemType;
    }

    public String getProblemType() 
    {
        return problemType;
    }
    public void setIsDel(Integer isDel) 
    {
        this.isDel = isDel;
    }

    public Integer getIsDel() 
    {
        return isDel;
    }
    public void setIsShow(Integer isShow) 
    {
        this.isShow = isShow;
    }

    public Integer getIsShow() 
    {
        return isShow;
    }
    public void setClickNum(Long clickNum) 
    {
        this.clickNum = clickNum;
    }

    public Long getClickNum() 
    {
        return clickNum;
    }
    public void setType(Integer type) 
    {
        this.type = type;
    }

    public Integer getType() 
    {
        return type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("problemType", getProblemType())
            .append("createTime", getCreateTime())
            .append("isDel", getIsDel())
            .append("isShow", getIsShow())
            .append("clickNum", getClickNum())
            .append("type", getType())
            .toString();
    }
}
