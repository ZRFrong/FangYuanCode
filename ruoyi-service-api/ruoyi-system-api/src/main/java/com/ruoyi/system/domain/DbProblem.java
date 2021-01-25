package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 db_problem
 * 
 * @author zheng
 * @date 2021-01-18
 */
@ApiModel
public class DbProblem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 问题表id */
    private Long id;

    /** 问题 */
    @Excel(name = "问题")
    @ApiModelProperty(value = "问题")
    private String problemText;

    /** 图片 */
    @Excel(name = "图片")
    @ApiModelProperty(value = "图片")
    private String images;

    /** 问题回应 */
    @Excel(name = "问题回应")
    @ApiModelProperty(value = "问题回应")
    private String answerText;

    /** 是否删除：0否 1是 */
    @Excel(name = "是否删除：0否 1是")
    @ApiModelProperty(value = "是否删除：0否 1是")
    private Integer isDel;

    /** 保留字 */
    @Excel(name = "保留字")
    @ApiModelProperty(value = "保留字")
    private Integer problemType;

    /** 问题热度：此为排序规则 */
    @Excel(name = "问题热度：此为排序规则")
    @ApiModelProperty(value = "问题热度：此为排序规则")
    private Long problemHot;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProblemText(String problemText) 
    {
        this.problemText = problemText;
    }

    public String getProblemText() 
    {
        return problemText;
    }
    public void setImages(String images) 
    {
        this.images = images;
    }

    public String getImages() 
    {
        return images;
    }
    public void setAnswerText(String answerText) 
    {
        this.answerText = answerText;
    }

    public String getAnswerText() 
    {
        return answerText;
    }
    public void setIsDel(Integer isDel) 
    {
        this.isDel = isDel;
    }

    public Integer getIsDel() 
    {
        return isDel;
    }
    public void setProblemType(Integer problemType) 
    {
        this.problemType = problemType;
    }

    public Integer getProblemType() 
    {
        return problemType;
    }
    public void setProblemHot(Long problemHot) 
    {
        this.problemHot = problemHot;
    }

    public Long getProblemHot() 
    {
        return problemHot;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("problemText", getProblemText())
            .append("images", getImages())
            .append("answerText", getAnswerText())
            .append("createTime", getCreateTime())
            .append("isDel", getIsDel())
            .append("problemType", getProblemType())
            .append("problemHot", getProblemHot())
            .toString();
    }
}
